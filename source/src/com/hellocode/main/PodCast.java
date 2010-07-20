/**
 * 
 */
package com.hellocode.main;

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
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				main = new MainFrame();
				main.setVisible(true);
			}
		});
		
		System.out.println("Main...");
		
	}

}
