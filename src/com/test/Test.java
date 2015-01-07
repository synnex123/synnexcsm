package com.test;

import java.io.InputStream;
import java.util.Properties;

import com.synnex.cms.utils.EmailUtils;



public class Test {

	public static void main(String[] args) throws Exception {
		
//		EmailUtils.send("SMTP.163.COM", "synnexcmsupport@163.com", 
//				"joeyy@synnex.com", "123","123213", "synnexcmsupport@163.com", "synnex");
        Properties properties = new Properties();
        //new FileInputStream("D:\\java\\idea\\KWO\\cis2000\\biz2\\mfg\\src\\main\\java\\mail.properties")
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
 
 