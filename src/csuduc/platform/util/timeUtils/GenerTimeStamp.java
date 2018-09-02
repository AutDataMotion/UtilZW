/**
 * <p>title:GenerTimeStamp.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月9日下午4:50:02
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util.timeUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**  
 * 创建时间：2015年11月9日 下午4:50:02  
 * 项目名称：UtilZW   
 * 文件名称：GenerTimeStamp.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015年11月9日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>
 * Title: GenerTimeStamp<／p>
 * <p>
 * Description:
 * 
 * 
 * //方法 一 System.currentTimeMillis(); 最快 //方法 二
 * Calendar.getInstance().getTimeInMillis(); //方法 三 new Date().getTime(); <／p>
 * 
 * @author ZhongwengHao
 * @date 2015年11月9日
 */
public class GenerTimeStamp {
	
	private final static String DF_yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	private final static String DF_yyyy_MM_dd = "yyyy-MM-dd";
	private final static String DF_yyyyMMdd = "yyyyMMdd";
	
	private static ThreadLocal<SimpleDateFormat>  df_datetime = new ThreadLocal<SimpleDateFormat>();

	private static ThreadLocal<SimpleDateFormat>  df_date = new ThreadLocal<SimpleDateFormat>();

	public synchronized static Timestamp dateToTimeStamp(Date date) {
		String time = fetchDateFormatDatetime().format(date);
		return Timestamp.valueOf(time);
	}
	public static String pickYearMonthDay(int year, Timestamp ts){
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		return String.format("%d%d%d", year, c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
	}
	
	public static int fetchYearByStep(int step){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, step);
		return c.get(Calendar.YEAR);
	}
	
	public long getSystemTimeMillisNow() {
		return System.currentTimeMillis();
	}
	
	public static SimpleDateFormat fetchDateFormatDatetime(){
		if (null == df_datetime.get()) {
			df_datetime.set(new SimpleDateFormat(DF_yyyy_MM_dd_HHmmss));
		}
		return df_datetime.get();
	}

	public  static String TimestampToStr(Timestamp timestamp) {
		try {	
			return fetchDateFormatDatetime().format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// df_date
	public static String pickDateStr(Timestamp timestamp){
		if (null == df_date.get()) {
			df_date.set(new SimpleDateFormat(DF_yyyy_MM_dd));
		}
		return df_date.get().format(timestamp);
	}
	
	public static String pickDateStrKey(Timestamp timestamp){
		if (null == df_date.get()) {
			df_date.set(new SimpleDateFormat(DF_yyyyMMdd));
		}
		return df_date.get().format(timestamp);
	}
	
	public static Timestamp DateKeyToTimestamp(String datekey){
		return Timestamp.valueOf(String.format("%s 00:00:00", datekey));
	}
	/**
	 * <p>
	 * Title: main<／p>
	 * <p>
	 * Description:
	 * 
	 * <／p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dtstr1 = "";
		Timestamp timestamp = dateToTimeStamp(new Date());
		dtstr1 = TimestampToStr(timestamp);
		System.out.println(dtstr1);
		String str2 = pickDateStr(timestamp);
		System.out.println(str2);
		
		String dateStr1 = "2018-08-09 00:00:00";
		Timestamp timestamp2 = Timestamp.valueOf(dateStr1);
		System.out.println(timestamp2);
	}

}
