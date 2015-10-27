package codebase;

public class StartWorkTimer extends Thread{
	private Thread e;
	private long delay;
	
	public StartWorkTimer(Thread e, long delayMillis) {
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
		this.e.start();
	}

}
