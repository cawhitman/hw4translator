import java.util.HashMap;
import java.util.regex.Pattern;

/* tokenDecode takes the string value of a token and returns the token ID, or takes the token ID and returns
 * the token character(s).
 */
public class tokenDecode {

	private HashMap<Integer, String> key = new HashMap<Integer, String>();

	public tokenDecode(){
		key.put(0,"program");
		key.put(2, "var");
		key.put(3, "begin");
		key.put(4, "end");
		key.put(5, "if");
		key.put(6, "else");
		key.put(7, "endif");
		key.put(8, "nat");
		key.put(9, "set");
		key.put(11, "{");
		key.put(12, "}");
		key.put(13, "(");
		key.put(14, ")");
		key.put(15, ";");
		key.put(16, ".");
		key.put(17, ",");
		key.put(18, ":=");
		key.put(19, "<=");
		key.put(20, "=");
		key.put(21, "not");
		key.put(22, "*");
		key.put(23, "+");
		key.put(24, "\\");
		key.put(25, "-");
		key.put(26, "in");
		key.put(27, "then");
		key.put(28, "CMP");
		key.put(29, "eof");
	}
	
	

	public int getID (String phrase){
		for(int i : key.keySet()){
			if(key.get(i).equals(phrase)){
				return i; //return the first found
			}
		}
		if (Pattern.matches("[a-zA-Z]+[a-zA-Z0-9]*",phrase))
				return 1;
		if (Pattern.matches("0|[1-9][0-9]*",phrase))
			return 10;
		return 30;
	}

	public String getType(int id) throws Exception {
		if (id<0 || id>30)
			throw new Exception("Invalid token ID");
		if (key.containsKey(id))
			return key.get(id);
		if (id==1)
			return "id";
		if (id==10)
			return "natconst";
		return "unrecognized";
	}


	public static void main (String[] args) throws Exception{

		tokenDecode decoder = new tokenDecode();
		System.out.println(decoder.getID("var"));
		System.out.println(decoder.getType(31));
		Token token = new Token("program",1);
		System.out.println(token.getTokenType());
	}

}
