/*
	Michael Toth
	CS125 - Universal Turing Machine

	Info:
		Alphabet: {'0', '1', '#' }, '#' dictates blank symbol
		Tape: 'R' and 'L' dictate right and left respectively
		States: {<Current State>, <Input Symbol>, <Write>, <New State>, <Move>}
			<Input Symbol>: Current symbol on the tape
			<Write>: aplhabet symbol to write to the tape
			<Move>: 'L' or 'R', the direction to move the tape or 'H' for halt
*/

import java.util.ArrayList;

public class UTM{

	public Tape tape;

	private State currentState;
	private State state0;
	private State state1;
	private State state2;
	private State state3;
	private State state4;
	private State state5;
	private State state6;
	private State state7;
	private State state8;
	private State state9;
	private State state10;
	private State state11;
	private State state12;

	private char print;

	public enum OPS{
		BUSYBEAVER3, BUSYBEAVER4, BUSYBEAVER5, ADDITION, SUBTRACTION, MULTIPLICATION, COUNTER, NTCOUNTER
	}

	public UTM(char verbose){
		if(verbose == 'y'){
			print = '1';
		}else{
			print = '0';
		}
	}

	//Constructor for counting
	public UTM(OPS program, int bits, char verbose){
		this(verbose);

		String w = "";
		for(int i=0; i<bits; i++){
			w=w+"0";
		}

		//Load the tape with the string
		tape = new Tape(w);

		String outMessage ="";
		//Create the counting transition functions
		if(program == OPS.COUNTER){
			initCounting();
			outMessage = "|Counts from 0 through 2 raised to the power of " + bits;
		}
		if(program == OPS.NTCOUNTER){
			initNonTerminatingCounting();
			outMessage = "|Counts from 0 to infinity";
		}

		System.out.println("\n|||>> UTM "+ program);
		System.out.println(outMessage);
		
		//Initialize the TM
		run();

	}//end: Constructor

	//Constructor for busy beaver or generic program
	public UTM(OPS program, char verbose){
		this(verbose);

		//Create the busybeaver transition functions
		switch(program){
			case BUSYBEAVER3:
			case BUSYBEAVER4:
			case BUSYBEAVER5:
				//Load the tape with a blank string
				tape = new Tape("#");
				
				initBusyBeaver(program);
				break;
		}
		

		//output header
		System.out.println("\n|||>> UTM " + program);

		run();
	}//end: Contructor - BUSYBEAVER

	//Constructor for addition/subtraction/multiplication
	public UTM(OPS program, char verbose, int M, int N){
		this(verbose);

		if(program == OPS.ADDITION || program == OPS.SUBTRACTION || program == OPS.MULTIPLICATION){
			String w = "";
			for(int i=0; i<M; i++){
				w = w + "0";
			}
			w = w + "1";
			for(int i=0; i<N; i++){
				w = w+ "0";
			}
			tape = new Tape(w);
		}

		String outMessage = "";
		if(program == OPS.ADDITION){
			initAddition();
			outMessage = "Sum: ";
		}
		if(program == OPS.SUBTRACTION){
			initSubtraction();
			outMessage = "Difference: ";
		}
		if(program == OPS.MULTIPLICATION){
			initMultiplication();
			outMessage = "Product: ";
		}

		System.out.println("\n|||>> UTM " + program);
		System.out.println("|"+M + " + " + N + " = # 0's in final tape");

		run();

		System.out.println("|"+outMessage + tape.addZeros());

	}

	//run(): Starts reading the tape with the current states
	public void run(){
		currentState = state0;

		//Print the current tape
		System.out.println("|");
		System.out.println("|Initial Tape");
		System.out.println("|" + tape.toString());
		System.out.println("|");

		long startTime = System.nanoTime();

		//Transition the state to the next with the input symbol
		int i = 0;
		while(currentState.isHalted()==false){
			currentState = currentState.transition(tape.read(), i);
			i++;
		}
		long estimatedTime = System.nanoTime() - startTime;
        double timeMS = (double)estimatedTime / 1000000.0;

		System.out.println("|Completed in " + i + " steps");
		System.out.println("|Elapsed Time: " + timeMS + "ms");


	}//end: run()

	public void initMultiplication(){
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
		state3 = new State(tape);
		state4 = new State(tape);
		state5 = new State(tape);
		state6 = new State(tape);
		state7 = new State(tape);
		state8 = new State(tape);
		state9 = new State(tape);
		state10 = new State(tape);
		state11 = new State(tape);
		state12 = new State(tape);

		//state0
		state0.addTransition('#', '#', state0, 'R', print);
		state0.addTransition('1', '1', state0, 'R', print);
		state0.addTransition('0', '1', state1, 'R', print);

		//state1
		state1.addTransition('#', '#', state2, 'R', print);
		state1.addTransition('1', '1', state2, 'R', print);
		state1.addTransition('0', '0', state1, 'R', print);

		//state2
		state2.addTransition('#', '#', state2, 'H', '1');
		state2.addTransition('1', '1', state2, 'H', '1');
		state2.addTransition('0', '1', state3, 'R', print);

		//state3
		state3.addTransition('#', '#', state4, 'R', print);
		state3.addTransition('1', '1', state4, 'R', print);
		state3.addTransition('0', '0', state3, 'R', print);

		//state4
		state4.addTransition('#', '0', state5, 'L', print);
		state4.addTransition('1', '0', state5, 'L', print);
		state4.addTransition('0', '0', state4, 'R', print);

		//state5
		state5.addTransition('#', '#', state6, 'L', print);
		state5.addTransition('1', '1', state6, 'L', print);
		state5.addTransition('0', '0', state5, 'L', print);
		
		//state6
		state6.addTransition('#', '0', state8, 'L', print);
		state6.addTransition('1', '0', state8, 'L', print);
		state6.addTransition('0', '0', state7, 'L', print);
		
		//state7
		state7.addTransition('#', '0', state2, 'R', print);
		state7.addTransition('1', '0', state2, 'R', print);
		state7.addTransition('0', '0', state7, 'L', print);
		
		//state8
		state8.addTransition('#', '#', state9, 'L', print);
		state8.addTransition('1', '1', state9, 'L', print);
		state8.addTransition('0', '0', state8, 'L', print);
		
		//state9
		state9.addTransition('#', '#', state11, 'R', print);
		state9.addTransition('1', '1', state11, 'R', print);
		state9.addTransition('0', '0', state10, 'L', print);
		
		//state10
		state10.addTransition('#', '#', state0, 'R', print);
		state10.addTransition('1', '1', state0, 'R', print);
		state10.addTransition('0', '0', state10, 'L', print);
		
		//state11
		state11.addTransition('#', '#', state11, 'R', print);
		state11.addTransition('1', '1', state11, 'R', print);
		state11.addTransition('0', '1', state12, 'R', print);
		
		//state12
		state12.addTransition('#', '#', state12, 'H', '1');
		state12.addTransition('1', '1', state12, 'H', '1');
		state12.addTransition('0', '1', state12, 'R', print);


	}

	public void initAddition(){
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
		state3 = new State(tape);
		state4 = new State(tape);

		//state0
		state0.addTransition('0', '0', state1, 'R', print);
		state0.addTransition('1', '1', state0, 'R', print);

		//state1
		state1.addTransition('1', '0', state2, 'R', print);
		state1.addTransition('0', '0', state1, 'R', print);

		//state2
		state2.addTransition('#', '#', state3, 'L', print);
		state2.addTransition('1', '1', state3, 'L', print);
		state2.addTransition('0', '0', state2, 'R', print);

		//state3
		state3.addTransition('0', '#', state4, 'L', print);
		
		//state4
		state4.addTransition('0', '0', state4, 'L', print);
		state4.addTransition('1', '1', state4, 'H', '1');
		state4.addTransition('#', '#', state4, 'H', '1');
	}


	//initBusyBeaver(): creates transition functions for busy beaver
	public void initBusyBeaver(OPS BBtype){

		//initialize states
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
			
		switch(BBtype){
			case BUSYBEAVER3:
				//Add transitions;
				//==>addTransition(<read>,<write>,<next state>, <action>, <printOutput: '0'=false, '1'=true>)

				//state0
				state0.addTransition('0', '1', state1, 'R', '1');
				state0.addTransition('1', '1', state0, 'H', '1');
				state0.addTransition('#', '1', state1, 'R', '1');

				//state1
				state1.addTransition('0', '0', state2, 'R', '1');
				state1.addTransition('1', '1', state1, 'R', '1');
				state1.addTransition('#', '0', state2, 'R', '1');

				//state2
				state2.addTransition('0', '1', state2, 'L', '1');
				state2.addTransition('1', '1', state0, 'L', '1');
				state2.addTransition('#', '1', state2, 'L', '1');

				break;

			case BUSYBEAVER4:
				state3 = new State(tape);

				//state0
				state0.addTransition('0', '1', state1, 'R', print);
				state0.addTransition('1', '1', state1, 'L', print);
				state0.addTransition('#', '1', state1, 'R', print);

				//state1
				state1.addTransition('0', '1', state0, 'L', print);
				state1.addTransition('1', '0', state2, 'L', print);
				state1.addTransition('#', '1', state0, 'L', print);

				//state2
				state2.addTransition('0', '1', state2, 'H', '1');
				state2.addTransition('1', '1', state3, 'L', print);
				state2.addTransition('#', '1', state2, 'H', '1');

				//state3
				state3.addTransition('0', '1', state3, 'R', print);
				state3.addTransition('1', '0', state0, 'R', print);
				state3.addTransition('#', '1', state3, 'R', print);

				break;

			case BUSYBEAVER5:
				state3 = new State(tape);
				state4 = new State(tape);

				//state0
				state0.addTransition('0', '1', state1, 'R', print);
				state0.addTransition('#', '1', state1, 'R', print);
				state0.addTransition('1', '1', state2, 'L', print);

				//state1
				state1.addTransition('0', '1', state2, 'R', print);
				state1.addTransition('#', '1', state2, 'R', print);
				state1.addTransition('1', '1', state1, 'R', print);

				//state2
				state2.addTransition('0', '1', state3, 'R', print);
				state2.addTransition('#', '1', state3, 'R', print);
				state2.addTransition('1', '0', state4, 'L', print);

				//state3
				state3.addTransition('0', '1', state0, 'L', print);
				state3.addTransition('#', '1', state0, 'L', print);
				state3.addTransition('1', '1', state3, 'L', print);

				//state4
				state4.addTransition('0', '1', state4, 'H',	print);
				state4.addTransition('#', '1', state4, 'H', print);
				state4.addTransition('1', '0', state0, 'L', print);

				break;
		}
		

	}//end: initBusyBeaver()

	//initSubtraction(): creates transition functions for subtraciton
	public void initSubtraction(){
		//initialize states
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
		state3 = new State(tape);
		state4 = new State(tape);
		state5 = new State(tape);
		state6 = new State(tape);
				
		//Add transitions;
		//==>addTransition(<read>,<write>,<next state>, <action>, <printOutput: '0'=false, '1'=true>)

		//state0
		state0.addTransition('0', '#', state1, 'R', print);
		state0.addTransition('1', '#', state5, 'R', print);

		//state1
		state1.addTransition('0', '0', state1, 'R', print);
		state1.addTransition('1', '1', state2, 'R', print);

		//state2
		state2.addTransition('0', '1', state3, 'L', print);
		state2.addTransition('1', '1', state2, 'R', print);
		state2.addTransition('#', '#', state4, 'L', print);

		//state3
		state3.addTransition('0', '0', state3, 'L', print);
		state3.addTransition('1', '1', state3, 'L', print);
		state3.addTransition('#', '#', state0, 'R', print);

		//state4
		state4.addTransition('0', '0', state4, 'L', print);
		state4.addTransition('1', '#', state4, 'L', print);
		state4.addTransition('#', '0', state6, 'H', '1');

		//state5
		state5.addTransition('0', '#', state5, 'R', print);
		state5.addTransition('1', '#', state5, 'R', print);
		state5.addTransition('#', '#', state6, 'H', print);


	}//end: initSubtraction();

	//createStates(): creations transition functions for counting
	public void initCounting(){

		//initialize states
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
		

		//Add transitions;
		//addTransition(<read>,<write>,<next state>, <action>, <printOutput: '0'=false, '1'=true>)
		
		//state0
		state0.addTransition('0', '0', state0, 'R', print);
		state0.addTransition('1', '1', state0, 'R', print);
		state0.addTransition('#', '#', state1, 'L', print);

		//state1
		state1.addTransition('0', '1', state0, 'R', '1');
		state1.addTransition('1', '0', state1, 'L', print);
		state1.addTransition('#', '#', state2, 'H', print);

	}//end: createStates()

	public void initNonTerminatingCounting(){
		//initialize states
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
		

		//Add transitions;
		//addTransition(<read>,<write>,<next state>, <action>, <printOutput: '0'=false, '1'=true>)
		
		//state0
		state0.addTransition('0', '0', state0, 'R', print);
		state0.addTransition('1', '1', state0, 'R', print);
		state0.addTransition('#', '#', state1, 'L', print);

		//state1
		state1.addTransition('0', '1', state0, 'R', '1');
		state1.addTransition('1', '0', state1, 'L', print);
		state1.addTransition('#', '0', state0, 'R', print);
	}

}//end: UTM