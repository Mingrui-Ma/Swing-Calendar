package cal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CustomCellRenderer extends DefaultTableCellRenderer {
	JTable table;
	Object cellVal;
	int r,c;
	
	CustomCellRenderer(JTable tabCons, Object cellCons, int rowCons, int colCons)	{
		this.table = tabCons;
		this.cellVal = cellCons;
		this.r = rowCons;
		this.c = colCons;
		this.setHorizontalAlignment(JLabel.CENTER);
		this.getTableCellRendererComponent(table, cellCons, false, false, r, c);
	}
	
	@Override
public Component getTableCellRendererComponent(JTable table,
											Object value,
											boolean isSelected,
											boolean hasFocus,
											int row,
											int column)	{
	Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	comp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
	comp.setBackground(Color.gray);
	return comp;
	}
}

/*
for(int rowCount=0;rowCount<model.getRowCount();rowCount++)	{
	for(int colCount=0;colCount<7;colCount++)	{
		if(model.getValueAt(rowCount, colCount)==null)
			continue;
		else if(model.getValueAt(rowCount, colCount).equals(today))	{		//gets around the null values in model (guess it doesn't work?)
			DefaultTableCellRenderer renderer = new CustomCellRenderer(table, today, rowCount, colCount);
			//renderer.getTableCellRendererComponent(table, today, false, false, rowCount, colCount);
			table.setDefaultRenderer(Object.class, renderer);	
		}
	}
}		*/