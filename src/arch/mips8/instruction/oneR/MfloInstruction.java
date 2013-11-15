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
		} else {
			return false;
		}
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		Register lo = Globals.getRegister("lo");
		super.r1Val = lo.getContent();
		return true;
	}

	@Override
	public MfloInstruction copy() {
		return new MfloInstruction(this);
	}

}