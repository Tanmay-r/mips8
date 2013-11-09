package arch.mips8.instruction;

import arch.mips8.Register;

public class ThreeRInsruction implements Instruction {
	Register r1, r2, r3;
	long r1Val, r2Val, r3Val;
	int id;

	//r1 depends on this instruction
	//this instruction depends on r2 and r3

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ThreeRInsruction(Register r1, Register r2, Register r3) {
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}

	public ThreeRInsruction(ThreeRInsruction addInstruction) {
		this.r1 = addInstruction.r1;
		this.r2 = addInstruction.r2;
		this.r3 = addInstruction.r3;
		this.r1Val = addInstruction.r1Val;
		this.r2Val = addInstruction.r2Val;
		this.r3Val = addInstruction.r3Val;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean executeIF() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeIS() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeID() {
		// TODO 
		r1.lockRegister(id);
		if (r2.contentAvailable(id) && r3.contentAvailable(id)) {
			r2Val = r2.getContent();
			r3Val = r3.getContent();
			r1.lockRegister(id);
			r2.lockRegister(id);
			r3.lockRegister(id);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean executeEX() {
		// TODO In child class
		r1.lockRegister(id);
		return true;
	}


	@Override
	public boolean executeDF() {
		// TODO what?
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeDS() {
		// TODO what?
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeTC() {
		// TODO what?
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeWB() {
		// TODO Not necessarily locked might be unlocked in a case when some
		// other instruction wrote in it after it was locked r1.isLocked()
		// should I check?
		r1.setContent(r1Val);
		r1.unlockRegister();
		return true;
	}
	
	@Override
	public ThreeRInsruction copy(){
		return new ThreeRInsruction(this);
	}
}
