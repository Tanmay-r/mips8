package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class AddInstruction extends ThreeRInsruction {

	public AddInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "add";
	}

	public AddInstruction(AddInstruction addInstruction) {
		super(addInstruction);
		super.name = "add";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val + (int) super.r3Val);
		return true;
	}

	@Override
	public AddInstruction copy() {
		return new AddInstruction(this);
	}

}
