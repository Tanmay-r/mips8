package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class SwInstruction extends TwoRoneIInstruction {

	private int addr;

	public SwInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "sw";
	}

	public SwInstruction(SwInstruction swInstruction) {
		super(swInstruction);
		super.name = "sw";
	}

	@Override
	public boolean executeEX() {
		addr = (int) (super.r2Val + (int) super.immd);
		return true;
	}

	@Override
	public boolean executeID() {
		if (r2.contentAvailable(id) && r1.contentAvailable(id)) {
			r1Val = r1.getContent();
			r2Val = r2.getContent();
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean executeDS() {
		boolean b;
		if (super.r2.name.equals("$sp")) {
			b = Globals.memory.stackMemory.storeInt((int) super.r1Val, addr);
		} else {
			b = Globals.memory.dataMemory.storeInt((int) super.r1Val, addr);
		}
		return b;
	}

	@Override
	public boolean executeDF() {
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
	public SwInstruction copy() {
		return new SwInstruction(this);
	}

	@Override
	public String getInstructionName() {
		return this.name + " " + r1.name + " " + immd + " (" + r2.name + ")";
	}
}
