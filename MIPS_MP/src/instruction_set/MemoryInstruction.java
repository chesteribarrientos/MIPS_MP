package instruction_set;

/**
 * 
 * @author Chester
 *
 */

public class MemoryInstruction {

	public int getPartial(String statement) {
                System.out.println("Statement: "+statement);
		String[] words = statement.split("[(),\\s]+");

		int base = Integer.parseInt(words[3].substring(1));
		int rt = Integer.parseInt(words[1].substring(1));
		int offset = Integer.parseInt(words[2], 16);

		int finalOpcode = (base << 21) | (rt << 16) | offset;
		return finalOpcode;
	}

}
