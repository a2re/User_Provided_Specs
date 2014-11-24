package tat.filesystem.monitor;

public enum Event {
	openRead ("openRead"), openWrite ("openWrite"), read("read"), write("write"), close("close");
	
	private String name;
	Event (String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
