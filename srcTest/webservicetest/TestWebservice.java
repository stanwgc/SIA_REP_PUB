package webservicetest;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestWebservice {
	public Client client = Client.create();
	public final String restpath = "http://localhost:8080/JaxRSEducation/service/rest";
	
	
	//@Test
	public void testWebservice(){
		String url = restpath;
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		String result = response.getEntity(String.class);
		System.out.println("testRestServiceTest - Response from the Server: ");
		System.out.println(result);
		assert(response == null);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testWebservicePost(){
		String url = restpath + "/sendPerson/";
		WebResource webResource = client.resource(url);
		String inputData = getData();
		
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,inputData);
		
	
//		System.out.println("testRestServiceTest - Response from the Server: ");
//		System.out.println(response);
		assert(response != null);	
	}


	private String getData() {
		String data = "{\"name\":\"Müller\",\"vorname\":\"Hans\"}";
		return data;
	}
}
