package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author laurencefoz
 */
public class MainView extends JFrame {
    private final JPanel p_main;
    private final JLabel lbl_opCode, lbl_error, lbl_pMap, lbl_inReg, lbl_placeHolder;
    private final JTextArea ta_log, ta_inReg;
    private final JScrollPane jsp_1, jsp_2, jsp_3;
    private JFileChooser jfc;
    private File baseFile;
    private final JTable t_table;
    private final DefaultTableModel model;
    private final String[] columnNames = {"Instruction", "OpCode (In Hex)"};
    
    private JMenu file;
    private JMenuItem open;
    //private JMenuItem exit;
    
    public MainView(){
        super("MIPS64 Simulator");
        super.setJMenuBar(setMenuBar());
        
        lbl_opCode      = new JLabel("Instruction OpCode (in HEX)");
        lbl_error       = new JLabel("Activity and Error Log");
        lbl_pMap        = new JLabel("Pipeline Map");
        lbl_inReg       = new JLabel("Internal MIPS64 Registers");
        lbl_placeHolder = new JLabel("Pipeline Map to be inserted");
        lbl_opCode.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_error.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_pMap.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_inReg.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_placeHolder.setFont(new Font("Calibri", Font.PLAIN, 20));
        
        ta_log      = new JTextArea(10,30);
        ta_log.setEditable(false);
        ta_inReg    = new JTextArea(15,30);
        ta_inReg.setEditable(false);
        
        p_main      = new JPanel();
        p_main.setLayout(new GridBagLayout());
        
        Object data[][] = {};
        this.model = new DefaultTableModel(data, columnNames);
        this.t_table = new JTable(model);
        t_table.getColumnModel().getColumn(0).setPreferredWidth(15);
        t_table.getColumnModel().getColumn(1).setPreferredWidth(150);
        
        jsp_1 = new JScrollPane(t_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_2 = new JScrollPane(ta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_3 = new JScrollPane(ta_inReg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_1.setPreferredSize(new Dimension(100, 200));
        jsp_2.setPreferredSize(new Dimension(100, 200));
        jsp_3.setPreferredSize(new Dimension(200, 250));
        
        GridBagConstraints c = new GridBagConstraints();
        
        //First Line
	c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
        
	c.gridx = 0;
	c.insets = new Insets(10,10,5,10);
        this.p_main.add(lbl_opCode, c);
        
	c.gridx = 3;
        this.p_main.add(lbl_error, c);
        
        //Second Line
        c = new GridBagConstraints();
        c.gridy = 1;
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.insets = new Insets(10,10,5,10);
        c.gridx = 0;
        c.gridwidth = 3;
        this.p_main.add(jsp_1, c);
        
        c.gridx = 3;
        c.gridwidth = 3;
        this.p_main.add(jsp_2, c);
        
        //Third Line
        c = new GridBagConstraints();
        c.gridy = 2;
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridwidth = 3;
        this.p_main.add(lbl_pMap, c);
        
        c.gridx = 3;
        c.gridwidth = 3;
        this.p_main.add(lbl_inReg, c);
        
        //Fourth Line
        c = new GridBagConstraints();
        c.gridy = 3;
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridwidth = 3;
        this.p_main.add(lbl_placeHolder, c);
        
        c.gridx = 3;
        c.gridwidth = 3;
        this.p_main.add(jsp_3, c);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(800,600));
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
        open = new JMenuItem("Open");
        
        open.addActionListener((ActionEvent e) -> {
            openFile();
        });
        
        open.registerKeyboardAction((ActionEvent e) -> {open.doClick();},
                KeyStroke.getKeyStroke('O', Event.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        file.add(open);
        mb.add(file);
        
        return mb;
    }
    
    private void openFile() {
        jfc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Assembly Files", "asm", "s");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jfc.showDialog(this, "Open File");
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            baseFile = jfc.getSelectedFile();
            ta_log.append("File " + baseFile.getName() + " Selected");
            try (BufferedReader reader = new BufferedReader(new FileReader(baseFile))) {
            String line = "";
            
            while ((line = reader.readLine()) != null) {
                updateOCTable(line);
            }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ta_log.append("Open command cancelled by user\n");
        }
    }
    
    private void updateOCTable(String firstCol){
        Object[] temp = new Object[2];
	temp[0] = firstCol;
	temp[1] = "";
	model.addRow(temp);
    }
}
