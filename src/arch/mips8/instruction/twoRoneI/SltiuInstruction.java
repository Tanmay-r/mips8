package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class SltiuInstruction extends TwoRoneIInstruction {

	public SltiuInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "sltui";
	}

	public SltiuInstruction(SltiuInstruction sltiuInstruction) {
		super(sltiuInstruction);
		super.name = "sltui";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		if (super.r2Val < super.immd)
			super.r1Val = 1;
		else
			super.r1Val = 0;
		return true;
	}

	@Override
	public SltiuInstruction copy() {
		return new SltiuInstruction(this);
	}

}
