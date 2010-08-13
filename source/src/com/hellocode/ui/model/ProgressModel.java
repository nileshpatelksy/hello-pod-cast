package com.hellocode.ui.model;

import java.awt.Color;

import javax.swing.event.ChangeListener;

public interface ProgressModel {
	int CODE_PRE = 0;
	int CODE_INIT = 1;
	int CODE_RUN = 5;
	int CODE_SUSPEND = 9;
	int CODE_DONE = 10;
	int CODE_STOP = -1;

	public int code();

	void addChangeListener(ChangeListener x);

	void removeChangeListener(ChangeListener x);

	void setDoing(int doing);

	void setDone(String txt);

	String getDone_string();

	String getInit_string();

	void setInit_string(String initString);

	int getP_x();

	int getP_y();

	int getHight();

	int getW_long();

	int getDone();

	int getDoing();

	int getMax();

	void setMax(int m);

	void setDone(int d);

	void setColor(Color... color);

	int getCode();

	Color[] getColor();
}
