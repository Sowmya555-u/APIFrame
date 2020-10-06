package com.w2a.apitestingframework.testcases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Assignment5 {
	@Test
	public void Endtoend() {
		
		
		
		Response re= given().contentType(ContentType.JSON).
				formParam("circuitId", "albert").
				formParam("lat", "-7867").
				formParam("long", "1545").
				formParam("locality", "ghghjg").
				post("http://ergast.com/api/f1/2017/circuits.json");
		re.prettyPrint();
		
		JsonPath ju=re.jsonPath();
		System.out.println(ju.get("MRData.xmlns"));
		System.out.println(ju.get("MRData.url"));
		System.out.println(ju.get("MRData.limit"));
		System.out.println(ju.get("MRData.offset"));
		System.out.println(ju.get("MRData.total"));
		System.out.println(ju.get("MRData.CircuitTable"));
		
		List<Object> gy=ju.get("MRData.CircuitTable.Circuits.circuitId");
		System.out.println(gy);
		
		Assert.assertEquals(gy.contains("albert_park"), true);
		Assert.assertEquals(ju.get("MRData.xmlns"), "http://ergast.com/mrd/1.4");
		/*"series": "f1",
        "url": "http://ergast.com/api/f1/2017/circuits.json",
        "limit": "30",
        "offset": "0",
        "total": "20",
        "CircuitTable"*/
		
	}

}
