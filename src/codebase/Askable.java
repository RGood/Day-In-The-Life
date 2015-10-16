package codebase;

import java.util.concurrent.BlockingQueue;

public interface Askable {
	BlockingQueue<Employee> questions = null;
	
	public void answerQuestion(Answerable e);
}
