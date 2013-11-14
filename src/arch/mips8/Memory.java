package arch.mips8;

public class Memory {
	public MemoryDisk dataMemory;
	public MemoryDisk stackMemory;

	public Memory() {
		dataMemory = new MemoryDisk();
		stackMemory = new MemoryDisk();
	}
}