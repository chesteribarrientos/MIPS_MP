package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.IDEX;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */

public class BEQC extends BranchInstruction implements IConverter, IExecutor {

    @Override
    public int getOpcode(String statement) {
        String[] words = statement.split("[,\\s]+");
        
        int rs = Integer.parseInt(words[1].substring(1));
        int rt = Integer.parseInt(words[2].substring(1));
        int offset = Integer.parseInt(words[3]);
        if (offset < 0) offset = offset & 0x0000ffff;
        int finalOpcode = (Opcode.BEQC << 26) | (rs << 21) | (rt << 16) | offset;
        return finalOpcode;
    }
    
    @Override
	public void execute(int opcode, Machine machine) {
    	IDEX idex = (IDEX) machine.getPipeline().get("ID/EX");
    	EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		
		long imm = OpcodeUtils.imm(opcode);
		exmem.setALUOutput(machine.getPC() + (imm << 2));
		
		if (idex.A() == idex.B()) {
			exmem.setCond(true) ;
		} else {
			exmem.setCond(false);
		}
	}

	@Override
	public void execute_memory(int opcode, Machine machine) {	
		super.execute_memory(opcode, machine);
	}

	@Override
	public void execute_writeback(int opcode, Machine machine) {

	}
}
