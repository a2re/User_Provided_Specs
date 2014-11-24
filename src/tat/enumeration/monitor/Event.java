package tat.enumeration.monitor;

public enum Event {
	create ("create"), nextElement("nextElement"), update("update");
	
	private String name;
	Event (String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
