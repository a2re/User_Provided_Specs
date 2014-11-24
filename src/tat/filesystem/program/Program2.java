package tat.filesystem.program;

import tat.filesystem.monitor.File;


public class Program2 {
	
	public static void main (String args[]) {
		File f = new File("file.txt");
		f.close();
	}
}