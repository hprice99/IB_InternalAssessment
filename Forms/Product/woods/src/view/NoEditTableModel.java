package view;

import javax.swing.table.DefaultTableModel;

public class NoEditTableModel extends DefaultTableModel {
	// Class for setting table rows to be non-editable
	
	NoEditTableModel(Object[][] data, String[] columnNames){
		super(data, columnNames);
	}
	
	public NoEditTableModel(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	 public boolean isCellEditable(int row, int column) {
        return false;
    }
}
