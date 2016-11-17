package restservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.spi.resource.Singleton;
import restservice.meetings.Meeting;
import restservice.meetings.MeetingsManager;
import restservice.participants.ParticipantsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

@Singleton
@Path("/meetings")
public class Service {
    MeetingsManager meetingsManager;
    ParticipantsManager participantsManager;

    public Service() {
        meetingsManager = new MeetingsManager();
        participantsManager = new ParticipantsManager();
    }

    @GET
    @Produces("application/json")
    public Response getMeetings() {
        ArrayList<Meeting> meetings = meetingsManager.getMeetings();
        ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            if (meetings.size() > 0) {
                result = mapper.writeValueAsString(meetings);
            } else {
                result = "empty now";
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return Response.status(200).entity(result).build();
    }



    @PUT//!!!!!!!!
    @Path("/{name}")//@Consumes(MediaType.APPLICATION_JSON)
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