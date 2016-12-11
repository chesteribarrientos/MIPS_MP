package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author laurencefoz
 */
public class instPipeline {
    boolean isDone;
    int instNum, cNum, cName;
    ArrayList<JTable> cycles;
    private final String[] pNames = {"IF", "ID", "EX", "MEM", "WB", "*"};
    
    public instPipeline(int i){
        isDone = false;
        instNum = i;
        cNum = i;
        cName = 0;
        cycles = new ArrayList<>();
    }
    
    public JTable addTable(boolean cond){
        String[] cNames = {""};
        Object seagulls[][] = {{pNames[cond ? cName : 5]}};
        DefaultTableModel model = new DefaultTableModel(seagulls, cNames){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        JTable table = new JTable(model);
        DefaultTableCellRenderer cR = new DefaultTableCellRenderer();
        cR.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( cR );
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setTableHeader(null);
        if(cond){
            cNum++;
            cName++;
        }
        setDone();
        
        cycles.add(table);
        return table;
    }
    
    public GridBagConstraints getGBC(){
        GridBagConstraints cc = new GridBagConstraints();
        cc.anchor = GridBagConstraints.NORTHWEST;
        cc.weightx = 1;
        cc.weighty = 1;
        cc.gridy = instNum;
        cc.gridx = cNum;
        cc.insets = new Insets(5,0,5,0);
        
        return cc;
    }
    
    public String getCycle(int i){
        return cycles.get(i).getValueAt(0,0).toString();
    }
    
    public void setDone(){
        if(cName>4){
            isDone = true;
        }
    }
    
    public boolean isDone(){
        return isDone;
    }
}
