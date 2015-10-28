package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TeamLead extends Thread implements Answerable, Askable, Employee{
	private Integer teamNumber;
	private Integer memberNumber; // 1 for team leads
	private Askable manager;
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Task> answers;
	private BlockingQueue<Answerable> asker;
	
	private ConferenceRoom meetingRoom;
	
	private boolean running;
	
	public TeamLead(Integer n, Askable l){
		teamNumber = n;
		memberNumber = 1;
		manager = l;
		action = new ArrayBlockingQueue<Task>(1);
		answers = new ArrayBlockingQueue<Task>(1);
		asker = new ArrayBlockingQueue<Answerable>(1);
	}
	
	//Handles questions received
	@Override
	public void question() {
		try {
			Random x = new Random();
			if(x.nextBoolean()){
				asker.take().answer(Task.Answer);
			}else{
				manager.answer(this);
				answers.take();
				asker.take().answer(Task.Answer);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method for telling Team lead what to do
	@Override
	public void addTask(Task t) {
		try {
			action.put(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method for asking team lead a question
	@Override
	public void answer(Answerable a) {
		addTask(Task.Question);
		try {
			asker.put(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method for outside sources to respond to team lead questions
	@Override
	public void answer(Task t) {
		try {
			answers.put(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
				switch(action.take()){
				case Leave: //Break running loop
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
