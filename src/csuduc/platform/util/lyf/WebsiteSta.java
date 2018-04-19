package csuduc.platform.util.lyf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

//import puresport.mvc.pages.pagesController;

public class WebsiteSta {
	private static Logger log = Logger.getLogger(WebsiteSta.class);
	private static long number = 0;
	private static String fileName =  "count.txt";
	public static synchronized long countPeople()  
    {  
		 File file = new File(fileName); 
		 log.debug(file.getAbsolutePath());
        if(!file.exists())  
        {  
            number++;  
            try{  
                file.createNewFile();  
                FileOutputStream out = new FileOutputStream(file);  
                DataOutputStream dataOut = new DataOutputStream(out);  
                dataOut.writeLong(number);  
                out.close();  
                dataOut.close();
                //return number;
            }catch(IOException ex){}  
        }  
        else {   
            try{  
                FileInputStream in = new FileInputStream(file);  
                DataInputStream dataIn = new DataInputStream(in);  
                number = dataIn.readLong();  
                number++;  
                in.close();  
                dataIn.close();  
                FileOutputStream out = new FileOutputStream(file);  
                DataOutputStream dataOut = new DataOutputStream(out);  
                dataOut.writeLong(number);  
                out.close();  
                dataOut.close(); 
                //return number;
            }catch(IOException ex){}
        }
		return number;  
    }  
}
