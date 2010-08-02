package utest.ribbon;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.FocusManager;
import javax.swing.JFrame;

import org.fest.assertions.Assertions;
import org.fest.swing.edt.*;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.StringValuePair;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.ribbon.*;
import org.jvnet.flamingo.ribbon.ui.JRibbonGallery;

public class FestRibbonGalleryTestCase extends FestSwingJUnitTestCase {
	JCommandToggleButton[][] buttons;

	JRibbonGallery gallery;

	JRibbonFrame ribbonFrame;

	JRibbonBand ribbonBand;

	final static String GALLERY_NAME = "Gallery";

	@Override
	public void onSetUp() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				int groups = 4;
				int inGroup = 10;
				buttons = new JCommandToggleButton[groups][inGroup];
				for (int i = 0; i < groups; i++) {
					for (int j = 0; j < inGroup; j++) {
						buttons[i][j] = new JCommandToggleButton("Button " + i
								+ ":" + j, new EmptyResizableIcon(32));
					}
				}

				ribbonFrame = new JRibbonFrame();

				ribbonBand = new JRibbonBand("Band", new EmptyResizableIcon(32));

				Map<RibbonElementPriority, Integer> visibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
				visibleButtonCounts.put(RibbonElementPriority.LOW, 2);
				visibleButtonCounts.put(RibbonElementPriority.MEDIUM, 8);
				visibleButtonCounts.put(RibbonElementPriority.TOP, 10);

				List<StringValuePair<List<JCommandToggleButton>>> galleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
				for (int i = 0; i < groups; i++) {
					List<JCommandToggleButton> galleryButtonsList = new ArrayList<JCommandToggleButton>();
					for (int j = 0; j < inGroup; j++) {
						galleryButtonsList.add(buttons[i][j]);
					}
					galleryButtons
							.add(new StringValuePair<List<JCommandToggleButton>>(
									"Group " + i, galleryButtonsList));
				}

				ribbonBand.addRibbonGallery(GALLERY_NAME, galleryButtons,
						visibleButtonCounts, 6, 4, RibbonElementPriority.TOP);
				ribbonFrame.getRibbon().addTask(
						new RibbonTask("Task 0", ribbonBand));

				gallery = ribbonBand.getControlPanel().getRibbonGallery(
						GALLERY_NAME);
			}
		});
	}

	@Test
	public void testSelectionWithMouse() {
		for (int i = 0; i < this.gallery.getButtonCount(); i++) {
			Assertions.assertThat(
					this.gallery.getButtonAt(i).getActionModel().isSelected())
					.isFalse();
		}

		final int[] toTest = { 0, 1, 2, 3, 4, 5, 6 };
		// final boolean[] tested = new boolean[toTest.length];

		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getMaximumWindowBounds();
				ribbonFrame.setPreferredSize(new Dimension(r.width,
						r.height / 2));
				ribbonFrame.pack();
				ribbonFrame.setLocation(r.x, r.y);
				ribbonFrame.setVisible(true);
				ribbonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		robot().waitForIdle();

		for (int sel = 0; sel < toTest.length; sel++) {
			int selCol = toTest[sel];
			final JCommandToggleButton toClick = buttons[0][selCol];
			GuiActionRunner.execute(new GuiTask() {
				@Override
				protected void executeInEDT() throws Throwable {
					FocusManager.getCurrentManager().clearGlobalFocusOwner();
				}
			});
			// Point center = AWT.visibleCenterOf(toClick);
			// // System.err.println("Pressing " + toClick.getText() + " at "
			// // + center);
			// robot().pressMouse(toClick, center, MouseButton.LEFT_BUTTON);
			// robot().waitForIdle();
			// // System.err.println("Releasing");
			// robot().releaseMouse(MouseButton.LEFT_BUTTON);
			robot().click(toClick);
			robot().waitForIdle();

			// System.err.println("Clicked " + toClick.getText());// + " at " +
			// center);

			// System.err.println("Will query for selection of "
			// + toClick.getText());
			boolean isSelected = GuiActionRunner
					.execute(new GuiQuery<Boolean>() {
						@Override
						protected Boolean executeInEDT() throws Throwable {
							// System.err.println(toClick.getText() + ":"
							// + toClick.getActionModel().isSelected());
							return toClick.getActionModel().isSelected();
						}
					});
			Assertions.assertThat(isSelected).isTrue();

			for (int i = 0; i < gallery.getButtonGroupCount(); i++) {
				List<JCommandToggleButton> buttonGroup = gallery
						.getButtonGroup("Group " + i);
				for (int j = 0; j < buttonGroup.size(); j++) {
					final JCommandToggleButton button = buttonGroup.get(j);
					isSelected = GuiActionRunner
							.execute(new GuiQuery<Boolean>() {
								@Override
								protected Boolean executeInEDT()
										throws Throwable {
									return button.getActionModel().isSelected();
								}
							});
					// System.err.println(i + ":" + j + "-" + sel + ":"
					// + isSelected);
					Assertions.assertThat(isSelected).isEqualTo(
							(i == 0) && (j == sel));
				}
			}
		}
	}
}
