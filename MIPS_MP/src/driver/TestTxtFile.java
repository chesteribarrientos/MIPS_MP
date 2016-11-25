package driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import instruction_set.Instruction;
import utils.InstructionUtils;
import utils.Print;

public class TestTxtFile {

	public static void main(String[] args) {
		
		//text file test
		Path path = Paths.get("sample.txt");
		List<String> list = new ArrayList<>();
		
        try (Stream<String> lines = Files.lines(path)) {
        	list = lines.collect(Collectors.toList());
        } catch (IOException ex) {
        	System.err.println(ex);
        }
        
        List<Integer> opcodes = new ArrayList<Integer>();
        
        for (String line : list) {
        	if(!line.trim().isEmpty()){
		    	Instruction ins = InstructionUtils.getInstructionEnum(line);
		    	int opcode = ins.getInstructionConverter().getOpcode(line);
		    	opcodes.add(opcode);
        	}
		}
        
        Print.allOpcode(opcodes);
        
	}

}
