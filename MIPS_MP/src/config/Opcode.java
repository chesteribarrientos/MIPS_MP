package config;

/**
 * 
 * @author Chester
 *
 */
public class Opcode {

	/** I Type **/
	public static int BEQ		= 0b000100;
	//fill
	public static int SLTI 		= 0b001010;
	//fill
	public static int LUI 		= 0b001111;
	
	public static int LB 		= 0b100000;
        
        public static int LD 		= 0b110111;
        
        public static int SD 		= 0b111111;
        
        public static int DSUBU 	= 0b101111;
        
        public static int XOR           = 0b100110;
        
        public static int SLT           = 0b101010;
        
        public static int BC	= 0b110010;
        public static int BEQC = 0b001000;
        
}
