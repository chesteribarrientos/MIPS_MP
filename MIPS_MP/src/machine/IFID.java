/**
 * 
 */
package machine;

import utils.Stringify;

/**
 * @author Chester
 *
 */
public class IFID extends Pipeline {

	private long npc;

	public long NPC() {
		return npc;
	}

	public void setNPC(long value) {
		npc = value;
	}

	public String toString() {
		return "IF.IR: " + Stringify.as32bitHex(instructionRegister) + "\nNPC: " + Stringify.as64bitHex(npc);
	}
}
