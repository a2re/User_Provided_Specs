package tat.common;

public enum Verdict {
	TRUE("true"),
	CURRENTLY_TRUE("currently true"),
	CURRENTLY_FALSE("currently false"),
	FALSE("false");
	
	private String verdictName;
	
	Verdict(String name) {
		this.verdictName = name;
	}
	
	public String getName() {
		return this.verdictName;
	}
}
