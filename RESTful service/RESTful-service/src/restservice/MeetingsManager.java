package restservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;

public class MeetingsManager {
    private static ArrayList<Meeting> meetings;

    public MeetingsManager(){
        meetings = new ArrayList<>();
    }

    public MeetingsManager(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }

    public static void addMeeting(){
        ArrayList<String> participants = new ArrayList<>();
        participants.add("part1");
        participants.add("part2");
        meetings.add(new Meeting("meet", "new meet", new Date(), new Date(), participants, "HIGHT"));
    }

    public static void addMeeting(String name){
        ArrayList<String> participants = new ArrayList<>();
        participants.add("part1");
        participants.add("part2");
        meetings.add(new Meeting(name, "new meet", new Date(), new Date(), participants, "HIGHT"));
    }

    public static void editMeeting(Meeting newMeeting){

    }

    public static void deleteMeeting(Meeting meeting){

    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<Meeting> newMeetings) {
        meetings = newMeetings;
    }
}