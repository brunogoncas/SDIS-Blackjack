package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Game;

public class Access {

	public ArrayList<Game> getCourses(Connection con) throws SQLException {
		ArrayList<Game> courseList = new ArrayList<Game>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Games");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Game gameObj = new Game();
				gameObj.setId(rs.getInt("id"));
				courseList.add(gameObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseList;

	}

}
