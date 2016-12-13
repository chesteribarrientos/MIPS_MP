package instruction_set;

import machine.EXMEM;
import machine.MEMWB;
import machine.Machine;

public class BranchInstruction {
	
	public void execute_memory(int opcode, Machine machine) {
		EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
		MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
		
		//if (exmem.Cond() == true) machine.setPC(exmem.ALUOutput());
		//no need because of pipeline #2, creates errors
	}
}
