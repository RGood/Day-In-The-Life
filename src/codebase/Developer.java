package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Developer extends Thread implements Employee, Answerable{
	private Integer teamNumber;
	private Integer memberNumber; // 2-4, number on the team

	private Askable lead;
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Task> answers;
	
	private boolean running;
	
	private ConferenceRoom meetingRoom;
	
	public Developer(Integer n, Integer m, Askable l){
		teamNumber = n;
		memberNumber = m;
		lead = l;
		action = new ArrayBlockingQueue<Task>(1);
		answers = new ArrayBlockingQueue<Task>(1);
	}
	
	//Method to tell developer what to be doing
	public void addTask(Task t){
		try {
			action.put(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method for asking sending a question
	public void question() {
		lead.answer(this);
	}
	
	//Method for receiving question answer
	@Override
	public void answer(Task t) {
		// TODO Auto-generated method stub
		try {
			answers.put(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void log(String log) {
		System.out.println("Developer " + teamNumber.toString() + "" + memberNumber.toString() +
											log + "\t at " + Clock.getClock().currentTimeSimulated());
	}
	
	@Override
	public void setMeeting(ConferenceRoom cr) {
		this.meetingRoom = cr;
	}
	
	//Main run method
	public void run(){
		Random x = new Random();
		
		running = true;
		log(" enters work");
		while(running){
			try {
				switch(action.take()){ //Get current to-do. Developing otherwise.
				case Leave: //Break the loop
					running = false;
					break;
				case Question: //Ask a question and wait for an answer
					log(" asks a question");
					question();
					answers.take();
					log(" got an answer");
					break;
				case Lunch: //Go to lunch for 30-60 minutes
					log(" goes to lunch");
					try {
						sleep((long) (30 + (x.nextDouble() * 30)) * 10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case Meeting: //Go to meeting and wait
					log(" waits to meet");
					meetingRoom.awaitMeeting();
					log(" leaves meeting");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block for taking an action
				e.printStackTrace();
			}
		}
		log(" leaves for the day");
	}

	@Override
	public boolean atWork() {
		return running;
	}
}
