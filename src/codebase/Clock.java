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

		int hours = (int) (realTime/600);
		int minutes = (int) ((realTime-(600*hours)) / 10);
		
		hours = hours + 8; // clock actually starts at 8
		
		if (minutes < 10) {
			return hours + ":0" + minutes;
		} else {
			return hours + ":" + minutes;
		}
	}
}
