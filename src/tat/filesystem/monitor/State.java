package tat.filesystem.monitor;

public enum State {
	OpenedRead("OpenedRead"), OpenedWrite("OpenedWrite"), Closed("Closed"), Error("Error");
	
	private String name;
	State (String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
