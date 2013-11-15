package arch.mips8.instruction.twoRoneI;

import arch.mips8.Register;

public class AddiuInstruction extends TwoRoneIInstruction {

	public AddiuInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "addiu";
	}

	public AddiuInstruction(AddiuInstruction addiuInstruction) {
		super(addiuInstruction);
		super.name = "addiu";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = super.r2Val + super.immd;
		return true;
	}

	@Override
	public AddiuInstruction copy() {
		return new AddiuInstruction(this);
	}

}
