package fr.ujf.hasNext.iterator.aspect;

import java.util.HashMap;
import java.util.Iterator;

import fr.ujf.hasNext.common.Verdict;
import fr.ujf.hasNext.iterator.monitor.Event;
import fr.ujf.hasNext.iterator.monitor.VerificationMonitor;

public aspect ParametricHasNextEnforcement {

	public final static boolean enabled = true;
	HashMap<Iterator, VerificationMonitor> iteratorMap = new HashMap<Iterator, VerificationMonitor>();

	public Verdict dispatchEvent(String concreteEventName, Iterator it) {
		
		Verdict v = null;
		
		if (!this.iteratorMap.containsKey(it)) {
			VerificationMonitor monitor = new VerificationMonitor (it.hashCode());
			iteratorMap.put(it, monitor);
		}
		
		switch (concreteEventName) {
		case "hasNext":
			v = iteratorMap.get(it).receiveEvent(Event.hasNext);
			break;
		case "next":
			v = iteratorMap.get(it).receiveEvent(Event.next);
			break;
		default:
			//unrecognized event => do nothing
			break;
		}
		return v;
	}

	pointcut hasNext(Iterator it): call (boolean java.util.Iterator.hasNext()) && target(it) && if(enabled);
	pointcut next(Iterator it): call (* java.util.Iterator.next()) && target(it) && if(enabled);

	boolean around(Iterator it) : hasNext(it) {
		Verdict v = dispatchEvent("hasNext", it);
		if (v == Verdict.CURRENTLY_TRUE || v == Verdict.TRUE) {
			System.out.println("Allowing the execution of hasNext on "+it.hashCode());
			return proceed(it);
		}
		else {
			System.out.println("Preventing the execution of hasNext on "+it.hashCode());
			return false;
		}
	}

	Object around (Iterator it) : next(it) {
		 Verdict v = dispatchEvent("next", it);
		if (v == Verdict.CURRENTLY_TRUE || v == Verdict.TRUE) {
			System.out.println("Allowing the execution of next on "+it.hashCode());
			return proceed(it);
		}
		else {
			System.out.println("Preventing the execution of next on "+it.hashCode());
			return null;
		}
	}
	

}
