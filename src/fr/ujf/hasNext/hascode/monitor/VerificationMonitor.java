package fr.ujf.hasNext.hascode.monitor;

import fr.ujf.hasNext.common.Verdict;

public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;

	private State currentState = State.Editable;

	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}

	public VerificationMonitor(int id) {
		this.id = id;
	}

	public void updateState(Event e) {
		switch (this.currentState) {
		case Editable:
			switch (e) {
			case NotEdit: 
				this.currentState = State.NotEditable;
				break;
			default:
				break;
			}
			break;
		case NotEditable:
			switch (e) {
			case Edit:
				this.currentState = State.Editable;
				break;			
			case Update:
				this.currentState = State.Error;
				break;
			case NotEdit:
				break;
			}
			break;
		case Error:
			break;
		}
	}

	public Verdict currentVerdict() {
		switch (this.currentState) {
		case Editable:
		case NotEditable:
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

	public Verdict receiveEvent(Event e) {
		System.out.println("=> Monitor " + this.id + ": received event " + e);
		updateState(e);
		emitVerdict();
		return currentVerdict();
	}
}
