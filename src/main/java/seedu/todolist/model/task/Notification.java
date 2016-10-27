package seedu.todolist.model.task;

import java.time.LocalDateTime;

/**
 * represents the notification for a task
 * @author A0146682X
 *
 */

public class Notification {

	private final Task task;
	private static LocalDateTime currentTime = LocalDateTime.now();
	private final String MESSAGE = " is due on ";
	
	public Notification(Task task, ){};

}
