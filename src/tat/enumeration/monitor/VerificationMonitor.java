package tat.enumeration.monitor;
import tat.enumeration.aspect.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import tat.common.Verdict;


public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;
	
	private static State currentState = State.Default;
	
	HashMap<Integer, Integer> dsState = new HashMap<Integer, Integer>();
	//HashMap<Integer, Integer> enumState = new HashMap<Integer, Integer>();
	//HashMap<Integer, Integer> enumDs = new HashMap<Integer, Integer>();
	Integer en;
	Integer ds;
	
	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}
	
	public VerificationMonitor (int id) {
		this.id = id;
	}

	public void updateState(Event e, /*Integer t,*/ Integer v) {
		switch (this.currentState) {
		case Default:
			switch (e) {
			case create:
				//enumDs.put(t, v);
				System.out.println("Before created : " + dsState.toString());
					//enumState.put(t, dsState.get(v));
					en = dsState.get(v);
					ds = v;
				break;
			case update:
				if (dsState.containsKey(v))
					dsState.put(v, dsState.get(v) + 1);
				else {
					dsState.put(v, 0);
				}
				System.out.println("After updated : " + dsState.toString());
				break;
			case nextElement:
				System.out.println("Before nextElement: " + dsState.toString());
				if (en != dsState.get(v))
					this.currentState = State.Error;
				break;
			}
			break;
		case Error:
			// No need to execute any code.
			break;
		}
	}

	public Verdict currentVerdict () {
		switch(this.currentState) {
		case Default:
			return Verdict.CURRENTLY_TRUE;
		case Error:
			return Verdict.FALSE;
		default:
			return Verdict.FALSE;
		}
	}

	public void emitVerdict () {
		System.out.println("Monitor "+this.id+": "+currentVerdict());
	}

	public Verdict receiveEvent(Event e, Integer v) {
		System.out.println("=> Monitor "+this.id+": received event "+e);
		updateState(e, v);
		emitVerdict();
		return currentVerdict();
	}
}

