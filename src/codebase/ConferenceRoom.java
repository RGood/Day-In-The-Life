package codebase;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConferenceRoom extends Thread{
	private ArrayList<Employee> waiting;
	private long meetingTime;
	CyclicBarrier roomBarrier;
	private boolean roomSet;
	public static boolean meetingInProgress;
	
	public ConferenceRoom() {
		waiting = new ArrayList<Employee>();
		roomSet = false;
		meetingInProgress = false;
	}
	
	public void setRoomMeeting(long meetingTime, int meetingSize) {
		while(roomSet || ConferenceRoom.meetingInProgress);
		this.meetingTime = meetingTime;
		roomBarrier = new CyclicBarrier(meetingSize, this);
		roomSet = true;
	}
	
	public void addEmployee(Employee e) {
		waiting.add(e);
		e.setMeeting(this);
		e.addTask(Task.Meeting);
	}
	
	public void awaitMeeting() {
		while(ConferenceRoom.meetingInProgress);
		try {
			roomBarrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		meetingInProgress = true;
		for(Employee e : waiting) {
			e.log(" enters meeting");
		}
		try {
			sleep(meetingTime);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		roomSet = false;
		waiting.clear();
		meetingInProgress = false;
	}
	
}
