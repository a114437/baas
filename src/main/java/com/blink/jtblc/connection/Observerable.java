package com.blink.jtblc.connection;

public interface Observerable {

	public void register(Observer o);
	
	public void removeObserver(Observer o);
	
	public void notifyObserver(String message);
	
}
