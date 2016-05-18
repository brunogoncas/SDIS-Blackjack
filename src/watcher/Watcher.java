package watcher;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.Communication;

public class Watcher {
	
	static class TestAFK extends TimerTask {
	    public void run() {
	    	
	    	System.out.println("CHECKING FOR SLEEPERS!!!");
	    	
	    	ArrayList<Integer> List = new ArrayList<Integer>();
	    	
	    	String response=null;
	    	Gson gson = new Gson();
			try {
				response = Communication.GET("playerService/getAFK");
				Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
			    List = gson.fromJson(response, type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!List.isEmpty()){
				for (int i : List) {
			        System.out.println(i);
			    }
			}
			
			else{
				
				System.out.println("NOTHING CAUGHT FOR NOW!");
			}
			

	    }
	}
	
	public static void main(String[] args){
		System.out.println(" ===== WATCHING EVERYTHING ===== ");
		
		Timer timer = new Timer();
		timer.schedule(new TestAFK(), 0, 3500);
		
		
	}
	
	

}
