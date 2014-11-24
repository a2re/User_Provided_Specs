package tat.iterator.program;

import java.util.Iterator;
import java.util.Vector;

public class Iterator_ERROR_DOUBLE_NEXT {

	public static void main (String args[]) {

		Vector<String> words1 = new Vector<String>();
		words1.add("Mais");
		words1.add("Ou");
		words1.add("Et");
		words1.add("Donc");
		words1.add("Or");
		words1.add("Ni");
		
		Vector<String> words2 = new Vector<String>();
		words2.add("Un peu");
		words2.add("Beaucoup");
		words2.add("Énormément");
		words2.add("Pas du tout");
		words2.add("Très peu");
		words2.add("À peine");
		
		Iterator<String> it1 = words1.iterator();
		Iterator<String> it2 = words2.iterator();
		
		while(it1.hasNext() && it2.hasNext()) {
			it1.next();
			it1.next(); //error: next without hasNext before
			
			it2.next();
			it2.next(); //error: next without hasNext before
		}
		
	}

}
