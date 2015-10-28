package codebase;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public interface Employee extends Runnable {
	
	public void addTask(Task t);
	
	public void log(String log);
	
	public void setMeeting(ConferenceRoom cr);
	
	public boolean atWork();
}
