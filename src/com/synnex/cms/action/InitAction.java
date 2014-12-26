package com.synnex.cms.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.utils.UserUtil;

/*
 * @Author Pete Peng
 * function get club list for main page 
 * 2014/12/17
 */
public class InitAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(InitAction.class);
	private ClubService clubService;
	private String location;
	
	public void setClubService(ClubService clubService){
		this.clubService=clubService;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String execute(){
		try{
			List<ClubDto> clubList = new ArrayList<ClubDto>();
			HttpServletRequest request=ServletActionContext.getRequest();
			Integer flag=0;
			HttpSession session = request.getSession();
			if(location==null||"".equals(location)){
				location="chengdu";
				flag=1;	
				User user=UserUtil.getUser(request);
				Integer usertype = user.getUserType();
				session.setAttribute("usertype",usertype);
				String addCookie=request.getParameter("addCookie");
				if(addCookie!=null&&addCookie.equals("1")){
					Cookie userNameCookie=new Cookie("userName",user.getUserName());
					Cookie passwdCookie=new Cookie("password",user.getUserPassword());
					userNameCookie.setPath("/");
					passwdCookie.setPath("/");
					userNameCookie.setMaxAge(30*24*3600);
					passwdCookie.setMaxAge(30*24*3600);
					HttpServletResponse response=ServletActionContext.getResponse();
					response.addCookie(userNameCookie);
					response.addCookie(passwdCookie);
				}
			}
			clubList =clubService.getClubByLocation(location);
			session.setAttribute("clubList", clubList);	
			if (flag==1) {
				return "welcome";
			}else{
				return SUCCESS;
			}
		}catch(HibernateException e){
			logger.warn("exception at"+this.getClass().getName(), e);
			return ERROR;
		}
	}

		
		
}

	
	


