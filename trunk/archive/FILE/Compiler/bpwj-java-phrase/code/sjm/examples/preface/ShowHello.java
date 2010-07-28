package sjm.examples.preface;

import sjm.parse.Assembly;
import sjm.parse.Repetition;
import sjm.parse.Terminal;
import sjm.parse.tokens.TokenAssembly;

/**
 * This is a "Hello world" program. Once you get this 
 * working on your computer, you can get any example in this 
 * book to work.
 */
public class ShowHello {
/**
 * Create a little parser and use it to recognize 
 * "Hello world!".
 */
public static void main(String[] args) {
	Terminal terminal   = new Terminal();
	Repetition repetition = new Repetition(terminal);
	
	Assembly in  = new TokenAssembly("Hello world!");
	Assembly out = repetition.completeMatch(in);
	
	System.out.println(out.getStack());
}
}