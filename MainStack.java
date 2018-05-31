import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class MainStack {
	static String operators = "+-*/";
	static String delimiters = "() " + operators;
	boolean flag = true;
	private static Scanner in;
	boolean Dlm(String token) {
		if (token.length() != 1) return false;
		for (int i = 0; i < delimiters.length(); i++) {
			if (token.charAt(0) == delimiters.charAt(i)) return true;
		}
		return false;
	}
	boolean Op(String token){
		for (int a = 0; a < operators.length(); a++) {
			if (token.charAt(0) == operators.charAt(a)) return true;
		}
		return false;
	}
	static int prior(String token) {
		if (token.equals("(")) return 1;
		if (token.equals("+") || token.equals("-")) return 2;
		if (token.equals("*") || token.equals("/")) return 3;
		return 4;
	}
	
	List<String> stackParse(String infix){
		List<String> postfixNotate = new ArrayList<String>();
		Deque<String> parserStack = new ArrayDeque<String>();
		StringTokenizer StackToken = new StringTokenizer(infix, delimiters, true);
		String PREVIOUS = "";
		String CURRENT = "";
		while (StackToken.hasMoreTokens()) {
			CURRENT = StackToken.nextToken();
			if (!StackToken.hasMoreTokens() && Op(CURRENT)){
				System.out.println("Некорректное выражение.");
				flag = false;
				return postfixNotate;
			}
			if(CURRENT.equals(" ")) continue;
			if(Dlm(CURRENT)){
				if(CURRENT.equals("("))parserStack.push(CURRENT);
				else if (CURRENT.equals(")")){
					while (parserStack.peek().equals("(")){
						postfixNotate.add(parserStack.pop());
						if(parserStack.isEmpty()){
							System.out.println("Скобки не согласованы.");
							flag = false;
							return postfixNotate;
						}
					}
					if (CURRENT.equals("-") && (PREVIOUS.equals("") || (Dlm(PREVIOUS) && !PREVIOUS.equals(")")))){
						CURRENT = "u-";
					}else{
						while(!parserStack.isEmpty() && (prior(CURRENT)<= prior(parserStack.peek()))){
							postfixNotate.add(parserStack.pop());
						}
					}
					parserStack.push(CURRENT);
				}
			}else{
				postfixNotate.add(CURRENT);
			}
			PREVIOUS = CURRENT;
		}
		while(!parserStack.isEmpty()){
			if(Op(parserStack.peek())) postfixNotate.add(parserStack.pop());
			else{
				System.out.println("Скобки не согласованы.");
				flag = false;
				return postfixNotate;
			}
		}
		return postfixNotate;	
	}
	public static Double calculate(List<String> postfixer){
		Deque<Double> javaStack = new ArrayDeque<Double>();
		for(String z : postfixer){
			if (z.equals("+")) javaStack.push(javaStack.pop() + javaStack.pop());
			else if (z.equals("-")) javaStack.push(javaStack.pop() - javaStack.pop());
			else if (z.equals("*")) javaStack.push(javaStack.pop() * javaStack.pop());
			else if (z.equals("/")) javaStack.push(javaStack.pop() / javaStack.pop());
			else if (z.equals("u-")) javaStack.push(-javaStack.pop());
			else javaStack.push(Double.valueOf(z));
		}
		return javaStack.pop();
	}
	public static void main (String[] args) {
		in = new Scanner(System.in);
		String a = in.nextLine();
		MainStack x = new MainStack();
		List<String> expression = x.stackParse(a);
		boolean flag = x.flag;
		if (flag){
			for(String y : expression) System.out.print(y + " ");
			System.out.println();
			System.out.println(calculate(expression));
		}
	}
}