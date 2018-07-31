/**
 * <p>title:ComUtil.java<／p>
 * <p>Description: <／p>
 * @date:2016年11月10日下午6:15:15
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**  
 * 创建时间：2016年11月10日 下午6:15:15  
 * 项目名称：UtilZW   
 * 文件名称：ComUtil.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年11月10日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>
 * Title: ComUtil<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2016年11月10日
 */
final public class ComUtil {

	/**
	 * <p>
	 * Title: isEmptyStr<／p>
	 * <p>
	 * Description: 判断字符串是否为null或空串 或 只有空格 <／p>
	 * 
	 * @param aStr
	 * @return
	 */
	public final static boolean isEmptyStr(String aStr) {
		if (null == aStr || 0 == aStr.length() || 0 == aStr.trim().length()) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Title: isEmptyList<／p>
	 * <p>
	 * Description: 判断列表是否为null 或 size为0 <／p>
	 * 
	 * @param aList
	 * @return
	 */
	public final static <T> boolean isEmptyList(List<T> aList) {
		if (null == aList || 0 == aList.size()) {
			return true;
		}
		return false;
	}

	public final static <K, V> boolean isEmptyMap(Map<K, V> aMap) {
		if (null == aMap || 0 == aMap.size()) {
			return true;
		}
		return false;
	}

	public static Double formatDouble(Double d) {
		BigDecimal bg = new BigDecimal(d);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

	public static String formatDoubleToIntString(Double d) {
		return decimalFormat.format(d);
	}

	public static boolean notNullAndZero(Integer i) {
		return Objects.nonNull(i) && 0 != i;
	}

	public static boolean notNullAndZero(Long i) {
		return Objects.nonNull(i) && 0 != i;
	}

	public static void main(String[] args) {
		Integer i1 = 0;
		Integer i2 = null;
		Integer i3 = 1;
		System.out.println(notNullAndZero(i1));
		System.out.println(notNullAndZero(i2));
		System.out.println(notNullAndZero(i3));
	}
}
