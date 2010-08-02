package utest.common;

import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JSeparator;

import org.fest.assertions.Assertions;
import org.fest.swing.edt.*;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.ResizableIcon;

import test.svg.transcoded.edit_cut;
import test.svg.transcoded.edit_paste;

public class CommandButtonNoTextSizesTestCase extends FestSwingJUnitTestCase {
	JFrame buttonFrame;
	JCommandButton button1;
	JCommandButton button2;

	@Override
	@Before
	public void onSetUp() {
		final ResizableIcon icon1 = new edit_paste();
		final ResizableIcon icon2 = new edit_cut();

		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				buttonFrame = new JFrame();
				buttonFrame.setLayout(new FlowLayout());

				button1 = new JCommandButton(icon1);
				button1.setDisplayState(CommandButtonDisplayState.BIG);
				buttonFrame.add(button1);

				button2 = new JCommandButton(icon2);
				button2.setDisplayState(CommandButtonDisplayState.BIG);
				buttonFrame.add(button2);

				buttonFrame.setSize(300, 200);
				buttonFrame.setLocationRelativeTo(null);
				buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				buttonFrame.setVisible(true);
			}
		});

		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				Point locOnScreen = buttonFrame.getLocationOnScreen();
				locOnScreen.move(10, 20);
				robot().moveMouse(locOnScreen);
			}
		});
	}

	@Test
	public void actionOnlyBigAndActionOnlyBigHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.BIG);
				button2.setDisplayState(CommandButtonDisplayState.BIG);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button1.getPreferredSize().height
						- button2.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void splitBigAndSplitBigHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.BIG);
				button2.setDisplayState(CommandButtonDisplayState.BIG);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button1.getPreferredSize().height
						- button2.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void popupOnlyBigAndSplitBigHaveDifferentHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.BIG);
				button2.setDisplayState(CommandButtonDisplayState.BIG);
			}
		});

		robot().waitForIdle();

		// button heights should differ by the preferred height of a horizontal
		// separator
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isEqualTo(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return new JSeparator(JSeparator.HORIZONTAL).getPreferredSize().height;
			}
		}));
	}

	@Test
	public void actionOnlyTileAndActionOnlyTileHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyTileAndActionOnlyBigHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.BIG);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyTileAndSplitTileHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyTileAndSplit2TileHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyTileAndPopupOnlyTileHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void splitTileAndSplitTileHaveSameWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isZero();
	}

	@Test
	public void popupOnlyTileAndSplitTileHaveDifferentWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.TILE);
				button2.setDisplayState(CommandButtonDisplayState.TILE);
			}
		});

		robot().waitForIdle();

		// button widths should differ by the preferred width of a vertical
		// separator
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isEqualTo(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return new JSeparator(JSeparator.VERTICAL).getPreferredSize().width;
			}
		}));
	}

	@Test
	public void actionOnlyMediumAndActionOnlyMediumHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyMediumAndSplitMediumHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyMediumAndSplit2MediumHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlyMediumAndPopupOnlyMediumHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void splitMediumAndSplitMediumHaveSameWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isZero();
	}

	@Test
	public void popupOnlyMediumAndSplitMediumHaveDifferentWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.MEDIUM);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button widths should differ by the preferred width of a vertical
		// separator
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isEqualTo(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return new JSeparator(JSeparator.VERTICAL).getPreferredSize().width;
			}
		}));
	}

	@Test
	public void actionOnlySmallAndActionOnlySmallHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlySmallAndSplitSmallHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlySmallAndSplit2SmallHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void actionOnlySmallAndPopupOnlySmallHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

	@Test
	public void splitSmallAndSplitSmallHaveSameWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isZero();
	}

	@Test
	public void popupOnlySmallAndSplitSmallHaveDifferentWidth() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				button2
						.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.SMALL);
			}
		});

		robot().waitForIdle();

		// button widths should differ by the preferred width of a vertical
		// separator
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().width
						- button1.getPreferredSize().width;
			}
		})).isEqualTo(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return new JSeparator(JSeparator.VERTICAL).getPreferredSize().width;
			}
		}));
	}

	@Test
	public void actionOnlySmallAndActionOnlyMediumHaveSameHeight() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button1.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button2.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
				button1.setDisplayState(CommandButtonDisplayState.SMALL);
				button2.setDisplayState(CommandButtonDisplayState.MEDIUM);
			}
		});

		robot().waitForIdle();

		// button heights should be the same
		Assertions.assertThat(GuiActionRunner.execute(new GuiQuery<Integer>() {
			@Override
			protected Integer executeInEDT() throws Throwable {
				return button2.getPreferredSize().height
						- button1.getPreferredSize().height;
			}
		})).isZero();
	}

}
