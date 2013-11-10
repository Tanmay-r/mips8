package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class MulInstruction extends ThreeRInsruction {

	public MulInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
	}
	
	public MulInstruction(MulInstruction mulInstruction) {
		super(mulInstruction);
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val * super.r3Val;
		return true;
	}
	
	@Override
	public MulInstruction copy(){
		return new MulInstruction(this);
	}
	
	


}
