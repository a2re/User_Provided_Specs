package tat.filesystem.program;

import tat.filesystem.monitor.File;
import tat.filesystem.monitor.File.MODE;


public class Program {
	
	public static void main (String args[]) {
		File f = new File("file.txt");
		f.open(MODE.READ);
		f.read();
	}
}