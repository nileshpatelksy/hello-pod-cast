package test.contrib;

import javax.swing.*;

import org.jvnet.flamingo.ribbon.*;

import test.svg.transcoded.edit_select_all;

public class RibbonCheckBoxCutOff extends JRibbonFrame {
	JCheckBox _isNameVisible = new JCheckBox("Name");
	JCheckBox _isPropertyNameVisible = new JCheckBox("Variable Name");
	JCheckBox _isPropertyValueVisible = new JCheckBox("Variable Value");
	JCheckBox _isLimitsVisible = new JCheckBox("Limits");

	public RibbonCheckBoxCutOff() {
		super("Ribbon Checkbox Cutoff");
		JRibbonBand band = new JRibbonBand("Test", new edit_select_all());
		band.addRibbonComponent(new JRibbonComponent(_isNameVisible));
		band.addRibbonComponent(new JRibbonComponent(_isPropertyNameVisible));
		band.addRibbonComponent(new JRibbonComponent(_isPropertyValueVisible));
		// Adding this checkbox will cutoff the previous checkbox labels
		band.addRibbonComponent(new JRibbonComponent(_isLimitsVisible));
		RibbonTask task = new RibbonTask("test", band);
		this.getRibbon().addTask(task);
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				// SubstanceLookAndFeel.setSkin(new CremeSkin());
				new RibbonCheckBoxCutOff().setVisible(true);
			}
		});
	}

}
