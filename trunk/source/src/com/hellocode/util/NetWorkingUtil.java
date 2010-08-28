package com.hellocode.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.hellocode.service.RunTime;

public class NetWorkingUtil {

	public static void download(String http_url, String abs_file_name) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		URLConnection urlc = null;
		File file = null;
		FileOutputStream fos = null;
		String abs_name =null;
		try {
			URL url = new URL(http_url);
			urlc = (URLConnection) url.openConnection();
			String type = urlc.getContentType();
			Util.print("URL=:" + urlc.getURL());
			Util.print("type=:" + type);
			for (String s : urlc.getHeaderFields().keySet()) {
				Util.print(s + " = " + urlc.getHeaderField(s));
			}
			String name = Util.getFileName(urlc.getURL().toString());// copy
			abs_name = abs_file_name + File.separator + name;

			Util.print("$$$$$$:   " + abs_name);
			if (new File(abs_name).exists()) {
				// skip if had downloaded.
				return;
			}

			file = new File(abs_name);
			try {
				file.createNewFile();
			} catch (Exception e) {
//				int last = abs_file_name.indexOf(File.separator,
//						RunTime.CONFIG.disk_main.length() + 2);
//				String fileName = abs_file_name.substring(last + 1);
//				String pathName = abs_file_name.substring(0, last);
//				Util.print("fileName=" + fileName);
//				Util.print("pathName=" + pathName);
				abs_name = abs_file_name + File.separator
						+ Util.getRandomFileName();
				if (!type.startsWith(Media.AUDIO)) {
					abs_file_name += Media.FILE_MP4;
				} else {
					abs_file_name += Media.FILE_MP3;
				}
				int i = abs_file_name.lastIndexOf(Media.FILE_MP3);
				if (i > 0) {
					name = name.substring(0, i) + Media.FILE_MP3;
				}else{
					name = name + Media.FILE_MP3;					
				}
				file = new File(abs_name);
				Util.print("reConstruct file" + abs_name);
			}
			fos = new FileOutputStream(file);
			bis = new BufferedInputStream(urlc.getInputStream());
			bos = new BufferedOutputStream(fos);

			int i;
			while ((i = bis.read()) != -1) {
				bos.write(i);
			}
			
			
		} catch (Exception e) {
			// check the file size			
			if (file.exists() && file.length() == 0) {
				file.delete();
			}
			e.printStackTrace();
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}		
		}
		
		
	}

	/**
	 * public static void getHttpFile(String http_url, String abs_file_name) {
	 * String sURL = http_url; int nStartPos = 0; int nRead = 0;
	 * 
	 * URL url = null; HttpURLConnection httpConnection = null; RandomAccessFile
	 * oSavedFile = null; try { url = new URL(sURL); // 打开连接 httpConnection =
	 * (HttpURLConnection) url.openConnection(); // 获得文件长度 long nEndPos =
	 * getFileSize(sURL); oSavedFile = new RandomAccessFile(abs_file_name,
	 * "rw"); httpConnection .setRequestProperty("User-Agent",
	 * "Internet Explorer"); String sProperty = "bytes=" + nStartPos + "-"; //
	 * 告诉服务器book.rar这个文件从nStartPos字节开始传
	 * httpConnection.setRequestProperty("RANGE", sProperty);
	 * 
	 * InputStream input = httpConnection.getInputStream(); byte[] b = new
	 * byte[1024]; // 读取网络文件,写入指定的文件中 while ((nRead = input.read(b, 0, 1024)) >
	 * 0 && nStartPos < nEndPos) { oSavedFile.write(b, 0, nRead); nStartPos +=
	 * nRead; } httpConnection.disconnect(); input.close(); oSavedFile.close();
	 * } catch (Exception e) { e.printStackTrace(); } finally { try { url =
	 * null; httpConnection.disconnect(); oSavedFile.close(); httpConnection =
	 * null; oSavedFile = null; } catch (Exception e) { e.printStackTrace(); } }
	 * // here shall use finally{}...close something...
	 * 
	 * Util.print("save file ok =" + abs_file_name);
	 * 
	 * }
	 **/

	// 获得文件长度
	public static long getFileSize(String sURL) {
		int nFileLength = -1;
		try {
			URL url = new URL(sURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "Internet Explorer");

			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				System.err.println("Error Code : " + responseCode);
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(nFileLength);
		return nFileLength;
	}

}
