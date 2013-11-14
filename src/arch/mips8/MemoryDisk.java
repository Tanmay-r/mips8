package arch.mips8;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MemoryDisk {
	int index;
	ArrayList<Byte> memory;

	MemoryDisk() {
		memory = new ArrayList<Byte>(16384);
		index = 0;
	}

	public boolean storeInt(int integer, int index) {

		System.out.println("memory call s " + index + " " +integer);
		if (index + 3 < 10000) {
			for (int i = 3; i >= 0; i--) {
				memory.add(index, (byte) (integer >>> (i * 8)));
				System.out.println(memory.get(index));
				index++;
			}
			return true;
		}
		
		return false;
	}

	public int storeInt(int integer) {
		if (index + 3 < 10000) {
			for (int i = 3; i >= 0; i--) {
				memory.add(index, (byte) (integer >>> (i * 8)));
				index++;
			}
			return index - 4;
		}
		return index;
	}

	public int getInt(int index) {
		int result = 0;
		if (index + 3 < 10000) {
			byte[] bytes = new byte[] { memory.get(index++),
					memory.get(index++), memory.get(index++),
					memory.get(index++) };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		System.out.println("memory call " + index + " " + result);
		return result;
	}

	public boolean storeHalfword(int halfword, int index) {
		if (index + 1 < 10000) {
			memory.add(index++, (byte) (halfword >>> 8));
			memory.add(index, (byte) (halfword >>> 0));
			return true;
		}
		return false;
	}

	public int storeHalfword(int halfword) {
		if (index + 1 < 10000) {
			memory.add(index++, (byte) (halfword >>> 8));
			memory.add(index++, (byte) (halfword >>> 0));
			return index - 2;
		}
		return -1;
	}

	public int getHalfword(int index) {
		int result = 0;
		if (index + 1 < 10000) {
			byte[] bytes = new byte[] { memory.get(index++),
					memory.get(index++), 0, 0 };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}

	public boolean storeByte(int byteStored, int index) {
		if (index < 10000) {
			memory.add(index, (byte) (byteStored >>> 0));
			return true;
		}
		return false;
	}

	public int storeByte(byte byteStored) {
		if (index < 10000) {
			memory.add(index, byteStored);
			return index++;
		}
		return -1;
	}

	public int getByte(int index) {
		int result = 0;
		if (index < 10000) {
			byte[] bytes = new byte[] { memory.get(index++), 0, 0, 0 };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}

}
