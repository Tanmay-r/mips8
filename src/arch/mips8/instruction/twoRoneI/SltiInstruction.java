package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class SltiInstruction extends TwoRoneIInstruction {

	public SltiInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
	}
	
	public SltiInstruction(SltiInstruction sltiInstruction) {
		super(sltiInstruction);
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		if(super.r2Val < super.immd)super.r1Val=1;
		else super.r1Val=0;
		return true;
	}
	
	@Override
	public SltiInstruction copy(){
		return new SltiInstruction(this);
	}
	
	


}
