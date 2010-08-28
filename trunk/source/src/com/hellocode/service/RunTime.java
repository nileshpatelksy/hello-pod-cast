package com.hellocode.service;

import java.io.FileNotFoundException;

import com.hellocode.exception.NetProblem;
import com.hellocode.main.PodCast;
import com.hellocode.model.JDomPodCastURL;
import com.hellocode.util.FileUtil;
import com.hellocode.util.Util;
import com.hellocode.util.XML2JavaUtil;

public final class RunTime {

	public static String selectedFileName = "";
	private static final String CONFIG_XML = "mead.xml";
	public static boolean synchronizing = false;
	public static Config CONFIG = new Config();
	public static boolean refreshing = false;

	public static void resetAll() {
		FileUtil.deleteFile(CONFIG_XML);
	}

	public static void destroy() {
		try {
			// clear all the items, do not save to XML configuration file
			for (JDomPodCastURL pod : RunTime.CONFIG.feed_au) {
				pod.clearMedia();
			}
			XML2JavaUtil.java2XML(CONFIG_XML, RunTime.CONFIG);
		} catch (Exception e) {
			FileUtil.deleteFile(CONFIG_XML);
		}
		Util.print("DESTROY");
	}

	public static void init() {

		try {
			CONFIG = XML2JavaUtil.XML2Java(CONFIG_XML);
			CONFIG.first_time_use = false;
		}catch(FileNotFoundException fe){
			CONFIG.first_time_use = true;
		}catch (Exception e) {
			e.printStackTrace();
			FileUtil.deleteFile(CONFIG_XML);
			CONFIG = new Config();
			CONFIG.first_time_use = false;
		}

		FileUtil.createDir(CONFIG.disk_main);
		// feed_tv.add(url);
	}

	public static JDomPodCastURL findFeedByName(String name) {
		for (JDomPodCastURL pod : RunTime.CONFIG.feed_au) {
			if (pod.getName().equalsIgnoreCase(name)) {
				return pod;
			}
		}
		// throw new RuntimeException();
		return null;
	}

	public static void refreshOne(final String name) {
		PodCast.main.lb_info.setText("正在更新" + name + ",请稍候...");
		if (RunTime.CONFIG.feed_au.size() == 0) {
			PodCast.main.lb_info.setText("您没有订阅任何feed,请点击Add Feed. 谢谢使用");
		}
		if (refreshing) {
			PodCast.main.lb_info.setText("线程冲突，等待上一个线程刷新");
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (RunTime.CONFIG.feed_au) {
					refreshing = true;
					try {
						Util.print("刷新Name=="+name);
						for (JDomPodCastURL pod : RunTime.CONFIG.feed_au) {
							if (pod.getName().equalsIgnoreCase(name)) {
								PodCast.main.lb_info.setText(pod.getName()
										+ "正在更新" + pod.getURL());
								int mark = pod.reFreshURL();
								if(mark<=0){
									PodCast.main.lb_info.setText("feed无法获取.原因:网络异常,请设置代理!");
									PodCast.main.progress.setDone("feed无法获取.原因:网络异常,请设置代理!");
									assert mark<=0;{
										
									}
									throw new NetProblem();
									
								}
								
								break;
							}
							Util.print("POD.Name=="+pod.getName());
						}
						// PodCast.main.lb_note.setText("feeds完成更新!");
						PodCast.main.lb_info.setText("完成更新!");
					} catch (Exception e) {
						refreshing = false;
					} finally {
						refreshing = false;
					}
					refreshing = false;
				}
			}
		}).start();
		Util.print("runing...");
	}

	public static void refreshAll() {
		PodCast.main.lb_info.setText("正在更新feed,请稍候...");
		if (RunTime.CONFIG.feed_au.size() == 0) {
			PodCast.main.lb_info.setText("您没有订阅任何feed,请点击Add Feed. 谢谢使用");
		}
		if (refreshing) {
			PodCast.main.lb_info.setText("线程冲突，等待上一个线程刷新");
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (RunTime.CONFIG.feed_au) {
					refreshing = true;
					try {
						Util.print("runing...");
						for (JDomPodCastURL pod : RunTime.CONFIG.feed_au) {
							Util.print("runing...");
							// PodCast.main.lb_note.setText("正在更新"+pod.getName());
							PodCast.main.lb_info.setText(pod.getName() + "正在更新"
									+ pod.getURL());

							pod.reFreshURL();
						}
						// PodCast.main.lb_note.setText("feeds完成更新!");
						PodCast.main.lb_info.setText("完成更新!");
						Util.print("runing...");
					} catch (Exception e) {
						refreshing = false;
					} finally {
						refreshing = false;
					}
					refreshing = false;
				}
			}
		}).start();
		Util.print("runing...");
	}

	public static void main(String[] args) {
		// RunTime run = new RunTime();
		Util.print(RunTime.CONFIG.disk_main);

	}

	public static String font_name="黑体";
	public static String getFont(){
		//if there is no such a Font, what shall I do?
		return font_name;
	}
}
