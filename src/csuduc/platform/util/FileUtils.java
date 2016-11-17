/**
 * <p>title:FileUtils.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月5日下午5:14:26
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package csuduc.platform.util;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;

/**  
 * 创建时间：2015年11月5日 下午5:14:26  
 * 项目名称：zwplatform   
 * 文件名称：FileUtils.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2015年11月5日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>
 * Title: FileUtils<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2015年11月5日
 */
public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class);
	
	 
	/**
	 * @param filePath 
	 * @param fileName
	 * @param contents
	 * @param isAppend
	 * @return
	 */
	public static boolean writeTxtStrs(String filePath, String fileName, List<String> contents, boolean isAppend){
		boolean res = false;
		BufferedWriter fw = null;
		try {
			File file = new File(filePath+fileName);
			if (!file.exists()) {
				java.io.File filePathNew = new java.io.File(filePath);
				if (!filePathNew.exists()) {
					filePathNew.mkdirs();
				}
				file.createNewFile();
			}
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, isAppend), "UTF-8")); // 指定编码格式
			
			for (String string : contents) {
				fw.append(string);
				fw.newLine();
			}
			fw.flush(); // 全部写入缓存中的内容
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
			
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	public static boolean writeTxtStrs(String filePathName, List<String> contents, boolean isAppend){
		String filePath = getFilePath(filePathName);
		String fileName = getFileName(filePathName);
		return writeTxtStrs(filePath, fileName, contents, isAppend);
	}
	public final static String getFilePath(String filePathName){
		return filePathName.substring(0,filePathName.lastIndexOf("\\"));
	}
	public final static String getFileName(String filePathName){
		
		return filePathName.substring(filePathName.lastIndexOf("\\") +1 );
	}
	public static List<String> readTxt(String filePathName){
		File file = new File(filePathName);
		if (!file.exists()) {
			return null;
		}
		List<String> listContent = new LinkedList<String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			fis = new FileInputStream(file);
			// 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码
			isr = new InputStreamReader(fis,"UTF-8"); 
			reader = new BufferedReader(isr);  
			
			String str = null;
			while ((str = reader.readLine()) != null) {
				if (str.trim().length() >0) {
					listContent.add(str);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader !=null) {
					reader.close();
					reader = null;
				}
				if (isr!=null) {
					isr.close();
					isr = null;
				}
				if (fis != null) {
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listContent;
	}
	
	public static boolean copyFileLarge(String inputFilePath, String outFilePath) {
		// 创建两个文件
		File inFile = new File(inputFilePath);
		if (!inFile.exists()) {
			logger.error("inputfile not exist:"+inputFilePath);
			return false;
		}
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(inFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}

		return copyFileLargeInputStream(inStream, inFile.length(), outFilePath);
	}

	public static boolean copyFileLargeInputStream(InputStream inputStream, Long totalSize, String outFilePath) {

		if (inputStream==null) {
			logger.error("inputStream null");
			return false;
		}
		File outFile = new File(outFilePath);
		if (outFile.exists()) {
			outFile.delete();//先删除已经存在的
		}
		try {   
			outFile.createNewFile();   
        } catch (IOException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();  
            return false;
        }  
		// 最大的流为60Mb,当文件的容量大于60Mb的时候便分开流
		final int MAX_BYTE = 60000000;
		long streamTotal = totalSize; // 接受流的容量
		int streamNum = 0; // 流需要分开的数量
		int leave = 0; // 文件剩下的字符数
		byte[] inOutb; // byte数组接受文件的数据
		// 创建流文件读入与写出类
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(outFile);
			// 通过available方法取得流的最大字符数
			//streamTotal = inputStream.available();
			// 取得流文件需要分开的数量
			streamNum = (int) Math.floor(streamTotal / MAX_BYTE);
			// 分开文件之后,剩余的数量
			leave = (int) streamTotal % MAX_BYTE;
			System.out.println("----input available:"+streamTotal+"---"+streamNum+"---"+leave);
			// 文件的容量大于60Mb时进入循环
			if (streamNum > 0) {
				for (int i = 0; i < streamNum; ++i) {
					inOutb = new byte[MAX_BYTE];
					// 读入流,保存在byte数组
					inputStream.read(inOutb, 0, MAX_BYTE);
					outStream.write(inOutb); // 写出流
					outStream.flush(); // 更新写出的结果
				}
			}
			// 写出剩下的流数据
			inOutb = new byte[leave];
			inputStream.read(inOutb, 0, leave);
			outStream.write(inOutb);
			outStream.flush();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}
	

	public static boolean folderCheckAndMake(String aDir){
		java.io.File targetFolder = new java.io.File(aDir);
		if (!targetFolder.exists()) {
			try {
				return targetFolder.mkdirs();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	/**
	 * <p>
	 * Title: copyFile<／p>
	 * <p>
	 * Description: <／p>
	 * 
	 * @param sourcePath
	 * @param sourceName
	 * @param targetPath
	 * @param targetName
	 * @param preserve
	 * @return
	 */
	public static boolean copyFile(String sourcePath, String sourceName,
			String targetPath, String targetName, boolean preserve) {
		// target 不存在则先创建
		if (!folderCheckAndMake(targetPath)) {
			return false;
		}
		CopyOption[] options = (preserve) ? new CopyOption[] { COPY_ATTRIBUTES,
				REPLACE_EXISTING } : new CopyOption[] { REPLACE_EXISTING };
		//copy 失败则重复尝试3次
		int cnt = 3;
		while (cnt >0) {
			try {
				Files.copy(Paths.get(sourcePath + sourceName),
						Paths.get(targetPath + targetName), options);
				return true;
			} catch (IOException x) {
				System.out.println(String.format("Unable to copy file :\n from:%s \nto:%s \n errinfo: %s \n retry...", sourcePath
						+ sourceName,targetPath+targetName, x));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cnt --;
			}
			
		}
		return false;

	}
	public static boolean deleteFile(String pathName){
		File file = new File(pathName);
		if (!file.exists()) {
			System.out.println("file not exist,nothing to delete:"+pathName);
			return true;
		}
		int cnt = 3;
		while (cnt >0) {
			try {
				if (!file.delete()) {
					cnt--;
				}else{
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				cnt--;
			}
		}
		return false;

	}
	public static boolean cutFile(String sourcePath, String sourceName,
			String targetPath, String targetName, boolean preserve){
		return copyFile(sourcePath, sourceName, targetPath, targetName, preserve)
				&&deleteFile(sourcePath+sourceName);
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
		//写txt
		String path = "E:\\temp\\test\\";
		String Name = "in.txt";
		String filePathName = "E:\\temp\\test\\in.txt";
		System.out.println(getFilePath(filePathName));
		System.out.println(getFileName(filePathName));
//		List<String> listStrs = new ArrayList<String>();
//		listStrs.add("filePath11");
//		listStrs.add("filename");
//		listStrs.add("filePath22");
//		writeTxtStrs(path, Name, listStrs, false);
//		
//		List<String> listStrRead = readTxt(path+Name);
//		for (String string : listStrRead) {
//			System.out.println(string);
//		}
		//读txt
	}

}
