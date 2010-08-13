package com.hellocode.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getDateString() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getNow_YMDHMS() {

		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));

		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}

		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			day = "0" + day;
		}

		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		if (hour.length() == 1) {
			hour = "0" + hour;
		}

		String minute = String.valueOf(c.get(Calendar.MINUTE));
		if (minute.length() == 1) {
			minute = "0" + minute;
		}

		String second = String.valueOf(c.get(Calendar.SECOND));
		if (second.length() == 1) {
			second = "0" + second;
		}
		String ymdhms = year + month + day + hour + minute + second;
		return ymdhms;
	}

	public static void main(String[] args) {

		String precise_time = getNow_YMDHMS();
		Util.print(precise_time);
	}
}