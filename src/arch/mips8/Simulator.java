package arch.mips8;

import arch.mips8.instruction.Instruction;

public class Simulator {
	Stage IF, ID, IS, RF, EX, DF, DS, TC, WB;
	boolean done;
	Globals globals;
	int instructionCounter;
	
	Simulator(Globals globals) {
		IF = new Stage();
		IS = new Stage();
		ID = new Stage();
		EX = new Stage();
		DF = new Stage();
		DS = new Stage();
		TC = new Stage();
		WB = new Stage();
		this.globals = globals;
		this.done = false;
		this.instructionCounter = 0;
	}
	
	void simulate(){		
		int instrIndex = 0;
		while (!done) {
			if (!WB.free()) {				
				WB.execute(8);
			}
			if (!TC.free()) {
				TC.execute(7);
			}
			if (!DS.free()) {
				DS.execute(6);
			}
			if (!DF.free()) {
				DF.execute(5);
			}
			if (!EX.free()) {
				EX.execute(4);
			}
			if (!ID.free()) {
				ID.execute(3);
			}
			if (!IS.free()) {
				IS.execute(2);
			}
			if (!IF.free()) {
				System.out.println("\n  Random " + IF.free());
				IF.execute(1);
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
				if(i != null){
					System.out.println("\n Push IS ----" + i);
				}
				IS.addInstruction(i);
				IF.addInstruction(null);
			}
			if(instrIndex >= globals.instructions.size()){
				IF.addInstruction(null);
			}
			if (instrIndex < globals.instructions.size() && IF.free()){
				
				Instruction i = globals.instructions.get(instrIndex).copy();
				System.out.println("\n Push IF" + i);
				i.setId(instructionCounter);
				instructionCounter++;
				IF.addInstruction(i);
				instrIndex++;
			}
			/*if (WB.free()) {
				Instruction i = WB.getInstruction();
				if (i.equals(null)) {
					done = true;
				}
			}*/
		}
	}
	
	
	
}
