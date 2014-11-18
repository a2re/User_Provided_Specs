package fr.ujf.hasNext.hascode.monitor;

public enum Event {
	Edit("edit"), NotEdit("notEdit"), Update("update");

	private String name;

	Event(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
