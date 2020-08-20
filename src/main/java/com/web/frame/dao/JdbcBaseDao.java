package com.web.frame.dao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.web.frame.entity.table.system.User;
import com.web.frame.util.SessionContext;
import com.web.frame.util.Uuid;

@Scope("prototype")
@Repository
public class JdbcBaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@PersistenceContext
	private EntityManager entityManager;
	 /**
     * 代表mysql或者oracle  false:mysql  true:oracle
     */
    private boolean DBmark = false;
	
	/**
	 * 插入
	 * @param t
	 * @throws Exception 
	 */
	public <T> void insert(T t) throws  Exception  {
		
		User user = null;
		try {
			user = SessionContext.get("user");
		} catch (Exception e) {
		}
		if(user==null) {
			user = new User();
			user.setId("");
		}
		EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)entityManagerFactory.unwrap(SessionFactory.class);
		Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
		for(Map.Entry<String,EntityPersister> entity : persisterMap.entrySet()){
		    Class targetClass = entity.getValue().getMappedClass();
		    if(t.getClass() != targetClass) {
		    	continue;
		    }
		    SingleTableEntityPersister persister = (SingleTableEntityPersister)entity.getValue();
		    Iterable<AttributeDefinition> attributes = persister.getAttributes();
		    String entityName = targetClass.getSimpleName();//Entity的名称
		    String tableName = persister.getTableName();//Entity对应的表的英文名
		    
		    Field[] fields = targetClass.getDeclaredFields();
		    
		    StringBuilder columnSql = new StringBuilder();
	        StringBuilder unknownMarkSql = new StringBuilder();
	        List<Object> valueObj = new ArrayList<Object>();
		    
	        for (int i = 0; i < fields.length; i++) {
		    	 //得到属性
	            Field field = fields[i];
	            //打开私有访问
	            field.setAccessible(true);
	            //获取属性
	            String name = field.getName();
	            String[] columnName = new String[0];
	            try {
	            	columnName = persister.getPropertyColumnNames(name);
				} catch (Exception e) {
					// TODO: handle exception
				} //对应数据库表中的字段名
	            if(columnName.length==0) {
	            	continue;
	            }
            	//获取属性值
            	Object value = field.get(t);
            	if("create_date".equals(columnName[0])) {
            		value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            	}else if("create_id".equals(columnName[0])) {
            		value = user.getId() == null?"":user.getId();
            	}else if("id".equals(columnName[0])) {
            		if(value == null) {
            			value = Uuid.getUuid();
            		}
            	}
            	valueObj.add(value);
	            columnSql.append(columnName[0]);
	            columnSql.append(",");
	            unknownMarkSql.append("?");
	            unknownMarkSql.append(",");
	        }
		    StringBuilder sql = new StringBuilder();
	        sql.append("INSERT INTO ");
	        sql.append(tableName);
	        sql.append(" (");
	        sql.append(columnSql.length()>0?columnSql.substring(0, columnSql.length()-1):columnSql);
	        sql.append(" )  VALUES (");
	        sql.append(unknownMarkSql.length()>0?unknownMarkSql.substring(0, unknownMarkSql.length()-1):unknownMarkSql);
	        sql.append(" )");
	        Object[] bindArgs = valueObj.toArray();
	        jdbcTemplate.update(sql.toString(), bindArgs);
		    break;
		}
	}
	
	/**
	 * 批量插入
	 * @param list
	 * @throws Exception
	 */
	public <T> void batchInsert(List<T> list) throws Exception {
		
		if(list.size()==0) {
			return;
		}
		T t = list.get(0);
		User user = null;
		try {
			user = SessionContext.get("user");
		} catch (Exception e) {
		}
		if(user==null) {
			user = new User();
			user.setId("");
		}
		EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)entityManagerFactory.unwrap(SessionFactory.class);
		Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
		for(Map.Entry<String,EntityPersister> entity : persisterMap.entrySet()){
		    Class targetClass = entity.getValue().getMappedClass();
		    if(t.getClass() != targetClass) {
		    	continue;
		    }
		    SingleTableEntityPersister persister = (SingleTableEntityPersister)entity.getValue();
		    String entityName = targetClass.getSimpleName();//Entity的名称
		    String tableName = persister.getTableName();//Entity对应的表的英文名
		    Field[] fields = targetClass.getDeclaredFields();
		    
		    List<Object[]> valueList = new ArrayList<Object[]>();
		    StringBuilder columnSql = new StringBuilder();
		    StringBuilder unknownMarkSql = new StringBuilder();
		    StringBuilder sql = new StringBuilder();
		    
		    
		    for (int m = 0; m < list.size(); m++) {
		    	t = list.get(m);
		        List<Object> valueObj = new ArrayList<Object>();
			    
		        for (int i = 0; i < fields.length; i++) {
			    	 //得到属性
		            Field field = fields[i];
		            //打开私有访问
		            field.setAccessible(true);
		            //获取属性
		            String name = field.getName();
		            String[] columnName = new String[0];
		            try {
		            	columnName = persister.getPropertyColumnNames(name);
					} catch (Exception e) {
						// TODO: handle exception
					} //对应数据库表中的字段名
		            if(columnName.length==0) {
		            	continue;
		            } //对应数据库表中的字段名
	            	//获取属性值
	            	Object value = field.get(t);
	            	if("create_date".equals(columnName[0])) {
	            		value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	            	}else if("create_id".equals(columnName[0])) {
	            		value = user.getId() == null?"":user.getId();
	            	}else if("id".equals(columnName[0])) {
	            		if(value == null) {
	            			value = Uuid.getUuid();
	            		}
	            	}
	            	valueObj.add(value);
		            columnSql.append(columnName[0]);
		            columnSql.append(",");
		            unknownMarkSql.append("?");
		            unknownMarkSql.append(",");
		        }
		        
			    if(m == 0) {
			    	sql.append("INSERT INTO ");
			        sql.append(tableName);
			        sql.append(" (");
			        sql.append(columnSql.length()>0?columnSql.substring(0, columnSql.length()-1):columnSql);
			        sql.append(" )  VALUES (");
			        sql.append(unknownMarkSql.length()>0?unknownMarkSql.substring(0, unknownMarkSql.length()-1):unknownMarkSql);
			        sql.append(" )");
			    }
		        Object[] bindArgs = valueObj.toArray();
		        valueList.add(bindArgs);
			}
	        jdbcTemplate.batchUpdate(sql.toString(), valueList);
		    break;
		}
		
	}
	
	
	/**
     * 批量插入 -- 不推荐
    *
    * @param list 表字段（非属性）--数据
    * @param tableName 
    * @return
    * @throws Exception
    */
   public int[] batchInsert(String tableName, List<Map<String, Object>> list) throws Exception {
       if (CollectionUtils.isEmpty(list)) {
           return new int[1];
       }
       Map<String, Object>[] maps = new Map[list.size()];
       for(int i = 0;i< list.size(); i++) {
           maps[i] = list.get(i);
       }
       SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
       return simpleJdbcInsert.withTableName(tableName).executeBatch(maps);
   }

   
   /**
    * 修改
    * @param t 对象
    * @param byField where条件，类属性（非数据库字段）
    * @throws Exception
    */
   public <T> void update(T t,String byField) throws Exception {
	   
	   User user = null;
		try {
			user = SessionContext.get("user");
		} catch (Exception e) {
		}
		if(user==null) {
			user = new User();
			user.setId("");
		}
	   if(!StringUtils.isNotEmpty(byField)) {
		   byField = "id";
	   }
	   Object byFieldValue = null;
	   EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)entityManagerFactory.unwrap(SessionFactory.class);
		Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
		for(Map.Entry<String,EntityPersister> entity : persisterMap.entrySet()){
		    Class targetClass = entity.getValue().getMappedClass();
		    if(t.getClass() != targetClass) {
		    	continue;
		    }
		    SingleTableEntityPersister persister = (SingleTableEntityPersister)entity.getValue();
		    Iterable<AttributeDefinition> attributes = persister.getAttributes();
		    String entityName = targetClass.getSimpleName();//Entity的名称
		    String tableName = persister.getTableName();//Entity对应的表的英文名
		    
		    Field[] fields = targetClass.getDeclaredFields();
		    
		    StringBuilder columnSql = new StringBuilder();
	        List<Object> valueObj = new ArrayList<Object>();
		    
		    //属性
	        for (int i = 0; i < fields.length; i++) {
		    	 //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                String[] columnName = new String[0];
	            try {
	            	columnName = persister.getPropertyColumnNames(name);
				} catch (Exception e) {
					// TODO: handle exception
				} //对应数据库表中的字段名
	            if(columnName.length==0) {
	            	continue;
	            } //对应数据库表中的字段名
            	//获取属性值
            	Object value = field.get(t);
            	if("last_update_time".equals(columnName[0])) {
            		value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            	}else if("last_update_id".equals(columnName[0])) {
            		value = user.getId();
            	}
            	if(byField.equals(name)) {
            		byField = columnName[0];
            		byFieldValue = value;
            	}
            	if(value!=null) {
            		columnSql.append(columnName[0]+" = ?,");
            		valueObj.add(value);
            	}
			}
		    
		    StringBuilder sql = new StringBuilder();
	        sql.append("update ");
	        sql.append(tableName);
	        sql.append(" set ");
	        sql.append(columnSql.length()>0?columnSql.substring(0, columnSql.length()-1):columnSql);
	        sql.append(" where ");
	        sql.append( byField );
	        sql.append( " = " );
	        sql.append( " ? " );
	        valueObj.add(byFieldValue);
	        Object[] bindArgs = valueObj.toArray();
	        jdbcTemplate.update(sql.toString(), bindArgs);
		    break;
		}
	   
   }
	
   
   /**
    * 批量修改
    * @param list 只根据第一条数据属性修改，若之后有数据对应第一条属性的值为空，该字段值会更新为空！
    * @param byField where条件，类属性（非数据库字段）
    * @throws Exception
    */
   public <T> void batchUpdate(List<T> list,String byField) throws Exception {
	   
	   if(list.size()==0) {
		   return;
	   }
	   User user = null;
		try {
			user = SessionContext.get("user");
		} catch (Exception e) {
		}
		if(user==null) {
			user = new User();
			user.setId("");
		}
	   if(!StringUtils.isNotEmpty(byField)) {
		   byField = "id";
	   }
	   Object byFieldValue = null;
	   T t = list.get(0);
	   EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)entityManagerFactory.unwrap(SessionFactory.class);
		Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
		for(Map.Entry<String,EntityPersister> entity : persisterMap.entrySet()){
		    Class targetClass = entity.getValue().getMappedClass();
		    if(t.getClass() != targetClass) {
		    	continue;
		    }
		    SingleTableEntityPersister persister = (SingleTableEntityPersister)entity.getValue();
		    Iterable<AttributeDefinition> attributes = persister.getAttributes();
		    String entityName = targetClass.getSimpleName();//Entity的名称
		    String tableName = persister.getTableName();//Entity对应的表的英文名
		    
		    Field[] fields = targetClass.getDeclaredFields();
		    
		    StringBuilder sql = new StringBuilder();
		    StringBuilder columnSql = new StringBuilder();
	        List<Object[]> valueList = new ArrayList<Object[]>();
		    
	        List<String> oneDataField = new ArrayList<String>();
	        
	        for (int k = 0; k < list.size(); k++) {
				t = list.get(k);
				
				List<Object> valueObj = new ArrayList<Object>();
				//属性
		        for (int i = 0; i < fields.length; i++) {
			    	 //得到属性
	                Field field = fields[i];
	                //打开私有访问
	                field.setAccessible(true);
	                //获取属性
	                String name = field.getName();
	                String[] columnName = new String[0];
		            try {
		            	columnName = persister.getPropertyColumnNames(name);
					} catch (Exception e) {
						// TODO: handle exception
					} //对应数据库表中的字段名
		            if(columnName.length==0) {
		            	continue;
		            } //对应数据库表中的字段名
	            	//获取属性值
	            	Object value = field.get(t);
	            	if("last_update_time".equals(columnName[0])) {
	            		value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	            	}else if("last_update_id".equals(columnName[0])) {
	            		value = user.getId();
	            	}
	            	if(byField.equals(name)) {
	            		byField = columnName[0];
	            		byFieldValue = value;
	            	}
	            	if(k == 0) {//只根据第一条数据非空属性修改
	            		if(value!=null) {
	            			oneDataField.add(name);//只根据第一条数据非空属性修改
		            		columnSql.append(columnName[0]+" = ?,");
		            		valueObj.add(value);
		            	}
	            	}else {//后面的数据根据第一条数据属性值修改，不传则修改为空
	            		boolean isField = false;
	            		for (int j = 0; j < oneDataField.size(); j++) {
							if(name.equals(oneDataField.get(j))) {
								isField = true;
								break;
							}
						}
	            		if(isField) {
		            		valueObj.add(value);
	            		}
	            	}
				}
		        
		        if(k == 0) {
		        	sql.append("update ");
		        	sql.append(tableName);
		        	sql.append(" set ");
		        	sql.append(columnSql.length()>0?columnSql.substring(0, columnSql.length()-1):columnSql);
		        	sql.append(" where ");
		        	sql.append( byField );
		        	sql.append( " = " );
		        	sql.append( " ? " );
		        }
		        valueObj.add(byFieldValue);
		        Object[] bindArgs = valueObj.toArray();
		        valueList.add(bindArgs);
			}
	        jdbcTemplate.batchUpdate(sql.toString(), valueList);
		    break;
		}
   }
   
   /**
    * 批量修改 -- 不推荐
    * @param dataList
    * @param tableName
    * @param field
    */
   public void batchUpdate(List<Map<String, Object>> dataList,String tableName,String field){
		
		if(!StringUtils.isNotEmpty(field)){
			field = "id";
		}else{
			field = field.toLowerCase();
		}	
				
		if(dataList.size() > 0){
		
			String [] sqlArr = new String[dataList.size()];
			for(int i = 0 ; i < dataList.size();i++){
				String tempSql = "";
				String fieldVal = "";
				Map<String, Object> map = dataList.get(i);
				Set<String> keySet = map.keySet();
				for (String key:keySet) {
					if(key.toLowerCase().equals(field)){
						fieldVal = map.get(key).toString();
						continue;
					}
					tempSql+=key+"='"+map.get(key)+"',";
				}
				if(!"".equals(tempSql)){
					tempSql = tempSql.substring(0, tempSql.length()-1);
					tempSql = "update "+tableName+" set "+tempSql+" where "+field+" = '"+fieldVal+"'";
					sqlArr[i] = tempSql;
				}
			}
			jdbcTemplate.batchUpdate(sqlArr);
		}
	}
   
   /**
     * 查询  -- 返回数据库字段及对应数据
    * @param sql
    * @return
    */
   public List<Map<String,Object>> query(String sql){
		
		return jdbcTemplate.queryForList(sql);
	}
	
   /**
    * 查询 --返回实体类对象（非数据库字段）
    * @param sql
    * @param c
    * @return
    */
	public <T> List<T> query(String sql,Class c){
		
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(c));
	}
   
	public <T> T queryUnique(String sql,Class c) {
		
		List<T> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(c));
		if(list.size() == 0) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/**
     * 分页查询-返回数据库对应字段数据
     * @param page:页数
     * @param rows:每页显示的条数
     */
    public List<Map<String,Object>> query(String sql,String page,String rows){
        
    	if(DBmark){
    		//Oracle
        	int endNum = (Integer.parseInt(page))*(Integer.parseInt(rows));
        	int startNum = endNum - (Integer.parseInt(rows)) + 1;
        	endNum = endNum + 1;

        	sql = "select b.* from ( select a.*,rownum N from( "+sql+" ) a WHERE  rownum<"+endNum+" ) b where N>="+startNum;
            return jdbcTemplate.queryForList(sql);
    	}else{
    		//MySql
    		int pageNum = Integer.parseInt(page);
    		int rowsNum = Integer.parseInt(rows);
    		int start = (pageNum-1)*rowsNum;
    		
    		sql = sql +" limit "+start+","+rows;
    		return jdbcTemplate.queryForList(sql);
    	}
    }
    
    /**
     *  分页查询 -返回对象列表
     * @param sql
     * @param page
     * @param rows
     * @param c
     * @return
     */
    public <T> List<T> query(String sql,String page,String rows,Class<T> c){
        
    	if(DBmark){
    		//Oracle
        	int endNum = (Integer.parseInt(page))*(Integer.parseInt(rows));
        	int startNum = endNum - (Integer.parseInt(rows)) + 1;
        	endNum = endNum + 1;

        	sql = "select b.* from ( select a.*,rownum N from( "+sql+" ) a WHERE  rownum<"+endNum+" ) b where N>="+startNum;
        	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(c));
    	}else{
    		//MySql
    		int pageNum = Integer.parseInt(page);
    		int rowsNum = Integer.parseInt(rows);
    		int start = (pageNum-1)*rowsNum;
    		
    		sql = sql +" limit "+start+","+rows;
    		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(c));
    	}
    }
    
    /**
      * 执行sql
     * @param sql
     */
    public void execute(String sql) {
    	
    	jdbcTemplate.execute(sql);
    }
	
    
    /**
	 * 批量真删除
	 * @param ids
	 * @param tableName
	 * @return
	 */
	public void batchDelete(String ids,String tableName,String field){
		
		String[] idArr = ids.split(",");
		if(!StringUtils.isNotEmpty(field)){
			field = "id";
		}	
		String[] sqlArr = new String[idArr.length];
		for (int i = 0; i < idArr.length; i++) {
			String sql = " delete from "+tableName+" where "+field+" = '"+idArr[i]+"' ";
			sqlArr[i] = sql;
			jdbcTemplate.batchUpdate(sqlArr);
		}
	}
	
	/**
	 * 批量假删除
	 * @return
	 */
	public void batchDeleteUpdate(String ids,String tableName,String field){
		
		String[] idArr = ids.split(",");
		if(!StringUtils.isNotEmpty(field)){
			field = "id";
		}	
		String[] sqlArr = new String[idArr.length];
		for (int i = 0; i < idArr.length; i++) {
			String sql = " update "+tableName+" set is_delete = '1' where "+field+" = '"+idArr[i]+"' ";
			sqlArr[i] = sql;
		}
		jdbcTemplate.batchUpdate(sqlArr);
	}
	
}