package fr.ujf.hasNext.enumeration.monitor;

public enum State {
	Update("Update"), Error("Error");

	private String name;

	State(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
