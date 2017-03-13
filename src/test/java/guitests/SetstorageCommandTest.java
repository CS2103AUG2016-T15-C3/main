package guitests;

import org.junit.Test;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.SetstorageCommand;

//@@author A0158963M 
public class SetstorageCommandTest extends ToDoListGuiTest{
	@Test
	public void setstorageValidPath() {
		commandBox.runCommand("setstorage NewData");
		assertResultMessage(SetstorageCommand.MESSAGE_SUCCESS);
	}
	
	
	@Test
	public void setstorageInvalidPath() {
		commandBox.runCommand("setstorage NewData*?:");
		assertResultMessage(SetstorageCommand.MESSAGE_Invalid_Path);
	}
	
	@Test
	public void setstorageInvalidCommand() {
		commandBox.runCommand("setstorageNewData");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}
}
