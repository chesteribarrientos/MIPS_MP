package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * @author lawrencefoz
 */
public class SD extends MemoryInstruction implements IConverter, IExecutor {

    @Override
    public int getOpcode(String statement) {
        return (Opcode.SD << 26) | super.getPartial(statement);
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
		super.doMemoryCycleSD(machine);
	}

	@Override
	public void execute_writeback(int opcode, Machine machine) {

	}
}
