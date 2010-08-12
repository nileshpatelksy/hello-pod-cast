/**
 * 
 */
package com.hellocode.main;

import com.hellocode.service.RunTime;
import com.hellocode.ui.MainFrame;
import com.hellocode.util.Util;

/**
 * @author Administrator
 * 
 */
public final class PodCast {

	public static MainFrame main;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			RunTime.init();
		} catch (Exception e) {
			//it must be some severe ERROR
			RunTime.resetAll();
			System.exit(-1);
		}

		main = new MainFrame();
		main.setVisible(true);
		main.setTitle("@Hello Podcast");
		main.setDefaultLookAndFeelDecorated(true);

		Util.print("Main...");
		try {
			Thread.sleep(500);
			RunTime.refreshAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(RunTime.CONFIG.first_time_use){
			//Welcome new user.
			main.lb_info.setText("ª∂”≠ π”√Hello PodCast");
		}
	}

}
