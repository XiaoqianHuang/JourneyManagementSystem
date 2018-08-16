package yang;

import javax.swing.table.DefaultTableModel;

public class myTabelModel extends DefaultTableModel {
	Object[][] cellData;
	String[] columnNames;
	public myTabelModel(Object[][] cellData, String[] columnNames) {
		super(cellData,columnNames);
		this.cellData = cellData;
		this.columnNames = columnNames;
	} 
	
	public Class<?> getColumnClass(int column) { 
		if (cellData.length > 0 && column < cellData.length) 
		return getValueAt(0, column).getClass(); 
		return Object.class; 
	
}
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
