package arch.mips8.instruction.twoR;

import arch.mips8.Register;

public class MultInstruction extends TwoRInstruction {

	private int multCounter = 4;

	public MultInstruction(Register r1, Register r2) {
		super(r1, r2);
		super.name = "mult";
	}

	public MultInstruction(MultInstruction multInstruction) {
		super(multInstruction);
		super.name = "mult";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		long mul = super.r1Val * super.r2Val;
		loVal = (mul & (0xffffffff));
		hiVal = (mul >> 32);
		if (multCounter > 1) {
			multCounter--;
			return false;
		} else
			return true;
	}

	@Override
	public MultInstruction copy() {
		return new MultInstruction(this);
	}

}
