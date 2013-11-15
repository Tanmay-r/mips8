package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class SubInstruction extends ThreeRInsruction {

	public SubInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "sub";
	}

	public SubInstruction(SubInstruction subInstruction) {
		super(subInstruction);
		super.name = "sub";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val - (int) super.r3Val);
		return true;
	}

	@Override
	public SubInstruction copy() {
		return new SubInstruction(this);
	}

}
