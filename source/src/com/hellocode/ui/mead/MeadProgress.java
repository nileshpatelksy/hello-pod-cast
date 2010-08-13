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
package com.hellocode.ui.mead;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.hellocode.ui.mead.ui.BasicProgressUI;
import com.hellocode.ui.mead.ui.ProgressUI;
import com.hellocode.ui.model.DefaultProgressModel;
import com.hellocode.ui.model.ProgressModel;

/**
 * 
 * @author Kirill Grouchnikov
 */
public class MeadProgress extends JComponent {
	private static final String uiClassID = "MeadProcessUI";

	public void setUI(ProgressUI ui) {
		super.setUI(ui);
	}

	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			// 自定义look and feel
			setUI((ProgressUI) UIManager.getUI(this));
		} else {
			// 基本UI
			setUI(new BasicProgressUI());
		}
	}

	public ProgressUI getUI() {
		return (ProgressUI) ui;
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public MeadProgress(int max, int x, int y, int hight, int w_long,
			String init_string) throws NullPointerException,
			IllegalArgumentException {
		this.model = new DefaultProgressModel(max, x, y, hight, w_long,
				init_string);
		this.updateUI();
	}

	public void setColor(Color all, Color done, Color doing, Color text) {
		Color[] c = new Color[4];
		c[0] = all;
		c[1] = done;
		c[2] = doing;
		c[3] = text;
		this.model.setColor(c);

	}

	public void setModel(ProgressModel model) {
		this.model = model;
	}

	public void setMax(int max) {
		if(max<=0){
			this.setDone("任务完成");
		}
		this.model.setMax(max);
		this.updateUI();
	}

	public void setDone(int done) {
		this.model.setDone(done);
		this.updateUI();
	}

	public ProgressModel getModel() {
		return this.model;
	}

	public void setDoing(int doing) {
		this.model.setDoing(doing);
		this.updateUI();
	}

	public void setDone(String txt) {
		//this.model.setDoing(this.model.getMax());
		this.model.setDone(txt);
		this.updateUI();
	}

	protected ProgressModel model;
}
