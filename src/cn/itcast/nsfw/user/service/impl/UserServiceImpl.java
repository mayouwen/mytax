package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.ExcelUtil;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	private UserDao userDao;
	@Resource
	public void setUserDao(UserDao userDao) {
		super.setBasedao(userDao);
		this.userDao = userDao;
	}
	public void delete(Serializable id) {
		userDao.delete(id);
		//删除用户对应的所有权限
		userDao.deleteUserRoleByUserId(id);
	}

	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		ExcelUtil.exportExcel(userList,outputStream);
	}

	//导入excel
	public  void importExcel(File userExcel, String userExcelFileName) {
		try {
			FileInputStream inputStream = new FileInputStream(userExcel);
			boolean is03Excel=userExcelFileName.matches("^.+\\.(?i)(xls)$");
			//1读取工作簿
			Workbook workBook=is03Excel?new HSSFWorkbook(inputStream):new XSSFWorkbook(inputStream);
			//2读取工作表
			Sheet sheet = workBook.getSheetAt(0);
			//3读取行
			if(sheet.getPhysicalNumberOfRows()>2){
				User user=null;
				for(int i=2;i<sheet.getPhysicalNumberOfRows();i++){
					//读取单元格
					user=new User();
					Row row = sheet.getRow(i);
					//读取用户名
					Cell cell0 = row.getCell(0);
					user.setName(cell0.getStringCellValue());
					//读取账号
					Cell cell1 = row.getCell(1);
					user.setAccount(cell1.getStringCellValue());
					//读取部门
					Cell cell2 = row.getCell(2);
					user.setDept(cell2.getStringCellValue());
					//读取性别
					Cell cell3 = row.getCell(3);
					user.setGender(cell3.getStringCellValue().equals("男"));
					//读取手机号
					String mobile="";
					Cell cell4 = row.getCell(4);
					try {
						mobile=cell4.getStringCellValue();
					} catch (Exception e) {
						double dMobile=cell4.getNumericCellValue();
						mobile=BigDecimal.valueOf(dMobile).toString();
					}
					user.setMobile(mobile);
					//读取邮箱
					Cell cell5 = row.getCell(5);
					user.setEmail(cell5.getStringCellValue());
					//读取生日
					Cell cell6 = row.getCell(6);
					if(cell6.getDateCellValue()!=null){
						user.setBirthday(cell6.getDateCellValue());
					}
					//设置用户名密码
					user.setPassword("123456");
					user.setState(user.USER_STATE_VALID);
					save(user);
				}
			}
			workBook.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//账号唯一性检查
	public List<User> findUserByAccountAndId(String id,String account) {
		return userDao.findUserByAccountAndId(id,account);
	}
	//保存用户和角色
	public void saveUserAndRole(User user, String... roleIds) {
		userDao.save(user);
		//保存角色
		if(roleIds!=null){
			for(String roleId:roleIds){
				userDao.saveUserAndRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
			}
		}
	}

	public void updateUserAndRole(User user, String... roleIds) {
		//更具用户删除角色
		userDao.deleteUserRoleByUserId(user.getId());
		//更新
		userDao.update(user);
		//保存
		if(roleIds!=null){
			for(String roleId:roleIds){
				userDao.saveUserAndRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
			}
		}
	}
	//根据用户id获取角色
	public List<UserRole> getUserRoleByUserId(Serializable id) {
		return userDao.getUserRoleByUserId(id);
		
	}

	public List<User> findUserByAccountAndPass(String account, String password) {
		return userDao.findUserByAccountAndPass(account,password);
	}

}
