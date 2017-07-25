/* Generated By:JavaCC: Do not edit this line. DLGP2ParserConstants.java */
package fr.lirmm.graphik.DEFT.io.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface DLGP2ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int STRING_START = 6;
  /** RegularExpression Id. */
  int ANY_ESCAPED_CHAR = 7;
  /** RegularExpression Id. */
  int ANY_CHAR = 8;
  /** RegularExpression Id. */
  int STRING = 9;
  /** RegularExpression Id. */
  int THREEQUOTES = 10;
  /** RegularExpression Id. */
  int ANY_ESCAPED_CHAR_IN_LONG_STRING = 12;
  /** RegularExpression Id. */
  int LONG_STRING = 13;
  /** RegularExpression Id. */
  int DIGIT = 14;
  /** RegularExpression Id. */
  int DOUBLE = 15;
  /** RegularExpression Id. */
  int EXPONENT = 16;
  /** RegularExpression Id. */
  int INTEGER = 17;
  /** RegularExpression Id. */
  int DECIMAL = 18;
  /** RegularExpression Id. */
  int TRUE = 19;
  /** RegularExpression Id. */
  int FALSE = 20;
  /** RegularExpression Id. */
  int directive = 21;
  /** RegularExpression Id. */
  int comment_line = 22;
  /** RegularExpression Id. */
  int PREFIX = 23;
  /** RegularExpression Id. */
  int BASE = 24;
  /** RegularExpression Id. */
  int TOP = 25;
  /** RegularExpression Id. */
  int UNA = 26;
  /** RegularExpression Id. */
  int SECTION_FACTS = 27;
  /** RegularExpression Id. */
  int SECTION_RULES = 28;
  /** RegularExpression Id. */
  int SECTION_CONSTRAINTS = 29;
  /** RegularExpression Id. */
  int SECTION_QUERIES = 30;
  /** RegularExpression Id. */
  int OPENPAR = 31;
  /** RegularExpression Id. */
  int CLOSEPAR = 32;
  /** RegularExpression Id. */
  int OPEN_SQUARE_BRACKET = 33;
  /** RegularExpression Id. */
  int CLOSE_SQUARE_BRACKET = 34;
  /** RegularExpression Id. */
  int COMMA = 35;
  /** RegularExpression Id. */
  int DOT = 36;
  /** RegularExpression Id. */
  int UNDERSCORE = 37;
  /** RegularExpression Id. */
  int AT = 38;
  /** RegularExpression Id. */
  int DOUBLE_CARET = 39;
  /** RegularExpression Id. */
  int U_IDENT = 40;
  /** RegularExpression Id. */
  int L_IDENT = 41;
  /** RegularExpression Id. */
  int SIMPLE_CHAR = 42;
  /** RegularExpression Id. */
  int LETTER = 43;
  /** RegularExpression Id. */
  int UPPERCASE_CHAR = 44;
  /** RegularExpression Id. */
  int LOWERCASE_CHAR = 45;
  /** RegularExpression Id. */
  int PN_LOCAL = 46;
  /** RegularExpression Id. */
  int FULLIRI = 47;
  /** RegularExpression Id. */
  int NAME_OBJECT = 48;
  /** RegularExpression Id. */
  int PNAME_NS = 49;
  /** RegularExpression Id. */
  int PNAME_LN = 50;
  /** RegularExpression Id. */
  int LANG = 51;
  /** RegularExpression Id. */
  int PN_PREFIX = 52;
  /** RegularExpression Id. */
  int PN_CHARS_BASE = 53;
  /** RegularExpression Id. */
  int PN_CHARS = 54;
  /** RegularExpression Id. */
  int PN_CHARS_U = 55;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_STRING = 1;
  /** Lexical state. */
  int IN_LONG_STRING = 2;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "\"&nbsp;\"",
    "\"\\\"\"",
    "<ANY_ESCAPED_CHAR>",
    "<ANY_CHAR>",
    "\"\\\"\"",
    "<THREEQUOTES>",
    "<token of kind 11>",
    "<ANY_ESCAPED_CHAR_IN_LONG_STRING>",
    "<LONG_STRING>",
    "<DIGIT>",
    "<DOUBLE>",
    "<EXPONENT>",
    "<INTEGER>",
    "<DECIMAL>",
    "\"true\"",
    "\"false\"",
    "<directive>",
    "<comment_line>",
    "\"@prefix\"",
    "\"@base\"",
    "\"@top\"",
    "\"@una\"",
    "\"@facts\"",
    "\"@rules\"",
    "\"@constraints\"",
    "\"@queries\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\",\"",
    "\".\"",
    "\"_\"",
    "\"@\"",
    "\"^^\"",
    "<U_IDENT>",
    "<L_IDENT>",
    "<SIMPLE_CHAR>",
    "<LETTER>",
    "<UPPERCASE_CHAR>",
    "<LOWERCASE_CHAR>",
    "<PN_LOCAL>",
    "<FULLIRI>",
    "<NAME_OBJECT>",
    "<PNAME_NS>",
    "<PNAME_LN>",
    "<LANG>",
    "<PN_PREFIX>",
    "<PN_CHARS_BASE>",
    "<PN_CHARS>",
    "<PN_CHARS_U>",
    "\">\"",
    "\"?\"",
    "\":-\"",
    "\"!\"",
    "\"=\"",
  };

}