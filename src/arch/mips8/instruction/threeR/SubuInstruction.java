package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class SubuInstruction extends ThreeRInsruction {

	public SubuInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "subu";
	}

	public SubuInstruction(SubuInstruction subuInstruction) {
		super(subuInstruction);
		super.name = "subu";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val - super.r3Val;
		return true;
	}

	@Override
	public SubuInstruction copy() {
		return new SubuInstruction(this);
	}

}
