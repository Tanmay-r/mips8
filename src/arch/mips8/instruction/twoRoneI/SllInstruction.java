package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class SllInstruction extends TwoRoneIInstruction {

	public SllInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "sll";
	}

	public SllInstruction(SllInstruction sllInstruction) {
		super(sllInstruction);
		super.name = "sll";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val << (int) super.immd);
		return true;
	}

	@Override
	public SllInstruction copy() {
		return new SllInstruction(this);
	}

}
