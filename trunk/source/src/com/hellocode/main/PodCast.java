/**
 * 
 */
package com.hellocode.main;

import com.hellocode.service.RunTime;
import com.hellocode.ui.MainFrame;

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

		System.out.println("Main...");
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
