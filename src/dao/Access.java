package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

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
				newPlayer.setBetMoney(rs.getInt("betmoney"));
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

	public boolean LoginPlayer(Connection con, String name, String password, String token) throws SQLException {

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
		
		String queryUpdate = "update players set token = ? where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(queryUpdate);
		preparedStmt.setString(1, token);
		preparedStmt.setString(2, name);

		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		

		con.close();
		return val;
	}
	
	public void LogoutPlayer(Connection con, String token) throws SQLException {
		String queryUpdate = "update players set token = ? where token = ?";
		PreparedStatement preparedStmt = con.prepareStatement(queryUpdate);
		preparedStmt.setString(1, token);
		preparedStmt.setString(2, null);
		
		// execute the java preparedstatement
		preparedStmt.executeUpdate();

		con.close();
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
	
	public void updateTPlayer(Connection con, String Playername, int idRoom) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "update room_player as t1 INNER JOIN players as t2 ON(t1.idplayer = t2.idplayers AND t2.name = ?) set t1.timeouts = t1.timeouts+1 Where idRoom = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);
		preparedStmt.setInt(2, idRoom);

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
	
	public int getTimeouts(Connection con, String Playername, int idRoom) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT timeouts FROM room_player as t1 INNER JOIN players as t2 ON (t2.name = ? AND t1.idplayer = t2.idplayers) where t1.idRoom = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);
		preparedStmt.setInt(2, idRoom);

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
		String query = "SELECT COUNT(t1.name) FROM players as t1, room_player as t2, room as t3  where t3.name = ? AND t2.idroom = t3.idroom AND t2.idplayer = t1.idplayers AND t2.state <> 'Iddle'";
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
	
	public int getPlayersByIDRoom(Connection con, int idRoom) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT COUNT(t1.name) FROM players as t1, room_player as t2 Where t2.idroom = ? AND t2.idplayer = t1.idplayers";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, idRoom);

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
			preparedStmt.executeUpdate();
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
	
	public boolean Addbet(Connection con, String Playername, int betmoney) throws SQLException {

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
		
		if(result >= betmoney) {
			// create the java mysql update preparedstatement
			String query2 = "update players set betmoney = ?, chips = ? where name = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt2.setInt(1, betmoney);
			preparedStmt2.setInt(2, result - betmoney);
			preparedStmt2.setString(3, Playername);
	
			// execute the java preparedstatement
			preparedStmt2.executeUpdate();
			con.close();
			return true;
		}
		else {
			System.out.println("Nao tem dinheiro para essa aposta");
			con.close();
			return false;
		}
	}
	
	public JSONArray getCards(Connection con, String Playername, int  idRoom) throws SQLException {
		
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
		
		JSONArray ja = new JSONArray();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT t1.* FROM card as t1, hand as t2 where t1.idcard = t2.idcard AND t2.idroom = ? AND t2.idplayer = ?");
			stmt.setInt(1, idRoom);
			stmt.setInt(2, idPlayer);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("suit",rs.getString("suit"));
				jo.put("figure",rs.getString("figure"));
				jo.put("card_value",rs.getInt("card_value"));
				ja.put(jo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ja;
	}
	
	public JSONArray getCardsDealer(Connection con, String Dealername) throws SQLException {

		JSONArray ja = new JSONArray();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT t1.* FROM card as t1,hand_dealer as t2 INNER JOIN dealer as t3 ON (t3.name = ? AND t2.iddealer = t3.iddealer )where t1.idcard = t2.idcards");
		
			stmt.setString(1, Dealername);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("suit",rs.getString("suit"));
				jo.put("figure",rs.getString("figure"));
				jo.put("card_value",rs.getInt("card_value"));
				ja.put(jo);
			}
		} catch (SQLException e) {
			System.out.println("SUUUUUUUUP");
			e.printStackTrace();
		}

		return ja;
	}

	public int getPointsDealer(Connection con, int idRoom) throws SQLException {
		
		int points = 0;

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT SUM(t1.card_value) FROM card as t1,hand_dealer as t2 "
					+ "INNER JOIN room as t3 ON (t3.idroom = ? )"
					+ "INNER JOIN dealer as t4 ON (t3.iddealer = t4.iddealer AND t2.iddealer = t4.iddealer )"
					+ "where t1.idcard = t2.idcards");
		
			stmt.setInt(1, idRoom);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				points = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SUUUUUUUUP");
			e.printStackTrace();
		}

		return points;
	}
	
	public boolean AddCards(Connection con, String Playername, int idRoom, int numCards) throws SQLException {

		// create the java mysql update preparedstatement
		String query1 = "SELECT idplayers FROM players where name = ?";
		PreparedStatement preparedStmt1 = con.prepareStatement(query1);
		preparedStmt1.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs2 = preparedStmt1.executeQuery();
		int idPlayer = 0;
		while (rs2.next()) {
			idPlayer = rs2.getInt(1);
		}
		
		Random r = new Random();
		int max = 52;
		int min = 1;
		int c1 = r.nextInt((max - min) + 1) + min;
		int c2 = r.nextInt((max - min) + 1) + min;
		//System.out.println("SFA  " + idPlayer + " " + c1 + " " + c2 + " " + idRoom);
		// create the java mysql update preparedstatement
		if(numCards == 2) {
			String query2 = "insert into hand (idcard, idplayer, idroom) values (?,?,?)";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		
			preparedStmt2.setInt(1, c1);
		
			preparedStmt2.setInt(2, idPlayer);
			preparedStmt2.setInt(3, idRoom);
			// execute the java preparedstatement
			preparedStmt2.executeUpdate();
		}
		
		// create the java mysql update preparedstatement
		String query3 = "insert into hand (idcard, idplayer, idroom) values (?,?,?)";
		PreparedStatement preparedStmt3 = con.prepareStatement(query3);
	
		preparedStmt3.setInt(1, c2);
		
		preparedStmt3.setInt(2, idPlayer);
		preparedStmt3.setInt(3, idRoom);
		// execute the java preparedstatement
		preparedStmt3.executeUpdate();
	
		con.close();
		return true;
	}
	
	public boolean AddCardsDealer(Connection con, String Dealername, int numberofcards) throws SQLException {

		// create the java mysql update preparedstatement
		String query1 = "SELECT iddealer FROM dealer where name = ?";
		PreparedStatement preparedStmt1 = con.prepareStatement(query1);
		preparedStmt1.setString(1, Dealername);

		// execute the java preparedstatement
		ResultSet rs2 = preparedStmt1.executeQuery();
		int idDealer = 0;
		while (rs2.next()) {
			idDealer = rs2.getInt(1);
		}
		
		Random r = new Random();
		int max = 52;
		int min = 1;
		int c1 = r.nextInt((max - min) + 1) + min;
		int c2 = r.nextInt((max - min) + 1) + min;
		//System.out.println("SFA  " + idPlayer + " " + c1 + " " + c2 + " " + idRoom);
		// create the java mysql update preparedstatement
		
		String query2 = "insert into hand_dealer (iddealer, idcards) values (?,?)";
		if(numberofcards == 2) {
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		
			preparedStmt2.setInt(1, idDealer);
		
			preparedStmt2.setInt(2, c1);
			// execute the java preparedstatement
			preparedStmt2.executeUpdate();
		} 
		
		PreparedStatement preparedStmt2 = con.prepareStatement(query2);
	
		preparedStmt2.setInt(1, idDealer);
	
		preparedStmt2.setInt(2, c2);
		// execute the java preparedstatement
		preparedStmt2.executeUpdate();
		
	
		con.close();
		return true;
	}
	
	public boolean updatePlayerState(Connection con, int idRoom, String statePlayer, String namePlayer) {

		// create the java mysql update preparedstatement
		String query = "update room_player as t1 INNER JOIN players as t2 ON (t2.name = ? AND t1.idplayer = t2.idplayers) set t1.state = ? where t1.idroom = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, namePlayer);
			preparedStmt.setString(2, statePlayer);
			preparedStmt.setInt(3, idRoom);
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
	
	public String getPlayerState(Connection con, int idRoom, String namePlayer) {
		
		// create the java mysql update preparedstatement
		String query1 = "SELECT idplayers FROM players where name = ?";
		PreparedStatement preparedStmt1;
		int idPlayer=0;
		try {
			preparedStmt1 = con.prepareStatement(query1);
			preparedStmt1.setString(1, namePlayer);

			// execute the java preparedstatement
			ResultSet rs2 = preparedStmt1.executeQuery();
			while (rs2.next()) {
				idPlayer = rs2.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// create the java mysql update preparedstatement
		String query = "SELECT state FROM room_player where idroom = ? AND idplayer = ?";
		PreparedStatement preparedStmt;
		String state = null;
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idRoom);
			preparedStmt.setInt(2, idPlayer);

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
	
	public ArrayList<Player> getPlayerRoom(Connection con, String nameRoom) throws SQLException {
		ArrayList<Player> playerList = new ArrayList<Player>();
		PreparedStatement stmt = con.prepareStatement("SELECT t3.name FROM room_player as t1 INNER JOIN room as t2 ON(t1.idroom = t2.idroom AND t2.name = ?)INNER JOIN players as t3 ON(t3.idplayers = t1.idplayer) where t1.state = ? ");
		stmt.setString(1, nameRoom);
		stmt.setString(2, "myturn");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Player newPlayer = new Player();
				newPlayer.setName(rs.getString("name"));
				playerList.add(newPlayer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playerList;
	}
	
	public boolean removeCardsRoom(Connection con, String nameRoom) {

		// create the java mysql update preparedstatement
		String query = "delete t1.* from hand as t1 INNER JOIN room as t2 ON (t1.idroom = t2.idroom) WHERE t2.name = ?";
		String query2 = "delete t1.* from hand_dealer as t1 INNER JOIN room as t2 ON (t2.iddealer = t1.iddealer) WHERE t2.name = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt.setString(1, nameRoom);
			preparedStmt2.setString(1, nameRoom);
			// execute the java preparedstatement
			preparedStmt.execute();
			preparedStmt2.execute();
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	} 
	
	public JSONArray getCardDealer(Connection con, int idRoom) throws SQLException {

		JSONArray ja = new JSONArray();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT t1.* FROM card as t1,hand_dealer as t2 INNER JOIN room as t3 ON (t3.idroom = ? AND t2.iddealer = t3.iddealer )where t1.idcard = t2.idcards");
		
			stmt.setInt(1, idRoom);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("suit",rs.getString("suit"));
				jo.put("figure",rs.getString("figure"));
				jo.put("card_value",rs.getInt("card_value"));
				ja.put(jo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ja;
	}
	
	public ArrayList<Integer> getAFK(Connection con) throws SQLException {
		
		ArrayList<Integer>players = new ArrayList<Integer>(); 
		
		long time = System.currentTimeMillis();
		
		System.out.println("HELLOOOOOOOOOOOOOOOOOOO: " + time);

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT t1.iduser FROM requests as t1 WHERE (? - t1.lastreq) >= 30000");
		
			stmt.setLong(1, time);
			ResultSet rs2 = stmt.executeQuery();
			while (rs2.next()) {
				players.add(rs2.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}
	
	  public boolean RemovePlayerRoom(Connection con, String namePlayer, int idRoom) throws SQLException {
		  
	      String query = "delete t1.* from room_player as t1 INNER JOIN players as t2 ON (t1.idplayer = t2.idplayers AND t2.name = ?) WHERE t1.idRoom = ?";
	  
	      try {
	        PreparedStatement preparedStmt = con.prepareStatement(query);
	        preparedStmt.setString(1, namePlayer);
	        preparedStmt.setInt(2, idRoom);
	        
	        // execute the java preparedstatement
	        preparedStmt.execute();
	        con.close();
	        return true;
	        
	      } catch (SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
	      } 
	    }
}
