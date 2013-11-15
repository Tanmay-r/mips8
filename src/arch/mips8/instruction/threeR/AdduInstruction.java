package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class AdduInstruction extends ThreeRInsruction {

	public AdduInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "addu";
	}

	public AdduInstruction(AdduInstruction adduInstruction) {
		super(adduInstruction);
		super.name = "addu";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val + super.r3Val;
		return true;
	}

	@Override
	public AdduInstruction copy() {
		return new AdduInstruction(this);
	}

}
