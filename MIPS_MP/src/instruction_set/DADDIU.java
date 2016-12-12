package instruction_set;

import java.util.List;

import interfaces.IConverter;
import interfaces.IDependencyCheck;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */
public class DADDIU extends ITypeArithmetic implements IConverter, IExecutor, IDependencyCheck {
    
    @Override
    public int getOpcode(String statement) {
        return (0b011001 << 26) | super.getPartial(statement);
    }

	@Override
	public void execute(int opcode, Machine machine) {
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		
		int rs = OpcodeUtils.rs(opcode);
		
		long rsValue = machine.loadFromGPR(rs);
		int imm = OpcodeUtils.imm(opcode);
		long result = rsValue + imm;
		
		exmem.setALUOutput(result);
		exmem.setCond(false);
	}

	@Override
	public void execute_memory(int opcode, Machine machine) {	
		super.doMemoryCycle(opcode, machine);
	}

	@Override
	public void execute_writeback(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		super.doWriteback(opcode, machine);
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
	public boolean HasDependency(int opcode, List<Integer> code) {
		// TODO Auto-generated method stub
		return false;
	}	
}
