/**
 * <p>title:RandomUtil.java<／p>
 * <p>Description: <／p>
 * @date:2016年5月19日下午11:30:52
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util;

import java.util.Random;

/**  
 * 创建时间：2016年5月19日 下午11:30:52  
 * 项目名称：UtilZW   
 * 文件名称：RandomUtil.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年5月19日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>Title: RandomUtil<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年5月19日
 */
public class RandomUtil {
	private static Random rand = new Random(); 
	/**
	 * <p>Title: main<／p>
	 * <p>Description: <／p>
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isMobile());
	}

	public static int getRandom(int min, int max){
		if (min > max) {
			return max;
		}
		return rand.nextInt(max - min + 1) + min;
	}
	
	
	private final static String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
			"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
			"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
			"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
			"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
			"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
			"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
			"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
			"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
			"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
			"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
			"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
			"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v",
			"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
			"Googlebot-Mobile", "MicroMessenger"};
	
	private static String getRequest() {
		return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36\r\n" + 
				"X-Requested-With: XMLHttpRequest";
	}
	public static boolean isMobile() {
		
		String userAgent = getRequest();
		for (int i = 0; i < mobileAgents.length; i++) {
			if (userAgent.indexOf(mobileAgents[i]) != -1) {
				System.out.println(mobileAgents[i]);
				return true;
			}
		}
		return false;
	}
}
