package com.hellocode.util;

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
}
