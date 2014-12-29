package com.synnex.cms.utils;
/**
 * @author joeyy
 * function for getPromotionName 
 * 
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.synnex.cms.entity.User;

public class PromotionUtil {
	public static String getPromotionName(HttpServletRequest request){
		HttpSession usersession = request.getSession();
		User user=(User)usersession.getAttribute("user");
		String userName=user.getUserName();
		return DateUtils.getNowDate()+userName;	
	}

}
