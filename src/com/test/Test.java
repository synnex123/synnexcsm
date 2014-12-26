package com.test;

import com.synnex.cms.utils.EmailUtils;


public class Test {

	public static void main(String[] args) throws Exception {
		
		EmailUtils.send("SMTP.163.COM", "synnexcmsupport@163.com", 
				"joeyy@synnex.com", "123","123213", "synnexcmsupport@163.com", "synnex");
	}

}
 
 