package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
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
		if (Globals.forwardingEnable && r2.forwardAvailable()) {
			r2.setForwardTo(id, 4);
		}
		if(Globals.forwardingEnable){
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public AddiuInstruction copy() {
		return new AddiuInstruction(this);
	}

}
