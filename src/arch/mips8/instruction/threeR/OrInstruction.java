package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class OrInstruction extends ThreeRInsruction {

	public OrInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "or";
	}

	public OrInstruction(OrInstruction orInstruction) {
		super(orInstruction);
		super.name = "or";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val | (int) super.r3Val);
		return true;
	}

	@Override
	public OrInstruction copy() {
		return new OrInstruction(this);
	}

}
