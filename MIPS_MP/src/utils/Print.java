package utils;

import java.util.List;

public class Print {

	/** note padding is unsigned **/
	
	//for testing
	public static void longValue(long value, int radix){ 
		System.out.println(Long.toUnsignedString(value,radix));
	}
	//
	
	public static void as8bitHex(byte value) {
		System.out.println(String.format("%08X", value));
	}
	
	public static void as8bitHex (long value) {
		System.out.println(String.format("%02X", value));
	}
	
	public static void as64bitHex(long value) {
		System.out.println(String.format("%016X", value));
	}

	public static void as32bitHex(long value) {
		System.out.println(String.format("%08X", value));
	}
	
	public static void as16bitHex(long value) {
		System.out.println(String.format("%04X", value));
	}

	public static void asHex(long value, int numhex) {
		System.out.println(String.format("%0" + numhex + "X", value));
	}

	public static void allOpcode(List<Integer> code) {
		for (int opcode : code) {
			as32bitHex(opcode);
		}
	}
}
