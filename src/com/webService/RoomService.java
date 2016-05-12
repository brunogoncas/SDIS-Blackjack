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
	@Path("/room/{name}/player")
	public int PlayerByRoom(@PathParam("name") String nameRoom) {
		int players = 0;
		//String id = String.valueOf(idRoom);
		try {
			players = new AccessManager().getPlayersByRoom(nameRoom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
		
	@POST
	@Path("/room")
	@Produces("application/json")
	public static Response newRoom(@FormParam("roomname") String roomname, @FormParam("dealername") String dealername) throws Exception {
	
		boolean result = new AccessManager().insertRoom(roomname, dealername);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed").build();
		}
		
		else {
			return Response.ok("Adding user to Room Successful", MediaType.APPLICATION_JSON).build();
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
	
	@POST
	@Path("/room/{nameRoom}/state/{state}")
	@Produces("application/json")
	public static Response UpdateState(@PathParam("nameRoom") String nameRoom,
			@PathParam("state") String stateRoom) throws Exception {
	
		boolean result = new AccessManager().updateRoomState(nameRoom, stateRoom);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to update room state").build();
		}
		
		else {
			return Response.ok("Updated state successfully", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("room/{idRoom}")
	public Response getState(@PathParam("idRoom") int idRoom) throws Exception {
		
		String state = new AccessManager().getStateRoom(idRoom);
		
		if(state.isEmpty()){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed to get room state").build();
		}
		
		else {
			return Response.ok(state, MediaType.APPLICATION_JSON).build();
		}

	}
	
}
