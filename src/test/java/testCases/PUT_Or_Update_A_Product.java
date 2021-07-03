package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PUT_Or_Update_A_Product {
	
	@Test
	public void update_A_Products() {
//		https://techfios.com/api-prod/api/product
//		/update.php
//		{
//		 "id": "2062",
//        "name": "QA Fundamentals-Part-1",
//        "description": "Updated Version!!",
//        "price": "199",
//        "category_id": "6",
//        "category_name": "Books"
//			      
//			        }
		String payloadPath = ".\\src\\main\\java\\data\\updatepayload_2062.json";
		
		Response response =
				given()
					.log().all()  // .log.all will cover all codes till when
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.body(new File (payloadPath))// passinf the file 
					.when()
						.put("/update.php")
					.then().log().all()     //.headers()it will log the response headers
					.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode );
		Assert.assertEquals(statusCode, 200);
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("ActualResponseTime: "+ actualResponseTime);
		if(actualResponseTime<=3600) {
			System.out.println("Response time is within range.");
		}else {
			System.out.println("Response time is out of range.");
		}
		
//		 response.getBody().prettyPrint();
		 
		String reaponseBody = response.getBody().asString();
		System.out.println("Response Body: " + reaponseBody);
		
		JsonPath jp = new JsonPath(reaponseBody);

		String sucessMessage = jp.getString("message");
		System.out.println("Success Maessage: "+ sucessMessage);
		Assert.assertEquals(sucessMessage, "Product was updated.");
		

	}
		

}
