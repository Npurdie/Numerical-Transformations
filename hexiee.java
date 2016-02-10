import java.lang.*;
import java.util.Scanner;

public class hexiee	{

	public static long modulo(long a, int n)	{	//modulo class performs the modulo operation
		long r = a - n*(a/n);
		return r;
	}

	public static String binaryToHex(String binary)	{
		int exp = 0;
		long number = 0;
		char hex[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		String result = "";
		int counter = 0;

		for (int i=31; i>=0; i--) {
			number = ((int)binary.charAt(i)-48) * (int)Math.pow(2,exp) + number;	//converts binary to long
			exp ++;
			counter ++;
			if (counter == 4) {		//enters this loops for every 4 iterations of for loop
				result = hex[(int)modulo(number,16)] + result;
				exp = 0;		// reset exp, number and counter
				number = 0;
				counter = 0;
			}
		}
		return result;
	}

	public static String floatingPointToBinary(String userInput)	{
		double number = 0;
		boolean isNegative = false;
		String exponentString = "";
		for (int i=0; i<userInput.length(); i++) {		//the following converts exponential notation to a double
			if (userInput.charAt(i) == 69 || userInput.charAt(i) == 101) {	//if char is equal to e or E
				double fraction = Double.parseDouble((userInput.substring(0,i)));
				double exponent = Double.parseDouble(userInput.substring(i+1,userInput.length()));
				number = fraction * Math.pow(10,exponent);
			}
		}
		if (number == 0) {
			number = Double.parseDouble(userInput);
		}

		if (number < 0)	{		//checks for negative number and sets boolean value to true
			isNegative = true;
			number = (-1)*number;
		}

		int exponent = 0;
		while	(number < 1)	{
			number = number *2;
			exponent --;
		}
		while	(number > 2)	{
			number = number/2;
			exponent ++;
		}
		exponent = exponent + 127;

		number = number - 1;
		String result = "";
		for (int i=0; i<23; i++)	{
			double r = number*2;
			if (r >= 1) {
				result += "1";
				number = r - 1;
			}
			else	{
				result += "0";
				number = r;
			}
		}

		while(exponent > 0)	{					//convert exponent to binary
			exponentString = modulo(exponent,2) + exponentString;
			exponent = exponent/2;
		}
		while(exponentString.length() < 8)	{
			exponentString = "0" + exponentString;
		}

		if (isNegative)	{
			return "1" + exponentString + result;
		}

		return "0" + exponentString + result;
	}

	public static String hexToBinary(String userInput)	{
		String binaryNum = "";		//this string will store the binary number once converted
		while (userInput.length() < 8)	{	//loop lengthens input length to 8
			userInput = "0" + userInput;
		}
		int length = userInput.length();

		for (int i=length-1; i>=0; i--) { 	// iterate through every hex digit
			int temp = 0;
			int foo = (int)userInput.charAt(i);
			if (foo > 64 && foo <71) {		// if digit is A to F
				temp = foo-55;
			}
			else if (foo > 47 && foo < 58) {		// if digit is 0 to 9
				temp = foo-48;
			}
			else	{	// if other character found
				System.out.println("Error in number format.");
				return null;
			}
			for (int j=0; j<4; j++)	{		//converts hex digit to string of 4 binary numbers
				binaryNum = modulo(temp,2) + binaryNum;
				temp = temp/2;
			}
		}
		return binaryNum;
	}

	public static double binaryToFloatingPoint(String binary)	{
		String mantissaString = "";
		double mantissa = 0;
		double exp = 0;
		String expString = "";
		boolean isNegative = false;
		double result = 0;
		int foo = 0;

		if (binary.charAt(0) == '1')	{	// used to set the sign of floating point number before returning value
			isNegative = true;
		}

		expString = binary.substring(1,9);
		for (int i=7; i>=0; i--) {		//converts the exponential to a double
			exp = ((int)expString.charAt(i)-48) * Math.pow(2,foo) + exp;
			foo++;
		}

		mantissaString = binary.substring(9,32);
		foo = -1;
		for (int i=0; i<23; i++) {		//converts mantissa to a double
			mantissa = ((int)mantissaString.charAt(i)-48) * Math.pow(2,foo) + mantissa;
			foo --;
		}
		mantissa = 1 + mantissa; 	//adds the implied 1 that precedes tha mantissa
		exp = exp - 127;			//substracts bias from exponent
		result = mantissa * Math.pow(2,exp);

		if (isNegative) {			//multiplies by -1 if the leading bit is a 1
			result = result * (-1);
		}
		return result;
	}

	public static void main(String[] args)	{
		String userInput = "";

		if (args.length > 0) {
			userInput = args[0];
		}
		//if no arguments were entered
		else	{
			System.out.println("No arguments entered");
			System.out.println("Please enter a floating point number or a hexadecimal number :");
			Scanner in = new Scanner(System.in);
			userInput = in.nextLine();
		}
		
		System.out.print(userInput + "  <->    ");

		//if input is hexadecimal
		if (userInput.charAt(1) == 'x' || userInput.charAt(1) == 'X') {
			userInput = userInput.substring(2);	// removes the 0X at the begining of hex number
			if (userInput.length() > 8) {
				System.out.println("hexadecimal number is too long.");
			}
			String binary = hexToBinary(userInput);
			System.out.println(binaryToFloatingPoint(binary));
		}
		//if input is floating point
		else	{
			String binaryNum = floatingPointToBinary(userInput);
			String hex = binaryToHex(binaryNum);
			System.out.println(hex);
		}
	}
}