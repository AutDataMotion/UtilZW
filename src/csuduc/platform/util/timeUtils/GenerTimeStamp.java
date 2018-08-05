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
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static ThreadLocal<SimpleDateFormat>  df_date = new ThreadLocal<SimpleDateFormat>();

	public synchronized static Timestamp dateToTimeStamp(Date date) {
		String time = df.format(date);
		return Timestamp.valueOf(time);
	}

	public long getSystemTimeMillisNow() {

		return System.currentTimeMillis();
	}

	public synchronized static String TimestampToStr(Timestamp timestamp) {
		try {
			// 方法一
			return df.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// df_date
	public static String pickDateStr(Timestamp timestamp){
		if (null == df_date.get()) {
			df_date.set(new SimpleDateFormat("yyyy-MM-dd"));
		}
		return df_date.get().format(timestamp);
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
	}

}
