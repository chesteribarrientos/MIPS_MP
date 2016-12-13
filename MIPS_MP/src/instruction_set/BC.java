package instruction_set;

import java.util.List;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IDependencyCheck;
import interfaces.IExecutor;
import machine.EXMEM;
import machine.IFID;
import machine.Machine;
import utils.OpcodeUtils;
import utils.Stringify;

public class BC extends BranchInstruction implements IConverter, IExecutor, IDependencyCheck {
	
	@Override
	public int getOpcode(String statement) {
		String[] words = statement.split("[,\\s]+");

		int offset = Integer.decode(words[1]);
		if (offset < 0) offset = offset & 0x0000ffff;
		
		int finalOpcode = (Opcode.BC << 26) | offset;
		return finalOpcode;
	}
	
	@Override
	public void execute(int opcode, Machine machine) {
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		IFID ifid = (IFID) machine.getPipeline().get("IF/ID");
		
		long imm = OpcodeUtils.imm(opcode);
		//System.out.println(Stringify.as32bitHex(ifid.NPC()));
		//System.out.println(Stringify.as64bitHex((imm << 2)));
		exmem.setALUOutput(ifid.NPC());
		exmem.setCond(true);
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
		// TODO Auto-generated method stub
		return 0;
	}
    
}
