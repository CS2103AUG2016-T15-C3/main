package seedu.task.testutil;

import seedu.todolist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Interval interval;
    private Location location;
    private Remarks remarks;

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRemarks(Remarks remarks) {
    	this.remarks = remarks;
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public Remarks getRemarks() {
    	return remarks;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        
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
