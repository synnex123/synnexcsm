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
import com.synnex.cms.utils.EmailUtils;
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
	final String SMTP = "SMTP.163.COM";
	final String FORM = "synnexcmsupport@163.com";
	final String USERNAME = "synnexcmsupport@163.com";
	final String PASSWORD = "synnex";
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

	/**
	 * @author walker cheng 
	 * function get the user information by user Id
	 * 2014/12/16
	 * 
	 */
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
	
	/**
	 * @author walker cheng 
	 * function update the user information
	 * 2014/12/16
	 * @ajax
	 */
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
	
	/**
	 * @author walker cheng 
	 * function add the system manager
	 * 2015/01/05
	 * @ajax
	 */
	public void addSystemManager(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			userService.addSystemManager(user.getUserId());
			out.println("{\"status\":1}");
			User user1 =new User();
			user1=userService.getUserByUserId(user.getUserId());
			final String subject = "系统负责人任命通知";
			final String content ="Hi,"+user1.getUserName()+"，你已被认命为系统管理员，快点去看看吧！";
			final String to =user1.getUserEmail();
			new Thread(){
				public void run(){
					EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
							PASSWORD);
				}
			}.start();
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
	}
	/**function deleteUserByUserId
	 * 2015/01/06
	 * @author joeyy
	 */
public void deleteUser(){
	final String to=userService.getUserByUserId(user.getUserId()).getUserEmail();
	String result = userService.deleteUser(user.getUserId());
	try {
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if ("principal".equals(result)) {
			out.println("{\"status\":0,\"msg\":\"此用户为俱乐部负责人，暂无法注销！\"}");	
		}else if("votedUser".equals(result)){
			out.println("{\"status\":0,\"msg\":\"此用户正处于一次投票中且已具有选票，暂无法注销！\"}");	
		}else if("success".equals(result)){
			out.println("{\"status\":1,\"msg\":\"注销成功，用户将会收到提示邮件！\"}");
			
			final String subject="公司俱乐部管理系统:你的账户已被系统管理员注销";
			final String content="因为长期未登录系统或离职你的账户已被系统管理员注销,如有疑问请联系管理员";
			new Thread(){
				public void run(){
					EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
							PASSWORD);
				}
			}.start();
		}
	} catch (IOException e) {
		LOGGER.warn("exception at"+this.getClass().getName(),e);
	}

}
	}

	
	


