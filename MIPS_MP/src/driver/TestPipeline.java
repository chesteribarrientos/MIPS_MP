/**
 * 
 */
package driver;

import java.util.ArrayList;

import config.Config;
import machine.Machine;
import utils.BranchUtils;
import utils.InstructionUtils;
import utils.OpcodeUtils;
import utils.Print;

/**
 * @author Chester
 *
 */
public class TestPipeline {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Machine machine = new Machine();
		String code = "DADDIU R5, R0, 0x0004";
		//String code = "XOR r5, r1, r3";
		//String code = "DSUBU r5, r1, r3";
		//String code = "SLT r5, r1, r3";
		//String code = "LD r4, 1000(r1)"; 
		//String code = "SD r4, 1000(r1)";
		int opcode = InstructionUtils.getInstructionEnum(code).getInstructionConverter().getOpcode(code);
		machine.storeWordToMemory(Config.CODE_START, opcode);
		
		System.out.print("Opcode fetched: ");
		Print.as32bitHex(machine.loadWordFromMemory(Config.CODE_START));
		
		// test R-Type
		// note: dsubu is not unsigned
		//machine.storeToGPR(1, 1);
		//machine.storeToGPR(3, 2);
		
		// test LD
		//machine.storeDoubleWordToMemory(0x1004, 5); // need to check out of bounds, invalid address
		//machine.storeToGPR(4, 70);
		
		machine.doIFCycle();
		machine.doIDCycle();
		machine.doExCycle();
		machine.doMemCycle();
		machine.doWBCycle();
		
		// I-type
		System.out.print("R" + OpcodeUtils.rt(opcode) + " = ");
		Print.as64bitHex(machine.loadFromGPR(OpcodeUtils.rt(opcode)));
		
		// R-type
		// XOR, DSUBU Example
		//System.out.print("R" + OpcodeUtils.rd(opcode) + " = ");
		//Print.as64bitHex(machine.loadFromGPR(OpcodeUtils.rd(opcode)));
		//System.out.println("done");
	}

}
