package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Access;
import dao.Database;
import dto.Game;

public class AccessManager {
	public ArrayList<Game> getGames() throws Exception {
		ArrayList<Game> courseList = new ArrayList<Game>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		courseList = access.getCourses(con);
		return courseList;
	}
}