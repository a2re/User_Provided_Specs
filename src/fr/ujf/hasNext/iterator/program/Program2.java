package fr.ujf.hasNext.iterator.program;

import java.util.Iterator;
import java.util.Vector;

public class Program2 {

	public final static int VECTOR_SIZE = 1;
	
	public static void main (String args[]) {

		Vector<String> words = new Vector<String>();
		for (int i = 0; i < VECTOR_SIZE;i++) {
			words.add("Antoine");words.add("de");words.add("St Exupery");
		}

		Iterator<String> it = words.iterator(), it2 = words.iterator();
		while(it.hasNext() 
				&& it2.hasNext()) {
			it.next();
			it2.next();
			it2.next();
//			if (Math.random() > 0.5) {
//				it2.next();
//			}

		}
	}
}
