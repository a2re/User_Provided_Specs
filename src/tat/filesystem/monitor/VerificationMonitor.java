package tat.filesystem.monitor;

import tat.common.Verdict;


public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;
	
	private State currentState = State.Closed;
	
	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}
	
	public VerificationMonitor (int id) {
		this.id = id;
	}

	public void updateState(Event e) {
		switch (this.currentState) {
		case Closed:
			switch (e) {
			case openWrite:
				this.currentState = State.OpenedWrite;
				break;
			case openRead:
				this.currentState = State.OpenedRead;
				break;
			default: 
				this.currentState = State.Error;
				break;
			}
			break;
		case OpenedWrite:
			switch (e) {
			case write:
				break;
			case close:
				this.currentState = State.Closed;
				break;
			default: 
				this.currentState = State.Error;
				break;
			}
			break;
		case OpenedRead:
			switch (e) {
			case read:
				break;
			case close:
				this.currentState = State.Closed;
				break;
			default: 
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
		case Closed:
			return Verdict.CURRENTLY_TRUE;
		case OpenedRead:
		case OpenedWrite:
			return Verdict.CURRENTLY_FALSE;
		case Error:
			return Verdict.FALSE;
		default:
			return Verdict.FALSE;
		}
	}

	public void emitVerdict () {
		System.out.println("Monitor "+this.id+": "+currentVerdict());
	}

	public Verdict receiveEvent(Event e) {
		System.out.println("=> Monitor "+this.id+": received event "+e);
		updateState(e);
		emitVerdict();
		return currentVerdict();
	}
}

