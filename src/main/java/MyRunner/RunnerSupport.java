package MyRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class RunnerSupport {
	
	public static void prepareTestEnv(String str) {
		
		loadConfigFile(str);
		
	}

	private static void loadConfigFile(String filePath) {
		
		Properties prop = null;
		InputStream stream = null;
		try {
			stream = new FileInputStream(filePath);
			prop = new Properties();
			prop.load(stream);
			
			@SuppressWarnings("unchecked")
			Enumeration<String> enums = (Enumeration<String>) prop.propertyNames();
		    while (enums.hasMoreElements()) {
		      String key = enums.nextElement();
		      String value = prop.getProperty(key);
		      System.out.println(key + " : " + value);
		      System.setProperty(key, value);
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	public static Properties loadOR(String oRFile) {
		Properties OR = null;
		InputStream stream = null;
		try {
			stream = new FileInputStream(oRFile);
			OR = new Properties();
			OR.load(stream);
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return OR;
		
	}

}
