package arch.mips8.instruction.threeR;

import arch.mips8.Globals;
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
		if (r2.contentAvailable(id) && r3.contentAvailable(id)) {
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.forwardAvailable()) {
			r2.setForwardTo(id, 4);
			r3.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.contentAvailable(id)) {
			r2.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r3.forwardAvailable()
				&& r2.contentAvailable(id)) {
			r3.setForwardTo(id, 4);
		}
		if (Globals.forwardingEnable) {
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public SubInstruction copy() {
		return new SubInstruction(this);
	}

}
