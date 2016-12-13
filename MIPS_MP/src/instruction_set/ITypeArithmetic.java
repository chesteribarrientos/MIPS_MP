package instruction_set;

import java.util.List;

import interfaces.IDependencyCheck;
import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;
import utils.InstructionUtils;
import utils.OpcodeUtils;

/**
 * 
 * @author Chester
 *
 */
public class ITypeArithmetic implements IDependencyCheck {

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
		//System.out.println("mem alu: " + memwb.ALUOutput() + "rt: " + rt);
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

	@Override
	public boolean hasWriteBack() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasMemoryStore() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int HasDependency(int opcode, List<Integer> code, Machine machine) {
		int index = code.indexOf(opcode);
		
		int rs = OpcodeUtils.rs(opcode);
		//System.out.println("Checking rs, rt: " + rs + "," + rt);
		int i = index - 1;
		if(i<0) i = 0;
		int end = index - 4;
		if(end < 0) end = 0;
		
		while(i >= end){
			int currOpcode = code.get(i);
			
			if(((IDependencyCheck) InstructionUtils.getInstructionEnum(currOpcode).getInstructionConverter()).hasWriteBack()){
				if(OpcodeUtils.isRType(currOpcode)){
					int rd = OpcodeUtils.rd(currOpcode);
					//System.out.println("R type Writeback Reg - rd: " + rd);
					if(rs == rd){
						return currOpcode;
					}
				}
				else{
					int currRt = OpcodeUtils.rt(currOpcode);
					//System.out.println("Not R type Writeback Reg - rt: " + currRt);
					if(rs == currRt){
						return currOpcode;
					}
				}
			}
			i--;
		}
		return 0;
	}
}
