package fr.ujf.hasNext.enumeration.aspect;

import java.util.Vector;

public class Enumeration<E> {

	private java.util.Enumeration<E> e;
	
	public void create(Vector<E> e){
		this.e = e.elements();
	}
	
	public Object nextElement(){
		return e.nextElement();
	}
	
	public boolean hasMoreElements(){
		return e.hasMoreElements();
	}	
}