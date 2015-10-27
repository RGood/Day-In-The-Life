package codebase;

import java.util.Random;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// all employees
		Employee[] employees = new Employee[13];
		
		// the manager
		employees[0] = new Manager();
		((Thread) employees[0]).start(); // manager starts right at 8

		Random startTime = new Random();
		
		for(int teamNumber=0; teamNumber < 3; teamNumber++) {
			for (int memberNumber=1; memberNumber < 5; memberNumber++) {
				int employeeId = teamNumber*4+memberNumber;
				if (memberNumber == 1) {
					employees[employeeId] = 
							new TeamLead(teamNumber+1, (Manager)employees[0]);
					new StartWorkTimer((Thread)employees[employeeId], (long)(300*startTime.nextDouble())).start();

				}
				else {
					employees[employeeId] = 
							new Developer(teamNumber+1, memberNumber, (TeamLead)employees[teamNumber*4+1]);
					new StartWorkTimer((Thread)employees[employeeId], (long)(300*startTime.nextDouble())).start();
					
				}
			}
		}
		
		// employees are in the system
		
		
		
	}

}
