package arch.mips8.instruction.branch;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class BranchInstruction implements Instruction {

	Register r1, r2;
	long r1Val, r2Val;
	public long immd;
	int id;
	String name;
	boolean branchTaken;

	public void setName(String name) {
		this.name = name;
	}

	public String getInstructionName() {
		return this.name + " " + r1.name + " " + r2.name + " " + immd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BranchInstruction(Register r1, Register r2, long immd) {
		this.r1 = r1;
		this.r2 = r2;
		this.immd = immd;
	}

	public BranchInstruction(BranchInstruction instruction) {
		this.r1 = instruction.r1;
		this.r2 = instruction.r2;
		this.immd = instruction.immd;
		this.r1Val = instruction.r1Val;
		this.r2Val = instruction.r2Val;
		this.name = instruction.name;
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
		if (r2.contentAvailable(id) && r1.contentAvailable(id)) {
			r2Val = r2.getContent();
			r1Val = r1.getContent();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean executeEX() {
		return true;
	}

	@Override
	public boolean executeDF() {
		return true;
	}

	@Override
	public boolean executeDS() {
		return true;
	}

	@Override
	public boolean executeTC() {
		return true;
	}

	@Override
	public boolean executeWB() {
		return true;
	}

	@Override
	public BranchInstruction copy() {
		return new BranchInstruction(this);
	}

	public boolean getBranchTaken() {
		return branchTaken;
	}
}
