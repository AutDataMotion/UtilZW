/*
 * 
 * author:lyf
 * */
package csuduc.platform.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.core.ParseException;  
import freemarker.template.Configuration;  
import freemarker.template.MalformedTemplateNameException;  
import freemarker.template.Template;  
import freemarker.template.TemplateException;  
import freemarker.template.TemplateNotFoundException;  

@SuppressWarnings("deprecation")
public class PdfHelper {
	 public static ITextRenderer getRender(String ftlPath) throws DocumentException, IOException {  
		   
	        ITextRenderer render = new ITextRenderer();  
	   
	        //String path = getPath();  
	        //String Path = ReportUtil.getWebContentPath()+"files/productReport/reportTemplate/pdf/"; 
		      
	        //添加字体，以支持中文  
	        render.getFontResolver().addFont(ftlPath + "arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
	        render.getFontResolver().addFont(ftlPath + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
	   
	        return render;  
	    }
	//获取要写入PDF的内容  
	    public static String getPdfContent(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {  
	        return useTemplate(ftlPath, ftlName, o);  
	    }  
	  //使用freemarker得到html内容  
	    public static String useTemplate(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {  
	   
	        String html = null;  
	   
	        Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName,"utf-8");  
	        //tpl.setDefaultEncoding("UTF-8");  
	   
	        StringWriter writer = new StringWriter();  
	        tpl.process(o, writer);  
	        writer.flush();  
	        html = writer.toString();  
	        return html;  
	    } 
	    /** 
	     * 获取Freemarker配置 
	     * @param templatePath 
	     * @return 
	     * @throws IOException 
	     */  
	    public static Configuration getFreemarkerConfig(String templatePath) throws IOException {  
	        freemarker.template.Version version = new freemarker.template.Version("2.3.22");  
	        Configuration config = new Configuration(version);  
	        config.setDirectoryForTemplateLoading(new File(templatePath));  
	        config.setEncoding(Locale.CHINA, "utf-8");  
	        return config;  
	    }  
}
