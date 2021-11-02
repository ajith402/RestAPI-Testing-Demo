package com.rest.TestingDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.path.json.JsonPath;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class Location_Add_Update_Get_Delete {
	
	String strBaseURI,strKey,strAddPlaceResource,strGetPlaceResource,strBody,strPlaceId,
			strPutPlaceResource,strNewAddress,strUpdateAddressBody,strDeletePlaceResource ;
	int intAddPlaceStatusCode,intUpdatePlaceStatusCode,intGetPlaceStatusCode,
			intDeletePlaceStatusCode,intPageNotFoundStatusCode;
	
	
	@BeforeClass
	public void setUp() {
		
		strBaseURI = "https://rahulshettyacademy.com/";
		strKey = "qaclick123";
		strAddPlaceResource = "maps/api/place/add/json";
		strGetPlaceResource="maps/api/place/get/json";
		strPutPlaceResource="maps/api/place/update/json";
		strDeletePlaceResource="maps/api/place/delete/json";
		intAddPlaceStatusCode = 200;
		intUpdatePlaceStatusCode=200;
		intGetPlaceStatusCode=200;
		intDeletePlaceStatusCode=200;
		intPageNotFoundStatusCode=404;

	}
	
	
	@Test(priority=1)
	public void addPlace() {

		RestAssured.baseURI = strBaseURI;

		System.out.println("\nAdding Place:");
		
		strBody=PayLoad.getPayLoad();
		
		String strResponse=given()
								.queryParam("key", strKey)
								.header("Content-Type", "application/json")
								.body(strBody)
							.when()
								.post(strAddPlaceResource)
							.then()
								.assertThat().statusCode(intAddPlaceStatusCode)
								.body("status", equalTo("OK"))
								.extract().response().asString();
								
		System.out.println("Response:\n" + strResponse);
		

		// Parsing the JSon and storing the place_id in a string (strPlaceId)
		JsonPath jsonPath = new JsonPath(strResponse);

		strPlaceId = jsonPath.getString("place_id");

		System.out.println("Place_id: "+strPlaceId );
	}
	
	@Test(priority=2)
	public void updatePlace() {
		
		System.out.println("\nUpdating Address:");
		
		
		strNewAddress="Hyderabad, India";
		
		strUpdateAddressBody=PayLoad.getPayLoadWithNewAddress(strPlaceId, strNewAddress);
		
		RestAssured.baseURI = strBaseURI;
		
		given().log().all()	
				.queryParam("key", strKey)
				.header("Content-Type", "application/json")
				.body(strUpdateAddressBody)
		.when()
				.put(strPutPlaceResource)
		.then() //.log().all() //Validations
				.assertThat().statusCode(intUpdatePlaceStatusCode)	
				.body("msg", equalTo("Address successfully updated"));
	}
	
	
	@Test(priority=3)
	public void getPlaceWithUpdatedAddress() {
		
		//GET Place to validate the address
		System.out.println("\nValidating Address using GetPlace:");
		
		RestAssured.baseURI = strBaseURI;
		
		String strGetResponse= given().log().all()
									.queryParam("key", strKey)
									.queryParam("place_id", strPlaceId)
								.when()
									.get(strGetPlaceResource)
								.then().log().all()
										.assertThat().statusCode(intGetPlaceStatusCode)
										.extract().response().asString();
				
		System.out.println("\nGet Response: "+strGetResponse);
				
		// Parsing the JSon and validate the New Address
		JsonPath objJSonPath = new JsonPath(strGetResponse);
				

		String StrAddressFromResponse=objJSonPath.getString("address");
					
				
		System.out.println("\nStrAddressFromResponse: "+StrAddressFromResponse);
				
		//Validate the Address
		Assert.assertEquals(strNewAddress, StrAddressFromResponse);
		
	}
	
	
	@Test (priority=4)
	public void deletePlace() {
		
		
		RestAssured.baseURI = strBaseURI;
		System.out.println("\nDeleting the Place: ");
		
		String strDeletePlaceBody=PayLoad.getPayLoadDeletePlace(strPlaceId);
		
		given().log().all()
					.queryParam("key", strKey)
					.queryParam("place_id", strPlaceId)
					.body(strDeletePlaceBody)
			.when()
					.delete(strDeletePlaceResource)
				.then().log().all()
					.assertThat().statusCode(200)
					.body("status", equalTo("OK"));
		
	}
	
	@Test(priority=5)
	public void getPlaceAFterDeleting() {
		
		
		System.out.println("\nValidating No Place Data after deleting:");
		
		RestAssured.baseURI = strBaseURI;
	
		String strDeleteMessage="Get operation failed, looks like place_id  doesn't exists";
		
		given().log().all()
				.queryParam("key", strKey)
				.queryParam("place_id", strPlaceId)
		.when()
			 .get(strGetPlaceResource)
		.then().log().all()
				.assertThat().statusCode(intPageNotFoundStatusCode)
				.body("msg", equalTo(strDeleteMessage));
		}
	
	

}
