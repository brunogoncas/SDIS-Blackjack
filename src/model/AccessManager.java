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
}