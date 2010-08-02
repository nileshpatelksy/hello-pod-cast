package test.contrib;

import java.awt.Dimension;

import javax.swing.*;

import org.jvnet.flamingo.ribbon.*;

import test.ribbon.SimpleResizableIcon;

public class TestWideRibbonContent extends JRibbonFrame {
	public TestWideRibbonContent() {
		JRibbonBand rowSpanBand = new JRibbonBand("Row spans",
				new SimpleResizableIcon(RibbonElementPriority.TOP, 32, 32));

		JTextPane textPane1 = new JTextPane();
		textPane1.setText("this control should be shown on three rows");
		JScrollPane scrollPane1 = new JScrollPane(textPane1);
		scrollPane1.setPreferredSize(new Dimension(280, 100));
		JRibbonComponent text1 = new JRibbonComponent(scrollPane1);
		rowSpanBand.addRibbonComponent(text1, 3);

		JRibbonComponent text2 = new JRibbonComponent(new JTextField("one row",
				8));
		rowSpanBand.addRibbonComponent(text2, 1);

		RibbonTask task = new RibbonTask("Task", rowSpanBand);

		this.getRibbon().addTask(task);
		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TestWideRibbonContent().setVisible(true);
			}
		});
	}

}
