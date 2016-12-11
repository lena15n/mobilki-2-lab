package com.restservice.meetings;

import com.restservice.participants.Participant;

import java.util.ArrayList;
import java.util.Date;

public class MeetingsManager {
    private static ArrayList<Meeting> meetings;

    public MeetingsManager() {
        meetings = new ArrayList<>();
        init();
    }

    public MeetingsManager(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public void deleteMeeting(String name, String description) {
        int index = -1;

        for (Meeting meeting : meetings) {
            if (meeting.getName().equals(name) && meeting.getDescription().equals(description)){
                index = meetings.indexOf(meeting);
            }
        }

        if (index != -1) {
            meetings.remove(index);
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

    public void changeMeetingParticipants(Meeting changedMeeting) {
        for (Meeting meeting : meetings) {
            if (meeting.getName().equals(changedMeeting.getName()) &&
                    meeting.getDescription().equals(changedMeeting.getDescription())) {
                meeting.setParticipants(changedMeeting.getParticipants());
            }
        }
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }

    private void init() {
        ArrayList<String> participants = new ArrayList<>();
        participants.add(Participant.GALYA_SMITH + ", doctor");
        participants.add(Participant.ARTEMII_LEBEDEV + ", designer");
        addMeeting(new Meeting("Meeting", "This is new meeting!", new Date(), new Date(), participants, Meeting.CRITICAL));
    }
}