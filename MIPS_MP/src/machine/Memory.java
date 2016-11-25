package machine;

import java.util.ArrayList;
import java.util.List;

import utils.Print;

public class Memory {

	private List<Integer> memory;
	
	public Memory(int memorysize){
		memory = new ArrayList<Integer>();
		for(int i = 0; i < memorysize; i++){
			memory.add(0);
		}
	}
	
	/* size in byte */
	public int getFromMemory(int address, int size){
		int value = 0;
		for(int i = 0; i < size; i++){
			int currentByte = memory.get(address + i);
			value = value | (currentByte << (8 * i));
		}
		return value;
	}
	
	public void loadToMemory(int address, int size, long value){
		for(int i = 0; i < size; i++){
			long currentByte = ((value >> (8 * i)) & 0xFF);
			//Print.byteAsHex(currentByte);
			memory.set(address + i, (int)currentByte);
		}
	}
}
