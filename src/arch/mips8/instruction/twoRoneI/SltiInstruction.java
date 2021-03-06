package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class SltiInstruction extends TwoRoneIInstruction {

	public SltiInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "slti";
	}

	public SltiInstruction(SltiInstruction sltiInstruction) {
		super(sltiInstruction);
		super.name = "slti";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		if ((int) super.r2Val < (int) super.immd)
			super.r1Val = 1;
		else
			super.r1Val = 0;
		return true;
	}

	@Override
	public SltiInstruction copy() {
		return new SltiInstruction(this);
	}

}
