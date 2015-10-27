package codebase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Manager extends Thread implements Askable, Employee{
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Answerable> asker;
	
	public Manager(){		
		action = new ArrayBlockingQueue<Task>(1);
		asker = new ArrayBlockingQueue<Answerable>(1);
	}
	
	//Public method for telling the manager what it should be doing.
	@Override
	public void addTask(Task t) {
		action.add(t);
	}
	
	//Method for obtaining questions from other people
	@Override
	public void answer(Answerable a) {
		addTask(Task.Question);
		asker.add(a);
	}
	
	//Method for handling questions received
	private void question() {
		try {
			sleep(10 * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		asker.remove().answer(Task.Answer);
	}
	
	public void log(String log) {
		System.out.println("Manager" + log);
	}
	
	//Main running method
	public void run(){
		log(" enters work.");
		
		boolean running = true;
		log(" arrives at work");
		while(running){
			try {
				switch(action.take()){
				case Leave: //Break main loop
					running = false;
					break;
				case Question: //Answer a question
					log(" answers a question.");
					question();
					break;
				case Lunch: //Go to lunch for 60 minutes
					log(" goes to lunch.");
					try {
						sleep(60 * 10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case Meeting: //Go to meeting and wait
					log(" goes to a meeting.");
					action.take();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block for taking an action
				e.printStackTrace();
			}
		}
		log(" leaves for the day.");
		
	}

	

	

}
