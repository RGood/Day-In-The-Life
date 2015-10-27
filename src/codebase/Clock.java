package codebase;

public class Clock {
	private long startTime;
	private static Clock instance;
	
	private Clock(long s){
		startTime = s;
	}
	
	public static Clock getClock(){
		if(instance==null){
			instance = new Clock(System.currentTimeMillis());
		}
		return instance;
	}
	
	public long currentTimeReal(){
		return System.currentTimeMillis() - startTime;
	}
	
	public String currentTimeSimulated(){
		long realTime = currentTimeReal();
		long minutes = realTime/10;
		return ":";
	}
}
