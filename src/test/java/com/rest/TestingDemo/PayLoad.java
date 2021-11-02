package com.rest.TestingDemo;

public class PayLoad {
	
	public static String getPayLoad() {
		
		return "{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.453646, \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Ajith house\",\r\n"
				+ "  \"phone_number\": \"(+91) 9123 456 789\",\r\n"
				+ "  \"address\": \"Chennai, India\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"Home\",\r\n"
				+ "    \"House\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"English-IN\"\r\n"
				+ "}";
	}
	

	
	public static String getPayLoadWithNewAddress(String strPlaceId, String strNewAddress) {
		
		return "{\r\n"
				+ "\"place_id\":\""+strPlaceId+"\",\r\n"
				+ "\"address\":\""+strNewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}";
	}
	
	
	public static String getPayLoadDeletePlace(String strPlaceId) {
		
		return "{\r\n"
				+ "     \"place_id\": \""+strPlaceId+"\"\r\n"
				+ "}";
		
	}
	
	
	
}
