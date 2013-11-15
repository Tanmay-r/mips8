package arch.mips8;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MemoryDisk {
	int index;
	public ArrayList<Byte> memory;

	MemoryDisk() {
		memory = new ArrayList<Byte>(16384);
		for (int i = 0; i < 16384; i++) {
			memory.add(i, (byte) 0);
		}
		index = 0;
	}

	public boolean storeInt(int integer, int index) {
		if (index + 3 < 16384) {
			for (int i = 3; i >= 0; i--) {
				memory.set(index, (byte) (integer >>> (i * 8)));
				index++;
			}
			return true;
		}

		return false;
	}

	public int storeInt(int integer) {
		if (index + 3 < 16384) {
			for (int i = 3; i >= 0; i--) {
				memory.set(index, (byte) (integer >>> (i * 8)));
				index++;
			}
			return index - 4;
		}
		return index;
	}

	public int getInt(int index) {
		int result = 0;
		if (index + 3 < 16384) {
			byte[] bytes = new byte[] { memory.get(index++),
					memory.get(index++), memory.get(index++),
					memory.get(index++) };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}

	public boolean storeHalfword(int halfword, int index) {

		if (index + 1 < 16384) {
			memory.set(index++, (byte) (halfword >>> 8));
			memory.set(index, (byte) (halfword >>> 0));
			return true;
		}
		return false;
	}

	public int storeHalfword(int halfword) {
		if (index + 1 < 16384) {
			memory.set(index++, (byte) (halfword >>> 8));
			memory.set(index++, (byte) (halfword >>> 0));
			return index - 2;
		}
		return -1;
	}

	public int getHalfword(int index) {
		int result = 0;
		if (index + 1 < 16384) {
			byte[] bytes = new byte[] { memory.get(index++),
					memory.get(index++), 0, 0 };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}

	public boolean storeByte(int byteStored, int index) {
		if (index < 16384) {
			memory.set(index, (byte) (byteStored >>> 0));
			return true;
		}
		return false;
	}

	public int storeByte(byte byteStored) {
		if (index < 16384) {
			memory.set(index, byteStored);
			return index++;
		}
		return -1;
	}

	public int getByte(int index) {
		int result = 0;
		if (index < 16384) {
			byte[] bytes = new byte[] { memory.get(index++), 0, 0, 0 };
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}

}
