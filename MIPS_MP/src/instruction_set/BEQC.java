package instruction_set;

import java.util.List;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IDependencyCheck;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.IDEX;
import machine.Machine;
import utils.InstructionUtils;
import utils.OpcodeUtils;

/**
 * @author laurencefoz
 */

public class BEQC extends BranchInstruction implements IConverter, IExecutor, IDependencyCheck {

    @Override
    public int getOpcode(String statement) {
        String[] words = statement.split("[,\\s]+");
        
        int rs = Integer.parseInt(words[1].substring(1));
        int rt = Integer.parseInt(words[2].substring(1));
        int offset = Integer.decode(words[3]);
        if (offset < 0) offset = offset & 0x0000ffff;
        int finalOpcode = (Opcode.BEQC << 26) | (rs << 21) | (rt << 16) | offset;
        return finalOpcode;
    }
    
    @Override
	public void execute(int opcode, Machine machine) {
    	IDEX idex = (IDEX) machine.getPipeline().get("ID/EX");
    	EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		
		long imm = OpcodeUtils.imm(opcode);
		exmem.setALUOutput(machine.getPC());
		
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

	
	@Override
	public boolean hasWriteBack() {
		// TODO Auto-generated method stub
		return false;
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
