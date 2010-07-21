package com.hellocode.service;

import java.util.ArrayList;

import com.hellocode.model.JDomPodCastURL;

public final class Config {
	public  String disk_main = "C:\\PodCast";
	public  ArrayList<String> disk_other = new ArrayList<String>();
	public ArrayList<JDomPodCastURL> feed_au = new ArrayList<JDomPodCastURL>();
			
	public Config() {
		//create file
	}	

	@Override
	public String toString() {
		return "Config [disk_main=" + disk_main + ", disk_other=" + disk_other
				+ "]";
	}

	
	
}
