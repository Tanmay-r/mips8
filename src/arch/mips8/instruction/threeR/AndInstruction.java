package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class AndInstruction extends ThreeRInsruction {

	public AndInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "and";
	}

	public AndInstruction(AndInstruction andInstruction) {
		super(andInstruction);
		super.name = "and";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val & (int) super.r3Val);
		return true;
	}

	@Override
	public AndInstruction copy() {
		return new AndInstruction(this);
	}

}
