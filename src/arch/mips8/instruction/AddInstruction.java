package arch.mips8.instruction;

import arch.mips8.Register;

public class AddInstruction extends ThreeRInsruction {

	public AddInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
	}

	@Override
	public boolean executeEX() {
		super.r1Val = super.r2Val + super.r3Val;
		super.r1.fContent = super.r1Val;
		super.r1.canForward();
		return true;
	}

}
