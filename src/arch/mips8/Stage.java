package arch.mips8;

import arch.mips8.instruction.Instruction;

public class Stage {
	Instruction instruction;
	char state;
	int id;
	boolean stalled;
	public Stage(int id) {
		this.id = id;
		this.instruction = null;
		this.state = 'C';
		this.stalled = false;
	}
	
	public boolean execute(){
		switch(id){
		case 1:
			return instruction.executeIF();
		case 2:
			return instruction.executeIS();
		case 3:
			return instruction.executeID();
		case 4:
			return instruction.executeEX();
		case 5:
			return instruction.executeDF();
		case 6:
			return instruction.executeDS();
		case 7:
			return instruction.executeTC();
		case 8:
			return instruction.executeWB();
		}
		return false;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
		this.state = 'A';
		this.stalled = false;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
		if(state=='A'){
			
		}
		else if(state=='B'){
			stalled = false;
		}
		else if(state=='C'){
			instruction = null;
		}
	}

	public boolean isStalled() {
		return stalled;
	}

	public void setStalled(boolean stalled) {
		this.stalled = stalled;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String status(){
		if(state=='A' && !stalled){
			return "N" + instruction.getId();
		}
		else if(state=='A' && stalled){
			return "AS" + instruction.getId();
		}
		else if(state=='B'){
			return "B" + instruction.getId();
		}
		return "E";
	}
	
	
	
}
