package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Developer extends Thread implements Employee, Answerable{
	private String name;
	private Askable lead;
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Task> answers;
	
	public Developer(String n, Askable l){
		name = n;
		lead = l;
		action = new ArrayBlockingQueue<Task>(1);
		answers = new ArrayBlockingQueue<Task>(1);
	}
	
	//Method to tell developer what to be doing
	public void addTask(Task t){
		action.add(t);
	}
	
	//Method for asking sending a question
	public void question() {
		lead.answer(this);
	}
	
	//Method for receiving question answer
	@Override
	public void answer(Task t) {
		// TODO Auto-generated method stub
		answers.add(t);
	}
	
	//Main run method
	public void run(){
		Random x = new Random();
		
		boolean running = true;
		while(running){
			switch(action.remove()){ //Get current to-do. Developing otherwise.
			case Leave: //Break the loop
				running = false;
				break;
			case Question: //Ask a question and wait for an answer
				question();
				answers.remove();
				break;
			case Lunch: //Go to lunch for 30-60 minutes
				try {
					sleep((long) (30 + (x.nextDouble() * 30)) * 10);
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
		System.out.println("Developer " + name + " leaves for the day.");
	}
}
