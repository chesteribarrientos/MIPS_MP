package driver;

import machine.Memory;
import utils.Print;

public class TestMemory {

	public static void main(String[] args){
		Memory m = new Memory(2048); //2gb master race
		m.loadToMemory(0x0000, 4, 0x0F0F0F0F); //4 bytes
		m.loadToMemory(0x0004, 4, 0xABABABAB);
		
		/* note: if print size exceeds actual, padding is unsigned */
		
		//Print.byteAsHex(m.getFromMemory(0x0000, 1));//load byte
		//Print.as16bitHex(m.getFromMemory(0x0000, 2));//load half word
		//Print.as32bitHex(m.getFromMemory(0x0000, 4));//load word
		Print.as64bitHex(m.getFromMemory(0x0000, 8));//load double word
		
		Print.asHex(m.getFromMemory(0x0000, 2), 8);//alternatively, Print.asHex(value, num of hex numbers)
	}
}
