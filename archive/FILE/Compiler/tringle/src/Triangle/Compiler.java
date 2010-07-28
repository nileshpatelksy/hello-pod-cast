/*
 * @(#)Compiler.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle;

import Triangle.AbstractSyntaxTrees.Program;
import Triangle.CodeGenerator.Encoder;
import Triangle.ContextualAnalyzer.Checker;
import Triangle.SyntacticAnalyzer.Parser;
import Triangle.SyntacticAnalyzer.Scanner;
import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.TreeDrawer.Drawer;

/**
 * The main driver class for the Triangle compiler.
 * 
 * @version 2.1 7 Oct 2003
 * @author Deryck F. Brown
 */
public class Compiler {

	/** The filename for the object program, normally obj.tam. */
	static String objectName = "obj.tam";

	private static Scanner scanner;
	private static Parser parser;
	private static Checker checker;
	private static Encoder encoder;
	private static ErrorReporter reporter;
	private static Drawer drawer;

	/** The AST representing the source program. */
	private static Program theAST;

	/**
	 * Compile the source program to TAM machine code.
	 * 
	 * @param sourceName
	 *            the name of the file containing the source program.
	 * @param objectName
	 *            the name of the file containing the object program.
	 * @param showingAST
	 *            true iff the AST is to be displayed after contextual analysis
	 *            (not currently implemented).
	 * @param showingTable
	 *            true iff the object description details are to be displayed
	 *            during code generation (not currently implemented).
	 * @return true iff the source program is free of compile-time errors,
	 *         otherwise false.
	 */
	static boolean compileProgram(String sourceName, String objectName,
			boolean showingAST, boolean showingTable) {

		System.out.println("********** "
				+ "Triangle Compiler (Java Version 2.1)" + " **********");

		System.out.println("Syntactic Analysis ...");
		SourceFile source = new SourceFile(sourceName);

		if (source == null) {
			System.out.println("Can't access source file " + sourceName);
			System.exit(1);
		}

		reporter = new ErrorReporter();
		drawer = new Drawer();
		
		//扫描器,输入source
		scanner = new Scanner(source);
		//分析器
		parser = new Parser(scanner, reporter);
		//检查器
		checker = new Checker(reporter);
		//编码器
		encoder = new Encoder(reporter);
		

		// scanner.enableDebugging();先分析顶级的Program()
		theAST = parser.parseProgram(); // 1st pass
		if (reporter.numErrors == 0) {
			// if (showingAST) {
			// drawer.draw(theAST);
			// }
			System.out.println("Contextual Analysis ...");
			//上下文检查AST
			checker.check(theAST); // 2nd pass
			if (showingAST) {
				//画出AST抽象树
				drawer.draw(theAST);
			}
			if (reporter.numErrors == 0) {
				System.out.println("Code Generation ...");
				//编码,生成代码
				encoder.encodeRun(theAST, showingTable); // 3rd pass
			}
		}

		boolean successful = (reporter.numErrors == 0);
		if (successful) {
			//写二进制文件到编译成功的文件里面
			encoder.saveObjectProgram(objectName);
			System.out.println("Compilation was successful.");
		} else {
			System.out.println("Compilation was unsuccessful.");
		}
		return successful;
	}

	/**
	 * Triangle compiler main program.
	 * 
	 * @param args
	 *            the only command-line argument to the program specifies the
	 *            source filename.
	 */
	public static void main(String[] args) {
		boolean compiledOK;

		if (args.length != 1) {
			System.out.println("Usage: tc filename");
			System.exit(1);
		}

		String sourceName = args[0];
		compiledOK = compileProgram(sourceName, objectName, true, true);
	}
}
