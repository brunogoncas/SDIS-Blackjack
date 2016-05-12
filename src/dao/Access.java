package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Player;
import dto.Room;

public class Access {

	public ArrayList<Player> getPlayers(Connection con) throws SQLException {
		ArrayList<Player> playerList = new ArrayList<Player>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM players");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Player newPlayer = new Player();
				newPlayer.setID(rs.getInt("idplayers"));
				newPlayer.setName(rs.getString("name"));
				newPlayer.setPassword(rs.getString("password"));
				newPlayer.setChips(rs.getInt("chips"));
				playerList.add(newPlayer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playerList;
	}

	public ArrayList<Room> getRooms(Connection con) throws SQLException {
		ArrayList<Room> roomList = new ArrayList<Room>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM room");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Room newRoom = new Room();
				newRoom.setID(rs.getInt("idroom"));
				newRoom.setName(rs.getString("name"));
				roomList.add(newRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomList;
	}

	public boolean NewPlayer(Connection con, String name, String password, int chips) {
		String query = "INSERT INTO players (name, chips, password)" + "VALUES (?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement Stmt;
		try {
			Stmt = con.prepareStatement(query);
			Stmt.setString(1, name);
			Stmt.setInt(2, chips);
			Stmt.setString(3, password);

			Stmt.executeUpdate();
			con.close();

			System.out.println("Insert query(REGISTER) Successful!");
			return true;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Insert query(REGISTER) Failed!");
			return false;
		}

	}
	
	public boolean NewRoom(Connection con, String name, String dealer) throws SQLException {
		
		
		String query = "INSERT INTO dealer (name)" + "VALUES (?)";
		//String query = "INSERT INTO room (name)" + "VALUES (?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement Stmt;

		Stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		Stmt.setString(1, dealer);
		
		Stmt.executeUpdate();
		
		int idd = getIdDealer(con, dealer);
		
		String query2 = "INSERT INTO room (name, iddealer)" + "VALUES (?, ?)";
		PreparedStatement Stmt2;
		try {
			Stmt2 = con.prepareStatement(query2);
			Stmt2.setString(1, name);
			Stmt2.setInt(2, idd);
			
			Stmt2.executeUpdate();
		}
		catch (SQLException e2){
			e2.printStackTrace();
		}
		con.close();
		return true;
	}
	
	public int getIdDealer(Connection con, String Dealername) throws SQLException {
		
		// create the java mysql update preparedstatement
		String query = "SELECT iddealer FROM dealer where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Dealername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int idd = 0;
		while (rs.next()) {
			idd = rs.getInt(1);
		}
		
		return idd;
	}

	public boolean LoginPlayer(Connection con, String name, String password) throws SQLException {

		String query = "select * from players where name = ? and password = ?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		boolean val = rs.next(); // next() returns false if there are no-rows
									// retrieved

		if (val == false) {
			System.out.println("Login Errado"); // prints this message if your
												// resultset is empty
		}

		con.close();
		return val;
	}

	public void updatePlayer(Connection con, String Playername, int ChipsNewValue) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "update players set chips = ? where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, ChipsNewValue);
		preparedStmt.setString(2, Playername);

		// execute the java preparedstatement
		preparedStmt.executeUpdate();

		con.close();
	}

	public int getPlayerMoney(Connection con, String Playername) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}

		con.close();
		return result;
	}
	
	public int getPlayersByRoom(Connection con, String nameRoom) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT COUNT(t1.name) FROM players as t1, room_player as t2, room as t3  where t3.name = ? AND t2.idroom = t3.idroom AND t2.idplayer = t1.idplayers";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, nameRoom);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;

		while (rs.next()) {
			result = rs.getInt(1);
		}
		con.close();
		return result;
	}

	public void addChips(Connection con, String Playername, int addChips) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}

		// create the java mysql update preparedstatement
		String query2 = "update players set chips = ? where name = ?";
		PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		preparedStmt2.setInt(1, result + addChips);
		preparedStmt2.setString(2, Playername);

		// execute the java preparedstatement
		preparedStmt2.executeUpdate();

		con.close();
	}
	
	public boolean addPlayerRoom(Connection con, String Playername, int idRoom) throws SQLException {

		// create the java mysql update preparedstatement
		String query2 = "SELECT idplayers FROM players where name = ?";
		PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		preparedStmt2.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs2 = preparedStmt2.executeQuery();
		int idPlayer = 0;
		while (rs2.next()) {
			idPlayer = rs2.getInt(1);
		}
		
		// create the java mysql update preparedstatement
		String query = "insert into room_player (idroom, idplayer) values (?,?)";
			
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idRoom);
			preparedStmt.setInt(2, idPlayer);
			
			// execute the java preparedstatement
			int rs = preparedStmt.executeUpdate();
			con.close();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeChips(Connection con, String Playername, int removeChips) throws SQLException {

		boolean remove=false;
		
		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		
		if(result >= removeChips) {
			// create the java mysql update preparedstatement
			String query2 = "update players set chips = ? where name = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt2.setInt(1, result - removeChips);
			preparedStmt2.setString(2, Playername);

			// execute the java preparedstatement
			preparedStmt2.executeUpdate();
			
			remove = true;
		}
			
		con.close();
		
		return remove;
	} 
	
	public boolean updateRoom(Connection con, String nameRoom, String stateRoom) {

		// create the java mysql update preparedstatement
		String query = "update room set state = ? where name = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, stateRoom);
			preparedStmt.setString(2, nameRoom);
			// execute the java preparedstatement
			preparedStmt.executeUpdate();
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	public String getRoomState(Connection con, int idRoom) {

		// create the java mysql update preparedstatement
		String query = "SELECT state FROM room where idroom = ?";
		PreparedStatement preparedStmt;
		String state = null;
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idRoom);

			// execute the java preparedstatement
			ResultSet rs = preparedStmt.executeQuery();
		
			while (rs.next()) {
				state = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return state;
				
	}
}
