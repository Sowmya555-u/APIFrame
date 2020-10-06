package com.w2aframework.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fos= null;
	private XSSFWorkbook wb= null;
	private XSSFSheet sheet = null;
	private XSSFRow row= null;
	private XSSFCell cell= null;
	
	public ExcelReader(String path) throws IOException {
		this.path=path;
		try {
			fis= new FileInputStream(path);
			wb= new XSSFWorkbook(fis);
			sheet= wb.getSheetAt(0);
			fis.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public int getRowCount( String sheetName) {
		
		int index= wb.getSheetIndex(sheetName);
		if(index==-1)
           return 0;
		else {
			sheet= wb.getSheetAt(index);
			int number= sheet.getLastRowNum()+1;
			return number;
		}	
	}
	
	public String getCellData(String sheetName,String colName,int rowNum) {
		if(rowNum<=0) {
			return "";
		}
		int index = wb.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1) {
			return "";
		}
		sheet= wb.getSheetAt(index);
		row= sheet.getRow(0);
		for(int i=0;i<=row.getLastCellNum();i++) {
			if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
				 colNum=i;
			}
		}
		if(colNum==-1)
			return "";
		row= sheet.getRow(rowNum-1);
		if(row==null) {
			return "";
		}
		else {
			cell =row.getCell(colNum);
		}
		if(cell==null) {
			return "";
		}
		if(cell.getCellTypeEnum()==CellType.STRING)
			return cell.getStringCellValue();
		
		else if(cell.getCellTypeEnum()==CellType.NUMERIC || cell.getCellTypeEnum()== CellType.FORMULA) {
			String celltext = String.valueOf(cell.getNumericCellValue());
		     if(HSSFDateUtil.isCellDateFormatted(cell)) {
			 double d = cell.getNumericCellValue();
			 Calendar cal= Calendar.getInstance();
			 cal.setTime(HSSFDateUtil.getJavaDate(d));
			 celltext = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
			 celltext= cal.get(Calendar.MONTH)+1+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+celltext;
		}
		return celltext;
		}
		else if(cell.getCellTypeEnum()==CellType.BLANK) {
			return "";	
	}
		else {
			return String.valueOf(cell.getBooleanCellValue());
		}	
		
	}
	
	public boolean setCellData(String sheetName,String colName,int rowNum,String data) {
		
		try {
			fis =new  FileInputStream(path);
			wb= new XSSFWorkbook(fis);
			if(rowNum<0) {
				return false;
			}
			int index= wb.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
		sheet=wb.getSheetAt(index);
		row= sheet.getRow(0);
		for(int i=0;i<=row.getLastCellNum();i++) {
			if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
				colNum=i;
			}
		}
			
		if(colNum==-1) {
			return false;
		}
		sheet.autoSizeColumn(colNum);
		row= sheet.getRow(rowNum-1);
		if(row==null)
			row= sheet.createRow(rowNum-1);
		cell=row.getCell(colNum);
		if(cell==null)
			cell= row.createCell(colNum);
		cell.setCellValue(data);
		fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean setCellData(String sheetName,String colName,int rowNum,String data,String url) {
		try {
			fis= new FileInputStream(path);
			wb =new XSSFWorkbook(fis);
			if(rowNum<=0) {
				return false;
			}
			int index= wb.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
		sheet=wb.getSheetAt(index);
		row= sheet.getRow(0);
		for(int i=0;i<=row.getLastCellNum();i++) {
			if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
				colNum=i;
			}
		}
			
		if(colNum==-1) {
			return false;
		}
		sheet.autoSizeColumn(colNum);
		row= sheet.getRow(rowNum-1);
		if(row==null)
			row= sheet.createRow(rowNum-1);
		cell=row.getCell(colNum);
		if(cell==null)
			cell= row.createCell(colNum);
		cell.setCellValue(data);
		XSSFCreationHelper ch= wb.getCreationHelper();
		
	    CellStyle hlink_style= wb.createCellStyle();
	    XSSFFont hlink_font= wb.createFont();
	    hlink_font.setUnderline(XSSFFont.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    
	    fos= new FileOutputStream(path);
	    wb.write(fos);
	    fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean addSheet(String sheetName) {
		FileOutputStream fos;
		wb.createSheet(sheetName);
		try {
			fos= new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			}
	
		return true;
		
	}
	
	public boolean removeSheet(String sheetName) {
		int index= wb.getSheetIndex(sheetName);
		if(index==-1) {
			return false;
		}
		wb.removeSheetAt(index);
		try {
			fos= new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		}

	public boolean addColumn(String sheetName,String colName) {
		try {
			fis= new FileInputStream(path);
			wb= new XSSFWorkbook(fis);
			int index= wb.getSheetIndex(sheetName);
			if(index==-1) {
				return false;
			}
			
			XSSFCellStyle style= wb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			sheet=wb.getSheetAt(index);
			row= sheet.getRow(0);
			if(row==null) {
				row= sheet.createRow(0);
			}
			if(row.getLastCellNum()==-1) {
				cell=row.createCell(0);
			}
			else {
				cell= row.createCell(row.getLastCellNum());
			}
			cell.setCellValue(colName);
			cell.setCellStyle(style);
			fos= new FileOutputStream(path);
			wb.write(fos);
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
	try{
	if(!isSheetExist(sheetName))
	return false;
	fis = new FileInputStream(path);
	wb = new XSSFWorkbook(fis);
	sheet=wb.getSheet(sheetName);
	XSSFCellStyle style = wb.createCellStyle();
	style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
	//XSSFCreationHelper createHelper = workbook.getCreationHelper();
	style.setFillPattern(FillPatternType.NO_FILL);

	   

	for(int i =0;i<getRowCount(sheetName);i++){
	row=sheet.getRow(i);
	if(row!=null){
	cell=row.getCell(colNum);
	if(cell!=null){
	cell.setCellStyle(style);
	row.removeCell(cell);
	}
	}
	}
	fos = new FileOutputStream(path);
	wb.write(fos);
	   fos.close();
	}
	catch(Exception e){
	e.printStackTrace();
	return false;
	}
	return true;

	}


	  // find whether sheets exists
	public boolean isSheetExist(String sheetName){
	int index = wb.getSheetIndex(sheetName);
	if(index==-1){
	index=wb.getSheetIndex(sheetName.toUpperCase());
	if(index==-1)
	return false;
	else
	return true;
	}
	else
	return true;
	}


	// returns number of columns in a sheet
	public int getColumnCount(String sheetName){
	// check if sheet exists
	if(!isSheetExist(sheetName))
	return -1;

	sheet = wb.getSheet(sheetName);
	row = sheet.getRow(0);

	if(row==null)
	return -1;

	return row.getLastCellNum();



	}


	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){


	url=url.replace('\\', '/');
	if(!isSheetExist(sheetName))
	return false;

	   sheet = wb.getSheet(sheetName);
	   
	   for(int i=2;i<=getRowCount(sheetName);i++){
	    if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
	   
	    setCellData(sheetName, screenShotColName, i+index, message,url);
	    break;
	    }
	   }


	return true;
	}
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
		if(rowNum <=0)
		return "";

		int index = wb.getSheetIndex(sheetName);

		if(index==-1)
		return "";


		sheet = wb.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
		return "";
		cell = row.getCell(colNum);
		if(cell==null)
		return "";

		 if(cell.getCellTypeEnum()==CellType.STRING)
		 return cell.getStringCellValue();
		 else if(cell.getCellTypeEnum()==CellType.NUMERIC || cell.getCellTypeEnum()==CellType.FORMULA ){
		 
		 String cellText  = String.valueOf(cell.getNumericCellValue());
		 if (HSSFDateUtil.isCellDateFormatted(cell)) {
		          // format in form of M/D/YY
		 double d = cell.getNumericCellValue();

		 Calendar cal =Calendar.getInstance();
		 cal.setTime(HSSFDateUtil.getJavaDate(d));
		           cellText =
		            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		          cellText = cal.get(Calendar.MONTH)+1 + "/" +
		                     cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                     cellText;
		         
		       

		        }

		 
		 
		 return cellText;
		 }else if(cell.getCellTypeEnum()==CellType.BLANK)
		     return "";
		 else
		 return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){

		e.printStackTrace();
		return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
		}


	public int getCellRowNum(String sheetName,String colName,String cellValue){

	for(int i=2;i<=getRowCount(sheetName);i++){
	    if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    return i;
	    }
	   }
	return -1;

	}





	}


