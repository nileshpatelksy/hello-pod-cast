package utest.ribbon;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

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

public class SimpleFestRibbonGalleryTestCase extends FestSwingJUnitTestCase {
	JCommandToggleButton button;

	JRibbonGallery gallery;

	JRibbonFrame ribbonFrame;

	JRibbonBand ribbonBand;

	final static String GALLERY_NAME = "Gallery";

	@Override
	protected void onSetUp() {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button = new JCommandToggleButton("Button",
						new EmptyResizableIcon(32));

				button.addFocusListener(new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						System.out.println("Gained focus from "
								+ e.getOppositeComponent());
					}

					@Override
					public void focusLost(FocusEvent e) {
						System.out.println("Lost focus to "
								+ e.getOppositeComponent());
					}
				});

				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						System.out.println("Mouse pressed");
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						System.out.println("Mouse released");
					}
				});

				ribbonFrame = new JRibbonFrame();

				ribbonBand = new JRibbonBand("Band", new EmptyResizableIcon(32));

				Map<RibbonElementPriority, Integer> visibleButtonCounts = new HashMap<RibbonElementPriority, Integer>();
				visibleButtonCounts.put(RibbonElementPriority.LOW, 1);
				visibleButtonCounts.put(RibbonElementPriority.MEDIUM, 1);
				visibleButtonCounts.put(RibbonElementPriority.TOP, 1);

				List<StringValuePair<List<JCommandToggleButton>>> galleryButtons = new ArrayList<StringValuePair<List<JCommandToggleButton>>>();
				List<JCommandToggleButton> galleryButtonsList = new ArrayList<JCommandToggleButton>();
				galleryButtonsList.add(button);
				galleryButtons
						.add(new StringValuePair<List<JCommandToggleButton>>(
								"Group 0", galleryButtonsList));

				ribbonBand.addRibbonGallery(GALLERY_NAME, galleryButtons,
						visibleButtonCounts, 6, 4, RibbonElementPriority.TOP);
				ribbonFrame.getRibbon().addTask(
						new RibbonTask("Task 0", ribbonBand));

				gallery = ribbonBand.getControlPanel().getRibbonGallery(
						GALLERY_NAME);

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
	}

	@Test
	public void testSelectionWithMouse() {
		for (int i = 0; i < this.gallery.getButtonCount(); i++) {
			final int index = i;
			boolean isSelected = GuiActionRunner
					.execute(new GuiQuery<Boolean>() {
						@Override
						protected Boolean executeInEDT() throws Throwable {
							JCommandToggleButton button = gallery
									.getButtonAt(index);
							return button.getActionModel().isSelected();
						}
					});
			Assertions.assertThat(isSelected).isFalse();
		}

		robot().click(button);
		robot().waitForIdle();

		// robot().pressMouse(button, AWT.centerOf(button),
		// MouseButton.LEFT_BUTTON);
		// robot().waitForIdle();
		// robot().releaseMouse(MouseButton.LEFT_BUTTON);
		// robot().waitForIdle();

		boolean isSelected = GuiActionRunner.execute(new GuiQuery<Boolean>() {
			@Override
			protected Boolean executeInEDT() throws Throwable {
				return button.getActionModel().isSelected();
			}
		});
		Assertions.assertThat(isSelected).isTrue();
	}
}
