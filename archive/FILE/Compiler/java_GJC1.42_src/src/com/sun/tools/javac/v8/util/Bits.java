/**
 * @(#)Bits.java	1.14 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.tools.javac.v8.util;

/**
 * A class for extensible, mutable bit sets.
 */
public class Bits {
	private static final int wordlen = 32;
	private static final int wordshift = 5;
	private static final int wordmask = wordlen - 1;
	private int[] bits;

	/**
	 * Construct an initially empty set.
	 */
	public Bits() {
		this(new int[1]);
	}

	/**
	 * Construct a set consisting initially of given bit vector.
	 */
	public Bits(int[] bits) {
		super();
		this.bits = bits;
	}

	/**
	 * Construct a set consisting initially of given range.
	 */
	public Bits(int start, int limit) {
		this();
		inclRange(start, limit);
	}

	private void sizeTo(int len) {
		if (bits.length < len) {
			int[] newbits = new int[len];
			System.arraycopy(bits, 0, newbits, 0, bits.length);
			bits = newbits;
		}
	}

	/**
	 * This set = {}.
	 */
	public void clear() {
		for (int i = 0; i < bits.length; i++)
			bits[i] = 0;
	}

	/**
	 * Return a copy of this set.
	 */
	public Bits dup() {
		int[] newbits = new int[bits.length];
		System.arraycopy(bits, 0, newbits, 0, bits.length);
		return new Bits(newbits);
	}

	/**
	 * Include x in this set.
	 */
	public void incl(int x) {
		assert x >= 0;
		sizeTo((x >>> wordshift) + 1);
		bits[x >>> wordshift] = bits[x >>> wordshift] | (1 << (x & wordmask));
	}

	/**
	 * Include [start..limit) in this set.
	 */
	public void inclRange(int start, int limit) {
		sizeTo((limit >>> wordshift) + 1);
		for (int x = start; x < limit; x++)
			bits[x >>> wordshift] = bits[x >>> wordshift]
					| (1 << (x & wordmask));
	}

	/**
	 * Exclude x from this set.
	 */
	public void excl(int x) {
		assert x >= 0;
		sizeTo((x >>> wordshift) + 1);
		bits[x >>> wordshift] = bits[x >>> wordshift] & ~(1 << (x & wordmask));
	}

	/**
	 * Is x an element of this set?
	 */
	public boolean isMember(int x) {
		return 0 <= x && x < (bits.length << wordshift)
				&& (bits[x >>> wordshift] & (1 << (x & wordmask))) != 0;
	}

	/**
	 * this set = this set & xs.
	 */
	public Bits andSet(Bits xs) {
		sizeTo(xs.bits.length);
		for (int i = 0; i < xs.bits.length; i++)
			bits[i] = bits[i] & xs.bits[i];
		return this;
	}

	/**
	 * this set = this set | xs.
	 */
	public Bits orSet(Bits xs) {
		sizeTo(xs.bits.length);
		for (int i = 0; i < xs.bits.length; i++)
			bits[i] = bits[i] | xs.bits[i];
		return this;
	}

	/**
	 * this set = this set \ xs.
	 */
	public Bits diffSet(Bits xs) {
		for (int i = 0; i < bits.length; i++) {
			if (i < xs.bits.length) {
				bits[i] = bits[i] & ~xs.bits[i];
			}
		}
		return this;
	}

	/**
	 * this set = this set ^ xs.
	 */
	public Bits xorSet(Bits xs) {
		sizeTo(xs.bits.length);
		for (int i = 0; i < xs.bits.length; i++)
			bits[i] = bits[i] ^ xs.bits[i];
		return this;
	}

	/**
	 * Count trailing zero bits in an int. Algorithm from "Hacker's Delight" by
	 * Henry S. Warren Jr. (figure 5-13)
	 */
	private static int trailingZeroBits(int x) {
		assert wordlen == 32;
		if (x == 0)
			return 32;
		int n = 1;
		if ((x & 65535) == 0) {
			n += 16;
			x >>>= 16;
		}
		if ((x & 255) == 0) {
			n += 8;
			x >>>= 8;
		}
		if ((x & 15) == 0) {
			n += 4;
			x >>>= 4;
		}
		if ((x & 3) == 0) {
			n += 2;
			x >>>= 2;
		}
		return n - (x & 1);
	}

	/**
	 * Return the index of the least bit position >= x that is set. If none are
	 * set, returns -1. This provides a nice way to iterate over the members of
	 * a bit set:
	 * 
	 * <pre>
	 *  for (int i = bits.nextBit(0); i>=0; i = bits.nextBit(i+1)) ...
	 * </pre>
	 */
	public int nextBit(int x) {
		int windex = x >>> wordshift;
		if (windex >= bits.length)
			return -1;
		int word = bits[windex] & ~((1 << (x & wordmask)) - 1);
		while (true) {
			if (word != 0)
				return (windex << wordshift) + trailingZeroBits(word);
			windex++;
			if (windex >= bits.length)
				return -1;
			word = bits[windex];
		}
	}

	/**
	 * a string representation of this set.
	 */
	public String toString() {
		char[] digits = new char[bits.length * wordlen];
		for (int i = 0; i < bits.length * wordlen; i++)
			digits[i] = isMember(i) ? '1' : '0';
		return new String(digits);
	}

	/**
	 * Test Bits.nextBit(int).
	 */
	public static void main(String[] args) {
		java.util.Random r = new java.util.Random();
		Bits bits = new Bits();
		int dupCount = 0;
		for (int i = 0; i < 125; i++) {
			int k;
			do {
				k = r.nextInt(250);
			} while (bits.isMember(k));
			System.out.println("adding " + k);
			bits.incl(k);
		}
		int count = 0;
		for (int i = bits.nextBit(0); i >= 0; i = bits.nextBit(i + 1)) {
			System.out.println("found " + i);
			count++;
		}
		if (count != 125)
			throw new Error();
	}
}
