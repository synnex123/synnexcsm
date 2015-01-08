package com.synnex.cms.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.UserService;

public class UserLoginAction extends ActionSupport implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginAction.class);
	private User user =new User();
	private UserService userService;
	private String password1;
	private String password2;
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	@Override
	public User getModel() {
		return user;
	}
	/**
	 * @author walker cheng 
	 * function logout the system
	 * 2014/12/17
	 * @ajax
	 */
	
	public String logout(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		session.invalidate();
		return SUCCESS;
	}
	/**
	 * @author joeyy
	 * 2014/12/10
	 * function UserRegist
	 */
	public String UserRegist(){
		
//		try{
//			
//		}catch(BizException e){
//			ActionContext.getContext().put("msg", e.getMessage());	
//			return SUCCESS;	
//		}catch(Exception e){
//			logger.error(e.getMessage());
//			ActionContext.getContext().put("msg", "系统错误");
//		}
//		
		try {
			user.setUserType(1);
			if (userService.save(user)) {
				ActionContext.getContext().put("msg", "注册成功,请登陆");	
				return SUCCESS;				
			}else{
				ActionContext.getContext().put("errmsg", "你注册的用户名已存在，请重设");	
				return ERROR;	
			}
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return ERROR;
	}
	/**
	 * @author joeyy
	 * 2014/12/06
	 * function judge if two inputs are consistent
	 * @ajax
	 * 
	 */
	public void CheckPassword(){
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
//		AppContext.getContext().put("page", page);
		try {
			PrintWriter out = response.getWriter();
			if (!password1.equals(password2)) {
				out.println("{\"status\":0,\"msg\":\"两次输入密码不一致\"}");
			}else if("".equals(password2)||password2==null){
				out.println("{\"status\":0,\"msg\":\"不能为空\"}");
			}else{
				out.println("{\"status\":1}");
			}	
			
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}

	}
	/**
	 * @author joeyy
	 * 2014/12/06
	 * function userLogin
	 * @ajax
	 * 
	 */
	public void UserLogin() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession usersession=ServletActionContext.getRequest().getSession();
		//judge if user login success or failed			
			PrintWriter out = response.getWriter();
		try {
			if (userService.checklogin(user)!=null) {
				user=userService.checklogin(user);
				usersession.setAttribute("user", user);
				out.println("{\"status\":1,\"msg\":\"login success\"}");
			}else{
				out.println("{\"status\":0,\"msg\":\"bad credit\"}");
			}
		} catch (Exception e) {
			out.println("{\"status\":0,\"msg\":\"bad credit\"}");
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}


}

