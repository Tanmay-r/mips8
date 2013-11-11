package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class OneRInstruction implements Instruction {

	Register r1;
	long r1Val;
	int id;
	String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getInstructionName() {
		return this.name+" "+r1.name;
		
	}
	// r1 depends on this instruction
	// this instruction depends on r2 and immd

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OneRInstruction(Register r1) {
		this.r1 = r1;
	}

	public OneRInstruction(OneRInstruction instruction) {
		this.r1 = instruction.r1;
		this.r1Val = instruction.r1Val;
		this.name = instruction.name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean executeIF() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		reg.setContent(current_pc + 1);
		return true;
	}

	@Override
	public boolean executeIS() {

		return true;
	}

	@Override
	public boolean executeID() {
		// TODO
		if (r1.contentAvailable(id)) {
			r1Val = r1.getContent();
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean executeEX() {
		// TODO In child class

		return true;
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

	@Override
	public OneRInstruction copy() {
		return new OneRInstruction(this);
	}
}
