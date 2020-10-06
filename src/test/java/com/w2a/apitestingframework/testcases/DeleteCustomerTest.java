package com.w2a.apitestingframework.testcases;

import java.util.Hashtable;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.APILogic.CreateCustomerAPI;
import com.APILogic.DeleteCustomerAPI;
import com.BasesetUp.BaseTest;
import com.extentreports.ExtentListeners;

import com.w2aframework.utilities.DataUtil;
import com.w2aframework.utilities.Testutil;

import io.restassured.response.Response;

public class DeleteCustomerTest extends BaseTest {
	@Test(dataProviderClass= DataUtil.class,dataProvider="data")
	public void deleteCustomerAPI(Hashtable<String,String> data) {
		Response fr= DeleteCustomerAPI.sendDeleteRequestTODeleteCustomerAPIWithValidID(data);
		//we are just logging the info
		ExtentListeners.testReport.get().info(data.toString());
		//we cannot write our bussiness logic over here
	    //  Response re=given().auth().basic(cg.getProperty("validSecretkey"), "").
	    //	formParam("email", data.get("email")).formParam("description", data.get("description")).post(cg.getProperty("customerAPIEndPoint"));
	   fr.prettyPrint();
	    System.out.println(fr.statusCode());
	    //1st way of validation
	   /* String actual_id=fr.jsonPath().get("id").toString();
	    System.out.println(fr.jsonPath().get("id").toString());
	    Assert.assertEquals(actual_id, data.get("id"),"ID not matching");*/
	  //2 nd way of validation
	   /* JSONObject js = new JSONObject(fr.asString());
	   System.out.println(js.has("id")); 
	   Assert.assertTrue(js.has("id"),"id key is not present in json response");*/
	  Assert.assertTrue(Testutil.jsonHasKey(fr.asString(), "id"), "ID is not present in json response");
	   String actual= Testutil.getJsonKeyValue(fr.asString(), "id");
	  Assert.assertEquals(actual, data.get("id"),"Id not there");
	   //here we can add extent line to log info 
		
	}
	

}
