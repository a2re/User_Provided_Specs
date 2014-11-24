package tat.hashcode.monitor;

public enum Event {
	modify("modify"), addToSet("addToSet"), removeToSet("removeToSet");
	
	private String name;
	Event (String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
