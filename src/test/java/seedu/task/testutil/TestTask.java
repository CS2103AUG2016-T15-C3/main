package seedu.task.testutil;

import seedu.todolist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {
    
    private static final String KEYWORD_ADD = "add ";
    private static final String KEYWORD_FROM = "from ";
    private static final String KEYWORD_TO = " to ";
    private static final String KEYWORD_BY = "by ";
    private static final String KEYWORD_AT = "at ";
    private static final String KEYWORD_REMARKS = "remarks ";
    
    private static final String WHITE_SPACE = " ";
    

    private Name name;
    private Interval interval;
    private Location location;
    private Remarks remarks;
    private Status status;

    public void setName(Name name) {
        this.name = name;
    }
    
    //@@author A0138601M
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    //@@author
    
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRemarks(Remarks remarks) {
    	this.remarks = remarks;
    }
    
    //@@author A0138601M
    public void setStatus(Status status) {
        this.status = status;
    }
    //@@author
    
    @Override
    public Name getName() {
        return name;
    }
    
    //@@author A0138601M
    @Override
    public Interval getInterval() {
        return interval;
    }
    //author

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public Remarks getRemarks() {
    	return remarks;
    }
    
    //@@author A0138601M
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(KEYWORD_ADD + this.getName().fullName + WHITE_SPACE);
        
        //append interval
        Interval interval = getInterval();
        if (interval.isDeadlineWithTime()) {
            sb.append(KEYWORD_BY + interval.getEndDate() + WHITE_SPACE + interval.getEndTime() + WHITE_SPACE);
        } else if (interval.isDeadlineWithoutTime()) {
            sb.append(KEYWORD_BY + interval.getEndDate() + WHITE_SPACE);
        } else {
            sb.append(KEYWORD_FROM + this.getInterval().getStartDate() + WHITE_SPACE + this.getInterval().getStartTime() 
                    + KEYWORD_TO + this.getInterval().getEndDate()+ WHITE_SPACE + this.getInterval().getEndTime() + WHITE_SPACE);
        }
        
        //append location
        if (this.getLocation() != null) {
            sb.append(KEYWORD_AT + this.getLocation() + WHITE_SPACE);
        }
        
        //append remarks
        if (this.getRemarks() != null) {
            sb.append(KEYWORD_REMARKS + this.getRemarks() + WHITE_SPACE);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(ReadOnlyTask task) {
        return this.interval.compareTo(task.getInterval());
    }
    
    //@@author
    public String getEditCommand(int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit " + index + " " + this.getName().fullName + " ");
        
        //append interval
        Interval interval = getInterval();
        if (interval.isDeadlineWithTime()) {
            sb.append("by " + interval.getEndDate() + " " + interval.getEndTime() + " ");
        }
        else if (interval.isDeadlineWithoutTime()) {
            sb.append("by " + interval.getEndDate() + " ");
        }
        else {
            sb.append("from " + this.getInterval().getStartDate() + " " + this.getInterval().getStartTime() 
                    + " to " + this.getInterval().getEndDate()+ " " + this.getInterval().getEndTime() + " ");
        }
        
        //append location
        if (this.getLocation() != null) {
            sb.append("at " + this.getLocation() + " ");
        }
        
        //append remarks
        if (this.getRemarks() != null) {
            sb.append("remarks " + this.getRemarks());
        }
        return sb.toString();
    }
}
