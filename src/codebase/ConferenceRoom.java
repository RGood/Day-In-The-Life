package codebase;

import java.util.ArrayList;

public class ConferenceRoom extends Thread{
	ArrayList<Employee> waiting;
	long meetingTime;
	private static volatile boolean semiphoreLock; 
	
	public ConferenceRoom(long meetingTime) {
		waiting = new ArrayList<Employee>();
		this.meetingTime = meetingTime;
	}
	
	public void addEmployee(Employee e) {
		waiting.add(e);
	}
	
	public synchronized static void lock() {
		while (semiphoreLock);
		semiphoreLock = true;
	}
	
	public synchronized static void unlock() {
		semiphoreLock = false;
	}
	
	public boolean allPresent() {
		for (Employee e : waiting) {
			if (!e.inMeeting()) {
				return false;
			}
		}
		return true;
	}
	
	public void run(){
		for (Employee e : waiting) {
			e.addTask(Task.Meeting);
		}
		while (!allPresent());
		lock();
		{
			for (Employee e : waiting) {
				e.log(" enters a meeting");
			}
			try {
				sleep(meetingTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (Employee e : waiting) {
				e.addTask(Task.MeetingRelease);
				e.log(" leaves a meeting");
			}
		}
		unlock();
	}
	
}
