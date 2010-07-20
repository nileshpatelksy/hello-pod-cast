package com.hellocode.service;

import java.util.ArrayList;

import com.hellocode.model.PodCastURL;

public final class Config {
	public  String disk_main = "D:\\kankan";
	public  ArrayList<String> disk_other = new ArrayList<String>();
	public ArrayList<PodCastURL> feed_au = new ArrayList<PodCastURL>();
			
	public Config() {
		//create file
	}	

	@Override
	public String toString() {
		return "Config [disk_main=" + disk_main + ", disk_other=" + disk_other
				+ "]";
	}

	
	
}
