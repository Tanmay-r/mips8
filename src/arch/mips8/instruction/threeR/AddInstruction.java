package arch.mips8.instruction.threeR;

import arch.mips8.Globals;
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
		super.r1Val = super.r2Val + super.r3Val;

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
	public AddInstruction copy() {
		return new AddInstruction(this);
	}

}
