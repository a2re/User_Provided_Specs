package fr.ujf.filesystem.monitor;

public enum State {
	OpenedWrite("OpenedWrite"), OpenedRead("OpenedRead"), Closed("Closed"), Error("Error");
	
	private String name;
	State (String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}