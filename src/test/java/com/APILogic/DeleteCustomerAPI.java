package com.APILogic;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.BasesetUp.BaseTest;

import io.restassured.response.Response;

public class DeleteCustomerAPI extends BaseTest {
	
	public static Response sendDeleteRequestTODeleteCustomerAPIWithValidID(Hashtable<String,String> data) {
		
		
	     Response re= given().auth().
	    		 basic(cg.getProperty("validSecretkey"), "").
	    		 delete(cg.getProperty("customerAPIEndPoint")+"/"+data.get("id"));
	     return re;
	
}
	
}
