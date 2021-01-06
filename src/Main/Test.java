package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		UseApi testCode = new UseApi();
		JSONObject some = testCode.getModule(1);
		System.out.println(some.toJSONString());
		JSONObject some2 = testCode.searchModule("토익");
		System.out.println(some2.toJSONString());
		
		ArrayList<Map<String, String>> words = new ArrayList<>();
		
		for(int i = 0; i < 20; i++) {
			Map<String, String> word = new HashMap<>();
			word.put("word", "test");
			word.put("mean", "시험");
			words.add(word);
		}
		
		JSONObject some3 = testCode.createModule("test", "CV", words);
		System.out.println(some3.toJSONString());
	}

}
