package service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

import business.Spielzug;
import webapp.DBFiller;

@Path("/rest")
public class RestExample {
	DBFiller db = new DBFiller();
	Spielzug sz = new Spielzug();
	int zI = 2;

	@GET
	@Path("test/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON() {
		return "{\"test\":\"test\"}";
	}

	@GET
	@Path("CreateGame/")
	@Produces(MediaType.APPLICATION_JSON)
	public String CreateGame() throws InstantiationException, IllegalAccessException, SQLException {
		System.out.println("Bin in RestExample");
		return null;
	}

	@POST
	@Path("Spielzug/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void create(String zug) throws JSONException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		db.conection(1);
		sz.ZugFormatter(zug);
		System.out.println("Füre mal das get Zuege aus");
		db.conection(0);
	}
	
	@GET
	@Path("getSpielzug/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpielzuege() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
		db.conection(1);
		String wow  = db.getZuege();
		db.conection(0);
		return wow;
	}
	
	@GET
	@Path("playerConnection/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getData(@Context HttpServletRequest request)
			throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException {
		String player;
		player = request.getSession().getId();
		System.out.println("Player One with Session ip: " + player + " connected");
		db.conection(1);
		db.fillAll();
		if (db.getSpieler() == 1) {
			zI = 1;
			System.out.println("Setze das pC Var auf 1");
			db.conection(0);
			return "{\"test\":\"1\"}";
		} else if (db.getSpieler() == 2) {
			zI = 2;
			System.out.println("Setze das pC Var auf 2");
			db.conection(0);
			return "{\"test\":\"2\"}";
		} else {
			zI = 0;
			System.out.println("Player anz 0");
			db.conection(0);
			return "{\"test\":\"0\"}";
		}
	}

	@GET
	@Path("playerCheck/")
	@Produces(MediaType.APPLICATION_JSON)
	public String playerCheck() {
		System.out.println("Bin in playerCheck WAU " + zI);
		return "{\"test\":\"" + zI + "\"}";
	}
	

}
