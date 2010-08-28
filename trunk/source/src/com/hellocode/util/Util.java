package com.hellocode.util;

import java.util.Date;
import java.util.Random;

public class Util {
	private static Double d;
	private static Double f;
	private final static String unit = "MB";

	public static String transSize(String in) {
		try {
			d = Double.valueOf(in);
			f = d / 1048576.0;
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00 ");
			String new_f0 = df.format(f);
			return new_f0 + unit;
		} catch (Exception e) {
			return 0 + unit;
		}
	}

	public static String getRandomFileName() {
		String result = "r" + DateUtil.getDateString();
		return result;
	}

	public static void print(String msg) {
		System.out.println("INFO:#   " + msg);
	}

	public static void main(String[] args) {
		Util.print(getRandomFileName());
	}

	// spit url 2 name
	public static String getFileName(String url) {
		int i = url.lastIndexOf('/');
		String name = url.substring(i + 1);
		i = name.lastIndexOf(Media.FILE_MP3);
		if (i > 0) {
			name = name.substring(0, i) + Media.FILE_MP3;
		}
		Util.print("extract file name :== " + name);
		return name;
	}

	private static final Random rnd = new Random();
	private static final Date date = new Date();
}
