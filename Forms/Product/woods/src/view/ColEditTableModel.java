package view;

public class ColEditTableModel extends NoEditTableModel {

	public ColEditTableModel(int rows, int cols){
		super(rows, cols);
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		// Only sets the 2nd column to be editable so the "remove lesson/fixture" button works
		switch(col){
		case 4: return true;
		default: return false;
		}
	}
}
