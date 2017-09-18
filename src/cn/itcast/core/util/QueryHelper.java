package cn.itcast.core.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
	private List<Object> parameters;
	//from字句
	private String fromClause="";
	//where字句
	private String whereClause="";
	//order by 字句
	private String orderByClause="";
	public static String ORDER_BY_DESC="DESC";//降序
	public static String ORDER_BY_ASC="ASC";//升序
	/**
	 * 
	 * @param clazz 实体类
	 * @param alias 实体类对应的别名
	 */
	public QueryHelper(Class clazz,String alias) {
		fromClause=" FROM "+clazz.getSimpleName()+" "+alias;
	}
	/**
	 * 
	 * @param condition 查询条件（where i.title）
	 * @param params 查询条件所对应的值
	 */
	public void addCondition(String condition,Object...params){
		if(whereClause.length()>1){
			whereClause+=" AND "+condition;
		}else{
			whereClause+=" WHERE "+condition;
		}
		if(parameters==null){
			parameters=new ArrayList<Object>();
		}
		if(params!=null){
			for(Object param:params){
				parameters.add(param);
			}
		}
	}
	/**
	 * 
	 * @param property 排序字段
	 * @param order 顺序
	 */
	public void addOrderBy(String property,String order){
		if(orderByClause.length()>1){
			orderByClause+=","+property+" "+order;
		}else{
			orderByClause+=" ORDER BY "+property+" "+order;
		}
	}
	//查询hql语句
	public String getQueryListHql(){
		return fromClause+whereClause+orderByClause;
	}
	//查询总记录
	public String getQueryCount(){
		return "SELECT COUNT(*) "+ fromClause+whereClause;
	}
	//查询条件
	public List<Object> getParameters(){
		return parameters;
	}
}
