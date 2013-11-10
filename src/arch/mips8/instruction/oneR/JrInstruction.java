package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class JrInstruction  extends OneRInstruction{

	public JrInstruction(Register r1) {
		super(r1);
	}
	
	public JrInstruction(JrInstruction JrInstruction) {
		super(JrInstruction);
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		reg.setContent(current_pc - 1 + (super.r1Val/4));
		return true;
	}
	
	@Override
	public JrInstruction copy(){
		return new JrInstruction(this);
	}
	

}