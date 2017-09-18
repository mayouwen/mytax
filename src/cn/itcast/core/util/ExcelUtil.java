package cn.itcast.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.dao.impl.UserDaoImpl;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
import cn.itcast.nsfw.user.service.impl.UserServiceImpl;

public class ExcelUtil {
	//导出excel
	public static void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		try {
			//1创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1创建单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,4);
			//1.2标题样式
			HSSFCellStyle style1=createCellStyle(workbook,(short)16);
			//1.3列标题样式
			HSSFCellStyle style2=createCellStyle(workbook,(short)13);
			//2创建工作表
			HSSFSheet sheet = workbook.createSheet("用户列表");
			//2.1加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置列宽
			sheet.setDefaultColumnWidth(25);
			//3创建行
			//3.1头标题行
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			//加载标题样式
			cell1.setCellValue("用户列表");
			cell1.setCellStyle(style1);
			//3.2列标提
			HSSFRow row2 = sheet.createRow(1);
			String[] titles={"用户名","账号","所属部门","性别","电子邮箱"};
			for(int i=0;i<titles.length;i++){
				HSSFCell cell = row2.createCell(i);
				cell.setCellStyle(style2);
				cell.setCellValue(titles[i]);
			}
			//4操作单元格
			if(userList!=null){
				for(int j=0;j<userList.size();j++){
					HSSFRow row = sheet.createRow(j+2);
					HSSFCell cell11 = row.createCell(0);
					cell11.setCellValue(userList.get(j).getName());
					HSSFCell cell2 = row.createCell(1);
					cell2.setCellValue(userList.get(j).getAccount());
					HSSFCell cell3 = row.createCell(2);
					cell3.setCellValue(userList.get(j).getDept());
					HSSFCell cell4 = row.createCell(3);
					cell4.setCellValue(userList.get(j).getGender()?"男":"女");
					HSSFCell cell5 = row.createCell(4);
					cell5.setCellValue(userList.get(j).getEmail());
				}
			}
			//5输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param workbook 工作簿
	 * @param frontSize 字体大小
	 * @return
	 */
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,short frontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		///1.2.1创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontHeightInPoints((short) frontSize);
		style.setFont(font);
		return style;
	}

}
