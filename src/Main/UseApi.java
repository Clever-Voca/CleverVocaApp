package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

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
			System.out.println("�ּҰ� �̻��ѵ�");
		}

		return prv;

	}
}

public class UseApi {
	String URL;
	UseApi(){
		this.URL = GetBE.getUrl("./BE.txt");
	}
	JSONObject searchModule(String q) throws IOException, org.json.simple.parser.ParseException {
		String reqURL = this.URL + "/search/" + URLEncoder.encode(q, "utf-8");
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
	JSONObject createModule(String moduleName, String publisher, ArrayList<Map<String, String>> words) throws IOException, org.json.simple.parser.ParseException {
		String reqURL = this.URL;
		HttpURLConnection con = getCon("POST", reqURL);
		con.setDoOutput(true);
		
		Map<String, Object> arg = new HashMap<>();
		arg.put("\"module_name\"", "\"" + moduleName + "\"");
		arg.put("\"publisher\"", "\"" + publisher + "\"");
		ArrayList<Map<String, String>> wordList = new ArrayList<>();
		for(Map<String, String> word : words) {
			Map<String, String> subArg = new HashMap<>();
			subArg.put("\"word\"", "\"" + word.get("word") + "\"");
			subArg.put("\"mean\"", "\"" + word.get("mean") + "\"");
			wordList.add(subArg);
		}
		arg.put("\"word\"", wordList);
		
		String reqData = arg.toString().replaceAll("=", ":");
		
		byte[] out = reqData .getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		
		con.setFixedLengthStreamingMode(length);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.connect();
		try(OutputStream os = con.getOutputStream()){
			os.write(out);
		}
		
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
			System.out.println("POST request not worked");
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
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
		//con.setRequestProperty("Content-Type", "application/x-www-from-urlencoded; charset=UTF-8");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		con.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
		con.setConnectTimeout(10000);       
        con.setReadTimeout(5000);   

		return con;
	}
}
