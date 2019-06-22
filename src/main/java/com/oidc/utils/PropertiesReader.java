package com.oidc.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	
	public Properties getAllProperties() {
		Properties configProperties = null;
		
		try {
		    configProperties = new Properties();
		    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
		    configProperties.load(inputStream);
		}
		catch(Exception e){
		    System.out.println("Could not load the file");
		    e.printStackTrace();
		}
		return configProperties;
	}

}
