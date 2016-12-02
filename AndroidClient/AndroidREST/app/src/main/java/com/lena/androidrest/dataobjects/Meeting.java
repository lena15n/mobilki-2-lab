package com.lena.androidrest.dataobjects;

import java.util.ArrayList;
import java.util.Date;

public class Meeting {
    public static String CRITICAL = "Critical";
    public static String PLAN = "Plan";
    public static String NORMAL = "Normal";

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> participants;
    private String priority;

    public Meeting() {
        //for json in PUT method
    }

    public Meeting(String name, String description, Date startDate, Date endDate, ArrayList<String> participants,
                   String priority) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
