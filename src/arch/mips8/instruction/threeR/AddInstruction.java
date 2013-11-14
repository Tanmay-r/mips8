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
		if (r2.contentAvailable(id) && r3.contentAvailable(id)) {
			super.r1Val = (long)((int)super.r2Val + (int)super.r3Val);
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.forwardAvailable()) {
			return true;
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.contentAvailable(id)) {
			r3Val = r3.getContent();
			return true;
		} else if (Globals.forwardingEnable && r3.forwardAvailable()
				&& r2.contentAvailable(id)) {
			r2Val = r2.getContent();
			return true;
		}
		if(Globals.forwardingEnable && r2.forwardAvailable() && r3.forwardAvailable()){
			super.r1Val = r2.getForwardContent() + r3.getForwardContent();
			r2.setForwardTo(id, stage);
		}
		
		if(Globals.forwardingEnable){
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public AddInstruction copy() {
		return new AddInstruction(this);
	}

}
