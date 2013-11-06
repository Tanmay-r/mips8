package arch.mips8;

public class Register {
	public String name;
	public boolean lock;
	public boolean forward;
	public float content, fContent;

	Register(String name) {
		this.name = name;
		lock = false;
		forward = true;
		content = 0;
		fContent = 0;
	}

	public boolean isLocked() {
		return lock;
	}

	public void setContent(float newContent) {
		content = newContent;
		fContent = newContent;
	}

	public float getContent() {
		if(forward) return fContent;
		return content;
	}

	public void lockRegister() {
		lock = true;
	}

	public void unlockRegister() {
		lock = false;
	}

	public void canForward() {
		forward = true;
	}

	public void canNotForward() {
		forward = false;
	}

	public boolean contentAvailable() {
		// TODO Modify with forwarding lock and have a new value to set while
		// forwarding
		return !(lock) || forward;
	}
}
