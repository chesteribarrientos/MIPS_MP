package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import machine.EXMEM;
import machine.IDEX;
import machine.IFID;
import machine.MEMWB;
import machine.Pipeline;

/**
 * 
 * @author Chester
 *
 */
public class Print {

	/** note padding is unsigned **/
	// for testing
	public static void longValue(long value, int radix) {
		System.out.println(Long.toUnsignedString(value, radix));
	}
	//

	public static void as8bitHex(byte value) {
		System.out.println(String.format("%08X", value));
	}

	/** down casting is done on some, not useful for testing actual values ***/
	public static void as8bitHex(long value) {
		System.out.println(String.format("%02X", (byte) value));
	}

	public static void as64bitHex(long value) {
		System.out.println(String.format("%016X", value));
	}

	public static void as32bitHex(long value) {
		System.out.println(String.format("%08X", (int) value));
	}

	public static void as16bitHex(long value) {
		System.out.println(String.format("%04X", (short) value));
	}

	public static void asHex(long value, int numhex) {
		System.out.println(String.format("%0" + numhex + "X", value));
	}

	public static void allOpcode(List<Integer> code) {
		for (int opcode : code) {
			as32bitHex(opcode);
		}
	}
	
	public static void pipeline(HashMap<String,Pipeline> pipeline){
		IFID ifid = (IFID) pipeline.get("IF/ID");
		IDEX idex = (IDEX) pipeline.get("ID/EX");
		EXMEM exmem = (EXMEM) pipeline.get("EX/MEM");
		MEMWB memwb = (MEMWB) pipeline.get("MEM/WB");
		
		System.out.println(ifid.toString());
		System.out.println("******");
		System.out.println(idex.toString());
		System.out.println("******");
		System.out.println(exmem.toString());
		System.out.println("******");
		System.out.println(memwb.toString());
		System.out.println("******");
		
	}
}
