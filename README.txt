Michael Toth
CS 125
Universal Turing Machine
5/01/2013

/*SETUP*/
1. cd to the UTM folder
2. compile the files with the command "javac Main.java Tape.java State.java UTM.java"
3. run the program with the command "java Main"

/*Options*/
You will be prompted with the menu items and asked to select an option.
|Options:
|1: Counter 
|2: Subtraction
|3: Busy Beaver 3
|4: Busy Beaver 4
|5: Busy Beaver 5
|6: Addition
|7: Multiplication
|8: Non terminating counter

-Verbose: You are prompted to selected 'y' or 'n' for verbose mode. 
	example of counting to 2^2-1 with verbose on
		|00 -Step: 0
		|00# -Step: 1
		|00# -Step: 2
		|01# -Step: 3
		|01# -Step: 4
		|00# -Step: 5
		|10# -Step: 6
		|10# -Step: 7
		|10# -Step: 8
		|11# -Step: 9
		|11# -Step: 10
		|10# -Step: 11
		|#00# -Step: 12

	example of counting to 2^2-1 with verbose off
		|01# -Step: 3
		|10# -Step: 6
		|11# -Step: 9

	As you can see, verbose off for counting only shows steps where the then actual binary value increases by 1

-Counter: The counter will count in binary from 0 to a binary number with the selected bits
	Selecting this option will prompt you to select a power for base 2 (i.e the number of bits)
	ex: A selected power of 10 will count from 0 to 2^10 - 1, or 1023

-Subtraction: The subtractor will subtract an inputed number N from N... (M-N). Only works when M>N
	example output of M = 3, N= 1

	|Initial Tape
	|00010
	|
	|#0010 -Step: 0
	|#0010 -Step: 1
	|#0010 -Step: 2
	|#0010 -Step: 3
	|#0011 -Step: 4
	|#0011 -Step: 5
	|#0011 -Step: 6
	|#0011 -Step: 7
	|#0011 -Step: 8
	|##011 -Step: 9
	|##011 -Step: 10
	|##011 -Step: 11
	|##011# -Step: 12
	|##011# -Step: 13
	|##01## -Step: 14
	|##0### -Step: 15
	|##0### -Step: 16
	|
	|Resulting Tape
	|#00###
	|Completed in 18 steps

	The number of 00 in the 'Resulting Tape' is the answer

-Busy Beaver 3 && 4 && 5: no special options. I'd reccomend turning verbose off for 5, otherwise the program will print 47 million lines and take forever

-Addition: Adds inputted M and N together

-Multiplication: Multiplies inputed M and N together

-Non-Terminating Counter: Same as counter except starts at 0 and counts infinitely



/*end: Options */

/*Alphabet*/
{'1', '0', '#'}
# = blank character/empty character

/*Actions*/
{'R', 'L', 'H'}
R = move tape to the right (next())
L = move tape to the left (previous())
H = halt program

/*File Breakdown */
Main.java: This file is where the program is executed. It contains the menu options and directs which instance of UTM is called
UTM.java: This is the file that handles most of the UTM logic and states
State.java: This datastructure keep track of the state's transition functions and contains the method for transitioning from one state to the 	
			next
Tape.java: This is the datastructure for the tape. It keeps track of symbols on the tape, as well as the pointer (head). It has methods to 
			transition to the next/previous cell on the tape, or to replace a value

/*end: File Breakdown */



/*Adding custom programs to the UTM*/

In order to add addition programs to the UTM, it is fairly simple:
UTM.java
1. Add the name of your program, in all caps, to the "enum OPS", at the top of UTM.java
2. In the constructor of the form "public UTM(OPS program, char verbose)", add your program to the switch case
3. Initialize your tape with a default string in the switch case, e.g. blank string: tape = new Tape("#");
4. You may overload the constructor if you need additional parameters
5. Create a function - if you choose to follow the standards - called "initYOURPROGRAM()", and call it in your switch case
6. Inside your newly created function, initialize all the states you need, like so:
		state0 = new State(tape);
		state1 = new State(tape);
		state2 = new State(tape);
   If you need more states, you may declare them at the class level

7. Now your ready to add states to your program. To add a state to state0, the code would look like:
	//addTransition('<Input>', '<Write>', <NextState, '<Action>', '<print>'); print= '1' or '0', this prints this step or supresses it
	state0.addTransition('0', '#', state1, 'R', print); 
   
   The above transitions from state0 to state1 when it reads an input symbol of '0'. It writes '#' to the current
   tape position and 'R' moves the tape to the right (next)

8. Once all your states are defined, you simply need to add an option to the menu on Main.java
9. Simply add a console print out in the menu() method in Main.java to give an option for your program
10. In the Main() constructor, add a switch case that corresponds to your option from the menu. Here you may 
	add additional input prompts for the user for additional arguments. 
11. Call your new program like so in the switch case for your option:
		tm = new UTM(UTM.OPS.YOURPROGRAMNAME, options[1]); you may use options[1] (verbose or not) to your pleasing (becomes 'print' in UTM)


/*end: add custom programs to the UTM */