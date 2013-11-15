package arch.mips8.instruction.threeR;

import arch.mips8.Register;

public class SltuInstruction extends ThreeRInsruction {

	public SltuInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "sltu";
	}

	public SltuInstruction(SltuInstruction sltuInstruction) {
		super(sltuInstruction);
		super.name = "sltu";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		if (super.r2Val < super.r3Val)
			super.r1Val = 1;
		else
			super.r1Val = 0;
		return true;
	}

	@Override
	public SltuInstruction copy() {
		return new SltuInstruction(this);
	}

}
