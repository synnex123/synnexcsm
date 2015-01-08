package com.synnex.cms.action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.EmailUtils;
import com.synnex.cms.utils.UserUtil;

public class ExitClubAction extends ActionSupport implements ModelDriven<SearchUserClubDto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ExitClubAction.class);
	private SearchUserClubDto searchUserClubDto=new SearchUserClubDto();
	private UserService userService;
	@Override
	public SearchUserClubDto getModel() {
		return searchUserClubDto;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * @author walker cheng
	 * 2014/12/26
	 * function delete the information of UserClub data base due to exit the club
	 * @param entity UserClub
	 */
	public void exitClub(){
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;UTF-8");
		UserClub userClub=new UserClub();
		userClub.setClubId(searchUserClubDto.getClubId());
		userClub.setUserId(searchUserClubDto.getUserId());
		User user=UserUtil.getUser(request);
		try {
			PrintWriter out = response.getWriter();
			userService.deleteUserClubInfoDuoToExitClub(userClub);
			out.println("{\"status\":1}");
			final String subject = "俱乐部成员退出提醒！";
			final String content ="Hi,"+searchUserClubDto.getManagerName()+"您负责的俱乐部:"
						+ searchUserClubDto.getClubName() +","+"有一名成员："
						+user.getUserName()+"退出了该俱乐部！";
			final String to =searchUserClubDto.getManagerEmail();
			new Thread(){
				public void run(){
					EmailUtils.send(EmailUtils.SMTP, EmailUtils.FORM, to, subject, content, EmailUtils.USERNAME,
							EmailUtils.PASSWORD);
				}
			}.start();
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}

}
