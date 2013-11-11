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
		System.out.println(IF.status() + " " + IS.status() + " " + ID.status()
				+ " " + EX.status() + " " + DF.status() + " " + DS.status()
				+ " " + TC.status() + " " + WB.status());
		IF.execute();
		IS.execute();
		ID.execute();
		EX.execute();
		DF.execute();
		DS.execute();
		TC.execute();
		WB.execute();
		Globals.instructionPipeLine
				.setInitialX(45 + Globals.instructionPipeLine.getInitialX());
		if (IF.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(IF.getInstruction()
					.getId(), 1, IF.status());
		} else if (IF.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(IF.getForward().getId(),
					1, IF.status());
		}
		if (IS.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(IS.getInstruction()
					.getId(), 2, IS.status());
		} else if (IS.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(IS.getForward().getId(),
					2, IS.status());
		}
		if (ID.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(ID.getInstruction()
					.getId(), 3, ID.status());
		} else if (ID.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(ID.getForward().getId(),
					3, ID.status());
		}
		if (EX.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(EX.getInstruction()
					.getId(), 4, EX.status());
		} else if (EX.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(EX.getForward().getId(),
					4, EX.status());
		}
		if (DF.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(DF.getInstruction()
					.getId(), 5, DF.status());
		} else if (DF.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(DF.getForward().getId(),
					5, DF.status());
		}
		if (DS.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(DS.getInstruction()
					.getId(), 6, DS.status());
		} else if (DS.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(DS.getForward().getId(),
					6, DS.status());
		}
		if (TC.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(TC.getInstruction()
					.getId(), 7, TC.status());
		} else if (TC.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(TC.getForward().getId(),
					7, TC.status());
		}
		if (WB.getInstruction() != null) {
			Globals.instructionPipeLine.setClockCycles(WB.getInstruction()
					.getId(), 8, WB.status());
		} else if (WB.getForward() != null) {
			Globals.instructionPipeLine.setClockCycles(WB.getForward().getId(),
					8, WB.status());
		}

		if (IF.free()) {
			IF.forward();
			int pc = (int) Globals.getRegister("pc").getContent();
			System.out.println("PC is " + pc + " "
					+ Globals.instructions.size());
			if (pc < Globals.instructions.size()) {
				Instruction i = Globals.instructions.get(pc).copy();
				i.setId(instructionCounter);
				instructionCounter++;
				IF.addInstruction(i);
			} else {
				IF.addInstruction(null);
			}
		}
		if (IS.free()) {
			IS.forward();
			IS.addInstruction(IF.getForward());
			IF.forwardDone();
		}
		if (ID.free()) {
			ID.forward();
			ID.addInstruction(IS.getForward());
			IS.forwardDone();
		}
		if (EX.free()) {
			EX.forward();
			EX.addInstruction(ID.getForward());
			ID.forwardDone();
		}
		if (DF.free()) {
			DF.forward();
			DF.addInstruction(EX.getForward());
			EX.forwardDone();
		}
		if (DS.free()) {
			DS.forward();
			DS.addInstruction(DF.getForward());
			DF.forwardDone();
		}
		if (TC.free()) {
			TC.forward();
			TC.addInstruction(DS.getForward());
			DS.forwardDone();
		}
		if (WB.free()) {
			WB.addInstruction(TC.getForward());
			TC.forwardDone();
		}
		return true;
	}

}
