package arch.mips8.instruction.twoR;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class TwoRInstruction implements Instruction {

	Register r1, r2;
	Register lo, hi;
	long r1Val, r2Val,hiVal, loVal;
	int id;
	String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getInstructionName() {
		return this.name + " " + r1.name + " " + r2.name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TwoRInstruction(Register r1, Register r2) {
		this.r1 = r1;
		this.r2 = r2;
		this.lo = Globals.getRegister("lo");
		this.hi = Globals.getRegister("hi");
	}

	public TwoRInstruction(TwoRInstruction instruction) {
		this.r1 = instruction.r1;
		this.r2 = instruction.r2;
		this.r1Val = instruction.r1Val;
		this.r2Val = instruction.r2Val;
		this.name = instruction.name;
		this.hi = instruction.hi;
		this.lo = instruction.lo;
		this.hiVal = instruction.hiVal;
		this.loVal = instruction.loVal;
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
		hi.lockRegister(id);
		lo.lockRegister(id);
		if (r1.contentAvailable(id) && r2.contentAvailable(id)) {
			r1Val = r1.getContent();
			r2Val = r2.getContent();
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean executeEX() {
		hi.lockRegister(id);
		lo.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeDF() {
		hi.lockRegister(id);
		lo.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeDS() {
		hi.lockRegister(id);
		lo.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeTC() {
		hi.lockRegister(id);
		lo.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeWB() {
		hi.setContent(hiVal);
		lo.setContent(loVal);
		hi.unlockRegister();
		lo.unlockRegister();
		return true;
	}

	@Override
	public TwoRInstruction copy() {
		return new TwoRInstruction(this);
	}
}
