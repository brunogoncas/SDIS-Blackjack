package com.webService;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.Gson;

import dto.Player;
import model.AccessManager;

public class DealerService {
	
	@Path("/dealerService")
	public class PlayerService {
		
		@POST
		@Path("/addCards/{nameRoom}/{namedealer}")
		@Produces("application/json")
		public Response AddCardsDealer(@PathParam("namedealer") String namedealer, 
				@PathParam("nameRoom") String nameRoom) throws Exception {
		
			boolean result = new AccessManager().AddCardsDealer(namedealer, nameRoom);
			if(result==false){
				return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel dar cartas ao dealer.").build();
			}
			
			else {
				return Response.ok("Cartas dadas com sucesso ao dealer!", MediaType.APPLICATION_JSON).build();
			}
		}
		
		@GET
		@Path("/getCards/{nameRoom}/{name}")
		@Produces("application/json")
		public String getCards(@PathParam("name") String name, @PathParam("nameRoom") String nameRoom) throws Exception {
			
			JSONArray cards = new AccessManager().getCardsDealer(name, nameRoom);
			return cards.toString();
		}
	}
}
