package com.webService;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	public static String newPlayer(@FormParam("name") String name, 
			@FormParam("password") String password, 
			@FormParam("chips") int chips) throws Exception {
	
		new AccessManager().insertPlayer(name, password, chips);
		return "success";
	}
	
	@POST
	@Path("/login")
	@Produces("application/json")
	public static String newPlayer(@FormParam("name") String name, 
			@FormParam("password") String password) throws Exception {
	
		boolean result = new AccessManager().loginPlayer(name, password);
		
		if(result==false){
			return "Error";
		}
		else {
			return "success";
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
}
