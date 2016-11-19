package com.restservice;

import com.restservice.meetings.Meeting;
import com.restservice.meetings.MeetingsManager;
import com.restservice.participants.ParticipantsManager;
import com.sun.jersey.spi.resource.Singleton;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
@Path("/meetings")
public class ServiceLL {
    MeetingsManager meetingsManager;
    ParticipantsManager participantsManager;

    public ServiceLL() {
        meetingsManager = new MeetingsManager();
        participantsManager = new ParticipantsManager();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    public List<Meeting> getMeetings() {
        ArrayList<Meeting> meetings = meetingsManager.getMeetings();

        return meetings;
    }

    @PUT
    @Path("/send-meeting")
    @Consumes("application/json")
    public Response putOneMeeting(Meeting jsonMeeting) {
        meetingsManager.addMeeting(jsonMeeting);
        String out = "Meeting '" + jsonMeeting.getName() + "' was added!\n";
        return Response.status(200).entity(out).build();
    }

    @PUT//!!!
    @Path("/{name}")
    public Response putMeeting(@PathParam("name") String name) {
        //http://localhost:8080/sampel-glassfish-0.0.1-SNAPSHOT/crunchify/meetings/azaza
        ArrayList<String> participants = new ArrayList<>();
        participants.add("part1");
        participants.add("part2");
        meetingsManager.addMeeting(new Meeting(name, "desc", new Date(), new Date(), participants, Meeting.PLAN));

        String output = "PUT: Jersey say : " + name + "\n meeting has created";
        return Response.status(200).entity(output).build();
    }
}