package com.webService;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

//import model.AccessManager;

import dto.Game;
import dto.Player;
import model.AccessManager;

@Path("/playerService")
public class PlayerService {
	@GET
	@Path("/players")
	@Produces("application/json")
	public String games() {
		String players = null;
		ArrayList<Player> courseList = new ArrayList<Player>();
		try {
			courseList = new AccessManager().getPlayers();
			Gson gson = new Gson();
			players = gson.toJson(courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
}
