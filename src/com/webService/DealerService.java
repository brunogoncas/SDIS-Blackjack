package com.webService;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.Player;
import model.AccessManager;

public class DealerService {
	
	@Path("/dealerService")
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
	}
}
