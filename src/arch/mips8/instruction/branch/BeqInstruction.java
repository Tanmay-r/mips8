package arch.mips8.instruction.branch;

import arch.mips8.Globals;
import arch.mips8.Register;

public class BeqInstruction extends branch {
	public boolean branchTaken = false;
	long prev_pc = 0;
	
	public BeqInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name="beq";
	}
	
	public BeqInstruction(BeqInstruction beqInstruction) {
		super(beqInstruction);
		super.name="beq";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		System.out.println("print this shit" + ((int)super.r1Val == (int)super.r2Val));
		if ( (int)super.r1Val == (int)super.r2Val){
			reg.setContent(prev_pc + (int)super.immd);
			super.branchTaken = true;
		}
		return true;
	}
	
	@Override
	public boolean executeIF() {
		System.out.println("If shit");
		Register reg =Globals.getRegister("pc");
		long current_pc = reg.getContent();
		prev_pc  = current_pc;
		reg.setContent(current_pc+1);
		return true;
	}
	
	@Override
	public BeqInstruction copy(){
		return new BeqInstruction(this);
	}
	
}
