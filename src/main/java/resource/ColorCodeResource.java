package resource;

import dao.ColorDao;
import model.Color;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

@Path("/color")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColorCodeResource {

    private ColorDao colorDao;

    public ColorCodeResource(ColorDao colorDao) {
        this.colorDao = colorDao;
    }

    @GET
    @Path("/{color}")
    public Response get(@PathParam("color") String rgbColor){
        if(!colorDao.getColor(rgbColor).equals(Optional.empty())) {
            return Response.ok().entity("#ffffff").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/add")
    public Response add(Color color) {
        String colorId = colorDao.add(color);
        URI location = URI.create("color/"+colorId);
        return Response.created(location).build();
    }
}
