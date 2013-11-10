package arch.mips8;

import arch.mips8.instruction.Instruction;

public class Simulator {
	Stage IF, ID, IS, RF, EX, DF, DS, TC, WB;
	boolean done;
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
		this.done = false;
		this.instructionCounter = 1;
	}

	public void simulate() {
		int instrIndex = 0;
		while (!done) {
			if (!WB.free()) {
				WB.execute();
			}
			if (!TC.free()) {
				TC.execute();
			}
			if (!DS.free()) {
				DS.execute();
			}
			if (!DF.free()) {
				DF.execute();
			}
			if (!EX.free()) {
				EX.execute();
			}
			if (!ID.free()) {
				ID.execute();
			}
			if (!IS.free()) {
				IS.execute();
			}
			if (!IF.free()) {
				System.out.println("\n  Random " + IF.free());
				IF.execute();
			}
			if ((WB.free()) && (TC.free())) {
				Instruction i = TC.getInstruction();
				WB.addInstruction(i);
				TC.addInstruction(null);
			}
			if ((TC.free()) && (DS.free())) {
				Instruction i = DS.getInstruction();
				TC.addInstruction(i);
				DS.addInstruction(null);
			}
			if ((DS.free()) && (DF.free())) {
				Instruction i = DF.getInstruction();
				DS.addInstruction(i);
				DF.addInstruction(null);
			}
			if ((DF.free()) && (EX.free())) {
				Instruction i = EX.getInstruction();
				DF.addInstruction(i);
				EX.addInstruction(null);
			}
			if ((EX.free()) && (ID.free())) {
				Instruction i = ID.getInstruction();
				EX.addInstruction(i);
				ID.addInstruction(null);
			}
			if ((ID.free()) && (IS.free())) {
				Instruction i = IS.getInstruction();
				ID.addInstruction(i);
				IS.addInstruction(null);
			}
			if (IS.free() && IF.free()) {
				Instruction i = IF.getInstruction();
				if (i != null) {
					System.out.println("\n Push IS ----" + i);
				}
				IS.addInstruction(i);
				IF.addInstruction(null);
			}
			if (instrIndex >= Globals.instructions.size()) {
				IF.addInstruction(null);
			}
			if (instrIndex < Globals.instructions.size() && IF.free()) {

				Instruction i = Globals.instructions.get(instrIndex).copy();
				System.out.println("\n Push IF" + i);
				i.setId(instructionCounter);
				instructionCounter++;
				IF.addInstruction(i);
				instrIndex++;
			}
			/*
			 * if (WB.free()) { Instruction i = WB.getInstruction(); if
			 * (i.equals(null)) { done = true; } }
			 */
		}

	}

	public boolean nextStep() {
		WB.execute();
		TC.execute();
		DS.execute();
		DF.execute();
		EX.execute();
		ID.execute();
		IS.execute();
		IF.execute();

		if (IF.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(IF.getInstruction()
					.getId(), 1, "N");
		}
		if (IS.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(IS.getInstruction()
					.getId(), 2, "N");
		}
		if (ID.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(ID.getInstruction()
					.getId(), 3, "N");
		}
		if (EX.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(EX.getInstruction()
					.getId(), 4, "N");
		}
		if (DF.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(DF.getInstruction()
					.getId(), 5, "N");
		}
		if (DS.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(DS.getInstruction()
					.getId(), 6, "N");
		}
		if (TC.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(TC.getInstruction()
					.getId(), 7, "N");
		}
		if (WB.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(WB.getInstruction()
					.getId(), 8, "N");
		}
		
		Instruction temp = null, temp1 = null;
		
		if (IF.free()) {
			if(IF.getInstruction()!=null)
				temp = IF.getInstruction();
			int pc = (int) Globals.getRegister("pc").getContent();
			if (pc < Globals.instructions.size()) {
				Instruction i = Globals.instructions.get(pc).copy();
				i.setId(instructionCounter);
				instructionCounter++;
				IF.addInstruction(i);
			} else
				temp = null;
		}
		if (IS.free()) {
			temp1 = IS.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				System.out.println("copy");
				IS.addInstruction(temp);
				System.out.println("IS " + temp.getId());
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (ID.free()) {
			temp1 = ID.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				ID.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (EX.free()) {
			temp1 = EX.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				EX.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (DF.free()) {
			temp1 = DF.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				DF.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (DS.free()) {
			temp1 = DS.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				DS.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (TC.free()) {
			temp1 = TC.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				TC.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		if (WB.free()) {
			temp1 = WB.getInstruction();
			if (temp == null) {
				temp = temp1;
			} else {
				WB.addInstruction(temp);
				temp = temp1;
			}
		} else {
			temp = null;
		}
		return true;
	}

}
