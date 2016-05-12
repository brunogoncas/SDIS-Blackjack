package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Access;
import dao.Database;
import dto.Player;
import dto.Room;

public class AccessManager {
	
	public ArrayList<Player> getPlayers() throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		playerList = access.getPlayers(con);
		return playerList;
	}
	
	public ArrayList<Room> getRooms() throws Exception {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		roomList = access.getRooms(con);
		return roomList;
	}
	
	public int getPlayersByRoom(String nameRoom) throws Exception {
		int playerList = 0;
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		playerList = access.getPlayersByRoom(con,nameRoom);
		return playerList;
	}

	public boolean insertPlayer(String name, String password, int chips) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.NewPlayer(con, name, password, chips);
		return result;
	}
	
	public boolean insertRoom(String name, String dealer) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.NewRoom(con, name, dealer);
		return result;
	}
	
	public boolean loginPlayer(String name, String password) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.LoginPlayer(con, name, password);		
		return result;
	}
	
	public void updatePlayer(String Playername, int ChipsNewValue) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		access.updatePlayer(con, Playername, ChipsNewValue);
	}
	
	public int getMoneyPlayer(String Playername) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		int chips = access.getPlayerMoney(con, Playername);
		return chips;
	}
	
	public void AddChips(String name, int addChips) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		access.addChips(con, name, addChips);		
	}
	
	public boolean RemoveChips(String name, int removeChips) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.removeChips(con, name, removeChips);	
		return result;
	}
	
	public boolean AddPlayerInRoom(String Player, int idRoom) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.addPlayerRoom(con, Player, idRoom);
		
		return result;
	}
	
	public boolean updateRoomState(String nameRoom, String stateRoom) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.updateRoom(con, nameRoom, stateRoom);
		
		return result;
	}
	
	public String getStateRoom(int idRoom) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		String result = access.getRoomState(con, idRoom);
		
		return result;
	}
}