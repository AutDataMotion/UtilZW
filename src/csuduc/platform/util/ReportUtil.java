/*
 * 
 * author:lyf
 * */
package csuduc.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ReportUtil {
	public static final String ftlpath_doc = "files/productReport/reportTemplate/doc/";
	public static final String ftlpath_pdf = "files/productReport/reportTemplate/pdf/";
	
	public static final String ftlname = "reportTemplate.ftl";
	
	public static final String reportParentPath = "files/productReport/";
	
	//数据产品的存储
	public static final String productDataPath = "E:/thairiceproduct/";
	//arcgisServer的工作路径
	public static final String arcgisserver_shp_workspacePath = "E:/arcgisserver_shp_workspace/";
	
	public static final String mkUserReportDirectory(String userID,String ProductKind)
	{
		File file = null;
		try {
			String WebContentPath =  getWebContentPath();
			String UserReportDirectory = WebContentPath+reportParentPath+userID+"/"+ProductKind;
			file = new File(UserReportDirectory);
			if(!file.exists())
			{
				if(file.mkdirs())
				{
					return UserReportDirectory;
				}
			}
			else {
				
				return UserReportDirectory;
			}
		}catch(Exception e)
		{
			
		}finally {
			file = null;
		}
		return null;
	}
	 public static void WriteStringToFile(String str,String filePath) {
	        try {
	            FileOutputStream fos = new FileOutputStream(filePath);
	            //String s = "http://www.jb51.netl";
	            fos.write(str.getBytes());
	            fos.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	 
	public static boolean generateImage(String imgStr, String imagefilename,String userID,String ProductKind) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			String imagefile = null;
			
			String UserReportDirectory = mkUserReportDirectory(userID,ProductKind);
			
			imagefile = UserReportDirectory+"/"+imagefilename;
			
			OutputStream out = new FileOutputStream(imagefile);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}catch (Exception e)
		{
			return false;
		}
	}
	//根据url生成临时图片文件
	public static String generateTempMapImage(String url,String imagefilename,String userID,String ProductKind) {
		if (url == null)
			return null;
		
		try {
			InputStream in = getInputStreamByGet(url);
			//String tempImg = getWebContentPath()+reportParentPath+userID+"/"+ProductKind+"/maptemp.png";
			String UserReportDirectory = mkUserReportDirectory(userID,ProductKind);
			String tempImg = UserReportDirectory+"/"+imagefilename;
			File file = new File(tempImg);
			if(saveData(in, file))
			{
				return tempImg;
			}
			return null;
		}catch (Exception e)
		{
			return null;
		}
	}
	public static String getImageStr(InputStream imgStream) {
	    //InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        //inputStream = imgStream;
	        data = new byte[imgStream.available()];
	        imgStream.read(data);
	        imgStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
    // 通过get请求得到读取器响应数据的数据流
   public static InputStream getInputStreamByGet(String url) {
       try {
           HttpURLConnection conn = (HttpURLConnection) new URL(url)
                   .openConnection();
           conn.setReadTimeout(5000);
           conn.setConnectTimeout(5000);
           conn.setRequestMethod("GET");

           if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
               InputStream inputStream = conn.getInputStream();
               return inputStream;
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
// 通过get请求得到读取器响应数据的数据流
   public static String getImageStrByGet(String url) {
       try {
           HttpURLConnection conn = (HttpURLConnection) new URL(url)
                   .openConnection();
           conn.setReadTimeout(5000);
           conn.setConnectTimeout(5000);
           conn.setRequestMethod("GET");

           if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
               	InputStream inputStream = conn.getInputStream();
               
               	byte[] data = null;
       	    
       	        //inputStream = imgStream;
       	        data = new byte[inputStream.available()];
       	        inputStream.read(data);
       	        inputStream.close();
       	    
	       	    // 加密
	       	    BASE64Encoder encoder = new BASE64Encoder();
	       	    return encoder.encode(data);
       	    
               //return inputStream;
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
       // 将服务器响应的数据流存到本地文件
   public static boolean saveData(InputStream is, File file) {
	   BufferedInputStream bis = null;
	   BufferedOutputStream bos = null;
       try{
    	   bis = new BufferedInputStream(is);
           bos = new BufferedOutputStream(new FileOutputStream(file));
           byte[] buffer = new byte[1024];
           int len = -1;
           while ((len = bis.read(buffer)) != -1) {
               bos.write(buffer, 0, len);
               bos.flush();
               
           }
           bos.close();
           return true;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }finally {  
           try {  
               if (bis != null) {  
            	   bis.close();  
               }  
               if (bos != null) {  
            	   bos.close();  
               }  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
   }
   public static boolean fileCopy(File form,File to) {
	   FileInputStream input = null;
	   FileOutputStream output = null;
	   try {
		   input = new FileInputStream(form);
		   output = new FileOutputStream(to);
		   
		   byte[] bt = new byte[1024];
		   int realbyte = 0;
		   while((realbyte = input.read(bt))>0) {
			   output.write(bt, 0, realbyte);
		   }
		   output.close();
		   return true;
	   }catch(Exception e) {
//		   input.close();  
		   e.printStackTrace();
		   return false;
	   }finally {
		   try {
			   if (input != null) {  
	                input.close();  
	            }  
	            if (output != null) {  
	                output.close();  
	            }
//	            return true;
		   }catch(Exception e) {
			   e.printStackTrace();
			   
		   }
		   
	   }
//	   return false;
   }
   public static String exportSimpleWord(Map<Object, Object> dataMap,String userID,String nowTime,String ProductKind,String areaCode,String productDate){  
	   Writer out = null;
	   try {
		   // 要填充的数据, 注意map的key要和word中${xxx}的xxx一致  
//		      Map<String,String> dataMap = new HashMap<String,String>();  
//		      dataMap.put("title", "Yield");  
//		      dataMap.put("img", staPicUrl);  
		     //创建报告目录文件夹
		   	String UserReportDirectory = mkUserReportDirectory(userID,ProductKind);
		    //Configuration用于读取ftl文件  
		      //Configuration configuration = new Configuration();  
		      //configuration.setDefaultEncoding("utf-8");  
		        
		      /*以下是两种指定ftl文件所在目录路径的方式, 注意这两种方式都是 
		       * 指定ftl文件所在目录的路径,而不是ftl文件的路径 
		       */  
		      //指定路径的第一种方式(根据某个类的相对路径指定)  
		      //configuration.setClassForTemplateLoading(this.getClass(),"");  
		        
		      //指定路径的第二种方式,我的路径是C:/a.ftl  
		      //E:\codingWorkspace\ThaiRiceRS\WebContent\
		      String DirectoryForTemplatePath = getWebContentPath()+ftlpath_doc+ProductKind+"/";
		      
		      //configuration.setDirectoryForTemplateLoading(new File(DirectoryForTemplatePath));  
		      //Template t =  configuration.getTemplate("reportTemplate.ftl","utf-8"); 
		      Template tpl = PdfHelper.getFreemarkerConfig(DirectoryForTemplatePath).getTemplate(ftlname,"utf-8");
		      
//		      Date day = new Date();
//		      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		      String nowTime = df.format(day);
		      
		      //String reportFileName = areaCode+"&c_"+nowTime.replace(':', '_')+"&p_"+productDate+".doc";
		      String reportFileName = nowTime.replace(':', '_')+".doc";
		      // 输出文档路径及名称  
		     File outFile = new File(UserReportDirectory+"/"+reportFileName);  
		       
		     //以utf-8的编码读取ftl文件  
		     
		     out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);  
		     tpl.process(dataMap, out);  
		     out.close(); 
		     
		     return "/"+reportParentPath+userID+"/"+ProductKind+"/"+reportFileName;
		     
	   }catch (Exception e)
	   {
		   e.printStackTrace();
		   try {
			   out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
	   }
       
   }
   public static String export2Pdf(Map<Object, Object> dataMap,String userID,String nowTime,String ProductKind,String areaCode,String productDate){  
	   try {
//		   Map<Object, Object> o=new HashMap<Object, Object>();  
//           //存入一个集合  
//           List<String> list = new ArrayList<String>();  
//           list.add("lyf");  
//           list.add("wxy");  
//           list.add("lyf");  
//           list.add("wxy");
//           
//           o.put("name", "http://www.xdemo.org/");  
//           o.put("nameList", list);  
              
           //String path=PdfHelper.getPath(); 
		   String DirectoryForTemplatePath = getWebContentPath()+ftlpath_pdf+ProductKind+"/";
		   
		   String UserReportDirectory = mkUserReportDirectory(userID,ProductKind);
		   
           //String path = getWebContentPath()+"files/productReport/pdf/";
//		   Date day = new Date();
//	       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	       String nowTime = df.format(day);
//	      
	       //String reportFileName = areaCode+"&c_"+nowTime.replace(':', '_')+"&p_"+productDate+".pdf";
	       String reportFileName = nowTime.replace(':', '_')+".pdf";
		      // 输出文档路径及名称  
		  
		   String outputFile = UserReportDirectory+"/"+reportFileName;  
		   
           PdfUtils.generateToFile(DirectoryForTemplatePath, ftlname,UserReportDirectory, dataMap, outputFile); 
           
           return "/"+reportParentPath+userID+"/"+ProductKind+"/"+reportFileName;
           
		   //return "/files/productReport/pdf/"+"report.pdf";
		     
	   }catch (Exception e)
	   {
		   e.printStackTrace();
		   return null;
	   }
       
   }  
   public static String getWebContentPath()
   {
	   try {
		   	String t=Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
			int num=t.indexOf("WEB-INF");
			String path=t.substring(1,num);
			return path;
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   return null;
   }
   public static boolean deleteReportFileByFileUrl(String cxt,String FileUrl)
   {
	
	   try {
		   
		   String filePath = getWebContentPath()+FileUrl.substring(FileUrl.indexOf(reportParentPath));
		   
		   File reportFile = new File(filePath);
		   if(reportFile.exists()&&reportFile.isFile())
		   {
			   if(reportFile.delete())
			   {
				   System.out.println("删除文件"+filePath+"成功！");
				   return true;
			   }
			   else {
				   System.out.println("删除文件"+filePath+"失败！");
				   return false;
			   }
		   }
		   else {
			   System.out.println("删除文件"+filePath+"失败！文件不存在！");
			   return false;
		   }
	   }catch(Exception e)
	   {
		   e.printStackTrace();  
           return false;
	   }
	   
	   
   }
   //String->Date
   public static Date getDateFromString(String dateStr) {
	   Date date = new Date();   
       //注意format的格式要与日期String的格式相匹配   
       DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
       try {   
           date = sdf.parse(dateStr);   
           return date;
           //System.out.println(date.toString());   
       } catch (Exception e) {   
           e.printStackTrace();  
           return null;
       }  
   }
   //Date->String
   public static String getStringFromDate(Date date) {
	   String dateStr = "";   
       //Date date = new Date();   
       //format的格式可以任意   
       DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");   
       
       try {   
           dateStr = sdf.format(date);   
           //System.out.println(dateStr);   
           return dateStr;
       } catch (Exception e) {   
           e.printStackTrace();
           return null;
       } 
   }
   public static class Area_Yield {  
	    private String name;  
	    private String code;  
	    private String value;
	    public Area_Yield(String name, String code, String value) {  
	        super();  
	        this.name = name;  
	        this.code = code;  
	        this.value = value;  
	    }  
	      
	    public Area_Yield() {  
	        super();  
	    }  
	    public void setName(String name)
	    {
	    	this.name = name;
	    }
	    public String getName()
	    {
	    	return this.name;
	    }
	    public void setCode(String code)
	    {
	    	this.code = code;
	    }
	    public String getCode()
	    {
	    	return this.code;
	    }
	    public void setValue(String value)
	    {
	    	this.value = value;
	    }
	    public String getValue()
	    {
	    	return this.value;
	    }
	//生成set和get方法,此处省略  
	}
   public static class Drought {  
	    private String name;  
	    private String code;  
	    private String moist;
	    private String normal;
	    private String lightdrought;
	    private String middling;
	    private String heavy;
	    public Drought(String name, String code, String moist,String normal,String lightdrought,String middling,String heavy) {  
	        super();  
	        this.name = name;  
	        this.code = code;  
	        this.moist = moist;
	        this.normal = normal;
	        this.lightdrought = lightdrought;
	        this.middling = middling;
	        this.heavy = heavy;
	    }  
	      
	    public Drought() {  
	        super();  
	    }  
	    public void setName(String name)
	    {
	    	this.name = name;
	    }
	    public String getName()
	    {
	    	return this.name;
	    }
	    public void setCode(String code)
	    {
	    	this.code = code;
	    }
	    public String getCode()
	    {
	    	return this.code;
	    }
	    public void setMoist(String moist)
	    {
	    	this.moist = moist;
	    }
	    public String getMoist()
	    {
	    	return this.moist;
	    }
	    public void setNormal(String normal)
	    {
	    	this.normal = normal;
	    }
	    public String getNormal()
	    {
	    	return this.normal;
	    }
	    public void setLightdrought(String lightdrought)
	    {
	    	this.lightdrought = lightdrought;
	    }
	    public String getLightdrought()
	    {
	    	return this.lightdrought;
	    }
	    public void setMiddling(String middling)
	    {
	    	this.middling = middling;
	    }
	    public String getMiddling()
	    {
	    	return this.middling;
	    }
	    public void setHeavy(String heavy)
	    {
	    	this.heavy = heavy;
	    }
	    public String getHeavy()
	    {
	    	return this.heavy;
	    }
	//生成set和get方法,此处省略  
	}
   public static List<Drought> getDrought(String staData)
   {
	   JSONArray  AreaArray=JSONArray.fromObject(staData);
	   
	   List<Drought> list = new ArrayList<Drought>();
	// JSONArray的遍历  
       for (int i = 0; i < AreaArray.size(); i++) {  
           JSONObject jsonObject2 = AreaArray.getJSONObject(i);  
           //Area area = (Area)JSONObject.toBean(jsonObject2, Area.class); 
           Drought drought = new Drought(jsonObject2.getString("name"),jsonObject2.getString("code"),
        		   jsonObject2.getJSONArray("value").getString(0),jsonObject2.getJSONArray("value").getString(1),
        		   jsonObject2.getJSONArray("value").getString(2),jsonObject2.getJSONArray("value").getString(3),
        		   jsonObject2.getJSONArray("value").getString(4));
           //System.out.println(area);  
           list.add(drought);
       }  
		return list;
   }
   public static List<Area_Yield> getArea_Yield(String staData)
   {
	   JSONArray  Area_YieldArray=JSONArray.fromObject(staData);
	   
	   List<Area_Yield> list = new ArrayList<Area_Yield>();
	// JSONArray的遍历  
       for (int i = 0; i < Area_YieldArray.size(); i++) {  
           JSONObject jsonObject2 = Area_YieldArray.getJSONObject(i);  
           //Area area = (Area)JSONObject.toBean(jsonObject2, Area.class); 
           Area_Yield yield = new Area_Yield(jsonObject2.getString("name"),jsonObject2.getString("code"),jsonObject2.getString("value"));
           //System.out.println(area);  
           list.add(yield);
       }  
		return list;
   }
   
	public static boolean getProductDataAndCopy2Workspace(String productKind,String productDate,String areaCode)
	{
		//数据产品的存储
//		public static final String productDataPath = "E:/thairiceproduct/";
		//arcgisServer的工作路径
//		public static final String arcgisserver_shp_workspacePath = "E:/arcgisserver_shp_workspace/";
		String originalProductDataPath = productDataPath+productKind+"/"+productDate;
		String desProductDataPath = arcgisserver_shp_workspacePath+productKind+"/";
		//首先查看工作空间中是否已有
		File desproductDatas = new File(desProductDataPath);
		File[] desproductFiles = desproductDatas.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.lastIndexOf('.')>0)
				{
					int lastIndex = name.lastIndexOf('.');
					String fileName = name.substring(0, lastIndex);
					if(fileName.equals(productDate+'_'+areaCode))
					{
						return true;
					}
				}
				return false;
			}
			
		});
		if(!productKind.equals("Area")&&desproductFiles.length>0)
		{
			return true;
		}
		else {
			File originalproductDatas = new File(originalProductDataPath);
			if(originalproductDatas.isDirectory())
			{
				File[] productFiles = originalproductDatas.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						if(name.lastIndexOf('.')>0)
						{
							int lastIndex = name.lastIndexOf('.');
							String fileName = name.substring(0, lastIndex);
							if(fileName.equals(areaCode))
							{
								return true;
							}
						}
						return false;
					}
					
				});
				for(File path:productFiles)
				{
//					System.out.println(path.getName());
					String copyTo = desProductDataPath+productDate+'_'+path.getName();
					File newfile = new File(copyTo);
					fileCopy(path,newfile);
				}
				return true;
			}
		}
		
		return false;
	}
   public static File[] fileFilter(String fileDirpath,String filePrefix,String fileSuffix)
   {
	   File fileDir = new File(fileDirpath);
	   if(fileDir.isDirectory())
		{
			File[] productFiles = fileDir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					if(name.lastIndexOf('.')>0)
					{
						int lastIndex = name.lastIndexOf('.');
						String fileName = name.substring(0, lastIndex);
						if(fileName.equals(filePrefix))
						{
							return true;
						}
					}
					return false;
				}
				
			});
			return productFiles;
		}
	   return null;
   }
}
