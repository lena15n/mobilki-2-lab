package com.restservice.meetings;

import com.restservice.participants.Participant;

import java.util.ArrayList;
import java.util.Date;

public class MeetingsManager {
    private static ArrayList<Meeting> meetings;

    public MeetingsManager(){
        meetings = new ArrayList<>();
        init();
    }

    public MeetingsManager(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }

    public void addMeeting(Meeting meeting){
        meetings.add(meeting);
    }

    public void deleteMeeting(Meeting meeting){
        if (meetings.size() != 0) {
            meetings.remove(meeting);
        }
    }

    public ArrayList<Meeting> findMeeting(String name) {
        ArrayList<Meeting> foundMeetings = new ArrayList<>();

        for (Meeting meeting : meetings) {
            if (meeting.getName().contains(name)) {
                foundMeetings.add(meeting);
            }
        }

        if (foundMeetings.size() > 0) {
            return foundMeetings;
        }

        return null;
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }

    private void init() {
        ArrayList<String> participants = new ArrayList<>();
        participants.add(Participant.GALYA_SMITH);
        participants.add(Participant.ARTEMII_LEBEDEV);
        addMeeting(new Meeting("Meeting", "This is new meeting!", new Date(), new Date(), participants, Meeting.CRITICAL));
    }
}