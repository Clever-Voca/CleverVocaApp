package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class GetBE {

	static String getUrl(String path) {
		String prv = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			prv = br.readLine();
			br.close();
		} catch (Exception e) {
			System.out.println("주소가 이상한디");
		}

		return prv;

	}
}

public class UseApi {
	String URL;
	UseApi(){
		this.URL = GetBE.getUrl("./BE.txt");
	}
	
	JSONObject getModule(int moduleId) throws IOException, org.json.simple.parser.ParseException, ParseException {
		String reqURL = this.URL + "/" + Integer.toString(moduleId);
		HttpURLConnection con = getCon("GET", reqURL);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			
			JSONParser p = new JSONParser();
			JSONObject obj = null;
			
			obj = (JSONObject)p.parse(response.toString());
			
			return obj;
		} else {
			System.out.println("GET request not worked");
			return null;
		}
		
		
	
	}
	
	HttpURLConnection getCon(String method, String reqURL) throws IOException {
		URL obj = null;
		try {
			obj = new URL(reqURL);
		} catch (MalformedURLException e) {	
			e.printStackTrace();
		}
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		return con;
	}
}
class HttpURLConnectionExample {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "https://localhost:9090/SpringMVCExample";

	private static final String POST_URL = "https://localhost:9090/SpringMVCExample/home";

	private static final String POST_PARAMS = "userName=Pankaj";

	public static void main(String[] args) throws IOException {

		sendGET();
		System.out.println("GET DONE");
		sendPOST();
		System.out.println("POST DONE");
	}

	private static void sendGET() throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

	}

	private static void sendPOST() throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
	}

}