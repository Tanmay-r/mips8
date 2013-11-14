package arch.mips8;

public class DataType {
	String tag;
	String type;
	int index;
	int size;

	public DataType(String tag, String type, int index, int size) {
		this.tag = tag;
		this.type = type;
		this.index = index;
		this.size = size;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
