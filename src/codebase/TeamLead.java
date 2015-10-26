package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TeamLead extends Thread implements Answerable, Askable, Employee{
	private String name;
	private Askable manager;
	
	private BlockingQueue<Task> action;
	private BlockingQueue<Task> answers;
	private BlockingQueue<Answerable> asker;
	
	public TeamLead(String n, Askable l){
		name = n;
		manager = l;
		action = new ArrayBlockingQueue<Task>(1);
		answers = new ArrayBlockingQueue<Task>(1);
		asker = new ArrayBlockingQueue<Answerable>(1);
	}

	//Handles questions received
	@Override
	public void question() {
		Random x = new Random();
		if(x.nextBoolean()){
			asker.remove().answer(Task.Answer);
		}else{
			manager.answer(this);
			answers.remove();
			asker.remove().answer(Task.Answer);
		}
	}
	
	//Method for telling Team lead what to do
	@Override
	public void addTask(Task t) {
		action.add(t);
	}
	
	//Method for asking team lead a question
	@Override
	public void answer(Answerable a) {
		addTask(Task.Question);
		asker.add(a);
	}
	
	//Method for outside sources to respond to team lead questions
	@Override
	public void answer(Task t) {
		answers.add(t);
	}
	
	//Main run method
	public void run(){
		Random x = new Random();
		
		boolean running = true;
		while(running){
			switch(action.remove()){
			case Leave: //Break running loop
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
		
		System.out.println("Team lead" + name + " leaves for the day.");
	}
}