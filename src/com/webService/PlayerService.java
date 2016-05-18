package com.webService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.Gson;
import java.util.Base64;

//import model.AccessManager;

import dto.Player;
import logic.JWT;
import logic.MessagesEncrypter;
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

		String n = MessagesEncrypter.decrypt(name);
		//String p = MessagesEncrypter.decrypt(password);
		int c = Integer.parseInt(MessagesEncrypter.decrypt(chips));
	
		boolean result = new AccessManager().insertPlayer(n, password, c);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Register Failed for: " + name).build();
		}
		
		else {
			return Response.ok("Register Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/login")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public static Response  newPlayer(@FormParam("name") String name, 
			@FormParam("password") String password) throws Exception {
		
		String n = MessagesEncrypter.decrypt(name);
	
		boolean result = new AccessManager().loginPlayer(n, password);
		
        // Authenticate the user using the credentials provided
        //authenticate(name, password);

        // Issue a token for the user
        String token = issueToken(name);
		
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Login Failed for: " + name).build();
		}
		
		else {
			//return Response.ok("Login Successful", MediaType.APPLICATION_JSON)(token).build();~
			return Response.ok(token).build();
		}
	}

	//https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
    private static String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	
    	MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
    	
    	String token = null;
    			
    	try {
			token = messagesEncrypter.encrypt("blackjack_SDIS_" + username);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}   	
    	return token;
    }
	
	@PUT
	@Path("/editMoney")
	@Produces("application/json")
	public String newPlayer(@FormParam("name") String name, 
			@FormParam("chips") int chips) throws Exception {
	
		new AccessManager().updatePlayer(name, chips);
		return "Sucess";
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
	public static String AddChips(@FormParam("name") String name, 
			@FormParam("addChips") String addChips) throws Exception {
	
		String n = MessagesEncrypter.decrypt(name);
		int c = Integer.parseInt(MessagesEncrypter.decrypt(addChips));
		
		new AccessManager().AddChips(n, c);
		return "Sucess";
	}
	
	@POST
	@Path("/removeChips")
	@Produces("application/json")
	public static Response RemoveChips(@FormParam("name") String name, 
			@FormParam("removeChips") String removeChips) throws Exception {
		
		String n = MessagesEncrypter.decrypt(name);
		int c = Integer.parseInt(MessagesEncrypter.decrypt(removeChips));
	
		boolean result = new AccessManager().RemoveChips(n, c);
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
			@FormParam("addBet") String addBet) throws Exception {
		
		String n = MessagesEncrypter.decrypt(name);
		int c = Integer.parseInt(MessagesEncrypter.decrypt(addBet));
	
		Boolean result = new AccessManager().AddBet(n, c);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Não têm saldo suficiente para efetuar a aposta que pretende.").build();
		}
		
		else {
			return Response.ok("Bet efetuado com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/getCards/{idRoom}/{name}")
	@Produces("application/json")
	public String getCards(@PathParam("name") String name, @PathParam("idRoom") int idRoom) throws Exception {
		
		JSONArray cards = new AccessManager().getCards(name, idRoom);
		return cards.toString();
	}
	
	@POST
	@Path("/addCards/{idRoom}/{name}")
	@Produces("application/json")
	public static Response AddCards(@PathParam("name") String name, 
			@PathParam("idRoom") String idRoom) throws Exception {
		
		String n = MessagesEncrypter.decrypt(name);
		int r = Integer.parseInt(MessagesEncrypter.decrypt(idRoom));
	
		boolean result = new AccessManager().addCards(n, r);
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel dar cartas.").build();
		}
		
		else {
			return Response.ok("Cartas dadas com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/updateState/{idRoom}/{namePlayer}/{state}")
	@Produces("application/json")
	public static Response UpdateState(@PathParam("idRoom") String idRoom,
				@PathParam("namePlayer") String namePlayer,
			@PathParam("state") String stateRoom) throws Exception {
		
		int r = Integer.parseInt(MessagesEncrypter.decrypt(idRoom));
		String n = MessagesEncrypter.decrypt(namePlayer);
		String s = MessagesEncrypter.decrypt(stateRoom);
	
		boolean result = new AccessManager().updatePlayerState(r, s, n);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to update player state").build();
		}
		
		else {
			return Response.ok("Updated player state successfully", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/getState/{idRoom}/{namePlayer}")
	public Response getState(@PathParam("idRoom") int idRoom, @PathParam("namePlayer") String namePlayer) throws Exception {
		
		String state = new AccessManager().getStatePlayer(idRoom, namePlayer);
		
		if(state.isEmpty()){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to get player state").build();
		}
		else {
			return Response.ok(state, MediaType.APPLICATION_JSON).build();
		}
	}
	
}
