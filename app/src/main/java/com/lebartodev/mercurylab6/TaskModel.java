package com.lebartodev.mercurylab6;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Александр on 24.11.2016.
 */

public class TaskModel {
    private Calendar taskDate;
    private String description;
    private String title;

    public TaskModel(String description, Calendar taskDate, String title) {
        this.description = description;
        this.taskDate = taskDate;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Calendar taskDate) {
        this.taskDate = taskDate;
    }
}
