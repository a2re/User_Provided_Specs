package tat.iterator.program;

import java.util.HashMap;
import java.util.Iterator;

import tat.iterator.monitor.Event;
import tat.iterator.monitor.Verdict;
import tat.iterator.monitor.VerificationMonitor;

@SuppressWarnings("rawtypes")
public aspect AjParametricHasNext {

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

	pointcut hasNext(Iterator it): call (boolean java.util.Iterator.hasNext()) && target(it) && if(enabled) && within(fr.ujf.iterator.*);
	pointcut next(Iterator it): call (Object java.util.Iterator.next()) && target(it) && if(enabled) && within(fr.ujf.iterator.*);

	before(Iterator it) : hasNext(it) {
		dispatchEvent("hasNext", it);
	}

	before(Iterator it) : next(it) {
		dispatchEvent("next", it);
	}

}
