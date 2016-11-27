package machine;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Chester
 *
 */
public class Registers {

	private Map<Integer, Long> gpr;
	private Map<Integer, Long> fpr;

	private long PC;
	private int HI;
	private int LO;

	public Registers() {
		gpr = new HashMap<Integer, Long>();
		fpr = new HashMap<Integer, Long>();
		for (int i = 0; i < 32; i++) {
			gpr.put(i, 0L);
			fpr.put(i, 0L);
		}
	}

	public void storeToGPR(int key, long value) {
		gpr.put(key, value);
	}

	public long getFromGPR(int key) {
		return gpr.get(key);
	}

	public void storeToFPR(int key, long value) {
		fpr.put(key, value);
	}

	public long getFromFPR(int key) {
		return fpr.get(key);
	}

	public void setPC(long value) {
		PC = value;
	}

	public long getPC() {
		return PC;
	}
}
