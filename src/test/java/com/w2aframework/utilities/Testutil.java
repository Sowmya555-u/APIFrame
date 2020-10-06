package com.w2aframework.utilities;

import org.json.JSONObject;

import com.extentreports.ExtentListeners;

public class Testutil {
	
	public static boolean jsonHasKey(String json,String key) {
		JSONObject js = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating the presence of key");
	    return js.has(key);
		
	}
	
	public static String getJsonKeyValue(String json,String key) {
		JSONObject js = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating the value of key");
		return js.get(key).toString();
	}

}
