package tat.hashcode.program;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import tat.common.Verdict;
import tat.hashcode.monitor.Event;
import tat.hashcode.monitor.VerificationMonitor;

@SuppressWarnings("rawtypes")
public aspect HashCodeAspect {

	public final static boolean enabled = true;

	HashMap<Integer, VerificationMonitor> colMap = new HashMap<Integer, VerificationMonitor>();

	public Verdict dispatchEvent(String concreteEventName, Integer c, Integer i) {

		Verdict v = null;

		if (!this.colMap.containsKey(c)) {
			VerificationMonitor monitor = new VerificationMonitor (c.hashCode());
			colMap.put(c, monitor);
		}

		switch (concreteEventName) {
		case "modify":
			v = colMap.get(c).receiveEvent(Event.modify, i);
			break;
		case "addToSet":
			v = colMap.get(c).receiveEvent(Event.addToSet, i);
			break;
		case "removeToSet":
			v = colMap.get(c).receiveEvent(Event.removeToSet, i);
			break;
		default:
			//unrecognized event => do nothing
			break;
		}
		return v;
	}

	pointcut addToSet(Set t, Collection a): call (* Set.add( * )) && target(t) && args(a) && if(enabled) && !within(HashCodeAspect);
	pointcut removeToSet(Set t, Collection a): call (* Set.remove( * )) && target(t) && args(a) && if(enabled) && !within(HashCodeAspect);
	pointcut addCollection(Collection t, Object a): call (* Collection.add( * )) && target(t) && args(a) && if(enabled) && !within(HashCodeAspect);
	pointcut removeCollection(Collection t, Object a): call (* Collection.remove( * )) && target(t) && args(a) && if(enabled) && !within(HashCodeAspect);

	before(Collection t, Object a) : addCollection(t,a) {
		if (!(t instanceof Set) && !(t instanceof Vector))
			dispatchEvent("modify", System.identityHashCode(t), null);
	}

	before(Collection t, Object a) : removeCollection(t,a) {
		if (!(t instanceof Set) && !(t instanceof Vector))
			dispatchEvent("modify", System.identityHashCode(t), null);
	}

	before(Set t, Collection a) : addToSet(t,a) {
		dispatchEvent("addToSet", System.identityHashCode(a), System.identityHashCode(t));
	}

	before(Set t, Collection a) : removeToSet(t,a) {
		dispatchEvent("removeToSet", System.identityHashCode(a), System.identityHashCode(t));
	}
}
