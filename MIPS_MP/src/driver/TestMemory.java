package driver;

import machine.Memory;
import utils.Print;

public class TestMemory {

	public static void main(String[] args){
		Memory m = new Memory(2048); //2gb master race
		m.loadToMemory(0x0000, 4, 0x00080008); //4 bytes
		m.loadToMemory(0x0004, 4, 0xABAB0000);
		
		//using base methods from Memory Class, see Machine class for simplified version
		Print.as8bitHex(m.getFromMemory(0x0000, 1));//load byte
		Print.as16bitHex(m.getFromMemory(0x0000, 2));//load half word
		Print.as32bitHex(m.getFromMemory(0x0000, 4));//load word
		Print.as64bitHex(m.getFromMemory(0x0000, 8));//load double word
		
		Print.asHex(m.getFromMemory(0x0000, 2), 2);//alternatively, Print.asHex(value, num of hex numbers)
	}
}
