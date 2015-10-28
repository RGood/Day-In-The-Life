package codebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Clock clock = Clock.getClock(); // initialize clock
		
		// all employees
		Employee[] employees = new Employee[13];
		
		// the manager
		employees[0] = new Manager();
		((Thread) employees[0]).start(); // manager starts right at 8

		Random random = new Random();
		
		for(int teamNumber=0; teamNumber < 3; teamNumber++) {
			for (int memberNumber=1; memberNumber < 5; memberNumber++) {
				int employeeId = teamNumber*4+memberNumber;
				if (memberNumber == 1) {
					employees[employeeId] = 
							new TeamLead(teamNumber+1, (Manager)employees[0]);
				}
				else {
					employees[employeeId] = 
							new Developer(teamNumber+1, memberNumber, (TeamLead)employees[teamNumber*4+1]);
				}
			}
			
			// add tasks
			for (int memberNumber=1; memberNumber < 5; memberNumber++) {
				int employeeId = teamNumber*4+memberNumber;
				
				// add task to start work
				double startTime = 300*random.nextDouble();
				new StartWorkTimer(employees[employeeId], (long)(startTime)).start();
				
				// add task to go to lunch
				// assume everybody goes to lunch around 12 - 1
				new TimedAddTask(employees[employeeId], Task.Lunch, (long) (2400+random.nextDouble()*300));
				
				// add question tasks
				// assume everyone will ask around 5 questions at most
				int maxQuestions = random.nextInt(2);
				for(int q=0; q<maxQuestions; q++) {
					new TimedAddTask(employees[employeeId], Task.Question, (long) (startTime + 5100*random.nextDouble()));
				}
				
				// assume startTime + 8 hours 30 minutes will put people between 4:30 and 5:00
				if (memberNumber == 1) {
					// add task to leave work
					new LeaveWorkTimer(employees[employeeId], Task.Leave, (long)(startTime+5100+random.nextDouble()*300), 
							Arrays.asList(employees[employeeId+1],employees[employeeId+2], employees[employeeId+3]));
				}
				else {
					// add task to leave work
					new LeaveWorkTimer(employees[employeeId], Task.Leave, (long)(startTime+5100+random.nextDouble()*300));
				}
			}
		}
		
		// when the manager should lunch
		new TimedAddTask(employees[0], Task.Lunch, (long) (2400));
		
		// when the manager should leave
		// make a list of all employees (excluding the manager)
		ArrayList<Employee> employeeList = new ArrayList<Employee>(Arrays.asList(employees));
		employeeList.remove(0);
		new LeaveWorkTimer(employees[0], Task.Leave, 5400, employeeList);
				
		ConferenceRoom meeting = new ConferenceRoom();
		meeting.setRoomMeeting(150, 4);
		for (int employeeId : Arrays.asList(0, 1, 5, 9)) {
			meeting.addEmployee(employees[employeeId]);
		}
		
		for(int teamNumber=0; teamNumber<3; teamNumber++) {
			meeting.setRoomMeeting(150, 4);
			for (int employeeId : Arrays.asList(1+teamNumber*4, 2+teamNumber*4, 3+teamNumber*4, 4+teamNumber*4)) {
				meeting.addEmployee(employees[employeeId]);
			}
		}
		
	}

}
