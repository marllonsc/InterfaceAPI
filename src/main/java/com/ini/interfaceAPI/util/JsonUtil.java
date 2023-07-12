package com.ini.interfaceAPI.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 

public class JsonUtil {
	
	public static String createJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
