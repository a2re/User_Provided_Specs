package fr.ujf.hasNext.hashcode.aspect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import fr.ujf.hasNext.common.Verdict;
import fr.ujf.hasNext.hascode.monitor.Event;
import fr.ujf.hasNext.hascode.monitor.VerificationMonitor;

@SuppressWarnings("rawtypes")
public aspect Aspect {

	public final static boolean enabled = true;

	HashMap<Integer, VerificationMonitor> collectionMap = new HashMap<Integer, VerificationMonitor>();
	HashMap<Integer, List<Integer>> mapCollToColls = new HashMap<Integer, List<Integer>>();

	public Verdict dispatchEvent(String concreteEventName, Integer c) {

		Verdict v = null;

		if (!this.collectionMap.containsKey(c)) {
			VerificationMonitor monitor = new VerificationMonitor (c.hashCode());
			collectionMap.put(c, monitor);
		}

		switch (concreteEventName) {
		case "edit":
			v = collectionMap.get(c).receiveEvent(Event.Edit);
			break;
		case "notEdit":
			v = collectionMap.get(c).receiveEvent(Event.NotEdit);
			break;
		case "update":
			v = collectionMap.get(c).receiveEvent(Event.Update);
			break;
		default:
			break;
		}
		return v;
	}

	pointcut addSet(Set t, Collection a): call (* Set.add( * )) && target(t) && args(a) && if(enabled);
	pointcut removeSet(Set t, Collection a): call (* Set.remove( * )) && target(t) && args(a) && if(enabled);
	pointcut addCollection(Collection t, Object a): call (* Collection.add( * )) && target(t) && args(a) && if(enabled);
	pointcut removeCollection(Collection t, Object a): call (* Collection.remove( * )) && target(t) && args(a) && if(enabled);

	before(Collection t, Object a) : addCollection(t,a) {
		dispatchEvent("update", System.identityHashCode(t));
	}
	
	before(Collection t, Object a) : removeCollection(t,a) {
		dispatchEvent("update", System.identityHashCode(t));
	}

	before(Set t, Collection a) : addSet(t,a) {
		if (mapCollToColls.containsKey(System.identityHashCode(a))){
			mapCollToColls.get(System.identityHashCode(a)).add(System.identityHashCode(t));
		}
		else {
			mapCollToColls.put(System.identityHashCode(a), new ArrayList<Integer>());
			mapCollToColls.get(System.identityHashCode(a)).add((Integer) System.identityHashCode(t));
			dispatchEvent("notEdit", System.identityHashCode(a));
		}
	}

	before(Set t, Collection a) : removeSet(t,a) {
		mapCollToColls.get(System.identityHashCode(a)).remove((Integer) System.identityHashCode(t));
		if (mapCollToColls.get(System.identityHashCode(a)).size() == 0)		
			dispatchEvent("edit", System.identityHashCode(a));
	}
}
