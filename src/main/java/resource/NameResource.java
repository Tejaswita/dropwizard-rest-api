package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

@Path("/display")
public class NameResource {

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String display(@PathParam("name") String name) {
		if (StringUtils.isBlank(name)) {
			Response response = Response.status(Response.Status.BAD_REQUEST)
					.build();
			throw new WebApplicationException(response);
		}
		return name;
	}

}
