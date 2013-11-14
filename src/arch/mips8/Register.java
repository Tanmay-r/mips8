package arch.mips8;

public class Register {
	public String name;
	public int lock;
	public long content;

	Register(String name) {
		this.name = name;
		lock = 65536;
		content = 0;
	}

	public boolean isLocked(int instructionId) {
		return lock < instructionId;
	}

	public void setContent(long newContent) {
		content = newContent;
	}

	public long getContent() {
		return content;
	}

	public void lockRegister(int instructionId) {
		if(instructionId < lock){
			System.out.println("Lock " + name + " " + instructionId);
			lock = instructionId;
		}
	}

	public void unlockRegister() {
		lock = 65536;
	}

	public boolean contentAvailable(int instructionId) {
		// TODO Modify with forwarding lock and have a new value to set while
		// forwarding
//		System.out.println(name + " " + instructionId + " " + lock);
		return !isLocked(instructionId);
	}
}
