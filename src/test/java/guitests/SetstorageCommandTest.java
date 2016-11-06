package guitests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.SetstorageCommand;

//@@author A0158963M
public class SetstorageCommandTest extends ToDoListGuiTest {

	@Test
	public void setstorageSuccess() {
		commandBox.runCommand("setstorage NewData");
		assertResultMessage(SetstorageCommand.MESSAGE_SUCCESS);
	}
	
	@Test
	public void setstorageInvalidPath() {
		commandBox.runCommand("setstorage NewData*");
		assertResultMessage(SetstorageCommand.MESSAGE_Invalid_Path);
	}
	
	@Test
	public void setstorageInvalidCommand() {
		commandBox.runCommand("setstorageNewData");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

}
