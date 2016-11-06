package seedu.todolist.logic;

import javafx.collections.ObservableList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.logic.parser.CommandParser;
import seedu.todolist.model.Model;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandParser parser;
    private final Storage storage;
    
    //@@author A0158963M
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new CommandParser();
    }
    
    
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setStorage(storage);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredAllTaskList() {
        return model.getFilteredAllTaskList();
    }
    
    //@@author A0138601M
    @Override
    public ObservableList<ReadOnlyTask> getFilteredCompleteTaskList() {
        return model.getFilteredCompleteTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return model.getFilteredIncompleteTaskList();
    }
    
    @Override
    public void setCurrentTab(String tab) {
        model.setCurrentTab(tab);
    }
}
