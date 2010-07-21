package com.hellocode.service;

import java.io.File;
import java.util.ArrayList;

import com.hellocode.main.PodCast;
import com.hellocode.util.FileUtil;
import com.hellocode.util.NetWorkingUtil;

public class DownLoad {
	public volatile static Integer job_count = 0;
	private String folder_name;
	public ArrayList<Thread> threads = new ArrayList<Thread>();

	// not contain file name.
	private void getFile(String url) {
		// TODO net.url-file-save to path

		FileUtil.createDir(RunTime.CONFIG.disk_main + File.separator
				+ folder_name);
		String name = this.getFileName(url);// copy
		String abs_name = RunTime.CONFIG.disk_main + File.separator
				+ this.folder_name + File.separator + name;

		System.out.println("$$$$$$:   " + abs_name);
		if (new File(abs_name).exists()) {
			// skip if had downloaded.
			return;
		}

		NetWorkingUtil.getHttpFile(url, abs_name);

	}

	// spit url 2 name
	private String getFileName(String url) {
		int i = url.lastIndexOf('/');
		String name = url.substring(i + 1);
		System.out.println("extract file name :== " + name);
		return name;
	}

	private synchronized void add() {
		synchronized (job_count) {
			job_count++;
		}
		System.out.println("job_count..." + job_count);
	}

	private synchronized void sub() {
		synchronized (job_count) {
			job_count--;
		}
		System.out.println("job_count..." + job_count);
	}

	/**
	 * single thread
	 * 
	 * @param fileList
	 * @param Path
	 */
	public void Process(final ArrayList<String> urlList, String ipath) {
		if (urlList.isEmpty()) {
			return;
		}
		this.folder_name = ipath;
		if (!this.checkDir()) {
			return;
		}
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String test = job_count.toString();
				DownLoad.this.add();
				for (String url : urlList) {
					DownLoad.this.getFile(url);
					System.out.println(test + "downloading..." + url);
					PodCast.main.lb_info.setText("完成:" + url);
				}
				DownLoad.this.sub();
				PodCast.main.lb_info.setText(job_count + "个下载线程");
				if (job_count == 0) {
					PodCast.main.lb_info.setText("任务全部完成");
				}
				
			}
		});
		thread.setName(job_count.toString());
		thread.start();
	}

	// check dir exist, or create one
	private boolean checkDir() {
		// TODO
		return true;
	}

}
