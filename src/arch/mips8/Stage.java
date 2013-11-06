package arch.mips8;

import arch.mips8.instruction.Instruction;

public class Stage {
	Instruction currentInstr;
	boolean busy;
	boolean loaded;

	Stage() {
		currentInstr = null;
		busy = false;
		loaded = false;
	}

	public boolean free() {
		return (!busy);
	}

	public void execute(int stage) {
		// This will keep busy true until its function return false
		switch (stage) {
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
		loaded = true;
		busy = true;
	}

	public Instruction getInstruction() {
		return currentInstr;
	}

	public void unload() {
		currentInstr = null;
		loaded = false;
	}
}
