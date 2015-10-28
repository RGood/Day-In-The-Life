package codebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LeaveWorkTimer {
    Timer timer;
    Employee employee;
    Task task;
    List<Employee> waiting;

    public LeaveWorkTimer(Employee e, Task t, long delayMillis, List<Employee> waiting) {
        timer = new Timer();
        employee = e;
        task = t;
        timer.schedule(new AddTask(), delayMillis);
        this.waiting = waiting;
	}
    
    public LeaveWorkTimer(Employee e, Task t, long delayMillis) {
        timer = new Timer();
        employee = e;
        task = t;
        timer.schedule(new AddTask(), delayMillis);
        this.waiting = null;
	}

    class AddTask extends TimerTask {
        public void run() {
        	boolean cantLeave = (waiting != null);
        	while(cantLeave) {
        		cantLeave = false;
        		for (Employee e : waiting) {
        			cantLeave = cantLeave || e.atWork();
        		}
        	}
        	employee.addTask(task);
            timer.cancel(); //Terminate the timer thread
        }
    }
}