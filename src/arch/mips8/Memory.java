package arch.mips8;

public class Memory {
	MemoryDisk dataMemory;
	MemoryDisk stackMemory;
	
	public Memory() {
		// TODO Auto-generated constructor stub
		dataMemory = new MemoryDisk();
		stackMemory = new MemoryDisk();
	}

	public int getWord(int index){
		return dataMemory.getInt(index);
	}
	
	public int getHalfWord(int index){
		return dataMemory.getHalfword(index);
	}
	
	public int getByte(int index){
		return dataMemory.getByte(index);
	}
	
	
}