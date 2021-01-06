package Main;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		UseApi testCode = new UseApi();
		JSONObject some = testCode.getModule(1);
		System.out.println(some.toJSONString());
		JSONObject some2 = testCode.searchModule("토익");
		System.out.println(some2.toJSONString());
	}

}
