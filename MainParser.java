import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainParser {
	public static void main (String args[]){
		String str = "(4*(15-3))*45-40/8+5*(4*(55/5))";
		String delimiters = "+-*/()";
		List<String> stringList = parseString(str, delimiters);
		int res = (4*(15-3))*45-40/8+5*(4*(55/5));
		System.out.println(res); make(stringList); return;
	}
	public static List<String> parseString(String str, String delimiters)
	{
	List<String> list = new CopyOnWriteArrayList<>();
			
			StringTokenizer stringToken = new StringTokenizer(str,delimiters , true);
	
			
			while(stringToken.hasMoreTokens())
			{
				list.add(stringToken.nextToken());
			}
			System.out.println(list);
		return list;
	}
	
	public static void make(List<String> list)
	{
		while (list.size() != 1)
		{
			List<String> tmp = new CopyOnWriteArrayList<>();
			int start = 0, finish = 0;
			int listSize;
			for (int i=0; i<list.size(); i++)
			{
				if (list.get(i).equals("("))
				{
					start = i;
				}
				if (list.get(i).equals(")"))
				{
					finish = i;
					listSize = finish-start;
					tmp.addAll(list.subList(start+1, finish));
					String ax = makeMath(tmp);
					for(int j=0; j<=listSize; j++)
					{
						list.remove(start);
					}
					list.add(start, ax);
					tmp.clear();
				}
				
			}
			try
			{
			makeMath(list);
			}
			catch(Exception e)
			{
				continue;
			}
			System.out.println(tmp);
			System.out.println(list);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
 
	}
	
	public static String makeMath(List<String> list)
	{
		for (String aa: list)
		{
			if (aa.equals("*") || aa.equals("/") || aa.equals("+") || aa.equals("-"))
			{
				for (int i=0; i < list.size(); i++)
				{
					Double num;
					if (list.get(i).equals("*"))
					{
						num = mult(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
						list.set(i, num.toString());
						list.remove(i+1); list.remove(i-1);
					}
					else if (list.get(i).equals("/"))
					{
						num = div(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
						list.set(i, num.toString());
						list.remove(i+1); list.remove(i-1);
					}
					else if (list.get(i).equals("+"))
					{
						num = plus(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
						list.set(i, num.toString());
						list.remove(i+1); list.remove(i-1);
					}
					else if (list.get(i).equals("-"))
					{
						num = minus(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
						list.set(i, num.toString());
						list.remove(i+1); list.remove(i-1);
					}
				}
			}
		}
		return list.get(0);
	}
	static double plus(double a, double b)
	{
		return a+b;
	}
	static double minus(double a, double b)
	{
		return a-b;
	}
	static double mult(double a, double b)
	{
		return a*b;
	}
	static double div(double a, double b)
	{
		return a/b;
	}
}