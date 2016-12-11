package com.restservice;

import com.restservice.meetings.Meeting;
import com.restservice.meetings.MeetingsManager;
import com.restservice.participants.ParticipantsManager;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.QueryParam;

@Singleton
@Path("/meetings")
public class MeetingsService {
    MeetingsManager meetingsManager;
    ParticipantsManager participantsManager;

    public MeetingsService() {
        meetingsManager = new MeetingsManager();
        participantsManager = new ParticipantsManager();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json;charset=UTF-8")
    public List<Meeting> getMeetings() {
        ArrayList<Meeting> meetings = meetingsManager.getMeetings();

        return meetings;
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/name")
    @Produces("application/json;charset=UTF-8")
    public List<Meeting> findMeetings(@QueryParam("name") String name) {
        return meetingsManager.findMeeting(name);
    }

    @RolesAllowed("ADMIN")
    @POST
    @Path("/participants")
    @Consumes("application/json;charset=UTF-8")
    public Response changeMeetingParticipants(Meeting jsonMeeting) {
        meetingsManager.changeMeetingParticipants(jsonMeeting);
        String out = "Meeting '" + jsonMeeting.getName() + "' was changed\n";

        return Response.status(200).entity(out).build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @Path("/send-meeting")
    @Consumes("application/json;charset=UTF-8")
    public Response putOneMeeting(Meeting jsonMeeting) {
        meetingsManager.addMeeting(jsonMeeting);
        ArrayList<Meeting> meetings = meetingsManager.getMeetings();
        String out = "Meeting '" + jsonMeeting.getName() + "' was added!\n";

        return Response.status(200).entity(out).build();
    }
}