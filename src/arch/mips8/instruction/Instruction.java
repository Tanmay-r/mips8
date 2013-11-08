package arch.mips8.instruction;

public interface Instruction {
	
	public boolean executeIF();

	public boolean executeIS();

	public boolean executeID();

	public boolean executeEX();

	public boolean executeDF();

	public boolean executeDS();

	public boolean executeTC();

	public boolean executeWB();
	
	public int getId();
	
	public void setId(int id);

	public Instruction copy();

}
