package instruction_set;

import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * 
 * @author Chester
 *
 */
public class ITypeArithmetic {

	public int getPartial(String statement) {
		String[] words = statement.split("[,\\s]+");

		int rs = Integer.parseInt(words[2].substring(1));
		int rt = Integer.parseInt(words[1].substring(1));
		//int imm = Integer.parseInt(words[3], 16);
                int imm = Integer.decode(words[3]);

		int finalOpcode = (rs << 21) | (rt << 16) | imm;
		return finalOpcode;
	}

	public void doWriteback(int opcode, Machine machine){
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		int rt = OpcodeUtils.rt(opcode);
		System.out.println("mem alu: " + memwb.ALUOutput() + "rt: " + rt);
		machine.storeToGPR(rt, memwb.ALUOutput());
	}
	
	/**
     * memory cycle for i type ALU instructions
     * warning: use only for ALU Instruction
     */
    public void doMemoryCycle(int opcode, Machine machine){

		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		memwb.setALUOutput(exmem.ALUOutput());
    }
}
