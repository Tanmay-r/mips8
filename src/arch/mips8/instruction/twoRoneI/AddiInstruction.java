package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class AddiInstruction extends TwoRoneIInstruction {

	public AddiInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "addi";
	}

	public AddiInstruction(AddiInstruction addiInstruction) {
		super(addiInstruction);
		super.name = "addi";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = (long) ((int) super.r2Val + (int) super.immd);
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
	public AddiInstruction copy() {
		return new AddiInstruction(this);
	}

}
