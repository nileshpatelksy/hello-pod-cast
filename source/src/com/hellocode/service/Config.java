package com.hellocode.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.hellocode.model.JDomPodCastURL;

public final class Config {
	public String disk_main = "C:\\PodCast";
	public ArrayList<String> disk_other = new ArrayList<String>();
	public ArrayList<JDomPodCastURL> feed_au = new ArrayList<JDomPodCastURL>();
	public String proxy_host = "";
	public String proxy_port = "";
	public String proxy_name = "";
	public String proxy_pswd = "";
	public boolean proxy_enabel = false;
	public boolean first_time_use = true;
	private static final int proxy_timeout = 3838;

	public Config() {
		// create file
	}

	public void setProxy() {
		this.proxy_enabel = true;
		Properties prop = System.getProperties();
		// socks代理服务器的地址与端口
		prop.put("socksProxySet", "true");
		prop.setProperty("socksProxyHost", this.proxy_host);
		prop.setProperty("socksProxyPort", this.proxy_port);
		// 设置登陆到代理服务器的用户名和密码
		Authenticator.setDefault(new MyAuthenticator(this.proxy_name,
				this.proxy_pswd));
	}

	public boolean testProxy() {

		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			String encoding = null;
			url = new URL("http://www.google.com");

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/4.0");
			conn.setConnectTimeout(proxy_timeout);
			conn.connect();
			in = conn.getInputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = in.read(buffer, 0, 512)) != -1) {
				encoding += new String(buffer, 0, length);
			}

			if (encoding == null || encoding.equalsIgnoreCase("")) {
				return false;
			} else {
				System.out.println("代理设置成功" + encoding);
				return true;
			}

		} catch (Exception e) {
			System.out.println("代理设置失败");
			this.removeProxy();
			//e.printStackTrace();

		} finally {
			url = null;
			try {
				in.close();
				conn.disconnect();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		this.removeProxy();
		System.out.println("代理设置失败");
		return false;
	}

	public static class MyAuthenticator extends Authenticator {
		private String user = "";
		private String password = "";

		public MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}
	}

	private static final String host="socksProxyHost";
	private static final String port="socksProxyPort";
	private static final String proxy="socksProxySet";
	public void removeProxy() {
		this.proxy_enabel = false;
		Properties prop = System.getProperties();
		// socks代理服务器的地址与端口
		prop.remove(proxy);
		prop.remove(host);
		prop.remove(port);
	}

	@Override
	public String toString() {
		return "Config [disk_main=" + disk_main + ", disk_other=" + disk_other
				+ "]";
	}

	public static void main(String[] args) {
		Config cfg = new Config();
		cfg.proxy_host = "127.0.0.1";
		cfg.proxy_port = "7070";
		cfg.proxy_name = "";
		cfg.proxy_pswd = "";
		cfg.setProxy();
		cfg.testProxy();
	}
}
