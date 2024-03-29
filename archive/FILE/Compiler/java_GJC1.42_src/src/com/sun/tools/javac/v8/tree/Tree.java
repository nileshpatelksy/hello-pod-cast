/**
 * @(#)Tree.java	1.30 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.tools.javac.v8.tree;

import java.io.StringWriter;

import java.io.PrintWriter;

import com.sun.tools.javac.v8.util.*;

import com.sun.tools.javac.v8.code.*;

import com.sun.tools.javac.v8.code.Symbol.*;

/**
 * Root class for abstract syntax tree nodes. It provides definitions for
 * specific tree nodes as subclasses nested inside There are 40 such subclasses.
 * 
 * Each subclass is highly standardized. It generally contains only tree fields
 * for the syntactic subcomponents of the node. Some classes that represent
 * identifier uses or definitions also define a Symbol field that denotes the
 * represented identifier. Classes for non-local jumps also carry the jump
 * target as a field. The root class Tree itself defines fields for the tree's
 * type and position. No other fields are kept in a tree node; instead
 * parameters are passed to methods accessing the node.
 * 
 * The only method defined in subclasses is `visit' which applies a given
 * visitor to the tree. The actual tree processing is done by visitor classes in
 * other packages. The abstract class Visitor, as well as an Factory interface
 * for trees, are defined as inner classes in Tree.
 * 
 * @see TreeMaker
 * @see TreeInfo
 * @see TreeTranslator
 * @see Pretty
 */
public abstract class Tree {

	/**
	 * Toplevel nodes, of type TopLevel, representing entire source files.
	 */
	public static final int TOPLEVEL = 1;

	/**
	 * Import clauses, of type Import.
	 */
	public static final int IMPORT = TOPLEVEL + 1;

	/**
	 * Class definitions, of type ClassDef.
	 */
	public static final int CLASSDEF = IMPORT + 1;

	/**
	 * Method definitions, of type MethodDef.
	 */
	public static final int METHODDEF = CLASSDEF + 1;

	/**
	 * Variable definitions, of type VarDef.
	 */
	public static final int VARDEF = METHODDEF + 1;

	/**
	 * The no-op statement ";", of type Skip
	 */
	public static final int SKIP = VARDEF + 1;

	/**
	 * Blocks, of type Block.
	 */
	public static final int BLOCK = SKIP + 1;

	/**
	 * Do-while loops, of type DoLoop.
	 */
	public static final int DOLOOP = BLOCK + 1;

	/**
	 * While-loops, of type WhileLoop.
	 */
	public static final int WHILELOOP = DOLOOP + 1;

	/**
	 * For-loops, of type ForLoop.
	 */
	public static final int FORLOOP = WHILELOOP + 1;

	/**
	 * Labelled statements, of type Labelled.
	 */
	public static final int LABELLED = FORLOOP + 1;

	/**
	 * Switch statements, of type Switch.
	 */
	public static final int SWITCH = LABELLED + 1;

	/**
	 * Case parts in switch statements, of type Case.
	 */
	public static final int CASE = SWITCH + 1;

	/**
	 * Synchronized statements, of type Synchonized.
	 */
	public static final int SYNCHRONIZED = CASE + 1;

	/**
	 * Try statements, of type Try.
	 */
	public static final int TRY = SYNCHRONIZED + 1;

	/**
	 * Catch clauses in try statements, of type Catch.
	 */
	public static final int CATCH = TRY + 1;

	/**
	 * Conditional expressions, of type Conditional.
	 */
	public static final int CONDEXPR = CATCH + 1;

	/**
	 * Conditional statements, of type If.
	 */
	public static final int IF = CONDEXPR + 1;

	/**
	 * Expression statements, of type Exec.
	 */
	public static final int EXEC = IF + 1;

	/**
	 * Break statements, of type Break.
	 */
	public static final int BREAK = EXEC + 1;

	/**
	 * Continue statements, of type Continue.
	 */
	public static final int CONTINUE = BREAK + 1;

	/**
	 * Return statements, of type Return.
	 */
	public static final int RETURN = CONTINUE + 1;

	/**
	 * Throw statements, of type Throw.
	 */
	public static final int THROW = RETURN + 1;

	/**
	 * Assert statements, of type Assert.
	 */
	public static final int ASSERT = THROW + 1;

	/**
	 * Method invocation expressions, of type Apply.
	 */
	public static final int APPLY = ASSERT + 1;

	/**
	 * Class instance creation expressions, of type NewClass.
	 */
	public static final int NEWCLASS = APPLY + 1;

	/**
	 * Array creation expressions, of type NewArray.
	 */
	public static final int NEWARRAY = NEWCLASS + 1;

	/**
	 * Parenthesized subexpressions, of type Parens.
	 */
	public static final int PARENS = NEWARRAY + 1;

	/**
	 * Assignment expressions, of type Assign.
	 */
	public static final int ASSIGN = PARENS + 1;

	/**
	 * Type cast expressions, of type TypeCast.
	 */
	public static final int TYPECAST = ASSIGN + 1;

	/**
	 * Type test expressions, of type TypeTest.
	 */
	public static final int TYPETEST = TYPECAST + 1;

	/**
	 * Indexed array expressions, of type Indexed.
	 */
	public static final int INDEXED = TYPETEST + 1;

	/**
	 * Selections, of type Select.
	 */
	public static final int SELECT = INDEXED + 1;

	/**
	 * Simple identifiers, of type Ident.
	 */
	public static final int IDENT = SELECT + 1;

	/**
	 * Literals, of type Literal.
	 */
	public static final int LITERAL = IDENT + 1;

	/**
	 * Basic type identifiers, of type TypeIdent.
	 */
	public static final int TYPEIDENT = LITERAL + 1;

	/**
	 * Array types, of type TypeArray.
	 */
	public static final int TYPEARRAY = TYPEIDENT + 1;

	/**
	 * Parameterized types, of type TypeApply.
	 */
	public static final int TYPEAPPLY = TYPEARRAY + 1;

	/**
	 * Formal type parameters, of type TypeParameter.
	 */
	public static final int TYPEPARAMETER = TYPEAPPLY + 1;

	/**
	 * Error trees, of type Erroneous.
	 */
	public static final int ERRONEOUS = TYPEPARAMETER + 1;

	/**
	 * Unary operators, of type Unary.
	 */
	public static final int POS = ERRONEOUS + 1;
	public static final int NEG = POS + 1;
	public static final int NOT = NEG + 1;
	public static final int COMPL = NOT + 1;
	public static final int PREINC = COMPL + 1;
	public static final int PREDEC = PREINC + 1;
	public static final int POSTINC = PREDEC + 1;
	public static final int POSTDEC = POSTINC + 1;

	/**
	 * unary operator for null reference checks, only used internally.
	 */
	public static final int NULLCHK = POSTDEC + 1;

	/**
	 * Binary operators, of type Binary.
	 */
	public static final int OR = NULLCHK + 1;
	public static final int AND = OR + 1;
	public static final int BITOR = AND + 1;
	public static final int BITXOR = BITOR + 1;
	public static final int BITAND = BITXOR + 1;
	public static final int EQ = BITAND + 1;
	public static final int NE = EQ + 1;
	public static final int LT = NE + 1;
	public static final int GT = LT + 1;
	public static final int LE = GT + 1;
	public static final int GE = LE + 1;
	public static final int SL = GE + 1;
	public static final int SR = SL + 1;
	public static final int USR = SR + 1;
	public static final int PLUS = USR + 1;
	public static final int MINUS = PLUS + 1;
	public static final int MUL = MINUS + 1;
	public static final int DIV = MUL + 1;
	public static final int MOD = DIV + 1;

	/**
	 * Assignment operators, of type Assignop.
	 */
	public static final int BITOR_ASG = MOD + 1;
	public static final int BITXOR_ASG = BITOR_ASG + 1;
	public static final int BITAND_ASG = BITXOR_ASG + 1;
	public static final int SL_ASG = SL + BITOR_ASG - BITOR;
	public static final int SR_ASG = SL_ASG + 1;
	public static final int USR_ASG = SR_ASG + 1;
	public static final int PLUS_ASG = USR_ASG + 1;
	public static final int MINUS_ASG = PLUS_ASG + 1;
	public static final int MUL_ASG = MINUS_ASG + 1;
	public static final int DIV_ASG = MUL_ASG + 1;
	public static final int MOD_ASG = DIV_ASG + 1;

	/**
	 * The offset between assignment operators and normal operators.
	 */
	public static final int ASGOffset = BITOR_ASG - BITOR;
	public int pos;
	public Type type;
	public int tag;

	/**
	 * Initialize tree with given tag.
	 */
	public Tree(int tag) {
		super();
		this.tag = tag;
	}

	/**
	 * Convert a tree to a pretty-printed string.
	 */
	public String toString() {
		StringWriter s = new StringWriter();
		new Pretty(new PrintWriter(s), false).printExpr(this);
		return s.toString();
	}

	/**
	 * An empty list of trees.
	 */
	public static final List emptyList = new List();

	/**
	 * Set position field and return this tree.
	 */
	public Tree setPos(int pos) {
		this.pos = pos;
		return this;
	}

	/**
	 * Set type field and return this tree.
	 */
	public Tree setType(Type type) {
		this.type = type;
		return this;
	}

	/**
	 * Visit this tree with a given visitor.
	 */
	public void accept(Visitor v) {
		v.visitTree(this);
	}

	/**
	 * Everything in one source file is kept in a TopLevel structure.
	 * 
	 * @param pid
	 *            The tree representing the package clause.
	 * @param sourcefile
	 *            The source file name.
	 * @param defs
	 *            All definitions in this file.
	 * @param packge
	 *            The package it belongs to.
	 * @param namedImportScope
	 *            A scope for all named imports.
	 * @param starImportScope
	 *            A scope for all import-on-demands.
	 * @param docComments
	 *            A hashtable that stores all documentation comments indexed by
	 *            the tree nodes they refer to. defined only if option -s is
	 *            set.
	 * @param endPositions
	 *            A hashtable that stores ending positions of source ranges
	 *            indexed by the tree nodes they belong to. Defined only if
	 *            option -Xjcov is set.
	 */
	public static class TopLevel extends Tree {
		public Tree pid;
		public List defs;
		public Name sourcefile;
		public PackageSymbol packge;
		public Scope namedImportScope;
		public Scope starImportScope;
		public Hashtable docComments = null;
		public Hashtable endPositions = null;

		public TopLevel(Tree pid, List defs, Name sourcefile,
				PackageSymbol packge, Scope namedImportScope,
				Scope starImportScope) {
			super(TOPLEVEL);
			this.pid = pid;
			this.defs = defs;
			this.sourcefile = sourcefile;
			this.packge = packge;
			this.namedImportScope = namedImportScope;
			this.starImportScope = starImportScope;
		}

		public void accept(Visitor v) {
			v.visitTopLevel(this);
		}
	}

	/**
	 * An import clause.
	 * 
	 * @param qualid
	 *            The imported class(es).
	 */
	public static class Import extends Tree {
		public Tree qualid;

		public Import(Tree qualid) {
			super(IMPORT);
			this.qualid = qualid;
		}

		public void accept(Visitor v) {
			v.visitImport(this);
		}
	}

	/**
	 * A class definition.
	 * 
	 * @param flags
	 *            class flags
	 * @param name
	 *            the name of the class
	 * @param typarams
	 *            formal class parameters
	 * @param extending
	 *            the classes this class extends
	 * @param implementing
	 *            the interfaces implemented by this class
	 * @param defs
	 *            all variables and methods defined in this class
	 * @param sym
	 *            the symbol
	 */
	public static class ClassDef extends Tree {
		public long flags;
		public Name name;
		public List typarams;
		public Tree extending;
		public List implementing;
		public List defs;
		public ClassSymbol sym;

		public ClassDef(long flags, Name name, List typarams, Tree extending,
				List implementing, List defs, ClassSymbol sym) {
			super(CLASSDEF);
			this.flags = flags;
			this.name = name;
			this.typarams = typarams;
			this.extending = extending;
			this.implementing = implementing;
			this.defs = defs;
			this.sym = sym;
		}

		public void accept(Visitor v) {
			v.visitClassDef(this);
		}
	}

	/**
	 * A method definition.
	 * 
	 * @param flags
	 *            method flags
	 * @param name
	 *            method name
	 * @param restype
	 *            type of method return value
	 * @param typarams
	 *            type parameters
	 * @param params
	 *            value parameters
	 * @param thrown
	 *            exceptions thrown by this method
	 * @param stats
	 *            statements in the method
	 * @param sym
	 *            method symbol
	 */
	public static class MethodDef extends Tree {
		public long flags;
		public Name name;
		public Tree restype;
		public List typarams;
		public List params;
		public List thrown;
		public Block body;
		public MethodSymbol sym;

		public MethodDef(long flags, Name name, Tree restype, List typarams,
				List params, List thrown, Block body, MethodSymbol sym) {
			super(METHODDEF);
			this.flags = flags;
			this.name = name;
			this.restype = restype;
			this.typarams = typarams;
			this.params = params;
			this.thrown = thrown;
			this.body = body;
			this.sym = sym;
		}

		public void accept(Visitor v) {
			v.visitMethodDef(this);
		}
	}

	/**
	 * A variable definition.
	 * 
	 * @param flags
	 *            variable flags
	 * @param name
	 *            variable name
	 * @param vartype
	 *            type of the variable
	 * @param init
	 *            variables initial value
	 * @param sym
	 *            symbol
	 */
	public static class VarDef extends Tree {
		public long flags;
		public Name name;
		public Tree vartype;
		public Tree init;
		public VarSymbol sym;

		public VarDef(long flags, Name name, Tree vartype, Tree init,
				VarSymbol sym) {
			super(VARDEF);
			this.flags = flags;
			this.name = name;
			this.vartype = vartype;
			this.init = init;
			this.sym = sym;
		}

		public void accept(Visitor v) {
			v.visitVarDef(this);
		}

		public static final List emptyList = new List();
	}

	/**
	 * A no-op statement ";".
	 */
	public static class Skip extends Tree {

		public Skip() {
			super(SKIP);
		}

		public void accept(Visitor v) {
			v.visitSkip(this);
		}
	}

	/**
	 * A statement block.
	 * 
	 * @param stats
	 *            statements
	 * @param flags
	 *            modifier
	 */
	public static class Block extends Tree {
		public long flags;
		public List stats;
		public int endpos = Position.NOPOS;

		public Block(long flags, List stats) {
			super(BLOCK);
			this.stats = stats;
			this.flags = flags;
		}

		public void accept(Visitor v) {
			v.visitBlock(this);
		}
	}

	/**
	 * A do loop
	 */
	public static class DoLoop extends Tree {
		public Tree body;
		public Tree cond;

		public DoLoop(Tree body, Tree cond) {
			super(DOLOOP);
			this.body = body;
			this.cond = cond;
		}

		public void accept(Visitor v) {
			v.visitDoLoop(this);
		}
	}

	/**
	 * A while loop
	 */
	public static class WhileLoop extends Tree {
		public Tree cond;
		public Tree body;

		public WhileLoop(Tree cond, Tree body) {
			super(WHILELOOP);
			this.cond = cond;
			this.body = body;
		}

		public void accept(Visitor v) {
			v.visitWhileLoop(this);
		}
	}

	/**
	 * A for loop.
	 */
	public static class ForLoop extends Tree {
		public List init;
		public Tree cond;
		public List step;
		public Tree body;

		public ForLoop(List init, Tree cond, List step, Tree body) {
			super(FORLOOP);
			this.init = init;
			this.cond = cond;
			this.step = step;
			this.body = body;
		}

		public void accept(Visitor v) {
			v.visitForLoop(this);
		}
	}

	/**
	 * A labelled expression or statement.
	 */
	public static class Labelled extends Tree {
		public Name label;
		public Tree body;

		public Labelled(Name label, Tree body) {
			super(LABELLED);
			this.label = label;
			this.body = body;
		}

		public void accept(Visitor v) {
			v.visitLabelled(this);
		}
	}

	/**
	 * A "switch ( ) { }" construction.
	 */
	public static class Switch extends Tree {
		public Tree selector;
		public List cases;

		public Switch(Tree selector, List cases) {
			super(SWITCH);
			this.selector = selector;
			this.cases = cases;
		}

		public void accept(Visitor v) {
			v.visitSwitch(this);
		}
	}

	/**
	 * A "case  :" of a switch.
	 */
	public static class Case extends Tree {
		public Tree pat;
		public List stats;

		public Case(Tree pat, List stats) {
			super(CASE);
			this.pat = pat;
			this.stats = stats;
		}

		public void accept(Visitor v) {
			v.visitCase(this);
		}
	}

	/**
	 * A synchronized block.
	 */
	public static class Synchronized extends Tree {
		public Tree lock;
		public Tree body;

		public Synchronized(Tree lock, Tree body) {
			super(SYNCHRONIZED);
			this.lock = lock;
			this.body = body;
		}

		public void accept(Visitor v) {
			v.visitSynchronized(this);
		}
	}

	/**
	 * A "try { } catch ( ) { } finally { }" block.
	 */
	public static class Try extends Tree {
		public Tree body;
		public List catchers;
		public Tree finalizer;

		public Try(Tree body, List catchers, Tree finalizer) {
			super(TRY);
			this.body = body;
			this.catchers = catchers;
			this.finalizer = finalizer;
		}

		public void accept(Visitor v) {
			v.visitTry(this);
		}
	}

	/**
	 * A catch block.
	 */
	public static class Catch extends Tree {
		public VarDef param;
		public Tree body;

		public Catch(VarDef param, Tree body) {
			super(CATCH);
			this.param = param;
			this.body = body;
		}

		public void accept(Visitor v) {
			v.visitCatch(this);
		}

		public static List emptyList = new List();
	}

	/**
	 * A ( ) ? ( ) : ( ) conditional expression
	 */
	public static class Conditional extends Tree {
		public Tree cond;
		public Tree truepart;
		public Tree falsepart;

		public Conditional(Tree cond, Tree truepart, Tree falsepart) {
			super(CONDEXPR);
			this.cond = cond;
			this.truepart = truepart;
			this.falsepart = falsepart;
		}

		public void accept(Visitor v) {
			v.visitConditional(this);
		}
	}

	/**
	 * An "if ( ) { } else { }" block
	 */
	public static class If extends Tree {
		public Tree cond;
		public Tree thenpart;
		public Tree elsepart;

		public If(Tree cond, Tree thenpart, Tree elsepart) {
			super(IF);
			this.cond = cond;
			this.thenpart = thenpart;
			this.elsepart = elsepart;
		}

		public void accept(Visitor v) {
			v.visitIf(this);
		}
	}

	/**
	 * an expression statement
	 * 
	 * @param expr
	 *            expression structure
	 */
	public static class Exec extends Tree {
		public Tree expr;

		public Exec(Tree expr) {
			super(EXEC);
			this.expr = expr;
		}

		public void accept(Visitor v) {
			v.visitExec(this);
		}
	}

	/**
	 * A break from a loop or switch.
	 */
	public static class Break extends Tree {
		public Name label;
		public Tree target;

		public Break(Name label, Tree target) {
			super(BREAK);
			this.label = label;
			this.target = target;
		}

		public void accept(Visitor v) {
			v.visitBreak(this);
		}
	}

	/**
	 * A continue of a loop.
	 */
	public static class Continue extends Tree {
		public Name label;
		public Tree target;

		public Continue(Name label, Tree target) {
			super(CONTINUE);
			this.label = label;
			this.target = target;
		}

		public void accept(Visitor v) {
			v.visitContinue(this);
		}
	}

	/**
	 * A return statement.
	 */
	public static class Return extends Tree {
		public Tree expr;

		public Return(Tree expr) {
			super(RETURN);
			this.expr = expr;
		}

		public void accept(Visitor v) {
			v.visitReturn(this);
		}
	}

	/**
	 * A throw statement.
	 */
	public static class Throw extends Tree {
		public Tree expr;

		public Throw(Tree expr) {
			super(THROW);
			this.expr = expr;
		}

		public void accept(Visitor v) {
			v.visitThrow(this);
		}
	}

	/**
	 * An assert statement.
	 */
	public static class Assert extends Tree {
		public Tree cond;
		public Tree detail;

		public Assert(Tree cond, Tree detail) {
			super(ASSERT);
			this.cond = cond;
			this.detail = detail;
		}

		public void accept(Visitor v) {
			v.visitAssert(this);
		}
	}

	/**
	 * A method invocation
	 */
	public static class Apply extends Tree {
		public Tree meth;
		public List args;

		public Apply(Tree meth, List args) {
			super(APPLY);
			this.meth = meth;
			this.args = args;
		}

		public void accept(Visitor v) {
			v.visitApply(this);
		}
	}

	/**
	 * A new(...) operation.
	 */
	public static class NewClass extends Tree {
		public Tree encl;
		public Tree clazz;
		public List args;
		public ClassDef def;
		public Symbol constructor;

		public NewClass(Tree encl, Tree clazz, List args, ClassDef def,
				Symbol constructor) {
			super(NEWCLASS);
			this.encl = encl;
			this.clazz = clazz;
			this.args = args;
			this.def = def;
			this.constructor = constructor;
		}

		public void accept(Visitor v) {
			v.visitNewClass(this);
		}
	}

	/**
	 * A new[...] operation.
	 */
	public static class NewArray extends Tree {
		public Tree elemtype;
		public List dims;
		public List elems;

		public NewArray(Tree elemtype, List dims, List elems) {
			super(NEWARRAY);
			this.elemtype = elemtype;
			this.dims = dims;
			this.elems = elems;
		}

		public void accept(Visitor v) {
			v.visitNewArray(this);
		}
	}

	/**
	 * A parenthesized subexpression ( ... )
	 */
	public static class Parens extends Tree {
		public Tree expr;

		public Parens(Tree expr) {
			super(PARENS);
			this.expr = expr;
		}

		public void accept(Visitor v) {
			v.visitParens(this);
		}
	}

	/**
	 * A assignment with "=".
	 */
	public static class Assign extends Tree {
		public Tree lhs;
		public Tree rhs;

		public Assign(Tree lhs, Tree rhs) {
			super(ASSIGN);
			this.lhs = lhs;
			this.rhs = rhs;
		}

		public void accept(Visitor v) {
			v.visitAssign(this);
		}
	}

	/**
	 * An assignment with "+=", "|=" ...
	 */
	public static class Assignop extends Tree {
		public Tree lhs;
		public Tree rhs;
		public Symbol operator;

		public Assignop(int opcode, Tree lhs, Tree rhs, Symbol operator) {
			super(opcode);
			this.lhs = lhs;
			this.rhs = rhs;
			this.operator = operator;
		}

		public void accept(Visitor v) {
			v.visitAssignop(this);
		}
	}

	/**
	 * A unary operation.
	 */
	public static class Unary extends Tree {
		public Tree arg;
		public Symbol operator;

		public Unary(int opcode, Tree arg, Symbol operator) {
			super(opcode);
			this.arg = arg;
			this.operator = operator;
		}

		public void accept(Visitor v) {
			v.visitUnary(this);
		}
	}

	/**
	 * A binary operation.
	 */
	public static class Binary extends Tree {
		public Tree lhs;
		public Tree rhs;
		public Symbol operator;

		public Binary(int opcode, Tree lhs, Tree rhs, Symbol operator) {
			super(opcode);
			this.lhs = lhs;
			this.rhs = rhs;
			this.operator = operator;
		}

		public void accept(Visitor v) {
			v.visitBinary(this);
		}
	}

	/**
	 * A type cast.
	 */
	public static class TypeCast extends Tree {
		public Tree clazz;
		public Tree expr;

		public TypeCast(Tree clazz, Tree expr) {
			super(TYPECAST);
			this.clazz = clazz;
			this.expr = expr;
		}

		public void accept(Visitor v) {
			v.visitTypeCast(this);
		}
	}

	/**
	 * A type test.
	 */
	public static class TypeTest extends Tree {
		public Tree expr;
		public Tree clazz;

		public TypeTest(Tree expr, Tree clazz) {
			super(TYPETEST);
			this.expr = expr;
			this.clazz = clazz;
		}

		public void accept(Visitor v) {
			v.visitTypeTest(this);
		}
	}

	/**
	 * An array selection
	 */
	public static class Indexed extends Tree {
		public Tree indexed;
		public Tree index;

		public Indexed(Tree indexed, Tree index) {
			super(INDEXED);
			this.indexed = indexed;
			this.index = index;
		}

		public void accept(Visitor v) {
			v.visitIndexed(this);
		}
	}

	/**
	 * Selects through packages and classes
	 * 
	 * @param selected
	 *            selected Tree hierarchie
	 * @param selector
	 *            name of field to select thru
	 * @param sym
	 *            symbol of the selected class
	 */
	public static class Select extends Tree {
		public Tree selected;
		public Name name;
		public Symbol sym;

		public Select(Tree selected, Name name, Symbol sym) {
			super(SELECT);
			this.selected = selected;
			this.name = name;
			this.sym = sym;
		}

		public void accept(Visitor v) {
			v.visitSelect(this);
		}
	}

	/**
	 * An identifier
	 * 
	 * @param idname
	 *            the name
	 * @param sym
	 *            the symbol
	 */
	public static class Ident extends Tree {
		public Name name;
		public Symbol sym;

		public Ident(Name name, Symbol sym) {
			super(IDENT);
			this.name = name;
			this.sym = sym;
		}

		public void accept(Visitor v) {
			v.visitIdent(this);
		}
	}

	/**
	 * A constant value given literally.
	 * 
	 * @param value
	 *            value representation
	 */
	public static class Literal extends Tree {
		public int typetag;
		public Object value;

		public Literal(int typetag, Object value) {
			super(LITERAL);
			this.typetag = typetag;
			this.value = value;
		}

		public void accept(Visitor v) {
			v.visitLiteral(this);
		}
	}

	/**
	 * Identifies a basic type.
	 * 
	 * @param tag
	 *            the basic type id
	 * @see SemanticConstants
	 */
	public static class TypeIdent extends Tree {
		public int typetag;

		public TypeIdent(int typetag) {
			super(TYPEIDENT);
			this.typetag = typetag;
		}

		public void accept(Visitor v) {
			v.visitTypeIdent(this);
		}
	}

	/**
	 * An array type, A[]
	 */
	public static class TypeArray extends Tree {
		public Tree elemtype;

		public TypeArray(Tree elemtype) {
			super(TYPEARRAY);
			this.elemtype = elemtype;
		}

		public void accept(Visitor v) {
			v.visitTypeArray(this);
		}
	}

	/**
	 * A formal class parameter.
	 * 
	 * @param name
	 *            name
	 * @param bounds
	 *            bounds
	 */
	public static class TypeParameter extends Tree {

		TypeParameter() {
			super(0);
		}

		public static final List emptyList = new List();
	}

	public static class Erroneous extends Tree {

		public Erroneous() {
			super(ERRONEOUS);
		}

		public void accept(Visitor v) {
			v.visitErroneous(this);
		}
	}

	/**
	 * An interface for tree factories
	 */
	public interface Factory {

		TopLevel TopLevel(Tree pid, List defs);

		Import Import(Tree qualid);

		ClassDef ClassDef(long flags, Name name, List typarams, Tree extending,
				List implementing, List defs);

		MethodDef MethodDef(long flags, Name name, Tree restype, List typarams,
				List params, List thrown, Block body);

		VarDef VarDef(long flags, Name name, Tree vartype, Tree init);

		Skip Skip();

		Block Block(long flags, List stats);

		DoLoop DoLoop(Tree body, Tree cond);

		WhileLoop WhileLoop(Tree cond, Tree body);

		ForLoop ForLoop(List init, Tree cond, List step, Tree body);

		Labelled Labelled(Name label, Tree body);

		Switch Switch(Tree selector, List cases);

		Case Case(Tree pat, List stats);

		Synchronized Synchronized(Tree lock, Tree body);

		Try Try(Tree body, List catchers, Tree finalizer);

		Catch Catch(VarDef param, Tree body);

		Conditional Conditional(Tree cond, Tree thenpart, Tree elsepart);

		If If(Tree cond, Tree thenpart, Tree elsepart);

		Exec Exec(Tree expr);

		Break Break(Name label);

		Continue Continue(Name label);

		Return Return(Tree expr);

		Throw Throw(Tree expr);

		Apply Apply(Tree fn, List args);

		NewClass NewClass(Tree encl, Tree clazz, List args, ClassDef def);

		NewArray NewArray(Tree elemtype, List dims, List elems);

		Parens Parens(Tree expr);

		Assign Assign(Tree lhs, Tree rhs);

		Assignop Assignop(int opcode, Tree lhs, Tree rhs);

		Unary Unary(int opcode, Tree arg);

		Binary Binary(int opcode, Tree lhs, Tree rhs);

		TypeCast TypeCast(Tree expr, Tree type);

		TypeTest TypeTest(Tree expr, Tree clazz);

		Indexed Indexed(Tree indexed, Tree index);

		Select Select(Tree selected, Name selector);

		Ident Ident(Name idname);

		Literal Literal(int tag, Object value);

		TypeIdent TypeIdent(int typetag);

		TypeArray TypeArray(Tree elemtype);

		Erroneous Erroneous();
	}

	/**
	 * A generic visitor class for trees.
	 */
	public static abstract class Visitor {

		public Visitor() {
			super();
		}

		public void visitTopLevel(TopLevel that) {
			visitTree(that);
		}

		public void visitImport(Import that) {
			visitTree(that);
		}

		public void visitClassDef(ClassDef that) {
			visitTree(that);
		}

		public void visitMethodDef(MethodDef that) {
			visitTree(that);
		}

		public void visitVarDef(VarDef that) {
			visitTree(that);
		}

		public void visitSkip(Skip that) {
			visitTree(that);
		}

		public void visitBlock(Block that) {
			visitTree(that);
		}

		public void visitDoLoop(DoLoop that) {
			visitTree(that);
		}

		public void visitWhileLoop(WhileLoop that) {
			visitTree(that);
		}

		public void visitForLoop(ForLoop that) {
			visitTree(that);
		}

		public void visitLabelled(Labelled that) {
			visitTree(that);
		}

		public void visitSwitch(Switch that) {
			visitTree(that);
		}

		public void visitCase(Case that) {
			visitTree(that);
		}

		public void visitSynchronized(Synchronized that) {
			visitTree(that);
		}

		public void visitTry(Try that) {
			visitTree(that);
		}

		public void visitCatch(Catch that) {
			visitTree(that);
		}

		public void visitConditional(Conditional that) {
			visitTree(that);
		}

		public void visitIf(If that) {
			visitTree(that);
		}

		public void visitExec(Exec that) {
			visitTree(that);
		}

		public void visitBreak(Break that) {
			visitTree(that);
		}

		public void visitContinue(Continue that) {
			visitTree(that);
		}

		public void visitReturn(Return that) {
			visitTree(that);
		}

		public void visitThrow(Throw that) {
			visitTree(that);
		}

		public void visitAssert(Assert that) {
			visitTree(that);
		}

		public void visitApply(Apply that) {
			visitTree(that);
		}

		public void visitNewClass(NewClass that) {
			visitTree(that);
		}

		public void visitNewArray(NewArray that) {
			visitTree(that);
		}

		public void visitParens(Parens that) {
			visitTree(that);
		}

		public void visitAssign(Assign that) {
			visitTree(that);
		}

		public void visitAssignop(Assignop that) {
			visitTree(that);
		}

		public void visitUnary(Unary that) {
			visitTree(that);
		}

		public void visitBinary(Binary that) {
			visitTree(that);
		}

		public void visitTypeCast(TypeCast that) {
			visitTree(that);
		}

		public void visitTypeTest(TypeTest that) {
			visitTree(that);
		}

		public void visitIndexed(Indexed that) {
			visitTree(that);
		}

		public void visitSelect(Select that) {
			visitTree(that);
		}

		public void visitIdent(Ident that) {
			visitTree(that);
		}

		public void visitLiteral(Literal that) {
			visitTree(that);
		}

		public void visitTypeIdent(TypeIdent that) {
			visitTree(that);
		}

		public void visitTypeArray(TypeArray that) {
			visitTree(that);
		}

		public void visitErroneous(Erroneous that) {
			visitTree(that);
		}

		public void visitTree(Tree that) {
			assert false;
		}
	}
}
