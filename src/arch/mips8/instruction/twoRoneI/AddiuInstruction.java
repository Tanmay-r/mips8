package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class AddiuInstruction extends TwoRoneIInstruction {

	public AddiuInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name="addiu";
	}
	
	public AddiuInstruction(AddiuInstruction addiuInstruction) {
		super(addiuInstruction);
		super.name="addiu";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val + super.immd;
		return true;
	}
	
	@Override
	public AddiuInstruction copy(){
		return new AddiuInstruction(this);
	}
	
	


}
