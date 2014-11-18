package fr.ujf.filsystem.aspect;

import java.util.HashMap;

import fr.ujf.hasNext.common.Verdict;
import fr.ujf.filesystem.monitor.*;

public aspect Aspect {
	
	public enum MODE {
		READ, WRITE
	};

	public final static boolean enabled = true;

	HashMap<File, VerificationMonitor> fileMap = new HashMap<File, VerificationMonitor>();

	public Verdict dispatchEvent(String concreteEventName, File f) {

		Verdict v = null;

		if (!this.fileMap.containsKey(f)) {
			VerificationMonitor monitor = new VerificationMonitor (f.hashCode());
			fileMap.put(f, monitor);
		}

		switch (concreteEventName) {
		case "openRead":
			v = fileMap.get(f).receiveEvent(Event.openRead);
			break;
		case "openWrite":
			v = fileMap.get(f).receiveEvent(Event.openWrite);
			break;
		case "write":
			v = fileMap.get(f).receiveEvent(Event.write);
			break;
		case "read":
			v = fileMap.get(f).receiveEvent(Event.read);
			break;
		case "close":
			v = fileMap.get(f).receiveEvent(Event.close);
			break;
		default:
			break;
		}
		return v;
	}

	pointcut open(File f, MODE m): call (void File.open(MODE)) && target(f) && args(m) && if(enabled);
	pointcut write(File f): call (void File.write()) && target(f) && if(enabled);
	pointcut read(File f): call (void read()) && target(f) && if(enabled);
	pointcut close(File f): call (void close()) && target(f) && if(enabled);

	before(File f, MODE m) : open(f,m) {
		if (m == MODE.READ)
			dispatchEvent("openRead", f);
		else
			dispatchEvent("openWrite", f);
	}

	before(File f) : write(f) {
		dispatchEvent("write", f);
	}

	before(File f) : read(f) {
		dispatchEvent("read", f);
	}

	before(File f) : close(f) {
		dispatchEvent("close", f);
	}
}