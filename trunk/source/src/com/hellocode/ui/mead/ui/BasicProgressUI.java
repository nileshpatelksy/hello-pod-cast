/*
 * Copyright (c) 2005-2007 Flamingo Kirill Grouchnikov. All Rights Reserved.
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
package com.hellocode.ui.mead.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;

import com.hellocode.ui.mead.MeadProgress;
import com.hellocode.ui.model.ProgressModel;
import com.hellocode.util.Util;

/**
 * Basic UI for flexi slider {@link JFlexiSlider}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicProgressUI extends ProgressUI {
	/**
	 * The associated flexi slider.
	 */
	// delegate pattern
	protected MeadProgress progress;
	// composite kit

	protected CellRendererPane sliderRendererPane;
	// listener
	protected MouseListener mouseListener;
	protected MouseMotionListener mouseMotionListener;
	protected ChangeListener processChangeListener;

	public static ComponentUI createUI(JComponent c) {
		return new BasicProgressUI();
	}

	public void installUI(JComponent c) {
		this.progress = (MeadProgress) c;
		installDefaults();
		installComponents();
		installListeners();
		c.setLayout(createLayoutManager());
		c.setBorder(new EmptyBorder(1, 1, 100, 100));
	}

	public void uninstallUI(JComponent c) {
		c.setLayout(null);
		uninstallListeners();
		uninstallComponents();
		uninstallDefaults();

		this.progress = null;
	}

	public void installDefaults() {
	}

	public void installComponents() {
		this.sliderRendererPane = new CellRendererPane();
		this.progress.add(sliderRendererPane);
	}

	public void installListeners() {
		this.mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// 事件处理
			}
		};
		this.progress.addMouseListener(this.mouseListener);

		this.mouseMotionListener = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// 处理事件
			}
		};
		this.progress.addMouseMotionListener(this.mouseMotionListener);

		this.processChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				progress.repaint();
			}
		};

	}

	public void uninstallDefaults() {
		// 卸载组件
		this.progress.remove(this.sliderRendererPane);
		this.sliderRendererPane = null;
	}

	public void uninstallComponents() {

	}

	public void uninstallListeners() {
		this.progress.removeMouseListener(this.mouseListener);
		this.mouseListener = null;

		this.progress.removeMouseMotionListener(this.mouseMotionListener);
		this.mouseMotionListener = null;

		this.progress.getModel().removeChangeListener(
				this.processChangeListener);
		this.processChangeListener = null;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);

		this.paintMe(g);
	}

	private final static int PIX=14;
	private void paintMe(Graphics g) {
		ProgressModel m = this.progress.getModel();
		int code = m.getCode();
		int p_x = m.getP_x();
		int p_y = m.getP_y();
		int p_hight = m.getHight();
		// int p_all = m.getW_long();

		int p_done = m.getDone() * m.getW_long() / m.getMax();
		int p_doing = m.getDoing() * m.getW_long() / m.getMax();

		int p_doing_len = p_doing - p_done;

		String text_complete = m.getDone() * 100 / m.getMax() + "%" + "done";
		String text_doing = (m.getDoing() * 100 / m.getMax() - m.getDone()
				* 100 / m.getMax())
				+ "%" + "doing";

		if (p_doing_len < 0) {
			// return;
		} else if (m.getCode() == ProgressModel.CODE_DONE) {
			g.setColor(Color.green);
			g.fillRect(p_x, p_y, m.getW_long(), p_hight);

			g.setColor(m.getColor()[3]);
			g.drawChars(m.getDone_string().toCharArray(), 0, m.getDone_string()
					.length(), p_x, p_y+2);
			Util.print("完成###DONE= x=" + p_x + "; y=" + p_y);
			g.setColor(m.getColor()[3]);
			String txt_dong = "100%done";
			g.drawChars(txt_dong.toCharArray(), 0, txt_dong.length(), p_done,
					p_y + p_hight + 10);
			return;
		}
		// else if (m.getCode() <= ProgressModel.CODE_INIT) {
		// g.setColor(m.getColor()[0]);
		// g.fillRect(p_x, p_y, m.getW_long(), p_hight);
		// Util.print("INIT= x=" + p_x + "; y=" + p_y + "; long="
		// + m.getW_long());
		// g.drawChars(m.getInit_string().toCharArray(), 0, m.getInit_string()
		// .length(), p_x, p_y);
		// // in doing, change x=80,with=20=long
		// g.setColor(m.getColor()[2]);
		// g.fillRect(p_x, p_y, p_doing, p_hight);
		// Util.print("INIT,doing= x=" + p_x + "; y=" + p_y + "; long="
		// + p_doing_len);
		// // write in doing process
		// g.drawChars(text_doing.toCharArray(), 0, text_doing.length(),
		// p_done, p_y + p_hight + 10);
		//
		// return;
		// }

		// all long
		g.setColor(m.getColor()[0]);
		g.fillRect(p_x, p_y, m.getW_long(), p_hight);
		Util.print("1RUN,all= x=" + p_x + "; y=" + p_y + "; long="
				+ m.getW_long() + "  \n" + text_complete);

		// in doing, change x=80,with=20=long
		g.setColor(m.getColor()[2]);
		g.fillRect(p_x, p_y, p_doing, p_hight);
		Util.print("3RUN,doing= x=" + p_x + "; y=" + p_y + "; 起始=" + p_done
				+ "long=" + p_doing_len);
		// write in doing process
		g.drawChars(text_doing.toCharArray(), 0, text_doing.length(), p_doing,
				p_y + p_hight + 10);

		// done
		g.setColor(m.getColor()[1]);
		g.fillRect(p_x, p_y, p_done, p_hight);
		g.drawChars(text_complete.toCharArray(), 0, text_complete.length(),
				p_x, p_y+2);
		Util.print("2RUN,done: x=" + p_x + "; y=" + p_y + "; long=" + p_done);

	}

	protected LayoutManager createLayoutManager() {
		return new MProgressLayout();
	}

	/**
	 * Layout for the flexi slider.
	 * 
	 * @author Kirill Grouchnikov
	 */
	protected class MProgressLayout implements LayoutManager {
		public void addLayoutComponent(String name, Component c) {
		}

		public void removeLayoutComponent(Component c) {
		}

		public Dimension preferredLayoutSize(Container c) {
			int width = 0;
			int height = 0;
			ProgressModel m = BasicProgressUI.this.progress.getModel();
			width = m.getW_long() + 80;
			height = m.getHight() + 50;
			Util.print("x=" + width);
			Util.print("y=" + height);
			return new Dimension(width, height);
		}

		public Dimension minimumLayoutSize(Container c) {
			return this.preferredLayoutSize(c);
		}

		public void layoutContainer(Container c) {
			Insets ins = c.getInsets();
			int width = c.getWidth();
			int height = c.getHeight();
			Util.print("Container w" + width);
			Util.print("Container h" + height);
		}
	}

}
