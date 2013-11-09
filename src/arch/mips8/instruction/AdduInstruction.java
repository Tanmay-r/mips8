package arch.mips8.instruction;

import arch.mips8.Register;

public class AdduInstruction extends ThreeRInsruction {

	public AdduInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
	}
	
	public AdduInstruction(AdduInstruction adduInstruction) {
		super(adduInstruction);
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val + super.r3Val;
		return true;
	}
	
	@Override
	public AdduInstruction copy(){
		return new AdduInstruction(this);
	}

}

