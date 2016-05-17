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

public class Communication {
	
	static String urlStandard = "http://localhost:8080/SDIS_BlackJack/rest/";
	
	public static String GET(String path) throws IOException {
			
		  String pathh = urlStandard + path;
		  pathh = pathh.replace(" ", "%20");
		  URL url = new URL(pathh);
		  
		 
		  HttpURLConnection conn =
		      (HttpURLConnection) url.openConnection();
		  
		  if (conn.getResponseCode() != 200 && conn.getResponseCode() != 404) {
		    throw new IOException(conn.getResponseMessage());
		  }
		  
		 //System.out.println("GET request returned:" + conn.getResponseCode());

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
	
	public static int POST(String path, String[] paramName, String[] paramVal) throws IOException {
	 
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
	    
	    int response = urlConnection.getResponseCode();
		
		//print result
		//System.out.println("POST request returned:" + response);
	    
		urlConnection.disconnect();
	    return response;
	}
	
	public static void PUT(String path) throws IOException {
		// Starting HTTP connection
		URL serverUrl = new URL(urlStandard + path);
		HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();

		urlConnection.setRequestMethod("PUT");
		urlConnection.setDoOutput(true);

		OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());

		out.write("Resource content");
		out.close();

		// check connection response
		if (urlConnection.getResponseCode() != 200) {
			throw new IOException(urlConnection.getResponseMessage());
		}

		// TODO what we need to put in there

		if (urlConnection != null)
			urlConnection.disconnect();
	}

	public static void DELETE(String path) throws IOException {
		// Starting HTTP connection
		URL serverUrl = new URL(urlStandard + path);
		HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();

		urlConnection.setRequestMethod("DELETE");
		urlConnection.setDoOutput(true);
		// TODO put here the requests properties we have to create

		// check connection response
		if (urlConnection.getResponseCode() != 200) {
			throw new IOException(urlConnection.getResponseMessage());
		}

		// TODO what we need to put in there

		if (urlConnection != null)
			urlConnection.disconnect();
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
