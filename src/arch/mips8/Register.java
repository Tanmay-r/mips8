package arch.mips8;

import java.util.ArrayList;

public class Register {
	public String name;
	public int lock;
	public long content;
	boolean forwardReady;
	long forwardContent;
	int fromId;
	int fromStage;
	int toId;
	int toStage;

	Register(String name) {
		this.name = name;
		lock = 65536;
		content = 0;
		forwardReady = false;
		forwardContent = 0;
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
	
	public long getForwardContent(){
		return forwardContent;
	}

	public void lockRegister(int instructionId) {
		if(instructionId < lock){
			System.out.println("Lock " + name + " " + instructionId);
			lock = instructionId;
		}
	}

	public void unlockRegister() {
		forwardReady = false;
		forwardContent = 0;
		lock = 65536;
	}

	public void setForward(long content, int id, int stage){
		forwardReady = true;
		forwardContent  = content;
		fromId = id;
		fromStage = stage;
	}
	
	public void setForwardTo(int id, int stage){
		toId = id;
		toStage = stage;
		ArrayList<Integer> newForwarding = new ArrayList<Integer> ();
		newForwarding.add(fromId);
		newForwarding.add(fromStage);
		newForwarding.add(toId);
		newForwarding.add(toStage);
		Globals.forwardings.add(newForwarding);
	}
	
	public boolean contentAvailable(int instructionId) {
		// TODO Modify with forwarding lock and have a new value to set while
		// forwarding
//		System.out.println(name + " " + instructionId + " " + lock);
		return !isLocked(instructionId);
	}
	
	public boolean forwardAvailable(){
		return forwardReady;
	}
}
