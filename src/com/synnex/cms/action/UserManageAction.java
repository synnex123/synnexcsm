package com.synnex.cms.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.UserUtil;

public class UserManageAction extends ActionSupport implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(UserManageAction.class);
	private UserService userService;
	private User user = new User();
	private String password1;
	private String oldpassword;
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public User getModel() {		
		return user;
	}
	/**
	 * @author joeyy
	 * 2014/12/10
	 * function changePasswordByUser
	 * @request by ajax
	 * 
	 */
	public void UserChangePassword(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			HttpServletRequest request=ServletActionContext.getRequest();
			PrintWriter out = response.getWriter();
			User user=UserUtil.getUser(request);
			user.setUserPassword(oldpassword);
			if (userService.checklogin(user)!=null) {	
			user.setUserPassword(password1);
				if (userService.updatepassword(user)) {
					out.println("{\"status\":1}");
				}else{
					out.println("{\"status\":0,\"msg\":\"faild to change password\"}");
				}
			}else{
				out.println("{\"status\":0,\"msg\":\"the old password is wrong\"}");
			}
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}

	public String initUpdateUserInfo(){
		HttpServletRequest request=ServletActionContext.getRequest();
		try{
			User user1=new User();
			user1=userService.getUserByUserId(user.getUserId());
			request.setAttribute("user", user1);
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
			return ERROR;
		}
		return SUCCESS;
	}
	public void updateUserInfo(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			HttpServletRequest request=ServletActionContext.getRequest();
			PrintWriter out = response.getWriter();
			User user1=UserUtil.getUser(request);
			user1.setUserName(user.getUserName());
			user1.setUserPhone(user.getUserPhone());
			user1.setUserEmail(user.getUserEmail());
			user1.setUserPart(user.getUserPart());
			Boolean result= userService.updateUserInfo(user1);
			if(result){
				HttpSession session=request.getSession();
				session.setAttribute("user", user1);
				out.println("{\"status\":1,\"url\":\"init.action?location=chengdu\"}");
			}else {
				out.println("{\"status\":0,\"msg\":\"信息更改失败！\"}");
			}
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
	}

		
	}

	
	


