package tat.enumeration.program;

import java.util.HashMap;
import java.util.Vector;
import tat.common.Verdict;
import tat.enumeration.monitor.Enumeration;
import tat.enumeration.monitor.Event;
import tat.enumeration.monitor.VerificationMonitor;

@SuppressWarnings("rawtypes")
public aspect EnumarationAspect {

	public final static boolean enabled = true;

	HashMap<Integer, VerificationMonitor> enumMap = new HashMap<Integer, VerificationMonitor>();

	public Verdict dispatchEvent(String concreteEventName, Integer e, Integer a) {

		Verdict v = null;

		if (!this.enumMap.containsKey(e)) {
			VerificationMonitor monitor = new VerificationMonitor (e.hashCode());
			enumMap.put(e, monitor);
		}

		switch (concreteEventName) {
		case "create":
			v = enumMap.get(e).receiveEvent(Event.create, a);
			break;
		case "nextElement":
			v = enumMap.get(e).receiveEvent(Event.nextElement, a);
			break;
		case "update":
			v = enumMap.get(e).receiveEvent(Event.update, a);
			break;
		default:
			//unrecognized event => do nothing
			break;
		}
		return v;
	}

	pointcut update(Vector v): call (* Vector.add( * )) && target(v) && if(enabled) && within(tat.enumeration.program.*);
	pointcut create(Enumeration e, Vector v): call (* tat.enumeration.monitor.Enumeration.create( * )) && target(e) && args(v) && if(enabled);
	pointcut nextElement(Enumeration e): call (* tat.enumeration.monitor.Enumeration.nextElement()) && target(e) && if(enabled);

	before(Vector v) : update(v) {
		dispatchEvent("update", System.identityHashCode(v), System.identityHashCode(v));
	}

	before (Enumeration e, Vector v) : create(e,v) {
		dispatchEvent("create", System.identityHashCode(e), System.identityHashCode(v));
	}

	before (Enumeration e) : nextElement(e){
		dispatchEvent("nextElement", System.identityHashCode(e), null);
	}
}
