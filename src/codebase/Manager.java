package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Manager extends Thread implements Askable, Employee{
	private String name;
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Answerable> asker;
	
	public Manager(String n){
		name = n;
		
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
	
	//Main running method
	public void run(){
		Random x = new Random();
		
		boolean running = true;
		while(running){
			switch(action.remove()){
			case Leave: //Break main loop
				running = false;
				break;
			case Question: //Answer a question
				question();
				break;
			case Lunch: //Go to lunch for 60 minutes
				try {
					sleep(60 * 10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case Meeting: //Go to meeting and wait
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Manager " + name + " leaves for the day.");
		
	}

	

	

}
