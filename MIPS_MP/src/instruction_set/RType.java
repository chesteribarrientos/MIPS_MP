package instruction_set;

/**
 * @author laurencefoz
 */
public class RType {
    public int getRegisterOps(String statement) {
        String[] words = statement.split("[,\\s]+");
        
        int rs = Integer.parseInt(words[2].substring(1));
        int rt = Integer.parseInt(words[3].substring(1));
        int rd = Integer.parseInt(words[1].substring(1));
        //int imm = Integer.parseInt(words[3], 16);
        //int imm = Integer.decode(words[3]);
        
        int finalOpcode = (rs << 21) | (rt << 16) | (rd << 11);
        return finalOpcode;
    }
}
