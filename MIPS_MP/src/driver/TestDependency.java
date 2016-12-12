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
		code.add("DADDIU R5, R0, 0x0004");
		code.add("XOR r6, r0, r5");
		//code.add("SLT r5, r1, r3");
		//code.add("LD r4, 1000(r1)"); 
		//code.add("SD r4, 1000(r1)");
		
		List<Integer> opcodes = new ArrayList<Integer>();
		for(String s: code) {
			int opcode = InstructionUtils.getInstructionEnum(s).getInstructionConverter().getOpcode(s);
			opcodes.add(opcode);
		}
		
		controller.loadCodeIntoMemory(opcodes);
		
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
		
		
	}

}
