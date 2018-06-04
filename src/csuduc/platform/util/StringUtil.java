/**
 * <p>title:StringUtil.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月4日下午10:51:51
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

/**  
 * 创建时间：2015年11月4日 下午10:51:51  
 * 项目名称：zwplatform   
 * 文件名称：StringUtil.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015年11月4日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>
 * Title: StringUtil<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2015年11月4日
 */
public class StringUtil {

	public  static String getDateStr(String strFormat){
		
		return new SimpleDateFormat(strFormat).format(new Date());
	}
	public  static boolean isNullOrEmpty(String str) {

		if ((null == str) || (str.length() == 0)) {
			return true;
		}

		return false;
	}
	
	public static boolean validateLength(String str, int min, int max){
		if (min < 1) {
			throw new IllegalArgumentException("min length must > 0");
		}
		if (max < min) {
			throw new IllegalArgumentException("max length must > min length");
		}
		if (isNullOrEmpty(str))  return false;
		if (str.length() < min || str.length() > max) {
			return false;
		}
		return true;
	}
	
	public static boolean invalidateLength(String str, int min, int max){
		return !validateLength(str, min, max);
	}
	
	
	public static boolean notEmpty(String s){
		return !isNullOrEmpty(s);
	}
	
	public static boolean notEmptyOrDefault(String s, String defValue){
		return notEmpty(s) && (!s.equals(defValue));
	}
	
		
	public  static List<String> split(String src, char chr){
		if (isNullOrEmpty(src)) {
			return null;
		}
		List<String> strList = new ArrayList<String>();

		int idxBeg = 0;
		int idxEnd = 0;
		for (; idxEnd < src.length(); idxEnd++) {
			if(src.charAt(idxEnd)==chr){
				if (idxEnd>idxBeg) {
					strList.add(src.substring(idxBeg, idxEnd));
				}
				idxBeg = idxEnd+1;
			}
		}
		
		if (idxEnd>idxBeg) {
			strList.add(src.substring(idxBeg, idxEnd));
		}
		return strList;
	}
	public static void testSplit(String str, char chr1, char chr2){
		System.out.println("==========="+str);
		List<String> str1Arr = split(str, chr1);
		for (String string : str1Arr) {
			System.out.println("---"+string);
			List<String> str1ArrChild = split(string, chr2);
			for (String string2 : str1ArrChild) {
				System.out.println(string2);
			}
		}
	}
	
	public  static Date strToDate(String str,String format){
		try  
		{  
		    SimpleDateFormat sdf = new SimpleDateFormat(format);  
		    return  sdf.parse(str); 
		}  
		catch (ParseException e)  
		{  
		    e.printStackTrace();
		    return null;
		}
		
	}
	public  static Timestamp strToTimeStamp(String str,String format){
		try  
		{  
		    return new Timestamp(strToDate(str, format).getTime());
		}  
		catch (Exception e)  
		{  
		    e.printStackTrace();
		    return null;
		}
		
	}
	
	
	/**
	 * <p>
	 * Title: main<／p>
	 * <p>
	 * Description: <／p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("----test----");
		
		String str1 = "_IALT_&_1A_|_IALT_&_1C_";
		String str2 = "_ZWF_";
		String str3 = "_ZW360_|_ZW295_|_ZW265_";
		String str4 = "TS_TG02_MWI_VNI_IMG_20160513091839_20100101012000_20100101021509_000_0C.tif";
//		testSplit(str1,'|','&');
//		testSplit(str2,'|','&');
//		testSplit(str3,'|','&');
//		testSplit(str4, '_', '&');
		
		String str5 ="0_4_5_2_1-0_4_2_1";
		List<String> list = split(str5, '-');
		for (String item : list) {
			System.out.println(item);
		}
		String dataRowStr = "data[10][crdt_tp]";
		String modelName = "data[10]";
		System.out.println(dataRowStr.substring(modelName.length()+1, dataRowStr.length()-1));
		
		String modelPreStr = "";
		if (dataRowStr.startsWith("data[") ) {
			// 可以获得主键、但没做
			int rightFlag = dataRowStr.indexOf(']');
			modelPreStr = dataRowStr.substring(0, rightFlag+1);
			System.out.println(modelPreStr);
		}
		StringBuilder whereStr = new StringBuilder();
		System.out.println("whereStr len:"+whereStr.length() + " -"+whereStr.toString()+ "-");
		//		String strDate1 = "20160513091839";
//		String strDate2 = "20160513091840";
//		Date date1 = strToDate(strDate1, "yyyyMMddHHmmss");
//		Date date2 = strToDate(strDate2, "yyyyMMddHHmmss");
//		System.out.println(date1.getTime() - date2.getTime());
		Integer i1 = 70;
		Integer i2 = 90;
		String strResCnt = String.format("%d%%(%d/%d)", i1*100/i2, i1, i2);
		System.out.println(strResCnt);
		
		String str20180605_1 = "asdfa.xls";
		System.out.println(str20180605_1.substring(str20180605_1.length()-3));
	}

}
