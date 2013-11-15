package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class SltInstruction extends ThreeRInsruction {

	public SltInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "slt";
	}

	public SltInstruction(SltInstruction sltInstruction) {
		super(sltInstruction);
		super.name = "slt";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		if ((int) super.r2Val < (int) super.r3Val)
			super.r1Val = 1;
		else
			super.r1Val = 0;
		return true;
	}

	@Override
	public SltInstruction copy() {
		return new SltInstruction(this);
	}

}
