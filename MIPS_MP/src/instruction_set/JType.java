package instruction_set;

/**
 * 
 * @author Chester
 *
 */
public class JType {

	public int getPartial(String statement) {
		String[] words = statement.split("[,\\s]+");
		return Integer.decode(words[1]); // will not work for labels, add label
											// decoding later
	}
}
