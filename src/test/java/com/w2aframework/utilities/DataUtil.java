package com.w2aframework.utilities;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;

import com.BasesetUp.BaseTest;


public class DataUtil extends BaseTest {
	@DataProvider(name="data")
	public static Object[][] getData(Method m) {
		
		int rows = excel.getRowCount(cg.getProperty("DATA_SHEET"));
		System.out.println(rows);
		
		String testName=m.getName();
		System.out.println(testName);
		//Finding the first row
		int rowNum=1;
		for( rowNum=1;rowNum<=rows;rowNum++) {
			String testcasename=excel.getCellData(cg.getProperty("DATA_SHEET"), 0, rowNum);
			if(testcasename.equalsIgnoreCase(testName)) {
				break;
			}
			
		}
		
		System.out.println("Testcase starts from :"+ rowNum);
		
		//Finding the total  rows of testcase
		
		int datastartrow= rowNum+2;
		
		int rows1=0;
		while(!excel.getCellData(cg.getProperty("DATA_SHEET"), 0, datastartrow+rows1).equals("")) {
			rows1++;
		}
		
		
		System.out.println("total rows of data are:"+rows1);
		
		
		//checking total columns in a test case
		 int colcount=rowNum+1;
		 int rowe=0;
		 while(!excel.getCellData(cg.getProperty("DATA_SHEET"), rowe,colcount).equals("")){
			 rowe++;
		 }
		
		 System.out.println("total cols are:"+ rowe);
		 
		 //printing data
		Object[][] data= new Object[rows1][1];
		 int i=0;
		 for(int rowse= datastartrow;rowse<datastartrow+rows1;rowse++) {
			 Hashtable<String,String> table= new Hashtable<String,String>();
			 for(int colse=0;colse<rowe;colse++) {
				// System.out.println(excel.getCellData(Constants.DATA_SHEET, colse, rowse));
				 String colName= excel.getCellData(cg.getProperty("DATA_SHEET"), colse, colcount);
				 //System.out.println(colName);
				 String datarow=excel.getCellData(cg.getProperty("DATA_SHEET"), colse, rowse);
				// System.out.println(datarow);
				 table.put(colName, datarow);
			 }
			 
			 data[i][0]=table;
			 i++;
		 }
		
		//int rows=excel.getRowCount(sheetName);
		//int cols=excel.getColumnCount(sheetName);
		//Object[][] data = new Object[rows-1][cols];
		//for(int rowNum=2;rowNum<=rows;rowNum++) {
			//for(int colum=0;colum<cols;colum++) {
				//data[rowNum-2][colum]=excel.getCellData(sheetName, colum, rowNum);
			//}
			
		//}
		
		
		return data;
		
	}
	

}
