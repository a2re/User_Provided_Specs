package fr.ujf.monitor;


public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;
	
	private State currentState = State.DoHasNext;
	
	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}
	
	public VerificationMonitor (int id) {
		this.id = id;
	}

	public void updateState(Event e) {
		switch (this.currentState) {
		case DoHasNext:
			switch (e) {
			case hasNext:
				this.currentState = State.DoNext;
				break;
			case next: 
				this.currentState = State.Error;
				break;
			}
			break;
		case DoNext:
			switch (e) {
			case hasNext:
				break;
			case next: 
				this.currentState = State.DoHasNext;
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
		case DoHasNext:
			return Verdict.CURRENTLY_TRUE;
		case DoNext:
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

	public Verdict receiveEvent(Event e) {
		System.out.println("=> Monitor "+this.id+": received event "+e);
		updateState(e);
		emitVerdict();
		return currentVerdict();
	}
}

