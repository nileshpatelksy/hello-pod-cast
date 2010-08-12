package com.hellocode.service;

import java.io.File;
import java.util.ArrayList;

import com.hellocode.main.PodCast;
import com.hellocode.util.FileUtil;
import com.hellocode.util.NetWorkingUtil;
import com.hellocode.util.Util;

public class DownLoad {
	public volatile static Integer job_count = 0;
	private String folder_name;
	public static ArrayList<Thread> threads = new ArrayList<Thread>();

	// not contain file name.
	private void getFile(String url) {
		// TODO net.url-file-save to path

		FileUtil.createDir(RunTime.CONFIG.disk_main + File.separator
				+ folder_name);
		String name = Util.getFileName(url);// copy
		String abs_name = RunTime.CONFIG.disk_main + File.separator
				+ this.folder_name ;

		Util.print("$$$$$$:   " + abs_name);
//		if (new File(abs_name).exists()) {
//			// skip if had downloaded.
//			return;
//		}
		// down load it
		NetWorkingUtil.download(url, abs_name);

//		// check the file size
//		File file = new File(abs_name);
//		if (file.exists() && file.length() == 0) {
//			file.delete();
//		}

	}

	

	private synchronized void add() {
		synchronized (job_count) {
			job_count++;
		}
		Util.print("job_count..." + job_count);
	}

	private synchronized void sub() {
		synchronized (job_count) {
			job_count--;
		}
		Util.print("job_count..." + job_count);
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
				PodCast.main.lb_info.setText("添加下载线程，共" + job_count + "个下载线程");
				int all = files.get().size();
				int doing = 0;
				PodCast.main.progress.setMax(all);
				for (String url : files.get()) {
					
					all = files.get().size();
					PodCast.main.progress.setMax(all);
					PodCast.main.progress.setDone(doing);
					doing++;
					PodCast.main.progress.setDoing(doing);
					// get file
					DownLoad.this.getFile(url);
					Util.print(Thread.currentThread().getName() + "---"
							+ test + "downloading..." + url);
					
					//PodCast.main.lb_info.setText("完成:" + url);
				}
				files.remove();
				DownLoad.this.sub();
				PodCast.main.progress.setDone("还有" + job_count + "个下载线程");
				PodCast.main.lb_info.setText(DownLoad.this.folder_name+"已经完成,剩余" + job_count + "个下载线程");
				

				if (job_count == 0) {
					PodCast.main.progress.setDone("下载线程全部完成");
					PodCast.main.lb_info.setText("下载线程全部完成");
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
