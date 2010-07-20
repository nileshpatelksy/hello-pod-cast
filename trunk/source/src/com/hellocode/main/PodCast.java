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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
		
		System.out.println("Main...");
		
	}

}
