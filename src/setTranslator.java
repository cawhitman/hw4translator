import java.util.*;
import java.io.*;
/*

A template for the translator part of the project.

You will need to add more static variables and also methods
for each of the grammar variables, and modify the program method below.


*/
public class setTranslator{

    private static setScanner sc;
    private static PrintStream dest;

    private static void program(){
        // attempts the translation of the source file
        // into a Java source file.
    }


    // you should not need to modify the main method
    public static void main(String[] args) throws Exception{

        if (args.length == 0)
            sc = new setScanner(new Scanner(System.in));
        else
            sc = new setScanner(new Scanner(new File(args[0])));

        Token currTok = sc.lookahead();

        // adding a test for null so I can compile and run this
        if (currTok != null && currTok.getTokenType() == Token.PROGRAM)
            program();
        else
            throw new Exception("\"program\" expected.");

        // add a comment  indicating a successful parse
        dest.println("\n// Parsing completed successfully.");
        dest.close();


    }
}

/**
 *
 * NAT
 *
 * if (lookahead is not one of NAT, BEGIN, SET){
    throw an exception with message "\"nat\", \"set\", or \"begin\" expected.";
     else
     natDec() ;

     if (lookahead is not one of the members of the union of the rhs's
     of V)
     throw an exception with the message "... expected." where ...
     lists the TOKEN_LABELS string for the tokens of that union;
     the labels put double quotes around the reserved words
     else
     V();

     The actual code for natDec() would go something like

     if (lookahead = NAT){
     consume();
     if (lookahead is not ID)
     throw exception with message "identifier expected.";
     else
     neVarList();
     if (lookahead = SEMICOLON)
     consume();
     else
     throw an exception with message
     "semicolon expected."
     }
     else // no nat declarations
     return;
     }

 Pick a couple to do

 PROGRAM = 0, //  "program"  a reserved word
 ID = 1, // [a-zA-Z]+[a-zA-Z0-9]*
 VAR = 2, // "var"      a reserved word
 BEGIN = 3, // "begin"    a reserved word
 END = 4, // "end"      a reserved word
 IF = 5, //  "if"       a reserved word
 ELSE = 6, // "else"     a reserved word
 ENDIF = 7, // "endif"    a reserved word
 NAT = 8, //  "nat"      a reserved word
 SET = 9, // "set"      a reserved word
 NATCONST = 10, // 0|[1-9][0-9]*
 LEFTBRACE = 11,    // '{'
 RIGHTBRACE = 12,    // '}'
 LEFTPAREN = 13,    // '('
 RIGHTPAREN = 14,    // ')'
 SEMICOLON = 15,    // ';'
 PERIOD  =  16,    // '.'
 COMMA =  17,    // ','
 ASSIGN = 18, //  ":="  for assignment
 SUBSET = 19, //  "<="  for is subset of
 EQUALS = 20,  // '='   for equality comparisons of sets
 NOT =  21, //  "not" for boolean negation; so "not" is reserved
 INTERSECTION = 22 ,  // '*'   for set intersection
 UNION = 23,  // '+'   for set union
 SETDIFFERENCE = 24, // '\'   for binary set difference
 COMPLEMENT = 25,  // '-'   for unary set complement
 IS_IN  = 26, //  "in"  for set membership; so "in" is reserved
 THEN = 27, // "then" a reserved word
 CMP = 28, // "CMP" a reserved word
 EOF = 29, // to stand for reaching the end of the file
 UNRECOGNIZED = 30; // for anything else
 **/

