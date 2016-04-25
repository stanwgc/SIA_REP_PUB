package service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/rest")
public class RestExample {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON(){
		
		
		
		return "{\"test\":\"test\"}";
	}
	
	

}
