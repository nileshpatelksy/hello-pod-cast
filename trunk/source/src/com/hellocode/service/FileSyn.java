package com.hellocode.service;

import com.hellocode.util.FileUtil;

public class FileSyn {

	private boolean checkOtherDisk() {
		if (RunTime.CONFIG.disk_other.isEmpty()) {
			return false;
		}
		for (String p : RunTime.CONFIG.disk_other) {
			FileUtil.createDir(p);
		}
		FileUtil.createDir(RunTime.CONFIG.disk_main);
		return true;
	}

	public void Process() {
		if (!this.checkOtherDisk()) {
			return;
		}
		
		for (String p : RunTime.CONFIG.disk_other) {
			FileUtil.copyFolder(RunTime.CONFIG.disk_main, p);
		}

		
		//copy files

	}
}
