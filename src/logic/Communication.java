package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Communication {
	
	static String urlStandard = "http://localhost:8080/SDIS_BlackJack/rest/";
	
	public String GET(String path) throws IOException {
			
		  URL url = new URL(urlStandard + path);
		  
		  HttpURLConnection conn =
		      (HttpURLConnection) url.openConnection();

		  if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		  }

		  // Buffer the result into a string
		  BufferedReader rd = new BufferedReader(
		      new InputStreamReader(conn.getInputStream()));
		  StringBuilder sb = new StringBuilder();
		  String line;
		  while ((line = rd.readLine()) != null) {
		    sb.append(line);
		  }
		  rd.close();

		  conn.disconnect();
		  return sb.toString();
	}
	
	public static String POST(String path, String[] paramName, String[] paramVal) throws IOException {
	 
		 // Define the server endpoint to send the HTTP request to
	    URL serverUrl = 
	    new URL(urlStandard + path);
	    HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();
	 
	    // Indicate that we want to write to the HTTP request body
	    urlConnection.setDoOutput(true);
	    urlConnection.setRequestMethod("POST");
	 	    
	    // Create the form content
	    OutputStream out = urlConnection.getOutputStream();
	    Writer writer = new OutputStreamWriter(out, "UTF-8");
	    for (int i = 0; i < paramName.length; i++) {
	      writer.write(paramName[i]);
	      writer.write("=");
	      writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
	      writer.write("&");
	    }
	    writer.close();
	    out.close();
	 
	    // Reading from the HTTP response body
	    Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
	    while(httpResponseScanner.hasNextLine()) {
	        System.out.println(httpResponseScanner.nextLine());
	    }
	    httpResponseScanner.close();
	    
	    return "success";
	}
	
	static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}
