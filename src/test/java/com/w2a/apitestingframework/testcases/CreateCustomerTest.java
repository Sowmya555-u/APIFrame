package com.w2a.apitestingframework.testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.APILogic.CreateCustomerAPI;
import com.BasesetUp.BaseTest;
import com.extentreports.ExtentListeners;
import com.w2aframework.utilities.DataUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.Hashtable;

public class CreateCustomerTest extends BaseTest {
	
	@Test(dataProviderClass= DataUtil.class,dataProvider="data")
	public void validatecreateCustomerAPI(Hashtable<String,String> data) {
		Response fr= CreateCustomerAPI.sendPostRequestTOCreateCustomerAPIWithValidAuthkey(data);
		//we are just logging the info
		ExtentListeners.testReport.get().info(data.toString());
		//we cannot write our bussiness logic over here
	    //  Response re=given().auth().basic(cg.getProperty("validSecretkey"), "").
	    //	formParam("email", data.get("email")).formParam("description", data.get("description")).post(cg.getProperty("customerAPIEndPoint"));
	   fr.prettyPrint();
	   System.out.println(fr.statusCode());
	   Assert.assertEquals(fr.statusCode(), 200);
		
	}
	
	

}
