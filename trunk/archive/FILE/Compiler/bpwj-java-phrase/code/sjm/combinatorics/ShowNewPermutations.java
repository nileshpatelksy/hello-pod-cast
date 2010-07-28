package sjm.combinatorics;

/**
 * Insert the type's description here.
 * Creation date: (7/3/2001 5:24:34 AM)
 * @author: 
 */
class ShowNewPermutations {
/**
 * Insert the method's description here.
 * Creation date: (7/3/2001 5:24:56 AM)
 * @param args java.lang.String[]
 */
public static void main(String[] args) {
	Object[] children =
		{ "Leonardo", "Monica", "Nathan", "Olivia" };
	boolean more = true;
	do {
		for (int i = 0; i < children.length; i++) {
			System.out.print(children[i] + " ");
		}
		System.out.println();
		more = NewPermutations.moveIndex(children);
	}
	while (more);
}
}