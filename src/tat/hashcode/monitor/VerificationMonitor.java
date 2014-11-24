package tat.hashcode.monitor;

import java.util.ArrayList;
import java.util.List;

import tat.common.Verdict;


public class VerificationMonitor {

	private static final int DEFAULT_ID = -1;
	private int id;

	private List<Integer> setList = new ArrayList<Integer>();
	
	private State currentState = State.Editable;
	
	public VerificationMonitor() {
		this.id = DEFAULT_ID;
	}

	public VerificationMonitor (int id) {
		this.id = id;
	}

	public void updateState(Event e, Integer i) {
		switch (this.currentState) {
		case Editable:
			switch (e) {
			case addToSet:
				setList.add(i);
				this.currentState = State.NotEditable;
				break;
			default: 
				break;
			}
			break;
		case NotEditable:
			switch (e) {		
			case modify:
				this.currentState = State.Error;
				break;
			case addToSet:
				setList.add(i);
				break;
			case removeToSet:
				setList.remove((Integer) i);
				if (setList.isEmpty())
					this.currentState = State.Editable;
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
		case Editable:
		case NotEditable:
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

	public Verdict receiveEvent(Event e, Integer i) {
		System.out.println("=> Monitor "+this.id+": received event "+e);
		updateState(e, i);
		emitVerdict();
		return currentVerdict();
	}
}

