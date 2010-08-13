package com.hellocode.ui.model;

import java.awt.Color;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class DefaultProgressModel implements ProgressModel {

	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	protected void fireStateChanged() {
		ChangeEvent event = new ChangeEvent(this);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hellocode.ui.model.ProgressModelT#getChangeListeners()
	 */
	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) listenerList
				.getListeners(ChangeListener.class);
	}

	public DefaultProgressModel(int max, int x, int y, int hight, int w_long,
			String init) {
		this.p_x = x;
		this.p_y = y;
		this.hight = hight;
		this.max = max;
		this.w_long = w_long;
		this.code = CODE_PRE;
		this.doing = 0;
		this.done = 0;
		this.init_string = init;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hellocode.ui.model.ProgressModelT#setDoing(int)
	 */
	public void setDoing(int doing) {
		if (this.code == CODE_PRE) {
			//this.done = 0;
			this.doing = doing;
			this.code = CODE_INIT;
			return;
		}
		//this.done = this.doing;
		this.doing = doing;
		this.code = CODE_RUN;
	}
	@Override
	public void setDone(int d) {
		this.done = d;
	}

	@Override
	public void setDone(String txt) {
		this.code = CODE_DONE;
		this.done_string = txt;
	}

	@Override
	public int code() {
		return code;
	}

	public int getP_x() {
		return p_x;
	}

	public int getP_y() {
		return p_y;
	}

	public int getHight() {
		return hight;
	}

	public void setMax(int max) {
		this.max = max;
		this.code = CODE_PRE;
	}

	public int getDone() {
		return done;
	}

	public int getDoing() {
		return doing;
	}

	public int getW_long() {
		return w_long;
	}

	public int getMax() {
		return max;
	}


	public String getDone_string() {
		return done_string;
	}

	public void setColor(Color... color) {
		this.color = color;
	}

	public Color[] getColor() {
		return color;
	}

	public int getCode() {
		return code;
	}

	public String getInit_string() {
		return init_string;
	}

	public void setInit_string(String initString) {
		this.init_string = initString;
	}

	private Color[] color = new Color[] { Color.darkGray, Color.BLUE,
			Color.red, Color.black };
	private String init_string = "";
	private String done_string = "";
	private int w_long = 0;
	private int p_x = 0;
	private int p_y = 0;
	private int hight = 0;
	private int max = 0;
	private int done = 0;
	private int doing = 0;
	private int code = CODE_PRE;
	protected EventListenerList listenerList = new EventListenerList();

}
