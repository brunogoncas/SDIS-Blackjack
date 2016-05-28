package com.webService;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.Player;
import dto.Room;
import logic.MessagesEncrypter;
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
	
	@GET
	@Path("/room/{idRoom}/number")
	public int PlayerByIDRoom(@PathParam("idRoom") int idRoom) {
		int players = 0;
		//String id = String.valueOf(idRoom);
		try {
			players = new AccessManager().getPlayersByIDRoom(idRoom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return players;
	}
		
	@POST
	@Path("/room")
	@Produces("application/json")
	public static Response newRoom(@FormParam("roomname") String roomname,
			@FormParam("dealername") String dealername,
			@FormParam("password") String password ) throws Exception {

		/*System.out.println("FDPPSAPDPSAP " + roomname + "  " + dealername);
		String roomn = messagesEncrypter.decrypt(roomname);
		String dealern = messagesEncrypter.decrypt(dealername);
		System.out.println("CRLLLLLLLLLLLLLLLLL " + roomn + "  " + dealern);*/
		boolean result = new AccessManager().insertRoom(roomname, dealername, password);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed").build();
		}
		
		else {
			return Response.ok("Adding user to Room Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/room/player")
	@Produces("application/json")
	public static Response addPlayerRoom(@FormParam("name") String Player,
			@FormParam("token") String token,
			@FormParam("idRoom") String idRoom) throws Exception {
		

		/*System.out.println("SIMMMM " + Player + "  " + idRoom);
		String player = MessagesEncrypter.decrypt(Player);
		int idroom = Integer.parseInt(MessagesEncrypter.decrypt(idRoom));
		System.out.println("NAOOOoooo " + player + "  " + idroom);*/
		
		boolean tokenresult = new AccessManager().existToken(Player, token);
		if(tokenresult==false) {
			System.out.println("ERRO NO ADDPLAERROOM");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed Because Token FALSE").build();
		}
		
		boolean result = new AccessManager().AddPlayerInRoom(Player, Integer.parseInt(idRoom));
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Room Creation Failed").build();
		}
		
		else {
			return Response.ok("Room Creation Successful", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/room/state")
	@Produces("application/json")
	public static Response UpdateState(@FormParam("nameRoom") String nameRoom,
			@FormParam("state") String stateRoom) throws Exception {
		
		MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
		String nR = messagesEncrypter.decrypt(nameRoom);
		String sR = messagesEncrypter.decrypt(stateRoom);
	
		boolean result = new AccessManager().updateRoomState(nR, sR);
		
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
	
	@GET
	@Path("/room/getPass/{idRoom}")
	@Produces("application/json")
	public static String getPassRoom(@PathParam("idRoom") int idRoom) throws Exception {

		String result = new AccessManager().getPass(idRoom);
		return result;
	}
	
	@GET
	@Path("room/id/{nameRoom}")
	public String getID(@PathParam("nameRoom") String nameRoom) throws Exception {
		
		String ID = new AccessManager().getIDroom(nameRoom);
		
		if(ID.isEmpty()){
			return "";
		}
		else {
			return ID;
		}
	}
	
	@GET
	@Path("/room/getPlayers/{nameRoom}")
	@Produces("application/json")
	public String GetPlayersRoom(@PathParam("nameRoom") String nameRoom) {
		String Players = null;
		ArrayList<Player> playerList = new ArrayList<Player>();
		try {
			playerList = new AccessManager().getPlayerRoom(nameRoom);
			Gson gson = new Gson();
			Players = gson.toJson(playerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Players;
	}
	
	@DELETE
	@Path("/removeCards/{nameRoom}")
	@Produces("application/json")
	public Response RemoveCardsRoom(@PathParam("nameRoom") String nameRoom) throws Exception {
	
		boolean result = new AccessManager().RemoveCardsRoom(nameRoom);
		if(result==false){
			return Response.status(Response.Status.NOT_FOUND).entity("Nao foi possivel dar cartas ao dealer.").build();
		}
		
		else {
			return Response.ok("Cartas dadas com sucesso ao dealer!", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/room/CheckPass")
	@Produces("application/json")
	public static Response newPlayer(@FormParam("idRoom") String idRoom, 
			@FormParam("password") String password) throws Exception {
		
		MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
		int idroom = Integer.parseInt(messagesEncrypter.decrypt(idRoom));
		
		boolean result = new AccessManager().checkpassRoom(idroom, password);
		
		if(result==false){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Register Room Failed for: " + idRoom).build();
		}
		
		else {
			return Response.ok("Register Room Successful", MediaType.APPLICATION_JSON).build();
		}
	}
}
