/**
 * <p>title:ClassObjUtils.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月5日上午10:28:34
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * 创建时间：2015年11月5日 上午10:28:34  
 * 项目名称：zwplatform   
 * 文件名称：ClassObjUtils.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015年11月5日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>Title: ClassObjUtils<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2015年11月5日
 */
public class ClassObjUtils {

	/** 
	 * 根据属性名获取属性值 
	 * */  
	   private Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	           e.printStackTrace(); 
	           return null;    
	       }    
	   }   
	     
	   /** 
	    * 获取属性名数组 
	    * */  
	   private String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        System.out.println(fields[i].getType());  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
	   }  
	     
	   /** 
	    * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list 
	    * */  
	   private List getFiledsInfo(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	        List list = new ArrayList();  
	        Map infoMap=null;  
	    for(int i=0;i<fields.length;i++){  
	        infoMap = new HashMap();  
	        infoMap.put("type", fields[i].getType().toString());  
	        infoMap.put("name", fields[i].getName());  
	        infoMap.put("value", getFieldValueByName(fields[i].getName(), o));  
	        list.add(infoMap);  
	    }  
	    return list;  
	   }  
	     
	   /** 
	    * 获取对象的所有属性值，返回一个对象数组 
	    * */  
	   public Object[] getFiledValues(Object o){  
	    String[] fieldNames=this.getFiledName(o);  
	    Object[] value=new Object[fieldNames.length];  
	    for(int i=0;i<fieldNames.length;i++){  
	        value[i]=this.getFieldValueByName(fieldNames[i], o);  
	    }  
	    
	    return value;  
	   }      
	/**
	 * <p>Title: main<／p>
	 * <p>Description: <／p>
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
