package com.webService;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

//import model.AccessManager;

import dto.Game;
import model.AccessManager;

@Path("/gameService")
public class GameService {
	@GET
	@Path("/games")
	@Produces("application/json")
	public String games() {
		String courses = null;
		ArrayList<Game> courseList = new ArrayList<Game>();
		try {
			courseList = new AccessManager().getGames();
			Gson gson = new Gson();
			courses = gson.toJson(courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
}
