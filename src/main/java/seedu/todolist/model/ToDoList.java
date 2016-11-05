package seedu.todolist.model;

import javafx.collections.ObservableList;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Status;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public ToDoList() {}

    /**
     * Tasks are copied into this to-do list
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this to-do list
     */
    public ToDoList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyToDoList getEmptyToDoList() {
        return new ToDoList();
    }

//// list overwrite operations

    public ObservableList<Task> getAllTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Task> getIncompleteTasks() {
        return tasks.getFilteredTaskList(Status.Type.Incomplete.toString());
    }
    
    public ObservableList<Task> getCompletedTasks() {
        return tasks.getFilteredTaskList(Status.Type.Complete.toString());
    }
    
    public ObservableList<Task> getOverdueTasks() {
        return tasks.getFilteredTaskList(Status.Type.Overdue.toString());
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the to-do list.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
    }
    
    //@@author A0146682X
    /**
     * Edits a task in the to-do list
     */
    public boolean editTask(ReadOnlyTask key, Task replacement) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.edit(key, replacement)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    //@author
    
    public boolean markTask(ReadOnlyTask... keys) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.mark(keys)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public boolean removeTask(ReadOnlyTask... keys) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(keys)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToDoList // instanceof handles nulls
                && this.tasks.equals(((ToDoList) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
