package fr.ujf.filesystem.monitor;

public enum Event {
	openWrite ("openWrite"), openRead ("openRead"), write("write"), read("read"), close("close");
	
	private String name;
	Event (String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}