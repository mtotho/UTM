import java.util.Scanner;

public class Main{

	public UTM tm;

	public Main(){

		char[] options = menu();
		String response;
		Scanner input;

		//Switch on the selected option from the menu
		switch(options[0]){
			case '1': //Counter

				/*
					UTM Counting: count in binary.
					int bits = number of bit places or power of 2 count to. i.e bits=4 counts to 1111, or 15
				*/
				int bits = 0;

				System.out.println("|");
				System.out.println("|Please enter the number of bits to use (i.e. bits=5 counts to 2^5 - 1)");
				System.out.print("|(For effectively 'non-terminating' machine, enter large integer):");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,2}")){
					System.out.print("|Must be an integer or entered integer is too large. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}

				bits = Integer.parseInt(response);

				tm = new UTM(UTM.OPS.COUNTER, bits, options[1]);
		

				break;

			case '2': //Subtraction

				/*
					UTM subtraction: Subtracts N from M, i.e M-N. If N>M, 0 is returned.
				*/
				System.out.println("|Difference = M-N where M > N");
				System.out.print("|Please enter M: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|M must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int M = Integer.parseInt(response);

				System.out.println("|");
				System.out.print("|Please enter N: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|N must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int N = Integer.parseInt(response);
				tm = new UTM(UTM.OPS.SUBTRACTION, options[1],M, N);
				//UTMsubtract(M,N, options[1]);

				break;

			case '3': //BB3

				UTMbusybeaver(UTM.OPS.BUSYBEAVER3, options[1]);
				break;

			case '4': //BB4
				UTMbusybeaver(UTM.OPS.BUSYBEAVER4, options[1]);
				break;

			case '5':
				tm = new UTM(UTM.OPS.BUSYBEAVER5, options[1]);
				break;

			case '6':				
				System.out.println("|Addition = M+N");
				System.out.print("|Please enter M: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|M must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int A = Integer.parseInt(response);

				System.out.println("|");
				System.out.print("|Please enter N: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|N must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int B = Integer.parseInt(response);
				tm = new UTM(UTM.OPS.ADDITION, options[1],A, B);
				break;

			case '7':
				System.out.println("|Multiplication = M*N");
				System.out.print("|Please enter M: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|M must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int C = Integer.parseInt(response);

				System.out.println("|");
				System.out.print("|Please enter N: ");
				input = new Scanner(System.in);
				response = input.next();
				while(!response.matches("^[1-9][0-9]{0,}")){
					System.out.print("|N must be an integer. Try again:");
					input = new Scanner(System.in);
					response = input.next();
				}
				int D = Integer.parseInt(response);
				tm = new UTM(UTM.OPS.MULTIPLICATION, options[1],C, D);
				break;

			case '8':

				tm = new UTM(UTM.OPS.NTCOUNTER, 1, options[1]);

				break;

		}//end: switch

		System.out.print("\n||>>Program finished execution. Run another? (y/n):");
		input = new Scanner(System.in);
		response = input.next();

		//input validation
		while(response.length() > 1 || (response.charAt(0)!= 'y' && response.charAt(0)!='n')){
			System.out.print("|Invalid option. Try again:");
			input = new Scanner(System.in);
			response = input.next();
		}

		if(response.charAt(0) == 'y')
			new Main();
		else{
			System.out.println("||Thank you for using the Universal Turing Machine. Exiting.");
			System.exit(0);
		}

	}//end: Main()


	public void UTMbusybeaver(UTM.OPS bbType, char verbose){
		tm = new UTM(bbType, verbose);
	}

	public char[] menu(){
		char[] returnVals = {'0', '0'};
		String response = "";

		System.out.println("\n## Michael Toth - CS125 - Universal Turing Machine\n");

		System.out.println("||Select a Program to run:");
		System.out.println("|1: Counter");
		System.out.println("|2: Subtraction");
		System.out.println("|3: Busy Beaver 3");
		System.out.println("|4: Busy Beaver 4");
		System.out.println("|5: Busy Beaver 5 (Warning, will print 47 million lines unless verbose=n)");
		System.out.println("|6: Addition");
		System.out.println("|7: Multiplication");
		System.out.println("|8: Non-terminating Counter (Looks cooler with verbose = n)");
		System.out.println("|0: Exit");
		System.out.println("|");

		System.out.print("|Please Select an option: ");
		Scanner input = new Scanner(System.in);

		response = input.next();

		//input validation
		while(response.length() > 1 || response.charAt(0) > '8' || response.charAt(0) < '0'){
				System.out.print("|Invalid option. Try again:");
				input = new Scanner(System.in);
				response = input.next();
		}

		returnVals[0] = response.charAt(0);

		if(returnVals[0] != '0'){
			System.out.println("|");
			System.out.print("|Would you like to turn verbose mode on (displays every transition step)? (y/n): ");

			input = new Scanner(System.in);
			response = input.next();

			//input validation
			while(response.length() > 1 || (response.charAt(0)!= 'y' && response.charAt(0)!='n')){
				System.out.print("|Invalid option. Try again:");
				input = new Scanner(System.in);
				response = input.next();
			}

			returnVals[1] = response.charAt(0);
		}else{
			System.out.println("|");
			System.out.println("||Thank you for using the Universal Turing Machine. Exiting.");
			System.exit(0);
		}

		return returnVals;
	}

	public static void main(String[] args){
		new Main();
	}//: main()

}//end: Main