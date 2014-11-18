package fr.ujf.filsystem.aspect;

public class File {

	public enum MODE {
		READ, WRITE
	};

	public File(String filename) {}

	public void open(MODE m) {}

	public void close() {}

	public void write() {}

	public void read() {}
}