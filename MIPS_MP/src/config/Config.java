package config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {

<<<<<<< HEAD
	public static final int MEMORY_SIZE = 16384;
=======
	public static final int MEMORY_SIZE = 8192;
>>>>>>> origin/master
	public static final int CODE_START = 0x1000;
	public static final int CODE_END = 0x2FFF;
	public static final int DATA_START = 0x3000;
	public static final int DATA_END = 0x4FFF;
	
	public static final Integer[] BRANCH_VALUES = {Opcode.BEQC, Opcode.BC};
	public static final Set<Integer> BRANCH_SET = new HashSet<Integer>(Arrays.asList(BRANCH_VALUES));
	
}
