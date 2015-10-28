package codebase;

public class StartWorkTimer extends Thread{
	private Employee e;
	private long delay;
	
	public StartWorkTimer(Employee e, long delayMillis) {
		this.e = e;
		this.delay = delayMillis;
	}
	
	@Override
	public void run() {
		try {
			sleep(delay);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		((Thread) this.e).start();
	}

}
