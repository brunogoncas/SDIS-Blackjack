package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Access;
import dao.Database;
import dto.Player;

public class AccessManager {
	public ArrayList<Player> getPlayers() throws Exception {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		playerList = access.getPlayers(con);
		return playerList;
	}
	
	public boolean insertPlayer(String name, String password, int chips) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		boolean result = access.NewPlayer(con, name, password, chips);
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
}