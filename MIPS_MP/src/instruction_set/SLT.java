package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */
public class SLT extends RType implements IConverter, IExecutor {
    @Override
    public int getOpcode(String statement) {
    	if (super.getRegisterOps(statement) == Opcode.NOP) return 0x0;
        return (0b000000 << 26) | super.getRegisterOps(statement) | 0b00000 | Opcode.SLT;
    }
    
    @Override
    public void execute(int opcode, Machine machine) {
    	EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		
		int rs = OpcodeUtils.rs(opcode);
		long rsValue = machine.loadFromGPR(rs);
		
		int rt = OpcodeUtils.rt(opcode);
		long rtValue = machine.loadFromGPR(rt);
		
		long result = (rsValue < rtValue) ? 1 : 0;
		
		System.out.println(Long.toBinaryString(rsValue));
		System.out.println(Long.toBinaryString(rtValue));
		System.out.println("Result: " + result);
		exmem.setALUOutput(result);
		exmem.setCond(false);
    }
    
    @Override
    public void execute_memory(int opcode, Machine machine) {
    	super.doMemoryCycle(opcode, machine);
    }
    
    @Override
	public void execute_writeback(int opcode, Machine machine) {
    	super.doWriteBack(opcode, machine);
    }
    
}
