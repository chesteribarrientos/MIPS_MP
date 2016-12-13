package instruction_set;

import java.util.List;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IDependencyCheck;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.Machine;
import utils.InstructionUtils;
import utils.OpcodeUtils;
import utils.Print;

/**
 * @author laurencefoz
 */

public class LD extends MemoryInstruction implements IConverter, IExecutor, IDependencyCheck {
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
		
		//System.out.println(rsValue + " " + Integer.toHexString(imm) + " " + result + " " + Long.toHexString(result));
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
    	
    	long address = machine.loadFromGPR(OpcodeUtils.rs(opcode)) + OpcodeUtils.imm(opcode);
    	
		System.out.println("Address: " + address);
		int i = index - 1;
		if(i<0) i = 0;
		int end = index - 4;
		if(end < 0) end = 0;
		
		while(i >= end){
			int currOpcode = code.get(i);
			
			if(((IDependencyCheck) InstructionUtils.getInstructionEnum(currOpcode).getInstructionConverter()).hasMemoryStore()){
				long currAddress = machine.loadFromGPR(OpcodeUtils.rs(currOpcode)) + OpcodeUtils.imm(currOpcode);
				 
				System.out.println("Other address: " + currAddress);
				if(address == currAddress){
					return currOpcode;
				}
			}
			i--;
		}
		return 0;
	}
}
