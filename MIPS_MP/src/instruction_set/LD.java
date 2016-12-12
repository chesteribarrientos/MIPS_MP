package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.Machine;
import utils.OpcodeUtils;
import utils.Print;

/**
 * @author laurencefoz
 */

public class LD extends MemoryInstruction implements IConverter, IExecutor {
    @Override
    public int getOpcode(String statement) {
        return (Opcode.LD << 26) | super.getPartial(statement);
    }

	@Override
	public void execute(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		
		//base
		int rs = OpcodeUtils.rs(opcode);
		long rsValue = machine.loadFromGPR(rs);
		
		//offset
		int imm = OpcodeUtils.imm(opcode);
		long result = rsValue + imm;
		
		System.out.println(rsValue + " " + Integer.toHexString(imm) + " " + result + " " + Long.toHexString(result));
		exmem.setALUOutput(result);
		exmem.setCond(false); // can move to super
	}

	@Override
	public void execute_memory(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		super.doMemoryCycle(machine);
	}

	@Override
	public void execute_writeback(int opcode, Machine machine) {
		super.doWriteBack(opcode, machine);
	}
}
