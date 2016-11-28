package driver;

import instruction_set.Instruction;
import utils.InstructionUtils;
import utils.Print;

public class TestOpcodeReturn {

	public static void main(String[] args) {

		
//		int sampleopcode = Instruction.J.getInstructionConverter().getOpcode("J 0xBCD4");
		
//		Print.as32bitHex(sampleopcode);
		
//		Print.as32bitHex(		
//			Instruction.BEQ.getInstructionConverter().getOpcode("BEQ r1, r5, 0xABC4"));
//		Print.as32bitHex(
//			Instruction.NOP.getInstructionConverter().getOpcode("NOP"));
		
		//Instruction is = InstructionUtils.getInstructionEnum("LB r1, 0001(r1)"); //to get enum
		Instruction is = InstructionUtils.getInstructionEnum("NOP"); //to get enum
		int opcode = is.getInstructionConverter().getOpcode("NOP"); //to execute conversion
		Print.as32bitHex(opcode);
	}
}
