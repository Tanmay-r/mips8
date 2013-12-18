package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class OriInstruction extends TwoRoneIInstruction {

	public OriInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "ori";
	}

	public OriInstruction(OriInstruction oriInstruction) {
		super(oriInstruction);
		super.name = "ori";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val | (int) super.immd);
		if (r2.contentAvailable(id)) {

		} else if (Globals.forwardingEnable && r2.forwardAvailable()) {
			r2.setForwardTo(id, 4);
		}
		if(Globals.forwardingEnable){
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public OriInstruction copy() {
		return new OriInstruction(this);
	}

}
