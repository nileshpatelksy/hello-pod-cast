/**
 * @(#)FileEntry.java	1.12 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.tools.javac.v8.util;

import java.util.zip.*;

import java.io.*;

/**
 * A class that abstracts from the differences between Files and ZipEntries.
 */
public abstract class FileEntry {

	public FileEntry() {
		super();
	}

	/**
	 * Same functionality as File.open().
	 */
	public abstract InputStream open() throws IOException;

	/**
	 * Same functionality as File.getName().
	 */
	public abstract String getName();

	/**
	 * Same functionality as File.getPath().
	 */
	public abstract String getPath();

	/**
	 * Same functionality as File.length().
	 */
	public abstract long length();

	/**
	 * Same functionality as File.toString().
	 */
	public String toString() {
		return getName();
	}

	/**
	 * An `undetermined' value for the `lastmod' modification date cache.
	 */
	private static final int UNDET = -2;

	/**
	 * A cache for the modification date returned by lastModified().
	 */
	private long lastmod = UNDET;

	/**
	 * To be defined in subclass; same functionality as File.lastModified().
	 */
	abstract long lastMod();

	/**
	 * Same functionality as File.lastModified()
	 */
	public long lastModified() {
		if (lastmod == UNDET)
			lastmod = lastMod();
		return lastmod;
	}

	/**
	 * A subclass of FileEntry representing regular files.
	 */
	public static class Regular extends FileEntry {

		/**
		 * The file's name.
		 */
		private String name;

		/**
		 * The underlying file.
		 */
		File f;

		public Regular(String name, File f) {
			super();
			this.name = name;
			this.f = f;
		}

		public InputStream open() throws IOException {
			return new FileInputStream(f);
		}

		public String getName() {
			return name;
		}

		public String getPath() {
			return f.getPath();
		}

		public long length() {
			return f.length();
		}

		long lastMod() {
			return f.lastModified();
		}
	}

	/**
	 * A subclass of FileEntry representing zip entries.
	 */
	public static class Zipped extends FileEntry {

		/**
		 * The entry's name.
		 */
		private String name;

		/**
		 * The zipfile containing the entry.
		 */
		ZipFile zdir;

		/**
		 * The underlying zip entry object.
		 */
		ZipEntry entry;

		public Zipped(String name, ZipFile zdir, ZipEntry entry) {
			super();
			this.name = name;
			this.zdir = zdir;
			this.entry = entry;
		}

		public InputStream open() throws IOException {
			return zdir.getInputStream(entry);
		}

		public String getName() {
			return name;
		}

		public String getPath() {
			return zdir.getName() + "(" + entry.toString() + ")";
		}

		public long length() {
			return entry.getSize();
		}

		long lastMod() {
			return entry.getTime();
		}
	}
}
