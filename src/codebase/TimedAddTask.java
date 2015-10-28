package codebase;

import java.util.Timer;
import java.util.TimerTask;

public class TimedAddTask {
    Timer timer;
    Employee employee;
    Task task;

    public TimedAddTask(Employee e, Task t, long delayMillis) {
        timer = new Timer();
        employee = e;
        task = t;
        timer.schedule(new AddTask(), delayMillis);
	}

    class AddTask extends TimerTask {
        public void run() {
        	employee.addTask(task);
            timer.cancel(); //Terminate the timer thread
        }
    }
}