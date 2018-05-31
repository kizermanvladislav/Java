import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
	public Main (String args[]){
		LinkedList<Tokens> Main_Tokens = new LinkedList<Tokens>();
		String test = "q = 20 + 12 * a - 6 / b";
		String X = "q";
		for (int a = 0; a < test.length(); a++){
			if (test.toCharArray() [a]== 'c'){
				continue;
			}
			X += test.toCharArray()[a];
			String Y = "42";
			if(a < test.length()-1){
                Y = X + test.toCharArray()[a+1];
            }
			for (String key: Lexer.Main_Lexer.keySet()) {

                Pattern PATTERN = Pattern.compile(Lexer.Main_Lexer.get(key));

                Matcher MATCH_1 = PATTERN.matcher(X);
                Matcher MATCH_2 = PATTERN.matcher(Y);


                if (MATCH_1.find() && !MATCH_2.find()) {
                    Main_Tokens.add(new Tokens(key.toString(), X));
                    X = "q";
                }
            }
		}
	}
}
class Lexer{
	static Map <String, String> Main_Lexer = new HashMap <String, String>();
	public Lexer(){
        Main_Lexer.put("VAR", "^[a-z]+$");
        Main_Lexer.put("DIGIT", "^0|[1-9][0-9]*$");
        Main_Lexer.put("OPERATOR", "^[-|+|/|*]$");
        Main_Lexer.put("ASSIGNMENT OPERATOR", "^=$");
    }

}
class Tokens{
	String type;
	String token;
	Tokens(String Type, String Token){
		type = Type;
		token = Token;
	}
}
