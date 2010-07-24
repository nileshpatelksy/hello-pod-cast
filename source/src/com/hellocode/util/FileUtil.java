package com.hellocode.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	public static void createDir(String full_path) {
		// 处理异常，
		// 如果存在文件夹dir，
		File dFile = new File(full_path);
		if (!dFile.exists()) {
			System.out.println("创建空盘" + full_path);
			dFile.mkdir();
		}
		if (dFile.exists() && dFile.isFile()) {
			dFile.delete();
			dFile.mkdir();
		}

		return;
		// else 创建文件夹
	}

	/**
	 * 缓存的大小
	 */
	private static int BUFFER_SIZE = 1024;

	public static boolean copy(File src, File dst) {
		System.out.println("copy:src=" + src.getAbsolutePath());
		System.out.println("copy:dst=" + dst.getAbsolutePath());
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean copyFolder(String src, String dst) {
		System.out.println("copyFolder=" + src);
		System.out.println("copyFolder=" + dst);
		File sFile = new File(src);
		File dFile = new File(dst);
		if (!sFile.exists()) {
			System.out.println("no folder");
			return false;
		}
		if (sFile.isDirectory()) {
			if (!dFile.exists()) {
				dFile.mkdir();
			}
			File[] list = sFile.listFiles();
			String newFileString = "";
			File newFile = null;
			for (File f : list) {
				newFileString = dFile.getAbsolutePath() + File.separator
						+ f.getName();
				newFile = new File(newFileString);
				// not over write the old file, check is it exist.
				if (f.exists() && f.isFile() && !newFile.exists()) {
					FileUtil.copy(f, newFile);
				} else {
					FileUtil.copyFolder(f.getAbsolutePath(), dFile
							.getAbsolutePath()
							+ File.separator + f.getName());
				}
			}

		} else {
			// not over write the old file, check is it exist.
			if (dFile.isFile() && !dFile.exists()) {
				FileUtil.copy(sFile, dFile);
			}
		}
		return true;
	}

	public static boolean deleteFile(String src) {
		System.out.println("deleteFile=" + src);
		File delFile = new File(src);
		try {
			if (delFile.exists() && delFile.isFile()) {
				return delFile.delete();
			} else {
				return delFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteFile(File src) {
		try {
			System.out.println("deleteFile=" + src);
			if (src.exists() && src.isFile()) {
				return src.delete();
			} else {
				return src.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteFolder(File src) {
		try {
			System.out.println("deleteFolder=" + src);
			if (!src.exists()) {
				// no file
			}
			if (src.isDirectory()) {
				File[] files = src.listFiles();
				for (File file : files) {
					if (file.exists() && file.isFile()) {
						file.delete();
					} else {
						deleteFolder(file);
						// del now dir
						file.delete();
					}
					// del top dir
					src.delete();
				}

			} else if (src.isFile()) {
				src.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteFolder(String src) {
		File file = new File(src);
		if (!file.exists()) {
		}
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.exists() && f.isFile()) {
						f.delete();
					} else {
						deleteFolder(f);
						// del dir
						f.delete();
					}
				}
				// del top dir
				file.delete();
			} else if (file.isFile()) {
				file.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public static int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}

	public static void setBUFFER_SIZE(int buffer_size) {
		BUFFER_SIZE = buffer_size;
	}

	public static void main(String[] args) {
		// FileUtil.deleteFolder("H:\\test");
		FileUtil.copyFolder("H:\\test", "H:\\mead");
	}
}
