/**
 * 
 */
package driver;

import config.Config;
import machine.Machine;
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
		//String code = "DADDIU r1, r0, 0x8001";
		String code = "XOR r5, r1, r3";
		int opcode = InstructionUtils.getInstructionEnum(code).getInstructionConverter().getOpcode(code);
		machine.storeWordToMemory(Config.CODE_START, opcode);
		
		System.out.print("Opcode fetched: ");
		Print.as32bitHex(machine.loadWordFromMemory(Config.CODE_START));
		
		machine.storeToGPR(1, 3);
		machine.storeToGPR(3, 5);
		machine.doIFCycle();
		machine.doIDCycle();
		machine.doExCycle();
		machine.doMemCycle();
		machine.doWBCycle();
		
		// I-type
		//System.out.print("R" + OpcodeUtils.rt(opcode) + " = ");
		//Print.as64bitHex(machine.loadFromGPR(1));
		
		// R-type
		System.out.print("R" + OpcodeUtils.rd(opcode) + " = ");
		Print.as64bitHex(machine.loadFromGPR(OpcodeUtils.rd(opcode)));
		System.out.println("done");
	}

}
