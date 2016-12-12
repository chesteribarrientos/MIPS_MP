/**
 * 
 */
package utils;

import javax.swing.plaf.synth.SynthSeparatorUI;

import config.Config;
import config.Opcode;
import machine.Machine;

/**
 * @author Chester
 *
 */
public class OpcodeUtils {

	/** get parts of opcode **/
	public static int opcode6(int opcode) {
		return opcode >>> 26;
	}

	public static int rs(int opcode) {
		return opcode >>> 21 & 0b11111;
	}

	public static int rt(int opcode) {
		return opcode >>> 16 & 0b11111;
	}
	
	public static int rd(int opcode){
		return opcode >>> 11 & 0b11111;
	}
	
	public static short imm(int opcode) {
		return (short) (opcode & 0xFFFF);
	}

	public static int opcode21(int opcode) {
		return opcode & 0b11111111111;
	}

	public static int opcodeFunc(int opcode) {
		return opcode & 0b111111;
	}

	public static boolean isRType(int opcode){
		return opcode6(opcode) == Opcode.RType;
	}
	
	public static boolean isNOP(int opcode){
		return opcode == 0;
	}
	public static boolean isSD(int opcode){
		return opcode6(opcode) == Opcode.SD;
	}
	/** for type detection **/
	public static boolean isBranch(int opcode) {
		if (Config.BRANCH_SET.contains(opcode6(opcode))) {
			return true;
		}
		return false;
	}

	public static boolean opOutput(int opcode, Machine m) {
		long rsValue = m.loadFromGPR(rs(opcode));
		long rtValue = m.loadFromGPR(rt(opcode));
		System.out.println("rs in branch: " + rsValue);
		System.out.println("rt in branch: " + rtValue);
		switch (opcode6(opcode)) {
		case Opcode.BEQC:
			return rsValue == rtValue;
		case Opcode.BC:
			return true;
		default:
			System.out.println("Jump/Branch instruction missing in OpcodeUtils. Defaulting to false");
			return false;
		}
	}

	public static boolean isCompactBranch(int opcode) {
		if (Config.BRANCH_COMPACT_SET.contains(opcode6(opcode))) {
			return true;
		}
		return false;
	}
}
