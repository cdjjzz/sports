package commons;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
	
	private static Properties properties=new Properties();
	
	static{
		init();
	}
	private PropertiesHelper() {
	}
	
	public static void refresh(){
		properties.clear();
		init();
	}
	
	private static void init(){
		try {
			InputStream inputStream=PropertiesHelper.class.getClassLoader()
					.getResourceAsStream("jredis.properties");
			
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getAsString(String key){
		return properties.getProperty(key);
	}
	
	public static Integer getAsInteger(String key){
		try {
			return Integer.valueOf(
					properties.getProperty(key));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Object getAsObject(String key){
		return properties.get(key);
	}
}
