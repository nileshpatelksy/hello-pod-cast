/**
 * @(#)Retro.java	1.19 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.tools.javac.v8;

import com.sun.tools.javac.v8.code.Flags;
import com.sun.tools.javac.v8.code.Kinds;
import com.sun.tools.javac.v8.code.TypeTags;

/**
 * A class for retrofitting plain Java class files with generic signatures.
 */
public class Retro implements TypeTags, Kinds, Flags {

	public Retro() {
		super();
	}
}
