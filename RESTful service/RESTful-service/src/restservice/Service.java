package restservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.spi.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Singleton
@Path("/meetings")
public class Service {
   // @Context
   // @Inject
    MeetingsManager meetingsManager;

    public Service() {
        meetingsManager = new MeetingsManager();
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

        /* throws JSONException {
        JSONObject jsonObject = new JSONObject();
        MeetingsManager meetingsManager = new MeetingsManager();

        jsonObject.put(meetingsManager.getMeetings());

        String result = "" + jsonObject;*/
        return Response.status(200).entity(result).build();
    }

    @PUT
    @Path("/{param}")
    public Response putMeeting(@PathParam("param") String name) {
        //http://localhost:8080/sampel-glassfish-0.0.1-SNAPSHOT/crunchify/meetings/azaza
        meetingsManager.addMeeting(name);
        String output = "PUT: Jersey say : " + name + "\n meeting has created";
        return Response.status(200).entity(output).build();
    }


}