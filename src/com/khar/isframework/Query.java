package com.khar.isframework;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Query {
	String whereClause;
	String onClause;
	String selectClause;
	boolean distinct = false;
	String groupClause;
	String limitClause;
	String havingClause;
	String orderClause;
	Map<String,String> joinedTable;
	public Query(){
		joinedTable = new HashMap<String, String>();
	}
	public void addWhere(String query){
		if(whereClause==null)
		whereClause = query;
		else
			whereClause+=" and "+query;
	}
	public void addWhere(Map<String,String> attr){
		String tambahan = "(";
		Set<Entry<String,String>> j = attr.entrySet();
		Iterator<Entry<String,String>> iter1=j.iterator();
		while(iter1.hasNext()){
			Entry<String,String> e2 = iter1.next();
			tambahan+=e2.getKey()+"="+e2.getValue()+" ";
			if(iter1.hasNext())
				tambahan+=" and ";
		}
		tambahan +=")";
		if(whereClause==null){
			whereClause = tambahan;
		}
		else{
			whereClause += " and "+whereClause;
		}
	}

	/**
	 * 
	 * @param with string to string map with key is table name and value is join condition
	 */
	public void addJoinedTable(Map<String,String> with){
		joinedTable.putAll(with);
	}
	
	public void addJoinedTable(String tableName,String joinCondition){
		joinedTable.put(tableName, joinCondition);
	}
	/**
	 * 
	 * @param select comma separated select condition
	 */
	public void addSelect(String select){
		if(selectClause==null)
			selectClause = select;
		else
			selectClause+=select;
	}
	public void addSelect(List<String> cols){
		if(selectClause==null)
			selectClause = "";
		
		Iterator<String> it = cols.iterator();
		while(it.hasNext()){
			if(!selectClause.equals("")){
				selectClause+=",";
			}
			String j = it.next();
			selectClause += j;
		}
	}
	public void addSelect(String[] sh){
		if(selectClause==null)
			selectClause = "";
		int it = 0;
		while(it<sh.length){
			if(!selectClause.equals("")){
				selectClause+=",";
			}
			String j = sh[it];
			selectClause += j;
			it++;
		}
	}
	public void setGroupBy(String s){
		groupClause = s;
	}
	public void setHaving(String s){
		havingClause = s;
	}
	public String getHaving(){
		return havingClause;
	}
	public void setOrderBy(String s){
		orderClause = s;
	}
	public String getOrderBy() {
		return orderClause;
	}
	public void setLimit(String s){
		limitClause = s;
	}
	public String getLimit(){
		return limitClause;
	}
	public String getGroupBy(){
		return groupClause;
	}
	public String[] getSelect(){
		if(selectClause!=null && !selectClause.isEmpty()){
			String[] k = selectClause.split(",");
			return k;
		}
		else return null;
	
	}
	public void setDistinct(boolean dis){
		distinct = dis;
	}
	public String getWhere(){
		return whereClause;
	}
	public boolean getDistinct(){
		return distinct;
	}

}
