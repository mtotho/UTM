import java.util.ArrayList;

public class Tape{

	private ArrayList<Character> symbols;
	private int pointer;

	public Tape(String w){
		pointer = 0;
		symbols = new ArrayList();

		//Add the input string to the tape
		char[] charray = w.toCharArray();
		for(int i=0; i<charray.length; i++){
			append(charray[i]);
		}

		//Put the head at the end of the tape
	}//end: Constructor()

	//append(): Add an item to the end of the tape
	public void append(char item){
		symbols.add(item);
	}//end: append()

	public void replace(char item){
		symbols.set(pointer, item);
	}

	//addFront(): Add an item to the front of the tape
	public void addFront(char item){
		symbols.add(0, item);
	}//end: addFront();

	//insert(): add an item at the current pointer
	public void insert(char item){
		symbols.add(pointer, item);
	}//end: insert()

	//read(): read the current element on the tape
	public char read(){
		
		//If tape is empty, add the blank symbol before reading
		if(symbols.isEmpty()){
			symbols.add('#');
		}

		char value = symbols.get(pointer);
		return value;
	}//end: read()

	//next(): turn to the next item on the tape
	public void next(){
		if(isEnd()){
			append('#');
		}
		pointer++;
	}//end: next()

	//end(): set the pointer to the end of the tape
	public void end(){
		pointer = (symbols.size() - 1);
	}//end: end()

	//previous: turn the tape back 1
	public void previous(){
		if(!isFront()){
			pointer--;
		}else{
			addFront('#'); //Add blank to the front 
		}
	}//end: previous()

	//isEnd(): returns false if there is more symbols on the tape. returns true if end of tape is reached
	public boolean isEnd(){

		//Check for empty
		if(symbols.isEmpty()){
			return true; //Is empty, return end
		}else if(pointer == (symbols.size()-1)){	
			return true;
		}else{
			return false;
		}
	}//end: isEnd();

	//isFront(): return true if at the front of the tape
	public boolean isFront(){
		if(pointer == 0){
			return true;
		}else{
			return false;
		}
	}//end: isFront()

	//reset(): reset the pointer to the begining
	public void reset(){
		pointer = 0;
	}//end: reset()

	public int getPointer(){
		return pointer;
	}

	//toString()
	public String toString(){
		String output = "";
		for(int i=0; i<symbols.size(); i++){
			output = output + symbols.get(i);
		}
		return output;
	}//end: toString();

	public int addZeros(){
		int sum = 0;
		for(int i=0; i<symbols.size(); i++){
			if(symbols.get(i) == '0'){
				sum++;
			}
		}
		return sum;
	}
}//end: Tape