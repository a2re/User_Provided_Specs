package fr.ujf.hasNext.enumeration.monitor;

public enum Event {
	hasMoreElements("hasMoreElements"), nextElement("nextElement");

	private String name;

	Event(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
