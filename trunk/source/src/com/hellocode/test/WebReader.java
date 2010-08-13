package com.hellocode.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hellocode.util.Util;

public class WebReader extends JFrame {
  JTextArea box = new JTextArea("Getting data ...");

  public WebReader() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 300);
    JScrollPane pane = new JScrollPane(box);
    add(pane);
    setVisible(true);
  }

  void getData(String address) {
    try {
		setTitle(address);
		URL page = new URL(address);
		StringBuffer text = new StringBuffer();
		HttpURLConnection conn = (HttpURLConnection) page.openConnection();
		conn.connect();
		String type = conn.getContentType();
		Util.print("URL=:" + conn.getURL().toString());
		for (String s : conn.getHeaderFields().keySet()) {
			Util.print(s + " = " + conn.getHeaderField(s));
		}
		
		InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
		BufferedReader buff = new BufferedReader(in);
		box.setText("Getting data ...");
		String line;
		do {
		  line = buff.readLine();
		  text.append(line + "\n");
		  Util.print("##  "+line);
		} while (line != null);
		box.setText(text.toString());
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }

  public static void main(String[] arguments) {
    WebReader app = new WebReader();
    app.getData("http://www.scientificamerican.com/podcast/podcast.mp3?e_id=43244AFF-E717-98F8-6A8D18B005257EBD");
  }
}