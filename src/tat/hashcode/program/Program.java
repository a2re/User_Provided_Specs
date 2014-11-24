package tat.hashcode.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Program {

	public static void main (String args[]) {		
		Set<Collection<String>> s = new HashSet<Collection<String>>();
		Collection<String> c = new ArrayList<String>();
		c.add("this is ok");
		s.add(c);
		c.add("don’t do this"); /** MODIFY COLLECTION (c) **/
		System.out.println(s.contains(c));		
	}	
}