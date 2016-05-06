package webService;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

//import model.AccessManager;

import dto.Game;

@Path("/courseService")
public class GameService {
	@GET
	@Path("/courses")
	@Produces("application/json")
	public String courses() {
		String courses = null;
		ArrayList<Game> courseList = new ArrayList<Game>();
		try {
			// courseList = new AccessManager().getCourses();
			Gson gson = new Gson();
			courses = gson.toJson(courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courses;
	}
}
