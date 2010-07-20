package com.hellocode.test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

class CheckBoxRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}

}

class CheckButtonEditor extends DefaultCellEditor implements ItemListener {
	private JCheckBox button;

	public CheckButtonEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		button = (JCheckBox) value;
		button.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}

public class CheckboxTable {
	JTable table = new JTable();

	public CheckboxTable() {
		JFrame frame = new JFrame("Table");
		table = this.gettable();
		table.addMouseListener(new MouseAdapter(){});
		JScrollPane src = new JScrollPane(table);
		frame.getContentPane().add(src);
		frame.setSize(new Dimension(400, 200));
		frame.setVisible(true);
	}

	public JTable gettable() {
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] {
				{ "(1,1)", "(1,2)", "(1,3)", "(1,4)", "(1,5)",
						new JCheckBox("1") },
				{ "(2,1)", "(2,2)", "(2,3)", "(2,4)", "(2,5)",
						new JCheckBox("2") },
				{ "(3,1)", "(3,2)", "(3,3)", "(3,4)", "(3,5)",
						new JCheckBox("3") }, }, new Object[] { "第1列", "第2列",
				"第3列", "第4列", "第5列", "选取" });

		JTable table = new JTable(dm) {
//			public void tableChanged(TableModelEvent e) {
//				super.tableChanged(e);
//				repaint();
//			}
		};
		table.getColumn("选取").setCellEditor(
				new CheckButtonEditor(new JCheckBox()));
		table.getColumn("选取").setCellRenderer(new CheckBoxRenderer());

		return table;
	}

	public static void main(String args[]) {
		new CheckboxTable();
	}

	
}
