package com.hellocode.test;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

/**
 * @author Ankur Sharma Created on Sep 24, 2006
 */
class Sample extends JPanel {

	public Sample() {
		init();
	}

	private void init() {
		// test data
		Object[] columns = new Object[] { "Select", "Name" };
		Object[][] data = new Object[2][2];
		data[0][0] = new Boolean(true);
		data[0][1] = "naga";
		data[1][0] = new Boolean(false);
		data[1][1] = "Srinath";

		MyTableModel model = new MyTableModel(data, columns);
		JTable table = new JTable(model);

		table.getColumnModel().getColumn(0).setCellEditor(
				new CheckBoxCellEditor());

		JScrollPane tableScroller = new JScrollPane(table);

		add(tableScroller);
	}

	private class MyTableModel extends AbstractTableModel {

		private Object[][] data;
		private Object[] columns;

		public MyTableModel(Object[][] data, Object[] columns) {
			this.data = data;
			this.columns = columns;
		}

		public Class getColumnClass(int columnIndex) {
			return data[0][columnIndex].getClass();
		}

		public int getColumnCount() {
			return columns.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		// only allow the first column to be editable, this is the column
		// with our checkboxes
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return (columnIndex == 0);
		}
	}

	private class CheckBoxCellEditor extends AbstractCellEditor implements
			TableCellEditor {
		protected JCheckBox checkBox;

		public CheckBoxCellEditor() {
			checkBox = new JCheckBox();
			checkBox.setHorizontalAlignment(SwingConstants.CENTER);
			checkBox.setBackground(Color.white);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			checkBox.setSelected(((Boolean) value).booleanValue());

			Component c = table.getDefaultRenderer(String.class)
					.getTableCellRendererComponent(table, value, isSelected,
							false, row, column);
			if (c != null) {
				checkBox.setBackground(c.getBackground());
			}

			return checkBox;
		}

		public Object getCellEditorValue() {
			return Boolean.valueOf(checkBox.isSelected());
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Sample test = new Sample();

		f.getContentPane().add(test);
		f.setSize(300, 300);
		f.show();
	}
}