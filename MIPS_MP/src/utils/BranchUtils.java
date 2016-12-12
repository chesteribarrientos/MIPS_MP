package utils;

public class BranchUtils {
	
	public static void getLabel(String line){
        String newLabel = line.replaceAll("[a-zA-Z]\\w\\s*:\\s*","");
        if(!line.equals(newLabel)) {
        	System.out.println("Label: " + newLabel);
        	line = line.replace(newLabel,"");
        	line = line.replace(":","");
            line = line.replaceAll("\\s","");
        } 
    }
}
