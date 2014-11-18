package fr.ujf.hasNext.enumeration.aspect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import fr.ujf.hasNext.common.Verdict;
import fr.ujf.hasNext.enumeration.monitor.*;

@SuppressWarnings("rawtypes")
public aspect Aspect {

	public final static boolean enabled = true;

	HashMap<Integer, VerificationMonitor> enumerationMap = new HashMap<Integer, VerificationMonitor>();

	public Verdict dispatchEvent(String concreteEventName, Integer e, Integer i) {

		Verdict v = null;

		if (!this.enumerationMap.containsKey(e)) {
			VerificationMonitor monitor = new VerificationMonitor(e.hashCode());
			enumerationMap.put(e, monitor);
		}

		switch (concreteEventName) {
		case "init":
			v = enumerationMap.get(e).receiveEvent(Event.hasMoreElements, i);
			break;
		case "nextElement":
			v = enumerationMap.get(e).receiveEvent(Event.nextElement, i);
			break;
		default:
			break;
		}
		return v;
	}

	pointcut addDs(Vector t): call (* Vector.add( * )) && target(t) && if(enabled);
	pointcut removeDs(Vector t): call (* Vector.remove( * )) && target(t) && if(enabled);
	pointcut createEnum(Enumeration t, Vector a): call (* Enumeration.create( * )) && target(t) && args(a) && if(enabled);
	pointcut nextEnum(Enumeration t): call (* Enumeration.nextElement()) && target(t) && if(enabled);

	before(Vector t) : addDs(t) {
		if (!VerificationMonitor.dsState
				.containsKey(System.identityHashCode(t))) {
			VerificationMonitor.dsState.put(
					(Integer) System.identityHashCode(t), 0);
		} else {
			VerificationMonitor.dsState.put((Integer) System
					.identityHashCode(t), VerificationMonitor.dsState
					.get((Integer) System.identityHashCode(t)) + 1);
		}
	}

	before(Vector t) : removeDs(t) {
		if (!VerificationMonitor.dsState
				.containsKey(System.identityHashCode(t))) {
			VerificationMonitor.dsState.put(System.identityHashCode(t), 0);
		} else {
			VerificationMonitor.dsState.put(System.identityHashCode(t),
					VerificationMonitor.dsState.get((Integer) System
							.identityHashCode(t)) + 1);
		}
	}

	before(Enumeration t, Vector a) : createEnum(t,a) {
		dispatchEvent("hasMoreElements", (Integer) System.identityHashCode(t),
				(Integer) System.identityHashCode(a));
	}

	before(Enumeration t) : nextEnum(t){
		dispatchEvent("nextElement", (Integer) System.identityHashCode(t), null);
	}
}
