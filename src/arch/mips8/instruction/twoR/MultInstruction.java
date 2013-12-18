package arch.mips8.instruction.twoR;

import arch.mips8.Globals;
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
		} else{
			if (r1.contentAvailable(id) && r2.contentAvailable(id)) {
				
			} else if (Globals.forwardingEnable && r1.forwardAvailable()
					&& r1.forwardAvailable()) {
				r1.setForwardTo(id, 4);
				r2.setForwardTo(id, 4);
			} else if (Globals.forwardingEnable && r1.forwardAvailable()
					&& r2.contentAvailable(id)) {
				r1.setForwardTo(id, 4);
			} else if (Globals.forwardingEnable && r2.forwardAvailable()
					&& r1.contentAvailable(id)) {
				r1.setForwardTo(id, 4);
			}
			if(Globals.forwardingEnable){
				lo.setForward(loVal, id, 4);
				hi.setForward(hiVal, id, 4);
			}
			return true;
		}
	}

	@Override
	public MultInstruction copy() {
		return new MultInstruction(this);
	}

}
