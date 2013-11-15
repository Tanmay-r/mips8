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
		this.name = name;
	}

	public String getInstructionName() {
		return this.name + " " + r1.name;
	}

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
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeEX() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeDF() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeDS() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeTC() {
		r1.lockRegister(id);
		return true;
	}

	@Override
	public boolean executeWB() {
		r1.setContent(r1Val);
		r1.unlockRegister();
		return true;
	}

	@Override
	public OneRInstruction copy() {
		return new OneRInstruction(this);
	}
}
