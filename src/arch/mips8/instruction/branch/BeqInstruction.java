package arch.mips8.instruction.branch;

import arch.mips8.Globals;
import arch.mips8.Register;

public class BeqInstruction extends BranchInstruction {
	public boolean branchTaken = false;
	long prev_pc = 0;

	public BeqInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "beq";
	}

	public BeqInstruction(BeqInstruction beqInstruction) {
		super(beqInstruction);
		super.name = "beq";
	}

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		if ((int) super.r1Val == (int) super.r2Val) {
			reg.setContent(prev_pc + (int) super.immd);
			super.branchTaken = true;
		}
		if (r1.contentAvailable(id) && r2.contentAvailable(id)) {
		} else if (Globals.forwardingEnable && r1.forwardAvailable()
				&& r2.forwardAvailable()) {
			r1.setForwardTo(id, 4);
			r2.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r1.forwardAvailable()
				&& r2.contentAvailable(id)) {
			r1.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r1.contentAvailable(id)) {
			r2.setForwardTo(id, 4);
		}
		return true;
	}

	@Override
	public boolean executeIF() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		prev_pc = current_pc;
		reg.setContent(current_pc + 1);
		return true;
	}

	@Override
	public BeqInstruction copy() {
		return new BeqInstruction(this);
	}

}
