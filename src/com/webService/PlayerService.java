package com.webService;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.Gson;

//import model.AccessManager;

import dto.Player;
import model.AccessManager;

@Path("/playerService")
public class PlayerService {
	@GET
	@Path("/players")
	@Produces("application/json")
	public String games() {
		String players = null;
		ArrayList<Player> playersList = new ArrayList<Player>();
		try {
			playersList = new AccessManager().getPlayers();
			Gson gson = new Gson();
			players = gson.toJson(playersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
		
	@POST
	@Path("/register")
	@Produces("application/json")
	public static Response newPlayer(@FormParam("name") String name,
			@FormParam("password") String password, 
			@FormParam("chips") String chips) throws Exception {


		//String n = MessagesEncrypter.decrypt(name);
		//String p = MessagesEncrypter.decrypt(password);
		//int c = Integer.parseInt(MessagesEncrypter.decrypt(chips));
	
		boolean result = new AccessManager().insertPlayer(name, password, Integer.parseInt(chips));
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Register Failed for: " + name).build();
		}
		
		else {
			return Response.ok("Register Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/logout")
	@Produces("application/json")
	public static String  newPlayer(@FormParam("token") String token) throws Exception {
	
		MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
		String t = messagesEncrypter.decrypt(token);
		
		new AccessManager().logoutPlayer(t);
		
		return "Sucess";
	}
	
	@POST
	@Path("/login")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public static Response  newLogin(@FormParam("name") String name, 
			@FormParam("token") String token,
			@FormParam("password") String password) throws Exception {
		
		//String n = MessagesEncrypter.decrypt(name);
		//String t = MessagesEncrypter.decrypt(token);
		
		// Issue a token for the user
        //String token = issueToken(name);
		
		boolean result = new AccessManager().loginPlayer(n, password, t);
		
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Login Failed for: " + name).build();
		}
		
		else {
			//Globals.token = token;
			return Response.ok("Login Successful", MediaType.APPLICATION_JSON).build();
		}
	}
		
	@GET
	@Path("/getMoney")
	public int getMoney(@QueryParam("name") String name) throws Exception {
		
		int chips = new AccessManager().getMoneyPlayer(name);
		return chips;
	}
	
	@POST
	@Path("/addChips")
	@Produces("application/json")
	public static Response AddChips(@FormParam("name") String name, 
			@FormParam("token") String token,
			@FormParam("addChips") String addChips) throws Exception {
	
		MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
		String n = messagesEncrypter.decrypt(name);
		int c = Integer.parseInt(messagesEncrypter.decrypt(addChips));
		
		boolean tokenresult = new AccessManager().existToken(name, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO ADD CHIPS");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Add Chips Failed Because Token FALSE").build();
		}
		
		new AccessManager().AddChips(name, Integer.parseInt(addChips));
		
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Sucess add chips player!").build();
	}
	
	@GET
	@Path("/getUserRoom")
	public String getUserRoom(@QueryParam("username") String name) throws Exception {
	
		String roomName = new AccessManager().getUserRoom(name);
		return roomName;
	}
	
	@POST
	@Path("/editTimeout")
	@Produces("application/json")
	public String editTimeout(@FormParam("name") String name, 
			@FormParam("idRoom") String idRoom) throws Exception {
		
		MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
		String n = messagesEncrypter.decrypt(name);
		int id = Integer.parseInt(messagesEncrypter.decrypt(idRoom));
	
		new AccessManager().updateTPlayer(n, id);
		return "Sucess";
	}
	
	@POST
	@Path("/removeChips")
	@Produces("application/json")
	public static Response RemoveChips(@FormParam("name") String name, 
			@FormParam("token") String token,
			@FormParam("removeChips") String removeChips) throws Exception {

		
		//String n = MessagesEncrypter.decrypt(name);
		//int c = Integer.parseInt(MessagesEncrypter.decrypt(removeChips));
		
		boolean tokenresult = new AccessManager().existToken(name, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO REMOVE CHIPS");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Remove Chips Failed Because Token FALSE").build();
		}
		
		boolean result = new AccessManager().RemoveChips(name, Integer.parseInt(removeChips));
		
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Não têm saldo suficiente para efetuar o levantamento de "+removeChips+" chips.").build();
		}
		
		else {
			return Response.ok("Levantamento efetuado com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/addBet")
	@Produces("application/json")
	public static Response addBet(@FormParam("name") String name, 
			@FormParam("token") String token,
			@FormParam("addBet") String addBet) throws Exception {
		
		//String n = MessagesEncrypter.decrypt(name);
		//int c = Integer.parseInt(MessagesEncrypter.decrypt(addBet));
			
		boolean tokenresult = new AccessManager().existToken(name, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO ADD BET");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Add BET Failed Because Token FALSE").build();
		}
		
		Boolean result = new AccessManager().AddBet(name, Integer.parseInt(addBet)	);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Não tem saldo suficiente para efetuar a aposta que pretende.").build();
		}
		
		else {
			return Response.ok("Aposta efetuada com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/getCards/{idRoom}/{name}")
	@Produces("application/json")
	public String getCards(@PathParam("name") String name,
			@PathParam("idRoom") int idRoom) throws Exception {
				
		JSONArray cards = new AccessManager().getCards(name, idRoom);
		return cards.toString();
	}
	
	@GET
	@Path("/getTimeouts/{idRoom}/{namePlayer}")
	@Produces("application/json")
	public String getTimeouts(@PathParam("idRoom") int idRoom,
			@PathParam("namePlayer") String namePlayer) throws Exception {
		
		int timeouts = new AccessManager().getTimeouts(namePlayer,idRoom);
		return String.valueOf(timeouts);
	}
	
	@POST
	@Path("/addCards")
	@Produces("application/json")
	public static Response AddCards(@FormParam("name") String name, 
			@FormParam("token") String token,
			@FormParam("idRoom") String idRoom,
			@FormParam("numCards") String numCards ) throws Exception {
		
		/*String n = MessagesEncrypter.decrypt(name);
		int idroom = Integer.parseInt(MessagesEncrypter.decrypt(idRoom));
		int ncards = Integer.parseInt(MessagesEncrypter.decrypt(numCards));*/
		
		boolean tokenresult = new AccessManager().existToken(name, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO ADD CARDS");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Add Cards Failed Because Token FALSE").build();
		}
		
		boolean result = new AccessManager().addCards(name, Integer.parseInt(idRoom), Integer.parseInt(numCards));

		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel dar cartas.").build();
		}
		
		else {
			return Response.ok("Cartas dadas com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/updateState")
	@Produces("application/json")
	public static Response UpdateState(@FormParam("idRoom") String idRoom,
			@FormParam("namePlayer") String namePlayer,
			@FormParam("token") String token,
			@FormParam("state") String stateRoom) throws Exception {
		
		/*String n = MessagesEncrypter.decrypt(namePlayer);
		int idroom = Integer.parseInt(MessagesEncrypter.decrypt(idRoom));
		String sRoom = MessagesEncrypter.decrypt(stateRoom);*/
		
		boolean tokenresult = new AccessManager().existToken(namePlayer, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO Update State");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Add Cards Failed Because Token FALSE").build();
		}
		
		boolean result = new AccessManager().updatePlayerState(Integer.parseInt(idRoom), stateRoom, namePlayer);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to update player state").build();
		}
		
		else {
			return Response.ok("Updated player state successfully", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/getState/{idRoom}/{namePlayer}")
	public Response getState(@PathParam("idRoom") int idRoom,
			@PathParam("namePlayer") String namePlayer) throws Exception {
	
		String state = new AccessManager().getStatePlayer(idRoom, namePlayer);
		
		if(state == null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to get player state").build();
		}
		else {
			return Response.ok(state, MediaType.APPLICATION_JSON).build();
		}
	}
	
   @DELETE
   @Path("/removePlayer/{namePlayer}/{idRoom}/{token}")
   @Produces("application/json")
   public Response RemovePlayerRoom(@PathParam("namePlayer") String namePlayer,
       @PathParam("idRoom") int idRoom) throws Exception {
   
	      boolean result = new AccessManager().RemovePlayerRoom(namePlayer, idRoom);
	     if(result==false){
	       return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel remover o jogador da sala.").build();
	     }
	     
	     else {
	       return Response.ok("Jogador Removido da Sala!", MediaType.APPLICATION_JSON).build();
	      }
	    }
	
}
