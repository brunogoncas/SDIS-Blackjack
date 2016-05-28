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
		con.close();
		return playerList;
	}
	
	public ArrayList<Room> getRooms() throws Exception {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Connection con = db.getConnection();
		roomList = access.getRooms(con);
		con.close();
		return roomList;
	}
	
	public int getPlayersByRoom(String nameRoom) throws Exception {
		int playerList = 0;
		Connection con = db.getConnection();
		playerList = access.getPlayersByRoom(con,nameRoom);
		con.close();
		return playerList;
	}

	public int getPlayersByIDRoom(int idRoom) throws Exception {
		int playerList = 0;
		Connection con = db.getConnection();
		playerList = access.getPlayersByIDRoom(con,idRoom);
		con.close();
		return playerList;
	}
	
	public boolean insertPlayer(String name, String password, int chips) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.NewPlayer(con, name, password, chips);
		con.close();
		return result;
	}
	
	public boolean insertRoom(String name, String dealer, String password) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.NewRoom(con, name, dealer, password);
		con.close();
		return result;
	}
	
	public boolean loginPlayer(String name, String password, String token) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.LoginPlayer(con, name, password, token);		
		con.close();
		return result;
	}
	
	public void logoutPlayer(String token) throws Exception {
		Connection con = db.getConnection();
		access.LogoutPlayer(con, token);		
		con.close();
	}
	
	public String getUserRoom(String username) throws Exception {
		Connection con = db.getConnection();
		String roomName = access.getUserRoom(con, username);	
		con.close();
		return roomName;
	}
	
	public void updatePlayer(String Playername, int ChipsNewValue) throws Exception {
		Connection con = db.getConnection();
		access.updatePlayer(con, Playername, ChipsNewValue);
		con.close();
	}
	
	public void updateTPlayer(String Playername, int idRoom) throws Exception {
		Connection con = db.getConnection();
		access.updateTPlayer(con, Playername, idRoom);
		con.close();
	}
	
	public int getMoneyPlayer(String Playername) throws Exception {
		Connection con = db.getConnection();
		int chips = access.getPlayerMoney(con, Playername);
		con.close();
		return chips;
	}
	
	public int getTimeouts(String Playername, int idRoom) throws Exception {
		Connection con = db.getConnection();
		int chips = access.getTimeouts(con, Playername,idRoom);
		con.close();
		return chips;
	}
	
	public void AddChips(String name, int addChips) throws Exception {
		Connection con = db.getConnection();
		access.addChips(con, name, addChips);	
		con.close();
	}
	
	public boolean RemoveChips(String name, int removeChips) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.removeChips(con, name, removeChips);	
		con.close();
		return result;
	}
	
	public boolean AddPlayerInRoom(String Player, int idRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.addPlayerRoom(con, Player, idRoom);
		con.close();
		
		return result;
	}
	
	public String getIDroom(String nameRoom) throws Exception {
		Connection con = db.getConnection();
		String result = access.getIdRoom(con, nameRoom);
		con.close();
		
		return result;
	}
	
	public String getPass(int idRoom) throws Exception {
		Connection con = db.getConnection();
		String result = access.getPassRoom(con, idRoom);
		con.close();
		
		return result;
	}
	
	public boolean updateRoomState(String nameRoom, String stateRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.updateRoom(con, nameRoom, stateRoom);
		con.close();
		return result;
	}
	
	public String getStateRoom(int idRoom) throws Exception {
		Connection con = db.getConnection();
		String result = access.getRoomState(con, idRoom);
		con.close();
		return result;
	}
	
	public Boolean AddBet(String name, int addChips) throws Exception {
		Connection con = db.getConnection();
		Boolean result = access.Addbet(con, name, addChips);	
		con.close();
		return result;
	}
	
	public JSONArray getCards(String Playername, int idRoom) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCards(con, Playername, idRoom);
		con.close();
		return cards;
	}
	
	public JSONArray getCardsDealer(String Playername) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCardsDealer(con, Playername);
		con.close();
		return cards;
	}
	
	public JSONArray getCardDealer(int idRoom) throws Exception {
		Connection con = db.getConnection();
		JSONArray cards = access.getCardDealer(con, idRoom);
		con.close();
		return cards;
	}
	
	public int getPointsDealer(int idRoom) throws Exception {
		Connection con = db.getConnection();
		int points = access.getPointsDealer(con, idRoom);
		con.close();
		return points;
	}
	
	public Boolean addCards(String name, int idRoom, int numCards) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.AddCards(con, name, idRoom, numCards);
		con.close();
		return result;
	}
	
	public Boolean AddCardsDealer(String name,int numberofCards) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.AddCardsDealer(con, name,numberofCards);
		con.close();
		return result;
	}
	
	public boolean updatePlayerState(int idRoom, String statePlayer, String namePlayer) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.updatePlayerState(con, idRoom, statePlayer, namePlayer);
		con.close();
		return result;
	}
	
	public String getStatePlayer(int idRoom, String namePlayer) throws Exception {
		Connection con = db.getConnection();
		String result = access.getPlayerState(con, idRoom, namePlayer);
		con.close();
		return result;
	}
	
	public ArrayList<Integer> getAFK() throws Exception {
		ArrayList<Integer>players = new ArrayList<Integer>();
		Connection con = db.getConnection();
		players = access.getAFK(con);
		con.close();
		return players;
	}

	public ArrayList<Player> getPlayerRoom(String nameRoom) throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Connection con = db.getConnection();
		playerList = access.getPlayerRoom(con, nameRoom);
		con.close();
		return playerList;
	}
	
	public boolean RemoveCardsRoom(String nameRoom) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.removeCardsRoom(con,nameRoom);
		con.close();
		return result;
	}
	
	public boolean RemovePlayerRoom(String namePlayer, int idRoom) throws Exception {
      Connection con = db.getConnection();
      boolean result = access.RemovePlayerRoom(con,namePlayer, idRoom);
      con.close();
      return result;
   }
	
	public boolean checkpassRoom(int idRoom, String password) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.CheckRoomPass(con, idRoom, password);
		con.close();
		return result;
	}
	
	public boolean existToken(String namePlayer, String token) throws Exception {
		Connection con = db.getConnection();
		boolean result = access.existToken(con, namePlayer, token);
		con.close();
		return result;		
	}
}