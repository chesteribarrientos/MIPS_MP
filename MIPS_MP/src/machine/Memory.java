package machine;

import java.util.ArrayList;
import java.util.List;

import utils.Print;

public class Memory {

	private List<Byte> memory;
	
	public Memory(int memorysize){
		memory = new ArrayList<Byte>();
		for(int i = 0; i < memorysize; i++){
			memory.add((byte) 0);
		}
	}
	
	/* size in byte */
	public long getFromMemory(int address, int size){
		long value = 0;
		for(int i = size - 1; i >= 0; i--){
			byte currentByte = memory.get(address + i);
			value = value | (currentByte & 0xFF);
			if(i > 0)
				value = value << 8;
		}
		return value;
	}
	
	public void loadToMemory(int address, int size, long value){
		for(int i = 0; i < size; i++){
			byte currentByte = (byte) ((value >> (8 * i)) & 0xFF);
			memory.set(address + i, currentByte);
		}
	}
}
