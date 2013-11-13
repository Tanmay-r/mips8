package arch.mips8;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MemoryDisk {
	ArrayList<Byte> memory;
	
	MemoryDisk(){
		memory = new ArrayList<Byte>(10000);
	}
	
	public boolean storeInt(int integer,int index){
		if(index + 3 < 10000){
			for (int i = 0; i < 4; i++) {
			    memory.add(index, (byte)(integer >>> (i * 8)));
			    index++;
			}
			return true;
		}
		return false;		
	}
	
	public int getInt(int index){
		int result = 0;
		if(index + 3 < 10000){
			byte[] bytes = new byte[]{memory.get(index++), memory.get(index++), memory.get(index++), memory.get(index++)};
			result = ByteBuffer.wrap(bytes).getInt();
		}
		return result;
	}
	
	public boolean storeHalfword(int halfword, int index){
		if(index + 1 < 10000){
			memory.add(index, (byte)(halfword >>> 0));
			memory.add(index, (byte)(halfword >>> 8));
			return true;
		}		
		return false;
	}
	
	public int getHalfword(int index){
		int result = 0;
		if(index + 1 < 10000){
			byte[] bytes = new byte[]{memory.get(index++), memory.get(index++), 0, 0};
			result = ByteBuffer.wrap(bytes).getInt();		
		}
		return result;
	}
	
	public boolean storeByte(int byteStored, int index){
		if(index < 10000){
			memory.add(index, (byte)(byteStored >>> 0));
			return true;
		}
		return false;
	}
	
	public int getByte(int index){
		int result = 0;
		if(index < 10000){
			byte[] bytes = new byte[]{memory.get(index++), 0, 0, 0};
			result = ByteBuffer.wrap(bytes).getInt();		
		}
		return result;
	}
	
	
}
