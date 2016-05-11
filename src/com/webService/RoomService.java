package com.webService;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.Room;
import model.AccessManager;

@Path("/roomService")
public class RoomService {
	
	@GET
	@Path("/room")
	@Produces("application/json")
	public String rooms() {
		String rooms = null;
		ArrayList<Room> roomsList = new ArrayList<Room>();
		try {
			roomsList = new AccessManager().getRooms();
			Gson gson = new Gson();
			rooms = gson.toJson(roomsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}
	
	@GET
	@Path("/room/{idRoom}/player")
	public int PlayerByRoom(@PathParam("idRoom") int idRoom) {
		int players = 0;
		String id = String.valueOf(idRoom);
		try {
			players = new AccessManager().getPlayersByRoom(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
		
	@POST
	@Path("/room")
	@Produces("application/json")
	public static Response newRoom(@FormParam("name") String name) throws Exception {
	
		boolean result = new AccessManager().insertRoom(name);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed").build();
		}
		
		else {
			return Response.ok("Room Creation Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/room/{idRoom}/player/{name}")
	@Produces("application/json")
	public static Response addPlayerRoom(@PathParam("name") String Player,
			@PathParam("idRoom") int idRoom) throws Exception {
	
		boolean result = new AccessManager().AddPlayerInRoom(Player, idRoom);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed").build();
		}
		
		else {
			return Response.ok("Room Creation Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
}
