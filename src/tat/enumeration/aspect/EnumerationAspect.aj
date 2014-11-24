package tat.enumeration.aspect;

import java.util.HashMap;
import java.util.Vector;

import tat.common.Verdict;
import tat.enumeration.monitor.Event;
import tat.enumeration.monitor.VerificationMonitor;

@SuppressWarnings("rawtypes")
public aspect EnumerationAspect {

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
			v = enumMap.get(e).currentVerdict();
			break;
		}
		return v;
	}

	pointcut create(Enumeration e, Vector v): call (* Enumeration.create(*)) && target(e) && args(v) && if(enabled);
	pointcut nextElement(Enumeration e): call (* Enumeration.nextElement()) && target(e) && if(enabled);
	pointcut update(Vector v): call (* Vector.add(*)) && target(v) && if(enabled);

	before (Enumeration e, Vector v) : create(e, v) {
		dispatchEvent("create", System.identityHashCode(e), System.identityHashCode(v));
	}

	before (Enumeration e) : nextElement(e){
		dispatchEvent("nextElement", System.identityHashCode(e), null);
	}

	before(Vector v) : update(v) {
		dispatchEvent("update", System.identityHashCode(v), System.identityHashCode(v));
	}
}
