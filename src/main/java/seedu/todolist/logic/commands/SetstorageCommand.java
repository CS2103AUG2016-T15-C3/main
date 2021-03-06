package seedu.todolist.logic.commands;
import java.io.File;
//@@author A0158963M 
/**
 * Sets the location of the storage file. 
 */
public class SetstorageCommand extends Command{

	public static final String COMMAND_WORD = "setstorage";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the location of the storage file. \n"
			+ "Example: " + COMMAND_WORD + " C://user/documents/todolist";

	public static final String MESSAGE_SUCCESS = "Successfully set the storage location!";

	public static final String MESSAGE_Invalid_Path = "Given location path is invalid.";

	private final String filepath;
    
    public SetstorageCommand(String filepath) {
    	this.filepath = filepath;
    }
    
   
    @Override
	public CommandResult execute() {
    	File newfile = new File(filepath);
    	if(newfile.isDirectory()){ //new filepath is correct
	    	File file = new File(storage.getToDoListFilePath()); // delete original file
	     	if(file.exists()){
	     		file.delete();
	     	}
			storage.setFilePath(filepath);
	    	return new CommandResult(MESSAGE_SUCCESS);
    	}
    	else
    		return new CommandResult(MESSAGE_Invalid_Path);
	}
	

}