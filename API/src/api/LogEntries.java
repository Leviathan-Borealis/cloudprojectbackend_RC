package api;

import runners.MessageRunner;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is the handler for incoming http requests
 */

@Path("LogEntries")
public class LogEntries {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllLogEntries(){
        System.out.println("Message [GET] was handled");
        return Response.ok("getAllLogEntries", MediaType.TEXT_PLAIN_TYPE).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{entryId_from}/{entryId_to}")
    public Response getLogEntriesBetween(@PathParam("entryId_from") long entryId_from, @PathParam("entryId_to") long entryId_to){
        System.out.println("Message [" + entryId_from + "] was handled");
        return Response.ok("getLogEntriesBetween", MediaType.TEXT_PLAIN_TYPE).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLogEntry(String aLogEntry){
        System.out.println("Message [" + aLogEntry + "] was received");
        MessageRunner messageRunner = new MessageRunner();

        boolean[] success = {false};

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                success[0] = messageRunner.handleTextMessage(aLogEntry);
            }
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(success[0]){
            System.out.println("Message [" + aLogEntry + "] was handled");
            return Response.ok("addLogEntry", MediaType.TEXT_PLAIN_TYPE).build();
        }
        System.out.println("Message [" + aLogEntry + "] was NOT handled");
        return Response.ok("addLogEntry", MediaType.TEXT_PLAIN_TYPE).build();
    }
}
