package arch.mips8;

public interface Instruction {


	public boolean executeIF();

	public boolean executeIS();

	public boolean executeID();

	public boolean executeEX();

	public boolean executeDF();

	public boolean executeDS();

	public boolean executeTC();

	public boolean executeWB();

}
