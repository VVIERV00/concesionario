import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;*/

public class ReadWriteExcelFile {

	
	public  String[][] readXLSXFile(String path) throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream(path);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		//for
		int rowStart = sheet.getFirstRowNum();
	    int rowEnd = sheet.getLastRowNum();
		for (int i = rowStart; i <= rowEnd; i++) {
			Row r = sheet.getRow(i);
			ArrayList<String> fila = new ArrayList<String>();
			if(r==null) {
				fila.add("");
				lista.add(fila);
				continue;
			}
			int firstColum = r.getFirstCellNum();
			int lastColum = r.getLastCellNum();
			for (int j = firstColum; j < lastColum; j++) {
				Cell c = r.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				//meter null y demas tipos
				if(c==null) {
					fila.add("");
				}else if(c.getCellType() == Cell.CELL_TYPE_STRING){
					fila.add(c.getStringCellValue());
				}else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					fila.add(String.valueOf(c.getNumericCellValue()));
				}
			}
			//añado fila
			lista.add( fila);
			
		}
		
		
		
		//paso el arraylist a string [][]
		String resultado [][] = new String[lista.size()][lista.get(0).size()];
		for (int i = 0; i < lista.size(); i++) {
			ArrayList<String> aux = lista.get(i);
			for (int j = 0; j < aux.size() ; j++) {
				if(aux.get(j)!=null) {
					resultado[i][j]=aux.get(j);
				}else {
					resultado[i][j]="";
				}
				//System.out.print(resultado[i][j]);
			}
			//System.out.println("\n");
		}
		for (int i = 0; i < resultado.length; i++) {
			for (int j = 0; j < resultado[0].length; j++) {
				if(resultado[i][j]==null) {
					resultado[i][j]="";
				}
			}
		}
		return resultado;
	}
	public ArrayList<ArrayList<String>> readXLSXFileHoja2(String path) throws IOException{
		InputStream ExcelFileToRead = new FileInputStream(path);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;
		ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		//for
		int rowStart = sheet.getFirstRowNum();
	    int rowEnd = sheet.getLastRowNum();
		for (int i = rowStart; i <= rowEnd; i++) {
			Row r = sheet.getRow(i);
			ArrayList<String> fila = new ArrayList<String>();
			if(r==null) {
				fila.add("");
				lista.add(fila);
				continue;
			}
			int firstColum = r.getFirstCellNum();
			int lastColum = r.getLastCellNum();
			for (int j = firstColum; j < lastColum; j++) {
				Cell c = r.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				//meter null y demas tipos
				if(c==null) {
					fila.add("");
				}else if(c.getCellType() == Cell.CELL_TYPE_STRING){
					fila.add(c.getStringCellValue());
				}else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					fila.add(String.valueOf(c.getNumericCellValue()));
				}
			}
			//añado fila
			lista.add( fila);
			
		}
		return lista;
	}
	
	public  void writeXLSXFile(String path,String[][] datos) throws IOException {
		String excelFileName = path+"/Corregido.xlsx";//name of excel file
		String sheetName = "Sheet1";//name of sheet
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		//iterating r number of rows
		for (int r=0;r < datos.length; r++ )
		{
			XSSFRow row = sheet.createRow(r);
			//iterating c number of columns
			for (int c=0;c < datos[0].length; c++ )
			{
				XSSFCell cell = row.createCell(c);
				cell.setCellValue(datos[r][c]);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	/*
	public static void readXLSFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	}
	
	
	public static void writeXLSFile() throws IOException {
		String excelFileName = "C:/Test.xls";//name of excel file
		String sheetName = "Sheet1";//name of sheet
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;
		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	*/
	
	
}
