package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Player;

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
	
	public void NewPlayer(Connection con, String name, String password, int chips) throws SQLException {
		
		String query = "insert into players (name, password, chips) "
				+ "values (?, ?, ?)";
		
		  // create the mysql insert preparedstatement
	      PreparedStatement preparedStmt = con.prepareStatement(query);
	      preparedStmt.setString (1, name);
	      preparedStmt.setString (2, password);
	      preparedStmt.setInt    (3, chips);
	 
	      // execute the preparedstatement
	      preparedStmt.execute();
	       
	      con.close();
	}

	
	public boolean LoginPlayer(Connection con, String name, String password) throws SQLException {
			
		String query = "select * from players where name = ? and password = ?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		boolean val = rs.next(); //next() returns false if there are no-rows retrieved
        
		if(val==false){
            System.out.println("Login Errado"); //prints this message if your resultset is empty
        }
		
		return val;
	}
	
	public void updatePlayer(Connection con, String Playername, int ChipsNewValue) throws SQLException {
		
		// create the java mysql update preparedstatement
      String query = "update players set chips = ? where name = ?";
      PreparedStatement preparedStmt = con.prepareStatement(query);
      preparedStmt.setInt   (1, ChipsNewValue);
      preparedStmt.setString(2, Playername);
 
      // execute the java preparedstatement
      preparedStmt.executeUpdate();
       
      con.close();
	}

}
