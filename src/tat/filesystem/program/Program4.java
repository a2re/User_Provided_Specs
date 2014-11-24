package tat.filesystem.program;

import tat.filesystem.monitor.File;
import tat.filesystem.monitor.File.MODE;


public class Program4 {
	
	public static void main (String args[]) {
		File f = new File("file.txt");
		f.open(MODE.READ);
		f.read();
		f.close();
		f.open(MODE.WRITE);
		f.write();
		f.close();
	}
}