package com.BasesetUp;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.w2aframework.utilities.ExcelReader;

import io.restassured.RestAssured;

public class BaseTest {
	public static Properties cg= new Properties();
	private FileInputStream fis;
	public static ExcelReader excel;
	
	
	@BeforeSuite
	public void setUp() throws IOException {
		
		fis= new FileInputStream("C:\\Users\\sai\\Downloads\\com.restpractice\\com.resources\\properties\\config.properties");
		cg.load(fis);
		System.out.println(cg.getProperty("baseURI"));
		excel = new ExcelReader(System.getProperty("user.dir")+"\\com.resources\\com\\exceldata\\CustomerSuite.xlsx");
		RestAssured.baseURI=cg.getProperty("baseURI");
	    RestAssured.basePath=cg.getProperty("basePath");
	}
	
	
	@AfterSuite
	public void tearDown() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
