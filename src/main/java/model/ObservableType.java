package model;

import java.io.Serializable;
import java.util.Observable;

public class ObservableType extends Observable implements Serializable {
	private static final long serialVersionUID = -7995203750815842902L;

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public synchronized void clearChanged() {
		super.clearChanged();
	}
	
}
