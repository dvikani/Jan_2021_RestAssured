package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GET_Or_Read_A_Product {
	SoftAssert softAssert = new SoftAssert();
	
	@Test
	public void read_A_Products() {
//		https://techfios.com/api-prod/api/product
//		/read_one.php
//		?id=1685
//		HashMap<String, String> queryParam = new HashMap<String, String>();
		Response response =
				given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.queryParam("id", "1973")//this is for single query Parameters
//					.queryParams(queryParam)//for mulipule query parameters 
					.when()
						.get("/read_one.php")
					.then().extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode );
//		Hard Assert
//		Assert.assertEquals(statusCode, 200);
		softAssert.assertEquals(statusCode, 201, "Status code assertion is failing");
		
//		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
//		System.out.println("ActualResponseTime: "+ actualResponseTime);
//		if(actualResponseTime<=200) {
//			System.out.println("Response time is within range.");
//		}else {
//			System.out.println("Response time is out of range.");
//		}
		
//		 response.getBody().prettyPrint();
		 
		String reaponseBody = response.getBody().asString();
//		System.out.println("Response Body: " + reaponseBody);
		
		JsonPath jp = new JsonPath(reaponseBody);

		String productName = jp.getString("name");
		System.out.println("ProductName: "+ productName);
//		Assert.assertEquals(productName, "QA Fundamentals");
		softAssert.assertEquals(productName, "QA Fundamentals", "Product Name assertion is failing");
		
		String productDes = jp.getString("description");
		System.out.println("ProductDescription: "+ productDes);
//		Assert.assertEquals(productDes, "Required for class!");
		softAssert.assertEquals(productDes, "Required for class!", "Product Description assertion is failing");
		 
		String productPrice = jp.getString("price");
		System.out.println("ProductPrice: "+ productPrice);
//		Assert.assertEquals(productPrice, "199");
		softAssert.assertEquals(productPrice, "199", "Product Price assertion is failing");
		
		softAssert.assertAll();
	}
		

}
