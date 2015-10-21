package codebase;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TeamLead extends Thread implements Answerable, Askable, Employee{
	private BlockingQueue<Answerable> questions;
	private long lunchTime;
	private long lunchLength;
	private String name;
	private Askable manager;
	
	public TeamLead(String n, Askable l){
		Random x = new Random();
		lunchTime = (long)(x.nextDouble() * 8 * 60 * 10);
		lunchLength = (long)(x.nextDouble() * 30 * 10) + 30;
		name = n;
		manager = l;
		questions = new ArrayBlockingQueue<Answerable>(1);
	}

	enum Task {
		Questions,
		Meeting,
		Lunch
	}
	
	@Override
	public void goToMeeting(long minutes) {
		try {
			sleep(minutes * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Task getTask(){
		if(Clock.getClock().curTime() < lunchTime){
			return Task.Questions;
		}else if(Clock.getClock().curTime() < lunchTime + lunchLength){
			return Task.Lunch;
		}else if(Clock.getClock().curTime() < 8 * 60 * 10){
			return Task.Questions;
		}else if(Clock.getClock().curTime() < 8.5 * 60 * 10){
			return Task.Meeting;
		}else{
			return Task.Questions;
		}
	}
	
	public void askQuestion() {
		manager.question(this);
	}

	@Override
	public void question(Answerable e) {
		this.questions.add(e);
	}
	
	public void run(){
		long departure = Clock.getClock().curTime() + (60 * 8 * 10) + lunchLength;
		
		//Logic for manager meeting and team standup here
		
		while(Clock.getClock().curTime() < departure){
			switch(getTask()){
				case Questions: 
					//Logic for maybe asking questions
					askQuestion();
					break;
				case Lunch: 
					try {
						sleep(lunchLength);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					break;
				case Meeting: 
					try {
						this.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					break;
			}
		}
		System.out.println("Team lead" + name + " leaves for the day.");
	}
	
}
