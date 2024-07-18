package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	XSSFWorkbook wb;
	//write constructor for reading excel path
	public ExcelFileUtil(String ExcelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = new XSSFWorkbook(fi);
	}
	//method for counting no of rows in a sheet
	public int rowCount(String SheetName)
	{
		return wb.getSheet(SheetName).getLastRowNum();
	}
	// method for readinfo celldata
	public String getCellData(String SheetName,int row, int column)
	{
		String data="";
		if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	//method for writing
	public void setCellData(String SheetName,int row, int column,String status,String WriteExcel) throws Throwable
	{
		//get sheet from wb
		XSSFSheet ws = wb.getSheet(SheetName);
		//get row from sheet
		XSSFRow ro = ws.getRow(row);
		// create cell 
		XSSFCell cell = ro.createCell(column);
		cell.setCellValue(status);
		//System.out.println(status);
		if(status.equalsIgnoreCase("Pass"))
			
		{
			
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			//style.setFillPattern(FillPatternType.BRICKS);
			font.setColor(IndexedColors.GREEN.getIndex());			
			font.setBold(true);
			style.setFont(font);
			
			ro.getCell(column).setCellStyle(style);
			
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ro.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ro.getCell(column).setCellStyle(style);
		}
		
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
		
	}
	public static void main(String[] args) throws Throwable {
		 new ExcelFileUtil("./FileInput/Controller.xlsx");
	}

}
