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

/**
 * @author lawrencefoz
 */
public class SD extends MemoryInstruction implements IConverter, IExecutor, IDependencyCheck {

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
		
		//System.out.println(rsValue + " " + Integer.toHexString(imm) + " " + result + " " + Long.toHexString(result));
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

	@Override
	public boolean hasWriteBack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMemoryStore() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int HasDependency(int opcode, List<Integer> code) {
		int index = code.indexOf(opcode);
		
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
					if(rt == rd){
						return currOpcode;
					}
				}
				else{
					int currRt = OpcodeUtils.rt(currOpcode);
					//System.out.println("Not R type Writeback Reg - rt: " + currRt);
					if(rt == currRt){
						return currOpcode;
					}
				}
			}
			i--;
		}
		return 0;
	}
}
