package machine;

import java.util.HashMap;
import java.util.Map;

public class Registers {

	private Map<String, Integer> GPR;
	private Map<String, Integer> FPR;
	private int PC;
	private int HI;
	private int LO;
	
	public Registers(){
		GPR = new HashMap<String, Integer>();
		FPR = new HashMap<String, Integer>();
		
		for(int i = 0; i < 32; i++){
			GPR.put("r" + i, 0x000000);
			FPR.put("f" + i, 0x000000);
		}
	}
	
	public void storeToGPR(String gpr, int value){
		GPR.put(gpr, value);
	}
	
	public int getFromGPR(String gpr){
		return GPR.get(gpr);
	}
	
	public void storeToFPR(String fpr, int value){
		FPR.put(fpr, value);
	}
	
	public int getFromFPR(String fpr){
		return FPR.get(fpr);
	}
	
	public void setPC(int value){
		PC = value;
	}
	public int getPC(){
		return PC;
	}
}
