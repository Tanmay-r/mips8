package arch.mips8.instruction.twoRoneI;
import arch.mips8.Globals;
import arch.mips8.Register;

public class BneInstruction extends TwoRoneIInstruction {

	public BneInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
	}

	public BneInstruction(BneInstruction bneInstruction) {
		super(bneInstruction);
		// TODO Auto-generated constructor stub
	}

	// This should have two types of registers
	// 1 - the registers on whom the instruction depends - r2, r3
	// 2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		if (super.r1Val != super.r2Val)reg.setContent(current_pc - 1 + super.immd);
		return true;
	}

	
	@Override
	public boolean executeIS() {
		
		return true;
	}

	@Override
	public boolean executeID() {
		// TODO 
		if (r1.contentAvailable(id) && r2.contentAvailable(id)) {
			r1Val = r1.getContent();
			r2Val = r2.getContent();	
			return true;
		} else {
			return false;
		}

	}


	@Override
	public boolean executeDF() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeDS() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeTC() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeWB() {
		// TODO Not necessarily locked might be unlocked in a case when some
		// other instruction wrote in it after it was locked r1.isLocked()
		// should I check?
		return true;
	}
	

}
