package com.synnex.cms.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.UserService;

public class ExitClubAction extends ActionSupport implements ModelDriven<SearchUserClubDto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ExitClubAction.class);
	private SearchUserClubDto searchUserClubDto=new SearchUserClubDto();
	private UserService userService;
	@Override
	public SearchUserClubDto getModel() {
		return searchUserClubDto;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void exitClub(){
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;UTF-8");
		UserClub userClub=new UserClub();
		userClub.setClubId(searchUserClubDto.getClubId());
		userClub.setUserId(searchUserClubDto.getUserId());
		try {
			PrintWriter out = response.getWriter();
			userService.deleteUserClubInfoDuoToExitClub(userClub);
			out.println("{\"status\":1}");
		} catch (HibernateException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		}
	}

}
