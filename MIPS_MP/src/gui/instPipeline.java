package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author laurencefoz
 */
public class instPipeline {
    Boolean isDone;
    int instNum, cNum, cName;
    private final String[] pNames = {"IF", "ID", "EX", "MEM", "WB", "*"};
    
    public instPipeline(int i){
        isDone = false;
        instNum = i;
        cNum = i;
        cName = 0;
    }
    
    public JTable addTable(){
        String[] cNames = {""};
        Object seagulls[][] = {{pNames[cName]}};
        DefaultTableModel model = new DefaultTableModel(seagulls, cNames){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
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
        cNum++;
        cName++;
        
        if(cName>4){
            setDone();
        }
        
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
    
    public void setDone(){
        isDone = true;
    }
    
    public boolean isDone(){
        return isDone;
    }
}
