package driver;

import machine.Machine;
import utils.Print;

public class RegisterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Machine machine = new Machine();

		System.out.println("Example 1");
		long value = 0xFFFFFFFFFFFFFFL;
		machine.loadToGPR(1, value); //long is always zero extended
		Print.as64bitHex(machine.getRegisters().getFromGPR(1));
		
		System.out.println("Example 2");
		machine.loadToGPR(1, 0xFFFFFFFF); //this becomes an integer type (-1), thus sign extended
		machine.loadToGPRunsigned(1, 0xFFFFFFFF); //use to store integer (32bit) zero extended
		Print.as64bitHex(machine.getRegisters().getFromGPR(1));

		System.out.println("Example 3");
		short hw = (short)0xFFFF;
		machine.loadToFPR(1, hw); //short(usually size of immediate value) is also sign extended
		machine.loadToFPRunsigned(1, hw); //again, use this if unsigned is needed
		Print.as64bitHex(machine.getRegisters().getFromFPR(1));
		
		
	}

}
