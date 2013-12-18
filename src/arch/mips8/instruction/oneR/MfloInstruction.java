package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class MfloInstruction extends OneRInstruction {
	Register lo;
	Long loVal;

	public MfloInstruction(Register r1) {
		super(r1);
		super.name = "mflo";
		lo = Globals.getRegister("lo");
	}

	public MfloInstruction(MfloInstruction mfloInstruction) {
		super(mfloInstruction);
		super.name = "mflo";
		lo = Globals.getRegister("lo");
	}

	@Override
	public boolean executeID() {
		super.executeID();
		if (lo.contentAvailable(id)) {
			loVal = lo.getContent();
			return true;
		} else if (Globals.forwardingEnable && lo.forwardAvailable()) {
			loVal = lo.getForwardContent();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = loVal;

		if (lo.contentAvailable(id)) {
		} else if (Globals.forwardingEnable && lo.forwardAvailable()) {
			lo.setForwardTo(id, 4);
		}
		if (Globals.forwardingEnable) {
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public MfloInstruction copy() {
		return new MfloInstruction(this);
	}

}