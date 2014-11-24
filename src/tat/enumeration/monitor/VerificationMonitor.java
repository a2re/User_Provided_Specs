package tat.enumeration.monitor;

import java.util.HashMap;

import tat.common.Verdict;
import tat.enumeration.monitor.State;

public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;

	public static HashMap<Integer, Integer> dsState = new HashMap<Integer, Integer>();
	private Integer en;
	private Integer ds;

	private State currentState = State.Default;

	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}

	public VerificationMonitor(int id) {
		this.id = id;
	}

	public void updateState(Event e, Integer v) {
		switch (this.currentState) {
		case Default:
			switch (e) {
			case create:
				this.ds = v;
				en = dsState.get((Integer) v);
				break;
			case update:
				if (dsState.containsKey((Integer) v)) {
					dsState.put((Integer) v, dsState.get((Integer) v) + 1);
				} else {
					dsState.put((Integer) v, 0);
				}
			case nextElement: {
				if (en != dsState.get(this.ds)) {
					this.currentState = State.Error;
				}
				break;
			}
			default:
				break;
			}
			break;
		case Error:
			// No need to execute any code.
			break;
		}
	}

	public Verdict currentVerdict() {
		switch (this.currentState) {
		case Default:
			return Verdict.CURRENTLY_TRUE;
		case Error:
			return Verdict.FALSE;
		default:
			return Verdict.FALSE;
		}
	}

	public void emitVerdict() {
		System.out.println("Monitor " + this.id + ": " + currentVerdict());
	}

	public Verdict receiveEvent(Event e, Integer i) {
		System.out.println("=> Monitor " + this.id + ": received event " + e);
		updateState(e, i);
		emitVerdict();
		return currentVerdict();
	}
}
