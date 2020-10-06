package com.extentreports;

import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentListeners implements ITestListener {
	
	static Date d = new Date();
	//creates a new file name whenever run
	static String fileName = "Extent_"+ d.toString().replace(":", "_").replace(" ", "_")+".html";
	//step 1: create a file using extent reports and attach this file to project using extent
	private static ExtentReports extent = ExtentManager.createInstance(System.getProperty("user.dir")+"\\reports\\"+fileName);
	
	//Required in case of parallel testing where  we want to run each test in different thread
	
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	
	
   //step2: using extent create a test  and using extent test set or log the test
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ExtentTest test = extent.createTest(result.getTestClass().getName()+"  @TestCase :"+result.getMethod().getMethodName());
		testReport.set(test);
		
	}
//step 3: using extent test log reports
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		String methodname=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:-"+methodname.toUpperCase()+"PASSED"+"<b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
		
	}
//step 3:using  extent test log reports
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
		testReport.get().fail(result.getThrowable().getMessage().toString());
		String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<details>"+"<summary>"+"<b>"+
		"<font color="+"red>"
		+"Exception Occurred:Click to see"+
		"</font"+"</b>"+
		"</summary>"+
		exceptionMessage.replaceAll(",", "<br>")+
		"</details"+"\n");
		//here we can create log to create screenshot//
		
	//	ExtentManager.captureScreenshot();
		//testReport.get().fail("<b>"+"<font color="+"red>"+"screenshot of failure");
//	MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName);
		
		String failurelog="TesT Case Failed";
		Markup m= MarkupHelper.createLabel(failurelog, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		String methodname=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:-"+methodname.toUpperCase()+"SKIP"+"<b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
		
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
//step 4: flush the instance of extent;else report will not be generated
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
		if(extent!= null) {
			extent.flush();
		}
		
	}
	
	

}
