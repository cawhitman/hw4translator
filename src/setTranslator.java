import java.util.*;
import java.io.*;
/*

A template for the translator part of the project.

You will need to add more static variables and also methods
for each of the grammar variables, and modify the program method below.


*/
public class setTranslator{
    /*For the purpose of generating temporaries, it is convenient to use
    the following*/
    private static String setTempPrefix = "$sv";
    private static String natArrTempPrefix = "$iv";
    private static int
            nextNatArrTempSuffix,
            usedSetTemps,// tracks the set temp variables that ard currently in use
            decSetTemps; // tracks the set temp variables that have been
                        // declared in the current scope;
                        // discussed below; generally, usedSetTemps <= decSetTemps


    private static String setConstResultVariable;
    private static String setComplementedLiteralResultVariable;
    private static String setLiteralResultVariable;

    private static Map<String,Token> natMap = new TreeMap<>();

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

        if (sc.lookahead().getTokenType()!= 8 || sc.lookahead().getTokenType()!= 9 || sc.lookahead().getTokenType()!= 3)
            throw new Exception("\"nat\", \"set\", or \"begin\" expected.");
        else
            natDec();

        // add a comment  indicating a successful parse
        dest.println("\n// Parsing completed successfully.");
        dest.close();

    }
    public static void asgn(){ // assumes lookahead is ID
        Token id = sc.lookahead(); // DO NOT CONSUME

        if (!natMap.containsKey(id.getTokenString()))
            throw new Error("Identifier not declared.");
        else  if (natMap.get(id.getTokenString()).equals("NAT")) {
            //natAsgn();
        }else{
            // the only other alternative is declared as a set
            //setAsgn();
        }

    }

    //emitting code
    public void neStList(){
//        st();  // should emit all the code needed for the statement
//        while (lookahead = SEMICOLON){
//            consume the SEMICOLON
//            if (lookahead is ID or IF)
//            st();
//            else
//            throw an exception with the message
//            "identifier or if expected."
//        }
    }

    public void neVarList(){
//        get ID and consume it out of the token stream;
//        attempt to install ID in symbol table
//        and emit the Java declaration for it, but
//        check if it's a duplicate and throw an
//        exception if there is a problem.
//
//        while (lookahead = COMMA){
//            consume the COMMA out of the token stream;
//            if (next token is ID){
//                get ID and consume it out of the token stream;
//                attempt to install ID in symbol table
//                and generate the Java declaration for it,
//                        throwing an exception if there is a problem;
//            }
//            else
//            throw an exception with the message
//            "Identifier expected."
//        }
    }

    public static void natDec(){
//        if (lookahead = NAT){
//            consume();
//            if (lookahead is not ID)
//            throw exception with message "identifier expected.";
//            else
//            neVarList();
//            if (lookahead = SEMICOLON)
//                consume();
//            else
//                throw an exception with message
//            "semicolon expected."
//        }
//        else // no nat declarations
//            return;
    }


    /*to obtain fresh temp variables.  Because ID variables must begin with
       a letter, there is no chance for a name clash in the resulting Java file.*/
    public String nextTemp(boolean isSet) {

        if (isSet) {
            return setTempPrefix + (++decSetTemps);
        }
        //else
        //return natArrTempPrefix + (++natArrTempSuffix);
        //}
        return "remove this";
    }

    public void setAsgn(){
//        get ID and consume it; suppose id is its tokenString;
//        if it is not declared as a set variable, then throw
//                an exception with the message id + " not declared as a set variable."
//
//        if lookahead is not ASSIGN then
//        throw an exception with the message "assignment operator(:=),  expected."
//        else{
//            consume the lookahead;
//            if (lookahead is not one of CMP, LEFTBRACE, ID, LEFTPAREN,
//                    COMPLEMENT)
//            throw an exception with the message that lists them out as
//            expected tokens, using TOKEN_LABELS
//            else{
//                setExp();
//                emit code id + " = " + setExpResultVariable + ";\n" to
//                the output file;
//            }
//        }
    }

//    Note, the lookahead set for <set exp> is
//            CMP, LEFTBRACE, ID, LEFTPAREN, COMPLEMENT
//
//    and we assume that has been tested already.
//
//    As with <ne st list> above, the whole <set exp> productions
//    can be reworked to
//
//    <set level 2> (SETDIFFERENCE <set level 2>) *

    public void setExp(){
//        boolean needANewTemp;
//        String res; // string identifying the variable holding the result
//        setLevel2();
//        Token tk = lookahead;
//        int tkN = tk.getTokenId();
//        if (slevel2ResultVar.charAt(0) == '$'  || tkN != Token.SETDIFFERENCE)
//            res = slevel2ResultVar;
//        else{
//            // the result is a program variable and we will need to perform an op
//
//            needANewTemp = usedSetTemps == decSetTemps;
//
//            if (needANewTemp){
//                res = nextTemp(true);
//                usedSetTemps++;
//            }
//            else
//                res = setVarTempPrefix + (++usedSetTemps);
//
//            // if it's new, we have to declare it;
//            emit code ((needANewTemp? "CofinFin " : "") + res + " = " + slevel2ResultVar + ';');
//        }
//
//
//        while (tkN = Token.SETDIFFERENCE){
//            lex.consume();
//            tk = lookahead;
//            tkN = tk.getTokenType();
//            if (tkN != Token.CMP && tkN != Token.LEFTBRACE
//                    && tkN != Token.ID && tkN != Token.LEFTPAREN &&
//                    tkN != Token.COMPLEMENT)
//                throw exception with message listing the expected tokens
//            else{
//                setlevel2();
//                emit code for res = res.intersect(sLevel2ResultVar.complement());
//                tk = lookahead;
//                tkN = tk.getTokenType();
//            }
//
//        }
//        setExpResultVariable = res;
    }


//    ID
//    LEFTBRACE, CMP
//    LEFTPAREN
//
//    so the code for setAtomic() is, assuming the lookahead is one of those,

    public void setAtomic(){
//        if (lookahead == ID){
//            let str be ID's string
//            if (ID is declared to be of type set)
//            setatomicResultVariable = str;
//            else if (ID declared to be of type nat)
//            throw an exception with message
//            str is declared as nat, not set."
//            else
//            throw an exception with message
//            str is not declared
//        }
//        else if (lookahead == LEFTPAREN){
//            consume the LEFTPAREN;
//            if (lookahead is not one of <set exp>'s lookaheads)
//            throw the exception indicating what tokens are expected
//            else{
//                setExp();
//                if (lookahead = RIGHTPAREN){
//                    consume the RIGHTPAREN;
//                    setAtomicResultVariable = setExpResultVariable;
//                }
//                else
//                    throw an Exception with message
//                rightparen expected.
//            }
//        }
//        else{ // lookahead is either LEFTBRACE or CMP
//            setConst();
//            setAatomicResultVariable = setConstResultVariable;
//        }
    }

//    to hold the names of the temporary variables that
//    house the values.  Assuming the lookaheads are right the
//    code is roughly

    public void setConst(){
//        if (lookahead is CMP){
//            complemented();
//            setConstResultVariable = setComplementedLiteralResultVariable;
//        }
//        else{
//            setLiteral();
//            setConstResultVariable = setLiteralResultVariable;
//        }
    }

    public void complemented(){
//        consume CMP;
//        setLiteral();
//
//        emit code for
//        setLiteralResultVariable = setLiteralResultVariable.complement();
//
//        and finally
//        setComplementedLiteralResultVariable = setLiteralResultVariable;
//
//        to leave the result where it can be found.
    }

}

/**
 *


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

