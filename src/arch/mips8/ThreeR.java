package arch.mips8;

public class ThreeR implements Instruction {
	Register r1, r2, r3;
	float r1Val, r2Val, r3Val;

	public ThreeR(Register r1, Register r2, Register r3) {
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}

	@Override
	public boolean executeIF() {
		// TODO What ?
		return true;
	}

	@Override
	public boolean executeIS() {
		// TODO what?
		return true;
	}

	@Override
	public boolean executeID() {
		// TODO if not locked then also should be allowed to continue till there
		// is need to write ?? !r1.isLocked() && ??
		if (r2.contentAvailable() && r3.contentAvailable()) {
			r2Val = r2.getContent();
			r3Val = r3.getContent();
			r1.lockRegister();
			r1.canNotForward();
			r2.lockRegister();
			r3.lockRegister();
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean executeEX() {
		// TODO In child class
		return true;
	}

	@Override
	public boolean executeDF() {
		// TODO what?
		return true;
	}

	@Override
	public boolean executeDS() {
		// TODO what?
		return true;
	}

	@Override
	public boolean executeTC() {
		// TODO what?
		return true;
	}

	@Override
	public boolean executeWB() {
		// TODO Not necessarily locked might be unlocked in a case when some
		// other instruction wrote in it after it was locked
		if (r1.isLocked()) {
			r1.setContent(r1Val);
			r1.unlockRegister();
			return true;
		} else {
			return false;
		}
	}
}
