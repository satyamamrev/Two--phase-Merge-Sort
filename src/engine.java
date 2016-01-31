import java.util.Scanner;


public class engine {


	public static void main(String[] args) {
		
		//System.out.println("Enter your name ");
		Scanner scanner=new Scanner(System.in);
		String input=scanner.nextLine();
		//parseInput()
		Parser parseinput=new Parser();
		parseinput.parse(input);

	}

}
