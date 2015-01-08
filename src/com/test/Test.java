package com.test;

import java.io.InputStream;
import java.util.Properties;

import com.synnex.cms.utils.EmailUtils;



public class Test {

	public static void main(String[] args) throws Exception {
		
        Properties properties = new Properties();
        InputStream in = Test.class.getClassLoader().getResourceAsStream("mail.properties");
        properties.load(in);
        String user = properties.getProperty("FORM");
        System.out.print(user);
    	final String SMTP = properties.getProperty("SMTP");
    	final String FORM = properties.getProperty("FORM");
    	final String USERNAME = properties.getProperty("USERNAME");
    	final String PASSWORD = properties.getProperty("PASSWORD");
		EmailUtils.send(SMTP, FORM,"joeyy@synnex.com,walkerc@synnex.com", "test", "test", USERNAME,
				PASSWORD);

	}

}
 
 