package test.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.kie.kogito.event.avro.AvroIO;

@Path("v1")
@ApplicationScoped
public class TravellerResource {
    @Inject
    @Channel("travellers-out")
    Emitter<byte[]> emitter;


    @Path("emit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response produceCallbackStateTimeoutsEvent(Traveller traveller) {
        try {
            AvroIO utils = new AvroIO();
            emitter.send(utils.writeObject(traveller));
            return Response.ok("{}").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

}
