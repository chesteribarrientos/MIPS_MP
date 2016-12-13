package gui;

import instruction_set.Instruction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import utils.InstructionUtils;
import utils.Print;
import utils.Stringify;
import machine.Machine;
import config.Config;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import machine.EXMEM;
import machine.IDEX;
import machine.IFID;
import machine.MEMWB;
import utils.OpcodeUtils;
import controller.HighLevelController;
import controller.CycleFlags;
import java.util.HashMap;
import machine.Pipeline;

/**
 * @author laurencefoz
 */
public class MainView extends JFrame implements DocumentListener{
    private final JPanel p_main, p_reg, p_pipeline, p_mem;
    private final JLabel lbl_opCode, lbl_error, lbl_pMap, lbl_inReg, lbl_R, lbl_memory;
    private final JTextArea ta_log, ta_inReg, ta_test;
    private final JScrollPane jsp_1, jsp_2, jsp_3, jsp_4, jsp_5, jsp_6;
    private final ArrayList<JTextField> tf_register, tf_mem;
    private final ArrayList<JLabel> lbl_register, lbl_mem;
    private JFileChooser jfc;
    private File baseFile;
    private final JTable t_table;
    private final DefaultTableModel model;
    private final String[] columnNames = {"#", "Instruction", "OpCode"};
    //private final String[] pNames = {"IF", "ID", "EX", "MEM", "WB", "*"};
    private ArrayList<String> inst, labels;
    private List<Integer> opCodes;
    private ArrayList<instPipeline> pipes;
    private String path;
    private Integer num, check;
    private Machine machine;
    private HighLevelController hlc;
    
    private JMenu file, run;
    private JMenuItem open, runSingle, runFull;
    //private JMenuItem exit;
    
    public MainView(){
        super("MIPS64 Simulator");
        super.setJMenuBar(setMenuBar());
        
        //Initialize variables
        path = "";
        num = 0;
        check = 0;
        inst = new ArrayList<>();
        labels = new ArrayList<>();
        opCodes = new ArrayList<>();
        pipes = new ArrayList<>();
        tf_register = new ArrayList<>();
        tf_mem = new ArrayList<>();
        lbl_register = new ArrayList<>();
        lbl_mem = new ArrayList<>();
        machine = new Machine();
        hlc = new HighLevelController(machine);
        
        lbl_opCode      = new JLabel("Instruction OpCodes");
        lbl_error       = new JLabel("Activity and Error Log");
        lbl_pMap        = new JLabel("Pipeline Map");
        lbl_inReg       = new JLabel("Internal MIPS64 Registers");
        lbl_R           = new JLabel("Registers");
        lbl_memory      = new JLabel("Memory");
        lbl_opCode.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_error.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_pMap.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_inReg.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_R.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_memory.setFont(new Font("Calibri", Font.PLAIN, 14));
        
        ta_log      = new JTextArea(10,30);
        ta_log.append("WELCOME TO THE MIPS64 SIMULATOR!\n\n");
        ta_log.setEditable(false);
        ta_log.setWrapStyleWord(true);
        ta_log.setLineWrap(true);
        ta_inReg    = new JTextArea(10,20);
        ta_inReg.setEditable(false);
        ta_test    = new JTextArea(10,30);
        ta_test.setEditable(false);
        
        p_main          = new JPanel();
        p_main.setLayout(new GridBagLayout());
        p_reg           = new JPanel();
        p_reg.setLayout(new GridBagLayout());
        p_pipeline      = new JPanel();
        p_pipeline.setLayout(new GridBagLayout());
        p_mem           = new JPanel();
        p_mem.setLayout(new GridBagLayout());
        
        Object data[][] = {};
        this.model = new DefaultTableModel(data, columnNames);
        this.t_table = new JTable(model);
        t_table.getColumnModel().getColumn(0).setPreferredWidth(21);
        t_table.getColumnModel().getColumn(1).setPreferredWidth(175);
        t_table.getColumnModel().getColumn(2).setPreferredWidth(75);
        t_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        int start = 12288;
        for(int i=0; i<2048; i++){
            //System.out.println(Integer.toHexString(start).toUpperCase());
            JLabel lbl_temp = new JLabel(Integer.toHexString(start).toUpperCase());
            JTextField tf_temp = new JTextField(11);
            /*tf_temp.addKeyListener(new KeyAdapter(){
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ( ((c < '0') || (c > '9')) && ((c < 'A') || (c > 'F')) && tf_temp.getText().length() <= 16 ) {
                        e.consume();  // ignore event
                    }
                }
            });*/
            lbl_mem.add(lbl_temp);
            tf_mem.add(tf_temp);
            start += 4;
        }
        
        GridBagConstraints cd = new GridBagConstraints();
        for(int i=0; i<tf_mem.size(); i++){
            cd.anchor = GridBagConstraints.EAST;
            cd.gridy = i;
            cd.gridx = 0;
            cd.insets = new Insets(2,0,2,5);
            p_mem.add(lbl_mem.get(i),cd);
            cd.gridx = 1;
            cd.insets = new Insets(2,5,2,0);
            p_mem.add(tf_mem.get(i),cd);
        }
        
        for(int i=0; i<32; i++){
            String temp = "R"+i;
            JLabel lbl_temp = new JLabel(temp);
            lbl_temp.setHorizontalAlignment(JLabel.RIGHT);
            JTextField tf_temp = new JTextField(11);
            tf_temp.setText( Stringify.as64bitHex(machine.loadFromGPR(i)) );
            tf_temp.addKeyListener(new KeyAdapter(){
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ( ((c < '0') || (c > '9')) && ((c < 'A') || (c > 'F')) && tf_temp.getText().length() <= 16 ) {
                        e.consume();  // ignore event
                    }
                }
            });
            tf_temp.getDocument().addDocumentListener(this);
            tf_temp.setActionCommand(temp);
            lbl_register.add(lbl_temp);
            tf_register.add(tf_temp);
        }
        
        tf_register.get(0).setEditable(false);
        
        for(int i=0; i<tf_register.size(); i++){
            cd.anchor = GridBagConstraints.EAST;
            cd.gridy = i;
            cd.gridx = 0;
            cd.insets = new Insets(2,0,2,5);
            p_reg.add(lbl_register.get(i),cd);
            cd.gridx = 1;
            cd.insets = new Insets(2,5,2,0);
            p_reg.add(tf_register.get(i),cd);
        }
        
        jsp_1 = new JScrollPane(t_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_2 = new JScrollPane(ta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_3 = new JScrollPane(p_reg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_4 = new JScrollPane(p_pipeline, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_5 = new JScrollPane(ta_inReg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_6 = new JScrollPane(p_mem, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_1.setPreferredSize(new Dimension(140, 200));
        jsp_2.setPreferredSize(new Dimension(100, 200));
        jsp_3.setPreferredSize(new Dimension(100, 200));
        jsp_4.setPreferredSize(new Dimension(140, 200));
        jsp_5.setPreferredSize(new Dimension(100, 200));
        jsp_6.setPreferredSize(new Dimension(100, 200));
        
        GridBagConstraints c = new GridBagConstraints();
        
        //First Line
	c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.0;
        
	c.gridx = 0;
	c.insets = new Insets(10,10,5,10);
        this.p_main.add(lbl_opCode, c);
        
	c.gridx = 1;
        this.p_main.add(lbl_error, c);
        
        c.gridx = 2;
        this.p_main.add(lbl_R, c);
        
        //Second Line
        c = new GridBagConstraints();
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.0;
        
	c.insets = new Insets(10,10,5,10);
        c.gridx = 0;
        this.p_main.add(jsp_1, c);
        
        c.gridx = 1;
        this.p_main.add(jsp_2, c);
        
        c.gridx = 2;
        this.p_main.add(jsp_3, c);
        
        //Third Line
        c = new GridBagConstraints();
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
        
	c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        this.p_main.add(lbl_pMap, c);
        
        c.gridx = 1;
        this.p_main.add(lbl_inReg, c);
        
        c.gridx = 2;
        this.p_main.add(lbl_memory, c);
        
        //Fourth Line
        c = new GridBagConstraints();
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
        
	c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        this.p_main.add(jsp_4, c);
        
        c.gridx = 1;
        this.p_main.add(jsp_5, c);
        
        c.gridx = 2;
        this.p_main.add(jsp_6, c);
        
        //c.gridx = 2;
        //this.p_main.add(jsp_6, c);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(900,600));
	pack();
	setLayout(new BorderLayout());
	setLocationRelativeTo(null);
        setResizable(false);
	add(p_main, BorderLayout.CENTER);
	setVisible(true);
    }
    
    private JMenuBar setMenuBar(){
        JMenuBar mb = new JMenuBar();
        
        file = new JMenu("File");
        run = new JMenu("Run");
        open = new JMenuItem("Open");
        runFull = new JMenuItem("Run (Full Cycle)");
        runSingle = new JMenuItem("Run (Single Cycle)");
        
        open.addActionListener((ActionEvent e) -> {
            openFile();
        });
        
        runSingle.addActionListener((ActionEvent e) -> {
            /*if(inst.size() <= 0){
                ta_log.append("No file added yet.\n");
            } else if(!checkIfValid()) {
                ta_log.append("One of your textfields is too short.\n");
            } else {
                addTable(true);
            }*/
            doCycle(true);
        });
        
        runFull.addActionListener((ActionEvent e) -> {
            /*if(inst.size() <= 0){
                ta_log.append("No file added yet.\n");
            } else if(!checkIfValid()){
                ta_log.append("One of your textfields is too short.\n");
            } else {
                addTable(false);
            }*/
            doCycle(false);
        });
        
        open.registerKeyboardAction((ActionEvent e) -> {open.doClick();},
                KeyStroke.getKeyStroke('O', Event.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        file.add(open);
        run.add(runFull);
        run.add(runSingle);
        mb.add(file);
        mb.add(run);
        
        return mb;
    }
    
    public boolean checkIfValid(){
        for(int i=0; i<tf_register.size(); i++){
           if(tf_register.get(i).getText().length() < 16){
               return false;
           } 
        }
        return true;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        //System.out.println("inserted");
        //updateLog(e);
    }
    
    public void removeUpdate(DocumentEvent e) {
        //System.out.println("removed");
        //updateLog(e);
    }
    
    public void changedUpdate(DocumentEvent e) {
        System.out.println("changed");
    }
    
    public void updateLog(DocumentEvent e) {
        Document doc = (Document)e.getDocument();
        int len = doc.getLength();
        //System.out.println(len);
        if( 16 == len ){
            //System.out.println("inserted\n");
            for(int i=0; i<tf_register.size(); i++){
                long temp = Long.parseLong(tf_register.get(i).getText(),16);
                machine.storeToGPR(i, temp);
            }
            /*for(int i=0; i<tf_register.size(); i++){
                long temp = machine.loadFromGPR(i);
                System.out.println("R"+i+": "+Stringify.as64bitHex(temp));
            }*/
        }
    }
    
    private void clearFields(){
        ta_inReg.setText("");
        int rc = model.getRowCount();
        for(int i=0;i<rc;i++){
            model.removeRow(0);
        }
        p_pipeline.removeAll();
        p_pipeline.repaint();
        p_pipeline.revalidate();
    }
    
    private void openFile() {
        jfc = new JFileChooser(path);
        FileFilter filter = new FileNameExtensionFilter("Assembly Files", "asm", "s");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jfc.showDialog(this, "Open File");
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            clearFields();
            inst = new ArrayList<>();
            labels = new ArrayList<>();
            pipes = new ArrayList<>();
            opCodes = new ArrayList<>();
            num = 0;
            check = 0;
            machine = new Machine();
            hlc = new HighLevelController(machine);
            System.out.println("Pipes Size: "+pipes.size());
            
            baseFile = jfc.getSelectedFile();
            path = baseFile.getPath().replaceAll(baseFile.getName(),"");
            ta_log.append("File " + baseFile.getName() + " Selected\n");
            try (BufferedReader reader = new BufferedReader(new FileReader(baseFile))) {
                String line = "";
                //Boolean dotcode = false;
                Boolean valid = true;
                int i = 1;

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\t","");
                    /*if(!dotcode){
                        line = line.replaceAll("\\s","");
                    }*/
                    if(line.substring(0,1).matches("\\d")){
                        ta_log.append("ERROR: Instruction cannot begin with a number: line "+i+"\n");
                        valid = false;
                    } else if( (isRType(line) | isIType(line) | isJType(line)) && valid /*&& dotcode*/ && !line.startsWith(";")){
                        inst.add(line);
                    } /*else if(line.startsWith(".code")){
                        dotcode = true;
                    }*/ else if(line.startsWith(";")){ 
                    } else {
                        checkParams(line, i);
                        valid = false;
                    }
                    i++;
                }
                if(!inst.isEmpty() /*&& dotcode*/ && valid){
                    getLabels();
                    toBackEnd();
                    List<Integer> tempo = opCodes;
                    hlc.loadCodeIntoMemory(tempo);
                } /*else if(inst.isEmpty() && !dotcode){
                    ta_log.append("ERROR: Code Segment not found\n");
                    ta_log.append("Possible Solution: Check if code has '.code' segment\n");   
                } else if(!inst.isEmpty() && !valid){
                    ta_log.append("ERROR: Invalid Code: line "+i+"\n");
                }*/
                ta_log.append("\n");
                /*for(int j=0; j<inst.size(); j++){
                    System.out.println("Valid Instruction #"+(j+1)+": "+inst.get(j));
                    System.out.println("Label #"+(j+1)+": " + labels.get(j));
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ta_log.append("Open command cancelled by user\n\n");
        }
    }
    
    /*public void addTable(boolean SoF){
        boolean isDone = SoF;
        GridBagConstraints c;
        
        do{
            check++;
            ta_inReg.append("CYCLE #"+check+"\n\n");
            for (instPipeline pipe : pipes) {
                if (!pipe.isDone()) {
                    c = pipe.getGBC();
                    doCycle(pipe.getCName());
                    p_pipeline.add(pipe.addTable(true), c);
                    p_pipeline.repaint();
                    p_pipeline.revalidate();
                }
            }
            if(num<inst.size()){
                instPipeline temp = new instPipeline(num);
                c = temp.getGBC();
                doCycle(temp.getCName());
                pipes.add(temp);
                p_pipeline.add(pipes.get(num).addTable(true), c);
                p_pipeline.repaint();
                p_pipeline.revalidate();
                num++;
            }
            if(!SoF){
                isDone = allTrue();
                //System.out.println("Checked");
            }            
            //System.out.println(check);
            //System.out.println(isDone);
            //System.out.println(SoF);
            updateRegisters();
        } while(!isDone);
        //System.out.println("Pipes Size: "+pipes.size());
    }*/
    
    public void updateRegisters(){
        for(int i=0; i<32; i++){
            tf_register.get(i).setText( Stringify.as64bitHex(machine.loadFromGPR(i)) );
        }
    }
    
    public boolean allTrue(){
        for (instPipeline pipe : pipes) {
            boolean value = pipe.isDone();
            if(!value || pipes.size()!=inst.size()){
                //System.out.println("Pipes size not equal to Inst size");
                return false;
            }
        }
        return true;
    }
    
    public void doCycle(boolean soft){
        //System.out.println("Entered "+i);
        
        do{
            if(!hlc.getDone()){
                ta_inReg.append("Cycle #"+(check+1)+"\n");
                hlc.runCycle();
                HashMap<String,Pipeline> pipeline = machine.getPipeline();
                IFID ifid = (IFID) pipeline.get("IF/ID");
                IDEX idex = (IDEX) pipeline.get("ID/EX");
                EXMEM exmem = (EXMEM) pipeline.get("EX/MEM");
                MEMWB memwb = (MEMWB) pipeline.get("MEM/WB");
                ta_inReg.append("IF\n\n");
                ta_inReg.append(ifid.toString()+"\n");
                ta_inReg.append("ID\n\n");
                ta_inReg.append(idex.toString()+"\n");
                ta_inReg.append("EX\n\n");
                ta_inReg.append(exmem.toString()+"\n");
                ta_inReg.append("MEM\n\n");
                ta_inReg.append(memwb.toString()+"\n");
                ta_inReg.append("--------------------------\n");
                check++;
                updateRegisters();
            } else {
                soft = true;
            }
        }while(!soft);
        
        
        /*switch(i){
            case 0: machine.doIFCycle(); 
                IFID ifid = (IFID) machine.getPipeline().get("IF/ID");
                break;
            case 1: machine.doIDCycle();
                IDEX idex = (IDEX) machine.getPipeline().get("ID/EX");
                break;
            case 2: machine.doExCycle();
                EXMEM exmem = (EXMEM) machine.getPipeline().get("EX/MEM");
                break;
            case 3: machine.doMemCycle();
                MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
                ta_inReg.append("MEM\n\n");
                ta_inReg.append("MEM/WB.IR:\t"+Stringify.as32bitHex(memwb.IR())+"\n");
                ta_inReg.append("MEM/WB.ALUOUTPUT:\t"+Stringify.as64bitHex(memwb.ALUOutput())+"\n");
                ta_inReg.append("MEM/WB.LMD:\t"+Stringify.as64bitHex(memwb.LMD())+"\n");
                //STILL NEEDS MEM[ALUOUTPUT]
                ta_inReg.append("\n");
                break;
            case 4: machine.doWBCycle();
                ta_inReg.append("WB\n\n");
                memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
                String temp = Long.toHexString(memwb.IR());
                int opcode = Integer.parseInt(temp, 16) ;
                /*long turp = Long.parseLong(temp, 16);
                int opcode = (int) turp;
		ta_inReg.append("R" + OpcodeUtils.rt(opcode) + " = ");
                ta_inReg.append(""+Stringify.as64bitHex(machine.loadFromGPR(OpcodeUtils.rt(opcode)))+"\n");
                ta_inReg.append("\n");
                break;
            default: System.out.println("STALL");
        }*/
    }
    
    public void toBackEnd(){
        int i = 0;
        Boolean b = checkForDuplicateLabels();
        while(i < inst.size() && b){
            String line = inst.get(i).replaceAll("[a-zA-Z]\\w\\s*:\\s*","");
            if(line.startsWith("BEQC") || line.startsWith("BC")){
                line = forBranch(line, i);
            }
            if(!line.equals("")){
                Instruction is = InstructionUtils.getInstructionEnum(line); //to get enum
                int opcode = is.getInstructionConverter().getOpcode(line); //to execute conversion
                String opCode = Stringify.as32bitHex((long) opcode);
		//machine.storeWordToMemory(Config.CODE_START, opcode);
                
                if("00000000".equals(opCode) && !line.startsWith("NOP")) {
                    ta_log.append("Invalid instruction at line "+i+"\n");
                } else {
                    opCodes.add(opcode);
                    updateOCTable(String.valueOf(i+1), inst.get(i), opCode);
                }
            } else {
                //ta_log.append("ERROR: Line of code may be missing a parameter\n");
                //ta_log.append("Possible Solution: Refer to MIPS64 manual if all parameters are in place \n");
                //ta_log.append("Possible Solution: If accessing a label, please check if label exists\n");
                updateOCTable("X", inst.get(i), "ERROR");
                i = inst.size();
            }
            i++;
        }
    }
    
    public void getLabels(){
        for (String label : inst) {
            String newLabel = label.replaceAll("[a-zA-Z]\\w\\s*:\\s*","");
            if(!label.equals(newLabel)) {
            	System.out.println(newLabel);
                label = label.replace(newLabel,"");
                label = label.replace(":","");
                label = label.replaceAll("\\s","");
                labels.add(label);
            } else { labels.add(""); }
        }
    }
    
    public boolean checkForDuplicateLabels(){
        int i = 0;
        while(i < labels.size()){
            if(!labels.get(i).equals("")){
                for(int j=i+1; j<labels.size(); j++){
                    if( labels.get(i).equals(labels.get(j)) ){
                        ta_log.append("ERROR: Duplicate Labels found in your code: lines "+(i+1)+" and "+(j+1)+"\n");
                        ta_log.append("Possible Solution: Change the name of your labels \n");
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }
    
    public void checkParams(String line, int i){
        line = line.replaceAll("[a-zA-Z]\\w\\s*:\\s*","");
        String[] temp = line.split("[,\\s[(]]+");
        switch(temp[0]){
            case "XOR"      : 
            case "DSUBU"    : 
            case "SLT"      : 
            case "DADDIU"   : 
            case "BEQC"     : 
            case "LD"       :
            case "SD"       : 
                if(temp.length>4){
                    ta_log.append("Too many parameters for instruction : \""+temp[0]+"\" at line "+i+"\n");
                } else if(temp.length<4){
                    ta_log.append("Too little parameters for instruction : \""+temp[0]+"\" at line "+i+"\n"); }
                break;
            case "BC"       : 
                if(temp.length>2){
                    ta_log.append("Too many parameters for instruction : \""+temp[0]+"\" at line "+i+"\n");
                } else if(temp.length<2){
                    ta_log.append("Too little parameters for instruction : \""+temp[0]+"\" at line "+i+"\n"); }
                break;
            case "NOP"      :
                if(temp.length>1){
                    ta_log.append("Too many parameters for instruction : \""+temp[0]+"\" at line "+i+"\n");
                }
                break;
            default: ta_log.append("ERROR: Instruction in your code not found: line "+i+"\n");
        }
    }
    
    public boolean isRType(String inst){
        String temp0 = "\\s*([a-zA-Z]\\w\\s*:\\s+)*";
        String temp1 = "\\s*(XOR|DSUBU|SLT)\\s+";
        String temp2 = "\\s*R([0-9]|[1-2][0-9]|3[0-1])";
        String temp3 = "\\s*NOP\\s*$";
        Pattern p1 = Pattern.compile(temp0
                                   + temp1
                                   + temp2 + "\\s*,\\s*"
                                   + temp2 + "\\s*,\\s*"
                                   + temp2 + "\\s*$"
                                   + "|" + temp0+temp3); //Pattern match for XOR|DSUBU|SLT|NOP
        Matcher m1 = p1.matcher(inst);
        if(m1.find()){
            //System.out.println("Valid RType code");
            return true;
        } else { }
        return false;
    }
    
    public boolean isIType(String inst){
        String temp0 = "\\s*([a-zA-Z]\\w\\s*:\\s+)*";
        String temp1 = "\\s*(DADDIU)\\s+";
        String temp2 = "\\s*R([0-9]|[1-2][0-9]|3[0-1])";
        String temp3 = "\\s*(LD|SD)\\s+";
        String temp4 = "\\s*(BEQC)\\s+";
        String temp5 = "\\s*[a-zA-Z]\\w\\s*";
        Pattern p1 = Pattern.compile(temp0 +
                                     temp1
                                   + temp2 + "\\s*,\\s*"
                                   + temp2 + "\\s*,\\s*"
                                   + "\\s*0x\\d{4}\\s*$" //Pattern match for DADDIU
                                   + "|"+ temp0 + temp3
                                   + temp2 + "\\s*,\\s*"
                                   + "\\s*\\d{4}[(]"+temp2+"[)]\\s*$" //Pattern match for LD/SD
                                   + "|" + temp4
                                   + temp2 + "\\s*,\\s*"
                                   + temp2 + "\\s*,\\s*"
                                   + temp5 + "$");  //Pattern match for BEQC
        Matcher m1 = p1.matcher(inst);
        if(m1.find()){
            //System.out.println("Valid IType code");
            return true;
        } else { }
        return false;
    }
    
    public boolean isJType(String inst){
        String temp1 = "\\s*BC\\s+[a-zA-Z]\\w\\s*";
        Pattern p1 = Pattern.compile(temp1); //Pattern match for BC
        Matcher m1 = p1.matcher(inst);
        if(m1.find()){
            return true;
        } else { }
        return false;
    }
    
    public String forBranch(String line, int curr){
        String newVar = "";
        String[] words = line.split("[,\\s]+");
        for(int i=0; i<inst.size(); i++){
            if( inst.get(i).startsWith(words[words.length-1]) ){
                newVar = String.valueOf(i-curr-1);
            }
        }
        line = line.replaceAll(words[words.length-1], newVar);
        //System.out.println("forBranch: "+line);
        String[] temp = line.split("[,\\s]+");
        //System.out.println("forBranch params: "+temp.length);
        if( (temp[0].equals("BEQC") && temp.length!=4) || (temp[0].equals("BC") && temp.length!=2)){
            ta_log.append("ERROR: Label \""+words[words.length-1]+"\" not found.");
            line = "";
        }
        return line;
    }
    
    private void updateOCTable(String line_num, String firstCol, String secondCol){
        Object[] temp = new Object[3];
	temp[0] = line_num;
	temp[1] = firstCol;
	temp[2] = secondCol;
        model.addRow(temp);
    }
    
    private ArrayList<String> getCode(){
        return inst;
    }
}
