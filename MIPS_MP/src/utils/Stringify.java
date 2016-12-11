/**
 * 
 */
package utils;

/**
 * @author Chester
 *
 */
public class Stringify {

	/** down casting is done on some, not useful for testing actual values ***/
	public static String as8bitHex(long value) {
		return String.format("%02X", (byte) value);
	}

	public static String as64bitHex(long value) {
		return String.format("%016X", value);
	}

	public static String as32bitHex(long value) {
		return String.format("%08X", (int) value);
	}

	public static String as16bitHex(long value) {
		return String.format("%04X", (short) value);
	}

	//params: value, number of hex numbers to display
	public static String asHex(long value, int numhex) {
		return String.format("%0" + numhex + "X", value);
	}
}
