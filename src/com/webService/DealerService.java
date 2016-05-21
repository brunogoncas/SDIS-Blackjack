package com.webService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import logic.MessagesEncrypter;
import model.AccessManager;

@Path("/dealerService")
public class DealerService {
	
	@POST
	@Path("/addCards/{namedealer}/{numberCards}")
	@Produces("application/json")
	public Response AddCardsDealer(@PathParam("namedealer") String namedealer,
			@PathParam("numberCards") String numberCards) throws Exception {
		
		String n = MessagesEncrypter.decrypt(namedealer);
		int nC = Integer.parseInt(MessagesEncrypter.decrypt(numberCards));
	
		boolean result = new AccessManager().AddCardsDealer(n, nC);
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel dar cartas ao dealer.").build();
		}
		
		else {
			return Response.ok("Cartas dadas com sucesso ao dealer!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/getCards/{name}")
	@Produces("application/json")
	public String getCards(@PathParam("name") String name) throws Exception {
		JSONArray cards = new AccessManager().getCardsDealer(name);
		return cards.toString();
	}
	
	@GET
	@Path("/getDPoints/{idRoom}")
	public int getPoints(@PathParam("idRoom") int idRoom) throws Exception {
		System.out.println("NAMEEEEEEEEEE" + idRoom);
		int points = new AccessManager().getPointsDealer(idRoom);
		return points;
	}
	

	@GET
	@Path("/getCardD/{idRoom}")
	@Produces("application/json")
	public String getCards(@PathParam("idRoom") int idRoom) throws Exception {
		JSONArray cards = new AccessManager().getCardDealer(idRoom);
		return cards.toString();
	}

}

