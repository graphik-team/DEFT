package fr.lirmm.graphik.DEFT.dialectical_tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.lirmm.graphik.DEFT.core.DefeasibleKB;
import fr.lirmm.graphik.DEFT.dialectical_tree.DialecticalTree.Node;
import fr.lirmm.graphik.DEFT.gad.Derivation;
import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.AtomSet;
import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.core.InMemoryAtomSet;
import fr.lirmm.graphik.graal.api.core.Rule;
import fr.lirmm.graphik.graal.api.core.Substitution;
import fr.lirmm.graphik.graal.api.forward_chaining.ChaseException;
import fr.lirmm.graphik.graal.api.forward_chaining.RuleApplicationException;
import fr.lirmm.graphik.graal.api.homomorphism.HomomorphismException;
import fr.lirmm.graphik.graal.api.homomorphism.HomomorphismFactoryException;
import fr.lirmm.graphik.graal.core.DefaultConjunctiveQuery;
import fr.lirmm.graphik.graal.core.atomset.graph.DefaultInMemoryGraphAtomSet;
import fr.lirmm.graphik.graal.homomorphism.StaticHomomorphism;
import fr.lirmm.graphik.util.stream.CloseableIterator;
import fr.lirmm.graphik.util.stream.GIterator;

public class ArgumentationFramework {
	public DefeasibleKB kb;
	private ArgumentPreference preferenceFunction;
	
	public ArgumentationFramework(DefeasibleKB kb, ArgumentPreference pref) {
		this.kb = kb;
		this.preferenceFunction = pref;
	}

	public void setPreferenceFunction(ArgumentPreference pref) {
		this.preferenceFunction = pref;
	}
	
	public LinkedList<Argument> getAttackersFor(Argument arg) throws AtomSetException, HomomorphismException, HomomorphismFactoryException, RuleApplicationException, ChaseException {
		LinkedList<Argument> attackers = new LinkedList<Argument>();
		AtomSet supportAtoms = arg.support.getAtoms();
		
		for(Rule r : kb.negativeConstraintSet) {
			GIterator<Atom> ncIt = r.getBody().iterator();
			// We suppose that the first atom of the negative constraint is used in the support, so the second atom is an attacker
			Atom supportAtom = ncIt.next();
			Atom attackerAtom = ncIt.next();
			
			
			// Test if the first Atom of the NC is contained in the support
			InMemoryAtomSet atomSettified = new DefaultInMemoryGraphAtomSet();
			atomSettified.add(supportAtom);			
			DefaultConjunctiveQuery query = new DefaultConjunctiveQuery(atomSettified);
			
			CloseableIterator<Substitution> substitutions = StaticHomomorphism.instance().execute(query, supportAtoms);
			
			if(!substitutions.hasNext()) {
				// Test if the second Atom of the NC is contained in the support
				Atom tmp = attackerAtom;
				attackerAtom = supportAtom;
				supportAtom = attackerAtom;
				query = new DefaultConjunctiveQuery(atomSettified);
				
				substitutions = StaticHomomorphism.instance().execute(query, supportAtoms);
			}
			
			while(substitutions.hasNext()) {
				Substitution sub = substitutions.next();
				//Atom groundedSupportAtom = sub.createImageOf(supportAtom);
				Atom halfGroundAttackerAtom = sub.createImageOf(attackerAtom); // some variables might still be unground if they didn't show up in the support
				
				// Find all possible substitutions for AttackerAtom
				InMemoryAtomSet halfGroundAttackerAtomSet = new DefaultInMemoryGraphAtomSet();
				halfGroundAttackerAtomSet.add(halfGroundAttackerAtom);			
				DefaultConjunctiveQuery attackerQuery = new DefaultConjunctiveQuery(halfGroundAttackerAtomSet);
				
				CloseableIterator<Substitution> attackerSubstitutions = StaticHomomorphism.instance().execute(attackerQuery, kb.facts);
				
				while(attackerSubstitutions.hasNext()) {
					Substitution attackerSub = attackerSubstitutions.next();
					Atom groundedAttackerAtom = attackerSub.createImageOf(halfGroundAttackerAtom);
					attackers.addAll(this.getArgumentsFor(groundedAttackerAtom));
				}
			}
		}
		return attackers;
	}
	
	
	public LinkedList<Defeater> getDefeatersFor(Argument arg) throws AtomSetException, HomomorphismException, HomomorphismFactoryException, RuleApplicationException, ChaseException {
		LinkedList<Defeater> defeaters = new LinkedList<Defeater>();
		
		LinkedList<Argument> attackers = this.getAttackersFor(arg);
		
		for(Argument attacker : attackers) {
			int attackStatus = this.preferenceFunction.compare(attacker, arg);
			if(attackStatus != ArgumentPreference.NOT_DEFEAT) {
				defeaters.add(new Defeater(attacker, attackStatus));
			}
		}
		
		return defeaters;
	}
	
	public boolean isDefeated(Argument arg) throws AtomSetException, HomomorphismException, HomomorphismFactoryException, RuleApplicationException, ChaseException {
		DialecticalTree tree = computeDialecticalTreeFor(arg);
		boolean defeated = false;
		for(Node defeater : tree.defeaters) {
			if(defeater.getLabel() == DialecticalTree.UNDEFEATED) {
				defeated = true;
				break;
			}
		}
		
		return defeated;
	}
	public DialecticalTree computeDialecticalTreeFor(Argument arg) throws AtomSetException, HomomorphismException, HomomorphismFactoryException, RuleApplicationException, ChaseException {
		
		DialecticalTree tree = new DialecticalTree(arg, this.getDefeatersFor(arg));
		
		boolean defeated = false;
		Iterator<Node> it = tree.defeaters.iterator();
		while(it.hasNext() && !defeated) {
			Node defeater = it.next();
			this.computeTree(defeater);
			// label the tree
			this.labelTree(defeater);
			if(defeater.getLabel() == DialecticalTree.UNDEFEATED) {
				defeated = true;
			}
		}
		
		return tree;
	}
	
	private void computeTree(Node n) throws AtomSetException, HomomorphismException, HomomorphismFactoryException, RuleApplicationException, ChaseException {
		List<Defeater> defeaters = this.getDefeatersFor(n.getData().argument);
		
		if(defeaters.isEmpty()) {
			return;
		}
		
		for(Defeater defeater: defeaters) {
			// If this node is a blocking defeater, then it cannot be defeated with another blocking defeater!
			if(n.getData().defeatType == ArgumentPreference.BLOCKING_DEFEAT 
					&& defeater.defeatType == ArgumentPreference.BLOCKING_DEFEAT) {
				continue;
			} else {
				Node child = new Node(defeater);
				this.computeTree(child);
				n.addDefeater(child);
			}
		}
	}
	
	private void labelTree(Node n) {
		List<Node> children = n.getChildren();
		if(null == children || children.isEmpty()) {
			n.setLabel(DialecticalTree.UNDEFEATED);
			return;
		} 
		boolean defeated = false;
		Iterator<Node> it = children.iterator();
		while(it.hasNext() && !defeated) {
			Node child = it.next();
			if(child.getLabel() == DialecticalTree.NOT_LABELED) {
				this.labelTree(child);
			}
			if(child.getLabel() == DialecticalTree.UNDEFEATED) {
				defeated = true;
			}
		}
		if(defeated) {
			n.setLabel(DialecticalTree.DEFEATED);
		} else {
			n.setLabel(DialecticalTree.UNDEFEATED);
		}
	}
	
	
	public LinkedList<Argument> getArgumentsFor(Atom atom) throws HomomorphismException, HomomorphismFactoryException, RuleApplicationException, AtomSetException, ChaseException {
		LinkedList<Argument> arguments = new LinkedList<Argument>();
		LinkedList<Derivation> derivations = this.kb.getDerivationsFor(atom);
		
		for(Derivation d : derivations) {
			arguments.add(new Argument(d, atom));
		}
		return arguments;
	}
	
}