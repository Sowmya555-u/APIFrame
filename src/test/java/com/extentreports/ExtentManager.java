package com.extentreports;

import java.io.File;
import java.sql.DriverManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	//step 1
	//define the path  of the file and attach to project using extent
	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter  htmlReporter = new ExtentHtmlReporter(fileName);
		
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setReportName(fileName);
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		return extent;
		
	}
	//required only in case of selenium
    //method to capture screenshot in case of failure
	public static void captureScreenshot() {
		// TODO Auto-generated method stub
		
		//File srcfile= ((TakesScreenshot)DriverManager.getDriver());
		
	}

}
