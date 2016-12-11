package instruction_set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.Opcode;
import machine.MEMWB;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */
public class RType {
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
}
