package com.extentreports;

import java.util.Arrays;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Extentrough {
     
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	@BeforeTest
	public void setReport() {
		//through this we are defining the path of report
		htmlReporter = new ExtentHtmlReporter("./reports/extent.html");
		
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("automation report");
		htmlReporter.config().setReportName("test results");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
	}
	
	@Test
	public void doLogin() {
		test=extent.createTest("Logintest");
		System.out.println("executing login test");
		
	}
	
	//after every status execution we need to log the status
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()== ITestResult.FAILURE) {
			String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details>"+"<summary>"+"<b>"+
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
			test.log(Status.FAIL, m);
		}
		else if(result.getStatus()== ITestResult.SKIP) {
			String methodname=result.getMethod().getMethodName();
			String logText="<b>"+"TEST CASE:-"+methodname.toUpperCase()+"SKIP"+"<b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			test.skip(m);
		}
		else if(result.getStatus()== ITestResult.SUCCESS) {
			String methodname=result.getMethod().getMethodName();
			String logText="<b>"+"TEST CASE:-"+methodname.toUpperCase()+"PASSED"+"<b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.pass(m);
		}
		
	}
	

}
