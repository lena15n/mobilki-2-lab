package com.restservice.meetings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public Meeting(){
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

    public void addParticipant(String participant) {
        if (!participants.contains(participant)) {
            participants.add(participant);
        }
    }

    public void removeParticipant(String participant) {
        if (participants.contains(participant)) {
            participants.remove(participant);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Meeting){
            Meeting meetingObj = (Meeting)obj;

            if (meetingObj.getName().equals(getName()) &&
                    meetingObj.getDescription().equals(getDescription()) &&
                    meetingObj.getStartDate().equals(getStartDate()) &&
                    meetingObj.getEndDate().equals(getEndDate()) &&
                    meetingObj.getPriority().equals(getPriority()) &&
                    meetingObj.getParticipants().size() == getParticipants().size()) {

                for (String participant : meetingObj.getParticipants()) {
                    if (!participants.contains(participant)){
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + participants.size();

        // (mb it's bad, but I need take into account only content, but not its sequence)
        int temp = 0;
        for (String participant : participants) {
            temp += participant.hashCode();
        }

        result = 31 * result + temp;

        return result;
    }
}
