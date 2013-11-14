package arch.mips8;

import arch.mips8.instruction.Instruction;
import arch.mips8.instruction.twoRoneI.BeqInstruction;
import arch.mips8.instruction.twoRoneI.BneInstruction;

public class Simulator {
	Stage IF, ID, IS, RF, EX, DF, DS, TC, WB;
	int instructionCounter;

	public Simulator() {
		IF = new Stage(1);
		IS = new Stage(2);
		ID = new Stage(3);
		EX = new Stage(4);
		DF = new Stage(5);
		DS = new Stage(6);
		TC = new Stage(7);
		WB = new Stage(8);
		this.instructionCounter = 1;
	}

	public boolean nextStep() {
		boolean successful;
		// WB
		if (WB.getState() == 'A') {
			successful = WB.execute();
			if (successful) {
				WB.setState('B');
			} else {
				WB.setState('A');
				WB.setStalled(true);
			}
		}
		if (WB.getState() == 'B') {
			WB.setState('C');
		}

		// TC
		stageAction(TC, WB);
		// DS
		stageAction(DS, TC);
		// DF
		stageAction(DF, DS);
		// EX
		if (EX.getState() == 'A') {
			successful = EX.execute();
			if (successful) {
				if(EX.getInstruction() instanceof BeqInstruction){
					if(((BeqInstruction)EX.getInstruction()).getBranchTaken()){
						flush();
					}
				}
				if(EX.getInstruction() instanceof BneInstruction){
					if(((BneInstruction)EX.getInstruction()).getBranchTaken()){
						flush();
					}
				}
				EX.setState('B');				
			} else {
				EX.setState('A');
				EX.setStalled(true);
			}
		}
		if (EX.getState() == 'B') {
			if (DF.getState() == 'C') {
				DF.setInstruction(EX.getInstruction());
				EX.setState('C');
			}
		}
		// ID
		stageAction(ID, EX);
		// IS
		stageAction(IS, ID);
		// IF
		stageAction(IF, IS);
		if (IF.getState() == 'C') {
			// load new instruction in IF
			int pc = (int) Globals.getRegister("pc").getContent();
			if (pc < Globals.instructions.size()) {
				Instruction i = Globals.instructions.get(pc).copy();
				i.setId(instructionCounter);
				instructionCounter++;
				IF.setInstruction(i);
				Globals.instructionPipeLine.getInstructionList().add(
						IF.getInstruction().getInstructionName());
			} else {
				IF.setState('C');
			}
		}
		Globals.instructionPipeLine
				.setInitialX(45 + Globals.instructionPipeLine.getInitialX());
		showStatusGUI(IF);
		showStatusGUI(IS);
		showStatusGUI(ID);
		showStatusGUI(EX);
		showStatusGUI(DF);
		showStatusGUI(DS);
		showStatusGUI(TC);
		showStatusGUI(WB);
		return true;
	}

	private void showStatusGUI(Stage stage) {
		if (stage.getState() == 'A' || stage.getState() == 'B')
			Globals.instructionPipeLine.setClockCycles(stage.getInstruction()
					.getId(), stage.getId(), stage.status());

	}

	void stageAction(Stage prev, Stage next) {
		boolean successful;
		if (prev.getState() == 'A') {
			successful = prev.execute();
			if (successful) {
				prev.setState('B');
			} else {
				prev.setState('A');
				prev.setStalled(true);
			}
		}
		if (prev.getState() == 'B') {
			if (next.getState() == 'C') {
				next.setInstruction(prev.getInstruction());
				prev.setState('C');
			}
		}
	}
	
	void flush(){
		int id = EX.getInstruction().getId();		
		//TODO register unlocks
		for(String regName:Globals.registers.keySet()){
			Register r = Globals.getRegister(regName);
			if (!r.isLocked(id)){
				r.unlockRegister();
			}
		}

		IF.setState('C');
		IS.setState('C');
		ID.setState('C');
		
	}

}
