package instruction_set;

import machine.MEMWB;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * 
 * @author Chester
 *
 */

public class MemoryInstruction {

	public int getPartial(String statement) {
                //System.out.println("Statement: "+statement);
		String[] words = statement.split("[(),\\s]+");

		int base = Integer.parseInt(words[3].substring(1));
		int rt = Integer.parseInt(words[1].substring(1));
		int offset = Integer.parseInt(words[2], 16);

		int finalOpcode = (base << 21) | (rt << 16) | offset;
		return finalOpcode;
	}
	
	//warning: use only on load instructions
	public void doWriteBack(int opcode, Machine machine){
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		int rt = OpcodeUtils.rt(opcode);
		machine.storeToGPR(rt, memwb.LMD());
	}
}
