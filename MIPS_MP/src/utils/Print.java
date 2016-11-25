package utils;

import java.util.List;

public class Print {

	public static void byteAsHex(int value) {
		System.out.println(String.format("%02X", value));
	}

	public static void as64bitHex(int value) {
		System.out.println(String.format("%016X", value));
	}

	public static void as32bitHex(int value) {
		System.out.println(String.format("%08X", value));
	}
	
	public static void as16bitHex(int value) {
		System.out.println(String.format("%04X", value));
	}

	public static void asHex(int value, int numhex) {
		System.out.println(String.format("%0" + numhex + "X", value));
	}

	public static void allOpcode(List<Integer> code) {
		for (int opcode : code) {
			as32bitHex(opcode);
		}
	}
}
