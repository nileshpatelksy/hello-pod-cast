package com.hellocode.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

//这个是任务Bean

class Task {

	public String getDownURL() {
		return downURL;
	}

	public void setDownURL(String downURL) {
		this.downURL = downURL;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}

	public int getSectionCount() {
		return sectionCount;
	}

	public void setSectionCount(int sectionCount) {
		this.sectionCount = sectionCount;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public long[] getSectionsOffset() {
		return sectionsOffset;
	}

	public void setSectionsOffset(long[] sectionsOffset) {
		this.sectionsOffset = sectionsOffset;
	}

	public static int getHeadSize() {
		return HEAD_SIZE;
	}

	private String downURL;
	private String saveFile;
	private int bufferSize = 64 * 1024;
	private int workerCount;
	private int sectionCount;
	private long contentLength;
	private long[] sectionsOffset;
	public static final int HEAD_SIZE = 4096;

	// 读下载描述文件内容

	public synchronized void read(RandomAccessFile file) throws IOException {

		byte[] temp = new byte[HEAD_SIZE];
		file.seek(0);
		int readed = file.read(temp);
		if (readed != temp.length) {
			throw new RuntimeException();
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(temp);
		DataInputStream dis = new DataInputStream(bais);
		downURL = dis.readUTF();
		saveFile = dis.readUTF();
		sectionCount = dis.readInt();
		contentLength = dis.readLong();
		sectionsOffset = new long[sectionCount];
		for (int i = 0; i < sectionCount; i++) {
			sectionsOffset[i] = file.readLong();

		}

	}

	// 创建下载描述文件内容

	public synchronized void create(RandomAccessFile file) throws IOException {
		if (sectionCount != sectionsOffset.length) {
			throw new RuntimeException();
		}
		long len = HEAD_SIZE + 8 * sectionCount;
		file.setLength(len);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeUTF(downURL);
		dos.writeUTF(saveFile);
		dos.writeInt(sectionCount);
		dos.writeLong(contentLength);
		byte[] src = baos.toByteArray();
		byte[] temp = new byte[HEAD_SIZE];
		System.arraycopy(src, 0, temp, 0, src.length);
		file.seek(0);
		file.write(temp);
		writeOffset(file);

	}

	// 更新下载的过程

	public synchronized void writeOffset(RandomAccessFile file)
			throws IOException {
		if (sectionCount != sectionsOffset.length) {
			throw new RuntimeException();
		}

		file.seek(HEAD_SIZE);
		for (int i = 0; i < sectionsOffset.length; i++) {
			file.writeLong(sectionsOffset[i]);
		}

	}

}

// 这个是下载主程序

class TaskAssign {

	public void work(Task task) throws IOException {

		File file = new File(task.getSaveFile());
		if (file.exists()) {
			return;
		}

		// 这个是记录是否下载成功。我这里也没有增加失败回复、重试之类的工作。
		final AtomicBoolean success = new AtomicBoolean(true);
		// 任务描述文件
		File taskFile = new File(task.getSaveFile() + ".r_task");

		// 真正下载的文件
		File saveFile = new File(task.getSaveFile() + ".r_save");
		boolean taskFileExist = taskFile.exists();
		RandomAccessFile taskRandomFile = null;
		RandomAccessFile downRandomFile = null;

		try {
			taskRandomFile = new RandomAccessFile(taskFile, "rw");
			downRandomFile = new RandomAccessFile(saveFile, "rw");
			long rtnLen = getContentLength(task.getDownURL());
			if (!taskFileExist) {
				// 如果文件不存在，就初始化任务文件和下载文件
				task.setContentLength(rtnLen);
				initTaskFile(taskRandomFile, task);
				downRandomFile.setLength(rtnLen);
			} else {
				// 任务文件存在就读取任务文件
				task.read(taskRandomFile);
				if (task.getContentLength() != rtnLen) {
					throw new RuntimeException();
				}
			}

			int secCount = task.getSectionCount();

			// 分配线程去下载，这里用到线程池

			ExecutorService es = Executors.newFixedThreadPool(task
					.getWorkerCount());

			for (int i = 0; i < secCount; i++) {
				final int j = i;
				final Task t = task;
				final RandomAccessFile f1 = taskRandomFile;
				final RandomAccessFile f2 = downRandomFile;
				es.execute(new Runnable() {
					public void run() {
						try {
							down(f1, f2, t, j);

						} catch (IOException e) {
							success.set(false);
							e.printStackTrace(System.out);
						}
					}
				});
			}
			es.shutdown();
			try {
				es.awaitTermination(24 * 3600, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			taskRandomFile.close();
			taskRandomFile = null;
			downRandomFile.close();
			downRandomFile = null;
			// 如果下载成功，去掉任务描述文件、帮下载文件改名
			if (success.get()) {
				taskFile.delete();
				saveFile.renameTo(file);
			}
		} finally {
			if (taskRandomFile != null) {
				taskRandomFile.close();
				taskRandomFile = null;
			}
			if (downRandomFile != null) {
				downRandomFile.close();
				downRandomFile = null;
			}
		}
	}

	public void down(RandomAccessFile taskRandomFile,
			RandomAccessFile downRandomFile, Task task, int sectionNo)
			throws IOException {
		// 这里我用HttpURLConnection下载，你也可以用HttpClient或者自己实现一个Http协议（不过貌似没有必要）
		URL u = new URL(task.getDownURL());
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		long start = task.getSectionsOffset()[sectionNo];
		long end = -1;
		// 这里要注意一下，这里是计算当前块的长度
		if (sectionNo < task.getSectionCount() - 1) {
			long per = task.getContentLength() / task.getSectionCount();
			end = per * (sectionNo + 1);
		} else {
			end = task.getContentLength();
		}
		if (start >= end) {
			System.out.println("Section has finished before. " + sectionNo);
			return;
		}
		String range = "bytes=" + start + "-" + (end - 1);
		conn.setRequestProperty("Range", range);
		conn.setRequestProperty("User-Agent", "Ray-Downer");
		try {
			conn.connect();
			if (conn.getResponseCode() != 206) {
				throw new RuntimeException();
			}
			if (conn.getContentLength() != (end - start)) {
				throw new RuntimeException();
			}
			InputStream is = conn.getInputStream();
			byte[] temp = new byte[task.getBufferSize()];
			BufferedInputStream bis = new BufferedInputStream(is, temp.length);
			int readed = 0;
			while ((readed = bis.read(temp)) > 0) {
				long offset = task.getSectionsOffset()[sectionNo];
				synchronized (task) {
					// 下载之后顺便更新描述文件，你可能会发现这里效率比较低，在一个线程同步里进行两次文件操作。你可以自己实现一个缓冲写。
					downRandomFile.seek(offset);
					downRandomFile.write(temp, 0, readed);
					offset += readed;
					task.getSectionsOffset()[sectionNo] = offset;
					task.writeOffset(taskRandomFile);
				}
			}
		} finally {
			conn.disconnect();
		}
		System.out.println("Section finished. " + sectionNo);
	}

	public void initTaskFile(RandomAccessFile taskRandomFile, Task task)
			throws IOException {
		int secCount = task.getSectionCount();
		long per = task.getContentLength() / secCount;
		long[] sectionsOffset = new long[secCount];
		for (int i = 0; i < secCount; i++) {
			sectionsOffset[i] = per * i;
		}
		task.setSectionsOffset(sectionsOffset);
		task.create(taskRandomFile);
	}

	public long getContentLength(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		try {
			return conn.getContentLength();
		} finally {
			conn.disconnect();
		}
	}
}

// 稍微测试一下。

public class Main {
	public static void main(String[] args) throws IOException {
		test3();
		System.out.println("\n\n===============\nFinished.");
	}

	public static void test1() throws IOException {
		Task task = new Task();
		task
				.setDownURL("http://61.152.235.21/qqfile/qq/2007iistable/QQ2007IIKB1.exe");
		task.setSaveFile("H:/Test2.exe");
		task.setSectionCount(200);
		task.setWorkerCount(100);
		task.setBufferSize(256 * 1024);
		TaskAssign ta = new TaskAssign();
		ta.work(task);

	}

	public static void test2() throws IOException {
		Task task = new Task();
		task
				.setDownURL("http://student1.scut.edu.cn:8880/manage/news/data/1208421861893.xls");
		task.setSaveFile("H:/Test3.xls");
		task.setSectionCount(5);
		task.setWorkerCount(1);
		task.setBufferSize(128 * 1024);
		TaskAssign ta = new TaskAssign();
		ta.work(task);

	}

	public static void test3() throws IOException {

		Task task = new Task();
		task.setDownURL("http://go.microsoft.com/fwlink/?linkid=57034");
		task.setSaveFile("H:/vc2005express.iso");
		task.setSectionCount(500);
		task.setWorkerCount(200);
		task.setBufferSize(128 * 1024);
		TaskAssign ta = new TaskAssign();
		ta.work(task);

	}

	public static void test4() throws IOException {

		Task task = new Task();
		task.setDownURL("http://down.sandai.net/Thunder5.7.9.472.exe");
		task.setSaveFile("H:/Thunder.exe");
		task.setSectionCount(30);
		task.setWorkerCount(30);
		task.setBufferSize(128 * 1024);
		TaskAssign ta = new TaskAssign();
		ta.work(task);

	}

}
