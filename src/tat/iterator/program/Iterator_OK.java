package tat.iterator.program;

import java.util.Iterator;
import java.util.Vector;

public class Iterator_OK {

	public static void main (String args[]) {

		Vector<String> words1 = new Vector<String>();
		words1.add("Je");
		words1.add("Tu");
		words1.add("Il");
		words1.add("Nous");
		words1.add("Vous");
		words1.add("Ils");
		
		Vector<String> words2 = new Vector<String>();
		words2.add("Qui");
		words2.add("Que");
		words2.add("Quoi");
		words2.add("Dont");
		words2.add("Où");

		Iterator<String> it1 = words1.iterator();
		Iterator<String> it2 = words2.iterator();
		while(it1.hasNext() && it2.hasNext()) {
			it1.next();
			it2.next();
		}
		
	}

}
