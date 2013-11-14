package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class BeqInstruction extends TwoRoneIInstruction {
	boolean branchTaken = false;
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
		if ( (int)super.r1Val == (int)super.r2Val){
			reg.setContent(prev_pc + (int)super.immd);
			branchTaken = true;
		}
		return true;
	}
	
	@Override
	public boolean executeIF() {
		Register reg =Globals.getRegister("pc");
		long current_pc = reg.getContent();
		prev_pc  = current_pc;
		reg.setContent(current_pc+1);
		r1.lockRegister(id);
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
	
	public boolean getBranchTaken(){
		return branchTaken;
	}
	
}
