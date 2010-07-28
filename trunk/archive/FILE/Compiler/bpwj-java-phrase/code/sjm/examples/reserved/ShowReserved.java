package sjm.examples.reserved;

import java.util.Vector;

import sjm.parse.tokens.TokenAssembly;
import sjm.parse.tokens.Tokenizer;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 * 
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */

/**
 * This class shows the use of a customized tokenizer, and
 * the use of a terminal that looks for the new token type.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class ShowReserved {
/**
 * Show the use of a customized tokenizer, and the use of a 
 * terminal that looks for the new token type.
 */
public static void main(String[] args) {
		
	Tokenizer t = VolumeQuery2.tokenizer(); 	
	t.setString("How many cups are in a gallon?");
	
	Vector v = new Vector();
	v.addElement(new TokenAssembly(t));
	
	System.out.println(VolumeQuery2.query().match(v));
}
}