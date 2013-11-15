package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class OriInstruction extends TwoRoneIInstruction {

	public OriInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "ori";
	}

	public OriInstruction(OriInstruction oriInstruction) {
		super(oriInstruction);
		super.name = "ori";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val | (int) super.immd);
		return true;
	}

	@Override
	public OriInstruction copy() {
		return new OriInstruction(this);
	}

}
