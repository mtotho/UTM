import java.util.ArrayList;

public class State{

	//transition: {'<read>', '<write>', '<final state>', '<action>'}
	private ArrayList<char[]> transitionFuncs;
	private ArrayList<State> transitionFuncStates;
	private Tape tape;
	private boolean isHalted;

	public State(Tape tape){
		isHalted = false;
		transitionFuncs = new ArrayList();
		transitionFuncStates = new ArrayList();
		this.tape = tape;
	}

	public void setHalt(boolean bool){
		isHalted = bool;
	}

	public boolean isHalted(){
		return isHalted;
	}

	//addTransition(): Add the transition function with the given parameters to the State
	public void addTransition(char read, char write, State nextState, char action, char printOutput){
		char[] temp = {read,write,action, printOutput};
		transitionFuncs.add(temp);
		transitionFuncStates.add(nextState);
	}//end: addTransition()

	//trantion(): Transition to the next state based on the tape input
	public State transition(char input, int step){
		State nextState = null;

		//Loop through each transition and look for input symbol
		for(int i=0; i < transitionFuncs.size(); i++){
			char[] rwa = transitionFuncs.get(i); //Read Write Action

			//look for the transition function with the given inputer 
			if(rwa[0]==input){
				nextState = transitionFuncStates.get(i);

				//Get the write and action
 				char write = rwa[1];
				char action = rwa[2];
				char printOutput = rwa[3];

				//replace the current tape value with the write value
				tape.replace(write);

				//Move the tape forward or backward depending on action
				if(action == 'R'){
					tape.next();
				}else if(action == 'L'){
					tape.previous();
				}else if(action == 'H'){
					//Set the halt status of the next State to true so the program ends
					nextState.setHalt(true);
				}

				//print tape if flag is set to '1'
				if(printOutput == '1'){
					System.out.println("|"+tape.toString() + " -Step: " + (step+1) + " -Head: " + tape.getPointer());
				}
				if(printOutput == '1' && nextState.isHalted()){
					System.out.println("|");
					System.out.println("|Resulting Tape");
					System.out.println("|"+tape.toString());
				}

			}//end: if

		}//end: for

		return nextState;
	}//end: transition();
}