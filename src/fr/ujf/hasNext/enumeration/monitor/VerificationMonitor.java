package fr.ujf.hasNext.enumeration.monitor;

import java.util.HashMap;

import fr.ujf.hasNext.common.Verdict;

public class VerificationMonitor {
	private static final int DEFAULT_ID = -1;
	private int id;

	public static HashMap<Integer, Integer> dsState = new HashMap<Integer, Integer>();
	private Integer state;
	private Integer ds;

	private State currentState = State.Update;

	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}

	public VerificationMonitor(int id) {
		this.id = id;
	}

	public void updateState(Event e, Integer v) {
		if (this.currentState == State.Update) {
			switch (e) {
			case hasMoreElements:
				this.ds = v;
				this.state = dsState.get((Integer) v);
				break;
			case nextElement: {
				Integer st1 = this.state;
				Integer st2 = dsState.get(this.ds);
				if (st1 != st2)
					this.currentState = State.Error;
				break;
			}
			default:
				break;
			}
		}
	}

	public Verdict currentVerdict() {
		switch (this.currentState) {
		case Update:
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
