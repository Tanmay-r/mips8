package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class SrlInstruction extends TwoRoneIInstruction {

	public SrlInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name="srl";
	}
	
	public SrlInstruction(SrlInstruction srlInstruction) {
		super(srlInstruction);
		super.name="srl";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long)( (int)super.r2Val >> (int)super.immd);
		return true;
	}
	
	@Override
	public SrlInstruction copy(){
		return new SrlInstruction(this);
	}
	
	


}
