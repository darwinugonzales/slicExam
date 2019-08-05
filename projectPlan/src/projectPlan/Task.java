package projectPlan;

import java.util.Date;
import java.util.List;

public class Task {
	
	String taskId;
	String startDate;
	String endDate;
	int duration;
	String taskName;
	List<Task> dependency;
}
