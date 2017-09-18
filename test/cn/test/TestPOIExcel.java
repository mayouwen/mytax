package cn.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class TestPOIExcel {
	@Test
	public void testWrite() throws Exception {
		//1创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//2创建工作表
		HSSFSheet sheet=workbook.createSheet("helo word");
		//3创建行
		HSSFRow row=sheet.createRow(3);
		//4创建单元格
		HSSFCell cell=row.createCell(3);
		cell.setCellValue("hello word");
		//输出到硬盘
		FileOutputStream fileOutputStream = new FileOutputStream("D:\\itcast\\test.xls");
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();
	}
	@Test
	public void testRead() throws Exception {
		FileInputStream fileInputStream = new FileInputStream("D:\\itcast\\test.xls");
		//1读取工作簿
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		//2读取工作表
		HSSFSheet sheet=workbook.getSheetAt(0);
		//3读取行
		HSSFRow row=sheet.getRow(3);
		//4读取单元格
		HSSFCell cell=row.getCell(3);
		System.out.println("单元格内容为:"+cell.getStringCellValue());
		workbook.close();
		fileInputStream.close();
	}
}
