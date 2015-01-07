package com.synnex.cms.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.ApplyService;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.DateUtils;
import com.synnex.cms.utils.EmailUtils;

public class ApplyManageAction extends ActionSupport implements
		ModelDriven<Apply> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(ApplyManageAction.class);
	private Apply apply = new Apply();
	private ApplyService applyService;
	private Integer userId;
	private ClubService clubService;
	private UserService userService;
	private static Properties properties = new Properties();
	private static InputStream in =ApplyManageAction.class.getClassLoader().getResourceAsStream("mail.properties");
	static{
		try {
			properties.load(in);
		} catch (IOException e) {
			LOGGER.warn("exception at",e);
		}
	}
	final String SMTP = properties.getProperty("SMTP");
	final String FORM = properties.getProperty("FORM");
	final String USERNAME = properties.getProperty("USERNAME");
	final String PASSWORD = properties.getProperty("PASSWORD");
	
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ClubService getClubService() {
		return clubService;
	}

	public void setClubService(ClubService clubService) {
		this.clubService = clubService;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}

	@Override
	public Apply getModel() {
		return apply;
	}

	/**
	 * @author walkerc 2014/12/18 function saveApply modified by joeyy
	 *         2014/12/22 function mailsender
	 * @throws IOException 
	 * @ajax
	 * @params Entity Apply
	 */
	public void saveApply() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		apply.setApplyTime(DateUtils.getSysNow());
		apply.setApplyStatus(0);
		applyService.saveApply(apply);
		PrintWriter out = response.getWriter();
		out.println("{\"status\":1,\"url\":\"init.action?location=chengdu\"}");

		try {
			if (applyService.getSubmittedApplyByClubId(apply.getClubId())
					.size() >= 5) {
				final String subject = "请尽快处理俱乐部申请 ，未处理申请已达"
						+ "("
						+ applyService.getSubmittedApplyByClubId(
								apply.getClubId()).size() + ")" + "条";
				final String content = "您负责的俱乐部的未处理申请已达到"
						+ applyService.getSubmittedApplyByClubId(
								apply.getClubId()).size() + "条，请尽快登录系统处理"
						+ "\n" + "http://" + request.getRemoteHost() + ":8080"
						+ request.getContextPath() + "/user/login.jsp";
				Integer managerId = clubService.getClubByClubId(
						apply.getClubId()).getManagerId();
				final String to = userService.getUserByUserId(managerId)
						.getUserEmail();
				
				new Thread(){
					public void run(){
						EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
								PASSWORD);
					}
				}.start();
				
			}

		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}


	}

	/**
	 * @author joeyy 2014/12/18 function cancelApplyByUser
	 * @ajax
	 * @param applyId
	 */
	public void cancelApply() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			PrintWriter out = response.getWriter();
			if (applyService.cancelApply(apply.getApplyId())) {
				out.println("{\"status\":1,\"msg\":\"succeed to cancel\"}");
			} else {
				out.println("{\"status\":0,\"msg\":\"failed to cancel\"}");
			}

		} catch (IOException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}
	}

	/**
	 * @author joeyy 2014/12/18 function rejectApplyByManager
	 * @ajax
	 * @param applyId
	 */
	public void rejectApply() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String checkRes = apply.getCheckRes();
		Integer applyId = apply.getApplyId();

		try {
			PrintWriter out = response.getWriter();
			if (applyService.rejectApply(applyId, checkRes,
					DateUtils.getSysNow())) {
				// 获取申请详细信息用于邮件内容
				ApplyDto apply1 = applyService.getApplyDetails(
						apply.getApplyId()).get(0);
				final String subject = "您的申请被驳回" + DateUtils.getNowDate();
				final String content = "您于(" + apply1.getApplyTime() + ")发起的" + "\n"
						+ "加入" + apply1.getClubName() + "申请被驳回("
						+ DateUtils.getNowDate() + ")" + "\n"
						+ "rejected reason:" + apply1.getCheckRes() + "\n"
						+ "请登录系统查看" + "http://" + request.getRemoteHost()
						+ ":8080" + request.getContextPath()
						+ "/user/login.jsp";
				Integer userId = apply1.getRequesterId();
				// 获取当前选择的apply获取请求人的email
				final String to = userService.getUserByUserId(userId).getUserEmail();
				out.println("{\"status\":1,\"msg\":\"succeed to reject\"}");
				new Thread(){
					public void run(){
						EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
								PASSWORD);
					}
				}.start();

			} else {
				out.println("{\"status\":0,\"msg\":\"failed to reject\"}");
			}

		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);

		} catch (IOException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}

	}

	/**
	 * @author joeyy 2014/12/18 function processApplyByManager
	 * @ajax
	 * @param applyId
	 *            ,userId,clubId,checkTime
	 */
	public void processApply() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		UserClub uc = new UserClub();
		uc.setClubId(apply.getClubId());
		uc.setUserId(userId);
		try {
			PrintWriter out = response.getWriter();
			if (applyService.passApply(apply.getApplyId(),
					DateUtils.getSysNow(), uc)) {
				ApplyDto apply1 = applyService.getApplyDetails(
						apply.getApplyId()).get(0);
				final String subject = "您的申请已被通过" + DateUtils.getNowDate();
				final String content = "您于" + apply1.getApplyTime() + "发起的加入"
						+ apply1.getClubName() + "申请已通过"
						+ DateUtils.getNowDate() + "\n" + "请登录系统查看" + "http://"
						+ request.getRemoteHost() + ":8080"
						+ request.getContextPath() + "/user/login.jsp";
				Integer userId = apply1.getRequesterId();
				final String to = userService.getUserByUserId(userId).getUserEmail();
				out.println("{\"status\":1,\"msg\":\"succeed to process\"}");
				new Thread(){
					public void run(){
						EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
								PASSWORD);
					}
				}.start();

			} else {
				out.println("{\"status\":0,\"msg\":\"failed to process\"}");
			}

		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}

	}
}
