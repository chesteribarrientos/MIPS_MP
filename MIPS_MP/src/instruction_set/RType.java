package instruction_set;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.Opcode;
import interfaces.IDependencyCheck;
import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;
import utils.InstructionUtils;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */
public class RType implements IDependencyCheck{
    public int getRegisterOps(String statement) {
        String[] words = statement.split("[,\\s]+");
        
        //if (!checkForErrors(words)) {
            int rs = Integer.parseInt(words[2].substring(1));
            int rt = Integer.parseInt(words[3].substring(1));
            int rd = Integer.parseInt(words[1].substring(1));
            //int imm = Integer.parseInt(words[3], 16);
            //int imm = Integer.decode(words[3]);
            
            int finalOpcode = (rs << 21) | (rt << 16) | (rd << 11);
            return finalOpcode;
        //}
        
        //return Opcode.NOP;
    }
    
    /** @author Chester
     * for write back of all r types
     */
    public void doWriteBack(int opcode, Machine machine){
    	MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		int rd = OpcodeUtils.rd(opcode);
		machine.storeToGPR(rd, memwb.ALUOutput());
    }
    /**
     * memory cycle for r type ALU instructions
     * warning: use only for ALU Instruction
     */
    public void doMemoryCycle(int opcode, Machine machine){

		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		memwb.setALUOutput(exmem.ALUOutput());
    }
    
    /*public boolean checkForErrors(String[] words) {
    	String regex = "^(R|F)([0-9]|[1-2][0-9]|3[0-1])$";
    	Pattern p = Pattern.compile(regex); // Change later when can't use R0 and special registers
    	Matcher m1 = p.matcher(words[1]);
    	Matcher m2 = p.matcher(words[2]);
    	Matcher m3 = p.matcher(words[3]);
    	
    	if (words.length == 4) { // INST, rd, rs, rt
    		if (m1.find() && m2.find() && m3.find()) {
    			return false;
    		}
    	}
    	return true;
    }*/

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
		int rt = OpcodeUtils.rt(opcode);
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
					if(rs == rd || rt == rd){
						return currOpcode;
					}
				}
				else{
					int currRt = OpcodeUtils.rt(currOpcode);
					//System.out.println("Not R type Writeback Reg - rt: " + currRt);
					if(rs == currRt || rt == currRt){
						return currOpcode;
					}
				}
			}
			i--;
		}
		return 0;
	}
}
