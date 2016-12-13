/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import controller.HighLevelController;
import machine.Machine;
import utils.InstructionUtils;
import utils.Print;
import utils.Stringify;

/**
 * @author Chester
 *
 */
public class TestDependency {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Machine machine = new Machine();
		HighLevelController controller = new HighLevelController(machine);
		
		List<String> code = new ArrayList<String>();
		//code.add("DADDIU R1, R0, 0x0000");
		//code.add("DADDIU R5, R1, 0x0004");
		//code.add("DADDIU R4, R0, 0x0040");
		//code.add("XOR r6, r4, r5");
		//code.add("SLT r5, r1, r3");
		//machine.storeDoubleWordToMemory(Config.DATA_START, 0xABCD1234);
		//code.add("LD r6, 3000(r1)"); 
		//code.add("SD r6, 3000(r1)");
		//code.add("LD r6, 3000(r1)"); 
		//code.add("BEQC r1, r0, 0x0002");
		//code.add("DADDIU R5, R0, 0x0004");
		//code.add("DADDIU R5, R0, 0x0005");
		//code.add("DADDIU R5, R0, 0x0006");
		/*
		code.add("BC 0005");
		code.add("XOR r5, r1, r3");
		code.add("SLT r3,r1,r2");
		code.add("NOP");
		code.add("NOP");
		code.add("DADDIU r3, r0, 0x0002");
		code.add("SD r5, 1000(r4)");
		code.add("ld r1, 1000(r2)");
		*/
		code.add("DADDIU R1, R0, 0x0001");
		code.add("DSUBU R1, R0, R1");
		code.add("DSUBU R2, R1, R1");
		code.add("DADDIU R3, R0, 0x0001");
		code.add("DSUBU R4, R1, R3");
		code.add("DSUBU R5, R3, R1");
		
		List<Integer> opcodes = new ArrayList<Integer>();
		for(String s: code) {
			int opcode = InstructionUtils.getInstructionEnum(s).getInstructionConverter().getOpcode(s);
			opcodes.add(opcode);
		}
		
		controller.loadCodeIntoMemory(opcodes);
		//controller.runCode();
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		controller.runCycle();
		Print.pipeline(machine.getPipeline());
		System.out.println("-------------------------------------------");
		
		System.out.println("R1: " + Stringify.as64bitHex(machine.loadFromGPR(1)));
		System.out.println("R2: " + Stringify.as64bitHex(machine.loadFromGPR(2)));
		System.out.println("R3: " + Stringify.as64bitHex(machine.loadFromGPR(3)));
		System.out.println("R4: " + Stringify.as64bitHex(machine.loadFromGPR(4)));
		System.out.println("R5: " + Stringify.as64bitHex(machine.loadFromGPR(5)));
	}

}
