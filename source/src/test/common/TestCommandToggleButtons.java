/*
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package test.common;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.icon.FilteredResizableIcon;

import test.svg.transcoded.edit_paste;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class TestCommandToggleButtons extends JFrame {
	public TestCommandToggleButtons() {
		super("Command button test");
		this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

		this.setLayout(new BorderLayout());

		FormLayout lm = new FormLayout(
				"right:pref, 10dlu, center:pref, 4dlu, center:pref", "");
		// lm.setColumnGroups(new int[][] { { 3, 5, 7, 9 } });
		DefaultFormBuilder builder = new DefaultFormBuilder(lm);
		builder.setDefaultDialogBorder();

		builder.append("");
		builder.append("Short text");
		builder.append("Long text");

		addButtons(builder, CommandButtonDisplayState.BIG);
		addButtons(builder, CommandButtonDisplayState.TILE);
		addButtons(builder, CommandButtonDisplayState.MEDIUM);
		addButtons(builder, CommandButtonDisplayState.SMALL);

		JPanel buttonsPanel = builder.getPanel();
		this.add(buttonsPanel, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.configureControlPanel(controlPanel);

		this.add(controlPanel, BorderLayout.SOUTH);
	}

	protected static String stamp() {
		return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
	}

	private void addButtons(DefaultFormBuilder builder,
			CommandButtonDisplayState state) {
		builder.append(state.getDisplayName() + " state");

		JCommandToggleButton buttonWithShortText = createToggleButton(state,
				"Short");
		builder.append(buttonWithShortText);
		JCommandToggleButton buttonWithLongText = createToggleButton(state,
				"Longer lines");
		builder.append(buttonWithLongText);
	}

	protected JCommandToggleButton createToggleButton(
			CommandButtonDisplayState state, String title) {
		final JCommandToggleButton mainButton = new JCommandToggleButton(title,
				new edit_paste());
		mainButton.setDisabledIcon(new FilteredResizableIcon(new edit_paste(),
				new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),
						null)));
		mainButton.setExtraText("Extra for selection");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(stamp()
						+ ": button activated, selection state is "
						+ mainButton.getActionModel().isSelected());
			}
		});
		mainButton.setDisplayState(state);
		mainButton.setFlat(false);
		return mainButton;
	}

	protected void configureControlPanel(JPanel controlPanel) {
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		final JComboBox jcb = new JComboBox(lafs);
		jcb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JFrame frame = TestCommandToggleButtons.this;
						boolean wasDecoratedByOS = !frame.isUndecorated();
						try {
							LookAndFeelInfo selected = (LookAndFeelInfo) jcb
									.getSelectedItem();
							UIManager.setLookAndFeel(selected.getClassName());
							SwingUtilities.updateComponentTreeUI(frame);
						} catch (Exception exc) {
						}
						boolean canBeDecoratedByLAF = UIManager
								.getLookAndFeel()
								.getSupportsWindowDecorations();
						if (canBeDecoratedByLAF == wasDecoratedByOS) {
							boolean wasVisible = frame.isVisible();

							frame.setVisible(false);
							frame.dispose();
							if (!canBeDecoratedByLAF) {
								// see the java docs under the method
								// JFrame.setDefaultLookAndFeelDecorated(boolean
								// value) for description of these 2 lines:
								frame.setUndecorated(false);
								frame.getRootPane().setWindowDecorationStyle(
										JRootPane.NONE);

							} else {
								frame.setUndecorated(true);
								frame.getRootPane().setWindowDecorationStyle(
										JRootPane.FRAME);
							}
							frame.setVisible(wasVisible);
							wasDecoratedByOS = !frame.isUndecorated();
						}
					}
				});
			}
		});
		jcb.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				return super.getListCellRendererComponent(list,
						((LookAndFeelInfo) value).getName(), index, isSelected,
						cellHasFocus);
			}
		});
		controlPanel.add(jcb);

		final JCheckBox enabled = new JCheckBox("enabled");
		enabled.setSelected(true);
		enabled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scan(TestCommandToggleButtons.this);
						repaint();
					}

					private void scan(Container c) {
						for (int i = 0; i < c.getComponentCount(); i++) {
							Component child = c.getComponent(i);
							if (child instanceof JCommandToggleButton)
								child.setEnabled(enabled.isSelected());
							if (child instanceof Container)
								scan((Container) child);
						}
					}
				});
			}
		});
		controlPanel.add(enabled);

		final JCheckBox actionEnabled = new JCheckBox("action enabled");
		actionEnabled.setSelected(true);
		actionEnabled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scan(TestCommandToggleButtons.this);
						repaint();
					}

					private void scan(Container c) {
						for (int i = 0; i < c.getComponentCount(); i++) {
							Component child = c.getComponent(i);
							if (child instanceof JCommandToggleButton)
								((JCommandToggleButton) child).getActionModel()
										.setEnabled(actionEnabled.isSelected());
							if (child instanceof Container)
								scan((Container) child);
						}
					}
				});
			}
		});
		controlPanel.add(actionEnabled);

		final JCheckBox actionOnPress = new JCheckBox("action on press");
		actionOnPress.setSelected(false);
		actionOnPress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scan(TestCommandToggleButtons.this);
						repaint();
					}

					private void scan(Container c) {
						for (int i = 0; i < c.getComponentCount(); i++) {
							Component child = c.getComponent(i);
							if (child instanceof JCommandToggleButton)
								((JCommandToggleButton) child).getActionModel()
										.setFireActionOnPress(
												actionOnPress.isSelected());
							if (child instanceof Container)
								scan((Container) child);
						}
					}
				});
			}
		});
		controlPanel.add(actionOnPress);

		final JCheckBox flat = new JCheckBox("flat");
		flat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scan(TestCommandToggleButtons.this);
						repaint();
					}

					private void scan(Container c) {
						for (int i = 0; i < c.getComponentCount(); i++) {
							Component child = c.getComponent(i);
							if (child instanceof JCommandToggleButton)
								((JCommandToggleButton) child).setFlat(flat
										.isSelected());
							if (child instanceof Container)
								scan((Container) child);
						}
					}
				});
			}
		});
		controlPanel.add(flat);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String[] args) {
		UIManager.installLookAndFeel("JGoodies Plastic",
				"com.jgoodies.looks.plastic.PlasticLookAndFeel");
		UIManager.installLookAndFeel("JGoodies PlasticXP",
				"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Plastic3D",
				"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Windows",
				"com.jgoodies.looks.windows.WindowsLookAndFeel");

		UIManager.installLookAndFeel("Synthetica base",
				"de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlackMoon",
				"de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlackStar",
				"de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueIce",
				"de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueMoon",
				"de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueSteel",
				"de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
		UIManager.installLookAndFeel("Synthetica GreenDream",
				"de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel");
		UIManager
				.installLookAndFeel("Synthetica MauveMetallic",
						"de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel");
		UIManager
				.installLookAndFeel("Synthetica OrangeMetallic",
						"de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
		UIManager.installLookAndFeel("Synthetica SkyMetallic",
				"de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel");
		UIManager.installLookAndFeel("Synthetica SilverMoon",
				"de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica WhiteVision",
				"de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");

		UIManager.installLookAndFeel("A03", "a03.swing.plaf.A03LookAndFeel");
		UIManager.installLookAndFeel("Liquid",
				"com.birosoft.liquid.LiquidLookAndFeel");
		UIManager.installLookAndFeel("Napkin",
				"net.sourceforge.napkinlaf.NapkinLookAndFeel");
		UIManager.installLookAndFeel("Pagosoft",
				"com.pagosoft.plaf.PgsLookAndFeel");
		UIManager.installLookAndFeel("Squareness",
				"net.beeger.squareness.SquarenessLookAndFeel");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TestCommandToggleButtons frame = new TestCommandToggleButtons();
				frame.setSize(800, 400);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}
}
