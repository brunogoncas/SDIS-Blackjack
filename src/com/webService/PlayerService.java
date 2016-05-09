package com.webService;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
			@FormParam("chips") int chips) throws Exception {
	
		boolean result = new AccessManager().insertPlayer(name, password, chips);
		
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
	public static Response  newPlayer(@FormParam("name") String name, 
			@FormParam("password") String password) throws Exception {
	
		boolean result = new AccessManager().loginPlayer(name, password);
		
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Login Failed for: " + name).build();
		}
		
		else {
			return Response.ok("Login Successful", MediaType.APPLICATION_JSON).build();
		}
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
			@FormParam("addChips") int addChips) throws Exception {
	
		new AccessManager().AddChips(name, addChips);
		return "Sucess";
	}
	
	@POST
	@Path("/removeChips")
	@Produces("application/json")
	public static Response RemoveChips(@FormParam("name") String name, 
			@FormParam("removeChips") int removeChips) throws Exception {
	
		boolean result = new AccessManager().RemoveChips(name, removeChips);
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Não têm saldo suficiente para efetuar o levantamento de "+removeChips+" chips.").build();
		}
		
		else {
			return Response.ok("Levantamento efetuado com sucesso!", MediaType.APPLICATION_JSON).build();
		}
	}
}
