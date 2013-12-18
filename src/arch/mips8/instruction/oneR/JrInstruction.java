package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class JrInstruction extends OneRInstruction {
	Long r1Val;

	public JrInstruction(Register r1) {
		super(r1);
		super.name = "jr";
	}

	public JrInstruction(JrInstruction JrInstruction) {
		super(JrInstruction);
		super.name = "jr";
	}

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		reg.setContent(r1Val);
		if (r1.contentAvailable(id)) {
		} else if (Globals.forwardingEnable && r1.forwardAvailable()) {
			r1.setForwardTo(id, 4);
		}
		return true;
	}

	@Override
	public boolean executeID() {
		if (r1.contentAvailable(id)) {
			r1Val = r1.getContent();
			return true;
		} else if (Globals.forwardingEnable && r1.forwardAvailable()) {
			r1Val = r1.getForwardContent();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean executeDF() {
		return true;
	}

	@Override
	public boolean executeDS() {
		return true;
	}

	@Override
	public boolean executeTC() {
		return true;
	}

	@Override
	public boolean executeWB() {
		return true;
	}

	@Override
	public JrInstruction copy() {
		return new JrInstruction(this);
	}

}