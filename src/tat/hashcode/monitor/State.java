package tat.hashcode.monitor;

public enum State {
	Editable("Editable"), NotEditable("NotEditable"), Error("Error");
	
	private String name;
	State (String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
