/**
 * <p>title:GenerTimeStamp.java<锛弍>
 * <p>Description: <锛弍>
 * @date:2015骞�11鏈�9鏃ヤ笅鍗�4:50:02
 * @author锛歓hongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util.timeUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**  
 * 鍒涘缓鏃堕棿锛�2015骞�11鏈�9鏃� 涓嬪崍4:50:02  
 * 椤圭洰鍚嶇О锛歎tilZW   
 * 鏂囦欢鍚嶇О锛欸enerTimeStamp.java  
 * 绫昏鏄庯細  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015骞�11鏈�9鏃�     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>
 * Title: GenerTimeStamp<锛弍>
 * <p>
 * Description:
 * 
 * 
 * //鏂规硶 涓� System.currentTimeMillis(); 鏈�蹇� //鏂规硶 浜�
 * Calendar.getInstance().getTimeInMillis(); //鏂规硶 涓� new Date().getTime(); <锛弍>
 * 
 * @author ZhongwengHao
 * @date 2015骞�11鏈�9鏃�
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
		return String.format("%d%02d%02d", year, c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
	}
	
	public static String pickMonthDay(Timestamp ts){
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		return String.format("%02d%02d",  c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
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
	
	public static Integer dayNumOfYear(Timestamp st){
		if (Objects.isNull(st)) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(st);
		return cal.get(Calendar.DAY_OF_YEAR);
	}
	/**
	 * <p>
	 * Title: main<锛弍>
	 * <p>
	 * Description:
	 * 
	 * <锛弍>
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
		
		String dateStr1 = "2018-08-19 01:00:00";
		Timestamp timestamp2 = Timestamp.valueOf(dateStr1);
		System.out.println(timestamp2);
		
		String pickYearMonth = pickYearMonthDay(2000, timestamp2);
		System.out.println(pickYearMonth);
	}

}
