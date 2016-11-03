package seedu.todolist.model;

import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;

import java.time.DateTimeException;
import java.util.EmptyStackException;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();
    
    /** Reverts the previous state of the ToDoList */
    void undoToDoList() throws EmptyStackException;
    
    /** Marks the given task(s) as done. */
    void markTask(ReadOnlyTask... tasks) throws UniqueTaskList.TaskNotFoundException;

    /** Deletes the given task(s). */
    void deleteTask(ReadOnlyTask... tasks) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Sets notification for the given task */
    void notifyTask(ReadOnlyTask task, int bufferTime) throws UniqueTaskList.TaskNotFoundException;
    
    /** Sends notifications for tasks */
    void sendNotifications() throws IllegalValueException;
    
    /** Edits the given task */
    void editTask(ReadOnlyTask task, Task replacement) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredAllTaskList();
    
    /** Returns the filtered complete task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredCompleteTaskList();
    
    /** Returns the filtered incomplete task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords, String findType);
    
    /** Updates the filter of the filtered task list to filter by the given date filter */
    void updateFilteredTaskList(String dateFilter) throws DateTimeException;
    
    /** Set the current tab the user is looking at */
    void setCurrentTab(String tab);
    
    /** Get the current tab the user is looking at */
    String getCurrentTab();

}
