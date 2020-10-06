package com.APILogic;

import java.util.Hashtable;

import com.BasesetUp.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class CreateCustomerAPI extends BaseTest{
	
	
	
	public static Response sendPostRequestTOCreateCustomerAPIWithValidAuthkey(Hashtable<String,String> data) {
		     Response re= given().auth().
		    		 basic(cg.getProperty("validSecretkey"), "").
					formParam("email", data.get("email"))
				   .formParam("description", data.get("description"))
				  .post(cg.getProperty("customerAPIEndPoint"));
		     return re;
		
	}
	
    public static void sendPostRequestTOCreateCustomerAPIWithInValidAuthkey() {
		//similar to above with invalid data
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

	}

}
