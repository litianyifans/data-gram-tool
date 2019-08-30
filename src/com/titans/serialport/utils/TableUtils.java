package com.titans.serialport.utils;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableUtils {

	public static Vector<String> buildTableModel(String[] type) {
		Vector<String> vector = new Vector<String>();
		for (int i = 0; i < type.length; i++)
			vector.add(type[i]);
		return vector;
	}

	public static JTable buildTable(DefaultTableModel model) {

		JTable table = new JTable(model);

		table.setShowGrid(true);

		//table.setGridColor(Color.BLUE);

		table.setRowHeight(20);

		return table;

	}

	public static TableColumnModel getColumn(JTable table, int[] width) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnModel columns = table.getColumnModel();
		for (int i = 0; i < width.length; i++) {
			TableColumn column = columns.getColumn(i);
			column.setPreferredWidth(width[i]);
		}
		return columns;
	}
}
