package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.model.task.Location;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.Remarks;

//@@author A0138601M
public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //add one upcoming task
        TestTask taskToAdd = td.upcomingEvent;
        assertAddSuccess(taskToAdd, currentList);

        //add one overdue task
        taskToAdd = td.overdueDeadline;
        assertAddSuccess(taskToAdd, currentList);

        //add to empty list
        commandBox.runCommand("clear");
        currentList.clear();
        taskToAdd = td.eventWithLocation;
        assertAddSuccess(taskToAdd, currentList);
        
        //add task without name
        commandBox.runCommand("add");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS_EMPTY);

        //add task with invalid location
        commandBox.runCommand("add invalid location at !@#$");
        assertResultMessage(Location.MESSAGE_LOCATION_PARAMETER_CONSTRAINTS);
        
        //add task with invalid remarks
        commandBox.runCommand("add invalidRemarks remarks !@#$");
        assertResultMessage(Remarks.MESSAGE_REMARKS_PARAMETER_CONSTRAINTS);
        
        //invalid command
        commandBox.runCommand("adds invalidcommand");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs the add command to add new tasks and confirms the result is correct.
     * @param taskToAdd a new task to be added
     * @param currentList A copy of the current list of tasks (before addition).
     */
    private void assertAddSuccess(TestTask taskToAdd, TestTaskList currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
        confirmResult(taskToAdd, currentList);
    }
    
    /**
     * Check that the result after add command is as expected
     */
    private void confirmResult(TestTask taskToAdd, TestTaskList currentList) {
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        currentList.addTasksToList(taskToAdd);
        assertAllListMatching(currentList);
    }

}
