import java.util.*;
import java.io.*;
/*
NEEDS METHOD FOR EXCEPTIONS
NEEDS IMPROVED METHOD FOR BOOLEAN STATEMENTS
NEEDS EXPRESSION HANDLER (PRINT EXPRESSION AFTER END)

A template for the translator part of the project.

You will need to add more static variables and also methods
for each of the grammar variables, and modify the program method below.


 */
public class setTranslator{

	private static setScanner sc;
	private static PrintStream dest;
	private static ArrayList <String> nat, set;
	
	//done
	private static void setHeader() throws Exception{
		Token current = getNextToken();
		if (current.getTokenType()!=Token.ID)
			throw new Exception("\"id\" expected.");
		String title = current.getTokenString();
		dest = new PrintStream(String.format("%s.java", title));
		dest.printf("public class %s{\r\n\r\n", title);
	}

	//done
	private static void close(){
		dest.println("}");
		dest.close();
	}

	//done
	private static void natSetHandler() throws Exception{
		Token current = getNextToken();
		ArrayList <String> curVar;
		int tokType = current.getTokenType();
		if (tokType!=Token.NAT || tokType!=Token.SET)
			return;
		if (tokType==Token.NAT){
			dest.print("private static int ");
			curVar=nat;
		}
		else{
			dest.print("private static CofinFin ");
			curVar=set;
		}
		current = getNextToken();
		if (current.getTokenType()!=Token.ID)
			throw new Exception("\"id\" expected.");
		dest.print(current.getTokenString());
		current = getNextToken();
		while (current.getTokenType()==Token.COMMA){
			current = getNextToken();
			String tokString = current.getTokenString();
			if (current.getTokenType()!=Token.ID)
				throw new Exception("\"id\" expected.");
			if (set.contains(tokString)||nat.contains(tokString))
				throw new Exception(String.format("Duplicate variable \"%s\" used.", tokString));
			dest.printf(", %s", tokString);
			curVar.add(tokString);
			current = getNextToken();
		}
		dest.println(";");
		natSetHandler();
	}

	//done
	private static void varHandler() throws Exception{
		Token current = getNextToken();
		nat = new ArrayList<String>();
		set = new ArrayList<String>();
		int tokType = current.getTokenType();
		if (tokType!=Token.VAR)
			throw new Exception("\"var\" expected.");
		natSetHandler();
	}

	//done
	public static void beginHandler() throws Exception{
		int origin = sc.lookahead().getTokenType();
		if (origin != Token.BEGIN)
			throw new Exception("\"begin\" expected.");
		dest.println("public static void main(String[] args){");
		statementHandler();
	}

	//done
	private static void isInHandler() throws Exception{
		String number = sc.lookahead().getTokenString();
		getNextToken();
		cofinFinHandler();
		dest.printf(".isIn(%s)",number);
	}

	//done
	private static void cofinFinHandler() throws Exception{
		Token current = sc.lookahead();
		int tokType = current.getTokenType();
		if (tokType!=Token.ID && tokType != Token.COMPLEMENT && tokType!=Token.CMP && tokType!=Token.LEFTBRACE){
			throw new Exception("set value expected.");
		}
		if (tokType==Token.ID){
			String tokString = current.getTokenString();
			if (!set.contains(tokString)){
				if (!nat.contains(tokString))
					throw new Exception(String.format("Undefined variable \"%s\" used.", tokString));
				throw new Exception("Type mismatch exception.");
			}
			dest.print(tokString);
		}
		else if (tokType==Token.COMPLEMENT){
			complementHandler();
		}
		else{
			cofinFinBuilder();
		}
	}

	//done
	private static void complementHandler() throws Exception{
		getNextToken();
		cofinFinHandler();
		dest.print(".complement()");
	}

	//done
	private static void cofinFinBuilder() throws Exception{
		boolean comp = sc.lookahead().getTokenType()==Token.CMP;
		Token current = getNextToken();
		if (comp){
			if (current.getTokenType()!=Token.LEFTBRACE)
				throw new Exception("\"{\" expected.");
			current = getNextToken();
		}
		dest.printf("new CofinFin(%b,new int[]{",comp);
		if (current.getTokenType()==Token.RIGHTBRACE){
			dest.print("})");
			return;
		}
		if (current.getTokenType()!=Token.NATCONST && current.getTokenType()!=Token.ID){
			throw new Exception("invalid set member.");
		}
		if (current.getTokenType()==Token.ID && !nat.contains(current.getTokenString())){
			if (!set.contains(current.getTokenString()))
				throw new Exception(String.format("Undefined variable \"%s\" used.", current.getTokenString()));
			throw new Exception("Type mismatch exception.");
		}
		dest.print(current.getTokenString());
		current=getNextToken();
		while (current.getTokenType()!=Token.RIGHTBRACE){
			if (current.getTokenType()!=Token.NATCONST && current.getTokenType()!=Token.ID){
				throw new Exception("invalid set member.");
			}
			if (current.getTokenType()==Token.ID && !nat.contains(current.getTokenString())){
				if (!set.contains(current.getTokenString()))
					throw new Exception(String.format("Undefined variable \"%s\" used.", current.getTokenString()));
				throw new Exception("Type mismatch exception.");
			}
			dest.printf(", %s", current.getTokenString());
			current=getNextToken();
		}
		dest.print("})");
	}

	//done
	private static void subsetHandler() throws Exception{
		dest.print(".isSubsetOf(");
		getNextToken();
		cofinFinHandler();
		dest.print(")");
	}

	//done
	private static void setEqualsHandler() throws Exception{
		dest.print(".equals(");
		getNextToken();
		cofinFinHandler();
		dest.print(")");
	}

	//bulky. redo? not done
	private static void booleanBuilder() throws Exception{
		Token current = getNextToken();
		if (current.getTokenType()==Token.THEN)
			throw new Exception("boolean value expected.");
		while (current.getTokenType()!=Token.THEN){
			int tokType = current.getTokenType();
			if (tokType==Token.NOT){
				dest.print("!");
			}
			else if (tokType == Token.ID){
				String tokString = current.getTokenString();
				if (nat.contains(tokString)){
					current = getNextToken();
					if (current.getTokenType()!=Token.IS_IN)
						throw new Exception("\"in\" expected.");
					isInHandler();
				}
				else if (set.contains(tokString)){
					dest.print(current.getTokenString());
				}
				else{
					throw new Exception(String.format("Undefined variable \"%s\" used.", tokString));
				}
			}
			else if(tokType == Token.NATCONST){
				isInHandler();
			}
			else if (tokType == Token.COMPLEMENT || tokType==Token.CMP || tokType==Token.LEFTBRACE){
				cofinFinHandler();
			}
			else if (tokType == Token.SUBSET){
				subsetHandler();
			}
			else if (tokType == Token.SETDIFFERENCE || tokType == Token.UNION || tokType == Token.INTERSECTION){
				setOpsHandler();
			}
			else if (tokType==Token.EQUALS){
				setEqualsHandler();
			}
			current=getNextToken();
		}
	}

	//done
	private static void ifHandler() throws Exception{
		dest.print("if (");
		booleanBuilder();
		dest.println("){");
		statementHandler();
		dest.println("}");
	}

	//done
	private static void natAssign() throws Exception{
		Token current = getNextToken();
		if (current.getTokenType()!=Token.ID && current.getTokenType()!=Token.NATCONST)
			throw new Exception("Type Mismatch");
		dest.printf("%s;\r\n",current.getTokenString());
	}
	
	private static void setAssign() throws Exception{
		Token current = getNextToken();
		if (current.getTokenType()!=Token.ID && current.getTokenType()!=Token.NATCONST)
			throw new Exception("Type Mismatch");
		dest.printf("%s;\r\n",current.getTokenString());
	}
	
	private static void assignmentHandler() throws Exception{
		Token current = sc.lookahead();
		dest.print(current.getTokenString());
		current = getNextToken();
		if (current.getTokenType()!=Token.ASSIGN)
			throw new Exception("\":=\" expected.");
		dest.print(" = ");
		if (nat.contains(current.getTokenString()))
			natAssign();
		else if (set.contains(current.getTokenString()))
			setAssign();
		else
			throw new Exception(String.format("\"%s\" is an undeclared variable.",current.getTokenString()));
	}
	
	private static void statementHandler() throws Exception{
		int origin = sc.lookahead().getTokenType();
		Token current = getNextToken();
		while (!current.isTerminator(origin)){
			if (current.getTokenType()==Token.ID){
				assignmentHandler();
			}
			if (current.getTokenType()==Token.IF){
				ifHandler();
			}
			if (current.getTokenType()==Token.ELSE){
				dest.print("else {");
				statementHandler();
				dest.println("}");
			}
			if (current.getTokenType()==Token.UNRECOGNIZED){
				dest.println(current.getTokenString());
			}
			current = getNextToken();
		}
	}

	private static void program() throws Exception{
		setHeader();
		varHandler();
		beginHandler();
		setExpressionHandler();
		close();
	}

	//done
	private static Token getNextToken(){
		sc.consume();
		return sc.lookahead();
	}
	
	
	// you should not need to modify the main method
	public static void main(String[] args) throws Exception{

		if (args.length == 0)
			sc = new setScanner(new Scanner(System.in));
		else
			sc = new setScanner(new Scanner(new File(args[0])));

		Token currTok = getNextToken();

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
