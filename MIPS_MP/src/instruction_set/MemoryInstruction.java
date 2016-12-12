package instruction_set;

import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;
import utils.OpcodeUtils;
import utils.Print;

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
	
	// LD
	public void doMemoryCycle(Machine machine){
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		memwb.setALUOutput(exmem.ALUOutput());
		//memwb.setLMD(machine.getMemory().getFromMemory((int)exmem.ALUOutput(), 8)); // 8 double word
		memwb.setLMD(machine.loadDoubleFromMemory((int)exmem.ALUOutput()));
    }
	
	// SD, consider merging to one method
	public void doMemoryCycleSD(Machine machine){
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		memwb.setALUOutput(exmem.ALUOutput());
		machine.storeDoubleWordToMemory((int)exmem.ALUOutput(), exmem.B());
    }
	
	//warning: use only on load instructions
	public void doWriteBack(int opcode, Machine machine){
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		int rt = OpcodeUtils.rt(opcode);
		machine.storeToGPR(rt, memwb.LMD());
	}
}
