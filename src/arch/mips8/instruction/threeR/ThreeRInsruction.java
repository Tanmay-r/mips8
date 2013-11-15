package arch.mips8.instruction.threeR;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class ThreeRInsruction implements Instruction {
	Register r1, r2, r3;
	long r1Val, r2Val, r3Val;
	int id;
	String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getInstructionName() {
		return this.name + " " + r1.name + " " + r2.name + " " + r3.name;
	}

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
		this.name = addInstruction.name;
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
		if (r2.contentAvailable(id) && r3.contentAvailable(id)) {
			r2Val = r2.getContent();
			r3Val = r3.getContent();
			r1.lockRegister(id);
			return true;
		} else {
			return false;
		}

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
	public ThreeRInsruction copy() {
		return new ThreeRInsruction(this);
	}
}
