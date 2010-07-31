package com.hellocode.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.hellocode.main.PodCast;
import com.hellocode.util.FileUtil;
import com.hellocode.util.NetWorkingUtil;

public class DownLoad {
	public volatile static Integer job_count = 0;
	private String folder_name;
	public static ArrayList<Thread> threads = new ArrayList<Thread>();

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
		// down load it
		NetWorkingUtil.download(url, abs_name);

		// check the file size
		File file = new File(abs_name);
		if (file.exists() && file.length() == 0) {
			file.delete();
		}

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
			final ThreadLocal<ArrayList<String>> files = new ThreadLocal<ArrayList<String>>();
			@Override
			public void run() {
				
				files.set(urlList);

				String test = job_count.toString();
				Thread.currentThread().setName(test);
				DownLoad.this.add();
				for (String url : files.get()) {
					//get file
					DownLoad.this.getFile(url);
					System.out.println(Thread.currentThread().getName() + "---"
							+ test + "downloading..." + url);
					PodCast.main.lb_down_load.setText("完成:" + url);
					// PodCast.main.lb_info.setText("完成:" + url);
				}
				DownLoad.this.sub();
				PodCast.main.lb_down_load.setText("还有" + job_count + "个下载线程");

				if (job_count == 0) {
					PodCast.main.lb_down_load.setText("下载任务全部完成");
					// PodCast.main.lb_info.setText("任务全部完成");
				}

			}
		});
		thread.setName(job_count.toString());
		thread.start();
		this.threads.add(thread);
	}

	// check dir exist, or create one
	private boolean checkDir() {
		// TODO
		return true;
	}

}
