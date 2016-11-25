package utils;

import java.util.List;

public class Print {

	public static void as32bitHex(int value){
		System.out.println(String.format("%08X", value));
	}
	public static void allOpcode(List<Integer> code){
		for(int opcode: code){
			as32bitHex(opcode);
		}
	}
}
