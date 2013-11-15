package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class TwoRoneIInstruction implements Instruction {

	Register r1, r2;
	long r1Val, r2Val, immd;
	int id;
	String name;

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

	public TwoRoneIInstruction(Register r1, Register r2, long immd) {
		this.r1 = r1;
		this.r2 = r2;
		this.immd = immd;
	}

	public TwoRoneIInstruction(TwoRoneIInstruction instruction) {
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
		r1.lockRegister(id);
		if (r2.contentAvailable(id)) {
			r2Val = r2.getContent();
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
	public TwoRoneIInstruction copy() {
		return new TwoRoneIInstruction(this);
	}
}
