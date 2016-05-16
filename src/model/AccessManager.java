package model;

import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONArray;

import dao.Access;
import dao.Database;
import dto.Player;
import dto.Room;

public class AccessManager {
	
	Database db = new Database();
	Access access = new Access();
	
	public ArrayList<Player> getPlayers() throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Connection con = db.getConnection();
		playerList = access.getPlayers(con);
		return playerList;
	}
	
	public ArrayList<Room> getRooms() throws Exception {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Connection con = db.getConnection();
		roomList = access.getRooms(con);
		return roomList;
	}
	
	public int getPlayersByRoom(String nameRoom) throws Exception {
		int playerList = 0;
		Connection con = db.getConnection();
		playerList = access.getPlayersByRoom(con,nameRoom);
		return playerList;
	}

	public boolean insertPlayer(String name, String password, int chips) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.NewPlayer(con, name, password, chips);
		return result;
	}
	
	public boolean insertRoom(String name, String dealer) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.NewRoom(con, name, dealer);
		return result;
	}
	
	public boolean loginPlayer(String name, String password) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.LoginPlayer(con, name, password);		
		return result;
	}
	
	public void updatePlayer(String Playername, int ChipsNewValue) throws Exception {
		Connection con = db.getConnection();
		access.updatePlayer(con, Playername, ChipsNewValue);
	}
	
	public int getMoneyPlayer(String Playername) throws Exception {
		Connection con = db.getConnection();
		int chips = access.getPlayerMoney(con, Playername);
		return chips;
	}
	
	public void AddChips(String name, int addChips) throws Exception {
		Connection con = db.getConnection();
		access.addChips(con, name, addChips);		
	}
	
	public boolean RemoveChips(String name, int removeChips) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.removeChips(con, name, removeChips);	
		return result;
	}
	
	public boolean AddPlayerInRoom(String Player, int idRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.addPlayerRoom(con, Player, idRoom);
		
		return result;
	}
	
	public boolean updateRoomState(String nameRoom, String stateRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.updateRoom(con, nameRoom, stateRoom);
		
		return result;
	}
	
	public String getStateRoom(int idRoom) throws Exception {
		Connection con = db.getConnection();
		String result = access.getRoomState(con, idRoom);
		
		return result;
	}
	
	public Boolean AddBet(String name, int addChips) throws Exception {
		Connection con = db.getConnection();
		Boolean result = access.Addbet(con, name, addChips);		
		return result;
	}
	
	public JSONArray getCards(String Playername, int idRoom) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCards(con, Playername, idRoom);
		return cards;
	}
	
	public JSONArray getCardsDealer(String Playername) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCardsDealer(con, Playername);
		return cards;
	}
	
	public JSONArray getCardDealer(int idRoom) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCardDealer(con, idRoom);
		return cards;
	}
	
	public int getPointsDealer(int idRoom) throws Exception {
		Connection con = db.getConnection();
		int points = access.getPointsDealer(con, idRoom);
		return points;
	}
	
	public Boolean addCards(String name, int idRoom, int numCards) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.AddCards(con, name, idRoom, numCards);
		return result;
	}
	
	public Boolean AddCardsDealer(String name,int numberofCards) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.AddCardsDealer(con, name,numberofCards);
		return result;
	}
	
	public boolean updatePlayerState(int idRoom, String statePlayer, String namePlayer) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.updatePlayerState(con, idRoom, statePlayer, namePlayer);
		
		return result;
	}
	
	public String getStatePlayer(int idRoom, String namePlayer) throws Exception {
		Connection con = db.getConnection();
		String result = access.getPlayerState(con, idRoom, namePlayer);
		
		return result;
	}

	public ArrayList<Player> getPlayerRoom(String nameRoom) throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Connection con = db.getConnection();
		playerList = access.getPlayerRoom(con, nameRoom);
		return playerList;
	}
	
	public boolean RemoveCardsRoom(String nameRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.removeCardsRoom(con,nameRoom);
		
		return result;
	}
}