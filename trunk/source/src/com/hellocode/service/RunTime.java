package com.hellocode.service;

import java.io.FileNotFoundException;

import com.hellocode.model.PodCastURL;
import com.hellocode.util.XML2JavaUtil;

public final class RunTime {

	public static String selectedFileName = "";
	public static boolean run = false;
	public static Config CONFIG = new Config();
	static {
		init();
	}	

	public static void destroy(){
		try {
			XML2JavaUtil.java2XML("mead.xml", RunTime.CONFIG);
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("DESTROY");
	}
	public static void init() {

		try {
			CONFIG = XML2JavaUtil.XML2Java("mead.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CONFIG = new Config();
		}
		// feed_tv.add(url);
	}

	public static PodCastURL findFeedByName(String name) {
		for (PodCastURL pod : RunTime.CONFIG.feed_au) {
			if (pod.getName().equalsIgnoreCase(name)) {
				return pod;
			}
		}
		// throw new RuntimeException();
		return null;
	}

	public static void refreshAll() {
		for (PodCastURL pod : RunTime.CONFIG.feed_au) {
			pod.reFreshURL();
		}
	}

	public static void main(String[] args) {
		// RunTime run = new RunTime();
		System.out.println(RunTime.CONFIG.disk_main);

	}
}
