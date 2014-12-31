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
import com.synnex.cms.utils.PageInfo;
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
	private static Logger LOGGER = LoggerFactory.getLogger(InitAction.class);
	private ClubService clubService;
	private String location;
	private int currentPage;
	private int totalPage;
	private int pageRecords=5;
	public static final ThreadLocal pageInfo=new ThreadLocal();
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageRecords() {
		return pageRecords;
	}
	public void setPageRecords(int pageRecords) {
		this.pageRecords = pageRecords;
	}
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
			PageInfo page=new PageInfo();
			if(0==currentPage){
				currentPage=1;
			}	
			page.setCurrentPage(currentPage);
			page.setPageRecords(pageRecords);
			pageInfo.set(page);
			clubList =clubService.getClubByLocation(location);
			totalPage=page.getTotalPage();
			currentPage=page.getCurrentPage();
			session.setAttribute("totalPage", totalPage);
			session.setAttribute("currentPage", currentPage);
			session.setAttribute("clubList", clubList);	
			if (flag==1) {
				return "welcome";
			}else{
				return SUCCESS;
			}
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
			return ERROR;
		}
	}
}

	
	


