package projectPlan;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import projectPlan.Task;

public class ProjectPlan {
	static List<Task> tasksList = new ArrayList<Task>();
	static String startDate;
    static SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

	public static void main(String[] args) throws ParseException {
		
		
		System.out.print("Enter Start Date(mm/dd/yyyy): ");
		Scanner inDate = new Scanner(System.in);
        String inputDate = inDate.nextLine();
        Date date1 = formatter1.parse(inputDate);
        startDate = inputDate;
        String end = "";
        while (!end.equals("x")) {
        	end = menu();
        }
        System.out.println("Bye.");
	}
	
	public static String menu() throws ParseException {
		System.out.println("1 = Create new task | 2 = Show Project Plan | x = Exit");
		System.out.print("Enter menu: ");
		Scanner inMenu = new Scanner(System.in);
        String menu = inMenu.nextLine();
        if(menu.equals("1")) {
        	createTask();
    		System.out.println("Task created.");
        } else if(menu.equals("2")) {
        	showTask();
        }
		return menu;
	}
	
	public static void createTask() {
		Task task = new Task();

		System.out.print("Enter Task ID: ");
		Scanner inTaskId = new Scanner(System.in);
        String taskId = inTaskId.nextLine();
        task.taskId = taskId;

		System.out.print("Enter duration: ");
		Scanner inDuration = new Scanner(System.in);
        String duration = inDuration.nextLine();
        task.duration = Integer.parseInt(duration);

		System.out.print("Enter task description: ");
		Scanner inTaskName = new Scanner(System.in);
        String taskName = inTaskName.nextLine();
        task.taskName = taskName;
        
        String exit = "";
        task.dependency = new ArrayList<Task>();
        while(!exit.equals("x")) {
        	exit = addDependency();
        	if(!exit.equals("x")) {
        		Task dependency = new Task();
        		dependency.taskId = exit;
        		task.dependency.add(dependency);
        	}
        }
        
        tasksList.add(task);
	}
	
	public static String addDependency() {
		System.out.print("Enter dependency(x to exit): ");
		Scanner inDependency = new Scanner(System.in);
        String dependency = inDependency.nextLine();
		return dependency;
	}
	
	public static void showTask() throws ParseException {
		String endDate = "";
		for(int i = 0; i < tasksList.size(); i++) {
			if(tasksList.get(i).dependency.size() == 0) {
				tasksList.get(i).startDate = startDate;
				Calendar c = Calendar.getInstance();
				try {
				   c.setTime(formatter1.parse(startDate));
				} catch(ParseException e){
					e.printStackTrace();
				}
				c.add(Calendar.DAY_OF_MONTH, tasksList.get(i).duration);
				tasksList.get(i).endDate = formatter1.format(c.getTime());
			} else {
				String tmpDate = "";
				for(int j = 0; j < tasksList.get(i).dependency.size(); j++  ) {
					for(int k = 0; k < tasksList.size(); k++) {
						if(tasksList.get(i).dependency.get(j).taskId.equals(tasksList.get(k).taskId)) {
							tmpDate = tasksList.get(k).endDate;
						}
					}
				}
				tasksList.get(i).startDate = tmpDate;
				Calendar c = Calendar.getInstance();
				try {
				   c.setTime(formatter1.parse(tmpDate));
				} catch(ParseException e){
					e.printStackTrace();
				}
				c.add(Calendar.DAY_OF_MONTH, tasksList.get(i).duration);
				tasksList.get(i).endDate = formatter1.format(c.getTime());
			}
		}

		endDate = tasksList.get(0).endDate;
		Date date1 = formatter1.parse(endDate);
		Date date2;
		// get last date
		for(int i = 0; i < tasksList.size(); i++) {
			date2 = formatter1.parse(tasksList.get(i).endDate);
			if(date1.compareTo(date2) < 0) {
				date1 = date2;
				endDate = tasksList.get(i).endDate;
			}
		}
		
		System.out.println("Target Start Date: " + startDate);
		System.out.println("Target Completion Date: " + endDate);
		for(int i = 0; i < tasksList.size(); i++) {
			System.out.println(tasksList.get(i).taskId + " " + tasksList.get(i).startDate + " " + tasksList.get(i).endDate + " " + tasksList.get(i).duration);
		}
	}
	
}
