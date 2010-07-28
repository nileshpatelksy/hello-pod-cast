package sjm.combinatorics;

import java.util.*;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 * 
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */

/**
 * This didn't go in the article. Here I show how to provide
 * permuations just using static methods that operate on an
 * array.
 *
 * This code brings out the subtle complexity in Comparators and
 * Comparables. Most fundamental classes like String and Integer
 * implement Comparable. This code assumes that if you don't
 * supply a Comparator, you are permuting an array of Comparables,
 * like Strings. This class provides the seemingly handy
 * comparableComparator that creates a Comparator that assumes
 * its args are Comparables.
 * 
 */

public class NewPermutations   {











	public static Comparator comparableComparator;

/**
 * Insert the method's description here.
 * Creation date: (7/3/2001 5:48:25 AM)
 * @return java.util.Comparator
 */
public static Comparator comparableComparator() {
	if (comparableComparator == null) {
		comparableComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				Comparable c1 = (Comparable) o1;
				Comparable c2 = (Comparable) o2;
				return c1.compareTo(c2);
			}
		};
	}
	return comparableComparator;
}

/**
 * Move the index forward a notch. The algorithm first finds
 * the rightmost index that is less than its neighbor to the 
 * right. This is the 'dip' point. The algorithm next finds 
 * the least element to the right of the dip that is greater 
 * than the dip. That element is switched with the dip. 
 * Finally, the list of elements to the right of the dip is 
 * reversed.
 * <p>
 * For example, in a permuation of 5 items, the index may be 
 * {1, 2, 4, 3, 0}. The 'dip' is 2 -- the rightmost element 
 * less than its neighbor on its right. The least element to
 * the right of 2 that is greater than 2 is 3. These elements
 * are swapped, yielding {1, 3, 4, 2, 0}, and the list right 
 * of the dip point is reversed, yielding {1, 3, 0, 2, 4}.
 * <p>
 * The algorithm is from "Applied Combinatorics", by 
 * Alan Tucker.
 *
 */
public static boolean moveIndex(Object[] array) {
	return moveIndex(array, comparableComparator());
}

/**
 * Move the index forward a notch. The algorithm first finds
 * the rightmost index that is less than its neighbor to the 
 * right. This is the 'dip' point. The algorithm next finds 
 * the least element to the right of the dip that is greater 
 * than the dip. That element is switched with the dip. 
 * Finally, the list of elements to the right of the dip is 
 * reversed.
 * <p>
 * For example, in a permuation of 5 items, the index may be 
 * {1, 2, 4, 3, 0}. The 'dip' is 2 -- the rightmost element 
 * less than its neighbor on its right. The least element to
 * the right of 2 that is greater than 2 is 3. These elements
 * are swapped, yielding {1, 3, 4, 2, 0}, and the list right 
 * of the dip point is reversed, yielding {1, 3, 0, 2, 4}.
 * <p>
 * The algorithm is from "Applied Combinatorics", by 
 * Alan Tucker.
 *
 */
public static boolean moveIndex(Object[] array, Comparator c) {
	
	// find the index of the first element that dips
	int dip = rightmostDip(array, c);
	if (dip < 0) {
		return false;
		 
	}	
	
	// find the least greater element to the right of the dip
	int leastToRightIndex = dip + 1;
	for (int j = dip + 2; j < array.length; j++) {
		if ((c.compare(array[j], array[leastToRightIndex]) < 0) && 
			(c.compare(array[j], array[dip]              ) > 0)) {
			leastToRightIndex = j;
		}	
	}
	
	// switch dip element with least greater element to its 
	// right
	Object o = array[dip];
	array[dip] = array[leastToRightIndex];
	array[leastToRightIndex] = o;
	
	//if (m - 1 > i) {
	//// reverse the elements to the right of the dip
		//reverseAfter(i);	
	//// reverse the elements to the right of m - 1
		//reverseAfter(m - 1);
	//}	
	reverseAfter(array, dip);
	return true;
}

/**
 * Reverse the index elements to the right of the specified
 * index.
 */
private static void reverseAfter(Object[] array, int i) {
	int start = i + 1;
	int end = array.length - 1;
	while (start < end) {
		Object o = array[start];
		array[start] = array[end];
		array[end] = o;
		start++;
		end--;
	}
}

/**
 * @return int the index of the first element from the right
 * that is less than its neighbor on the right.
 */
private static int rightmostDip(Object[] array, Comparator c) {
	for (int i = array.length - 2; i >= 0; i--) {
		if (c.compare(array[i], array[i+1]) < 0) {
			return i;
		}	
	}
	return -1;
	
}
}