package tat.enumeration.aspect;

import java.util.Vector;

public class Enumeration<T> {
	private java.util.Enumeration<T> e;

	public void create(Vector<T> v) {
		e = v.elements();
	}

	public Object nextElement() {
		return e.nextElement();
	}

	public boolean hasMoreElements() {
		return e.hasMoreElements();
	}
}
