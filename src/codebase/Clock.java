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
	
	public long curTime(){
		return System.currentTimeMillis() - startTime;
	}
}
