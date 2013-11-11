package arch.mips8;

import arch.mips8.instruction.Instruction;

public class Stage {
	Instruction currentInstr, forwardInstr;
	boolean busy;
	boolean loaded;
	int id;

	Stage(int id) {
		currentInstr = null;
		busy = false;
		loaded = false;
		this.id = id;
	}

	public boolean free() {
		return (!busy);
	}

	public void execute() {
		// This will keep busy true until its function return false
		if(!busy) return;
		System.out.println(id);
		switch (id) {
		case 1:
			busy = !currentInstr.executeIF();
			break;
		case 2:
			busy = !currentInstr.executeIS();
			break;
		case 3:
			busy = !currentInstr.executeID();
			break;
		case 4:
			busy = !currentInstr.executeEX();
			break;
		case 5:
			busy = !currentInstr.executeDF();
			break;
		case 6:
			busy = !currentInstr.executeDS();
			break;
		case 7:
			busy = !currentInstr.executeTC();
			break;
		case 8:
			busy = !currentInstr.executeWB();
			break;
		}
	}

	public void addInstruction(Instruction i) {
		currentInstr = i;
		if (i != null){
			loaded = true;
			busy = true;
		}
		else{
			busy = false;
			loaded = false;
		}
		
	}

	public Instruction getInstruction() {
		return currentInstr;
	}

	public void unload() {
		currentInstr = null;
		loaded = false;
	}
	public String status(){
		if(busy) return id + " busy" + currentInstr.getId();
		else return id +" free";
	}
	public void forward(){
		if(currentInstr!=null)
			forwardInstr = currentInstr;
	}
	public void forwardDone(){
		forwardInstr = null;
	}
	public Instruction getForward(){
		return forwardInstr;
	}
}
