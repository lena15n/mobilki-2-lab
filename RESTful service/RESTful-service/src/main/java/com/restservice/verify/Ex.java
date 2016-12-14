package com.restservice.verify;

import com.restservice.meetings.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Ex {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Yurii");
        temp.add("Mary");
        temp.add("Zing");
        calendar.set(2016, Calendar.DECEMBER, 11);
        Date date1 = calendar.getTime();
        calendar.set(2016, Calendar.DECEMBER, 15);
        Date date2 = calendar.getTime();
        Meeting meeting1 = new Meeting("Hello f!", "Destfds df", date1, date2, temp, "Critical");
        ArrayList<String> temp2 = new ArrayList<>();
        temp2.add("Yurii");
        temp2.add("Zing");
        temp2.add("Mary");

        Meeting meeting2 = new Meeting("Hello f!", "Destfds df", date1, date2, temp2, "Critical");

        boolean eq = false;
        int hash1, hash2;

        eq = meeting1.equals(meeting2);
        hash1 = meeting1.hashCode();
        hash2 = meeting2.hashCode();
    }
}
