package com.restservice.meetings;

import com.restservice.participants.Participant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            if (meeting.getName().equals(name) && meeting.getDescription().equals(description)) {
                index = meetings.indexOf(meeting);
            }
        }

        if (index != -1) {
            meetings.remove(index);
        }
    }

    public ArrayList<Meeting> findMeeting(String name, String strDate) {
        ArrayList<Meeting> foundMeetings = new ArrayList<>();

        if (strDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");

            for (Meeting meeting : meetings) {
                Date startDate = meeting.getStartDate();
                Date endDate = meeting.getEndDate();
                Date date = null;
                try {
                    date = dateFormat.parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date != null && date.after(startDate) && date.before(endDate)) {
                    if (name != null) {
                        if (meeting.getName().contains(name) || meeting.getDescription().contains(name)) {
                            foundMeetings.add(meeting);
                        }
                    } else {
                        foundMeetings.add(meeting);
                    }
                }
            }
        } else {
            for (Meeting meeting : meetings) {
                if (meeting.getName().contains(name) || meeting.getDescription().contains(name)) {
                    foundMeetings.add(meeting);
                }
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