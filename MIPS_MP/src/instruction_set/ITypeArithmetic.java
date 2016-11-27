package instruction_set;

/**
 * 
 * @author Chester
 *
 */
public class ITypeArithmetic {

	public int getPartial(String statement) {
		String[] words = statement.split("[,\\s]+");

		int rs = Integer.parseInt(words[2].substring(1));
		int rt = Integer.parseInt(words[1].substring(1));
		int imm = Integer.parseInt(words[3], 16);

		int finalOpcode = (rs << 21) | (rt << 16) | imm;
		return finalOpcode;
	}

}
