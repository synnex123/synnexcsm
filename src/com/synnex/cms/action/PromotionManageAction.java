package com.synnex.cms.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.PromotionVoteRecord;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.service.PromotionService;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.DateUtils;
import com.synnex.cms.utils.EmailUtils;
import com.synnex.cms.utils.PromotionUtil;
import com.synnex.cms.utils.UserUtil;

public class PromotionManageAction extends ActionSupport implements
		ModelDriven<Promotion> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(PromotionManageAction.class);
	private Promotion promotion = new Promotion();
	private PromotionService promotionService;
	private ClubService clubService;
	private UserService userService;
	private Integer promotionUser;
	private static Properties properties = new Properties();
	private static InputStream in =PromotionManageAction.class.getClassLoader().getResourceAsStream("mail.properties");
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

	public Integer getPromotionUser() {
		return promotionUser;
	}

	public void setPromotionUser(Integer promotionUser) {
		this.promotionUser = promotionUser;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setClubService(ClubService clubService) {
		this.clubService = clubService;
	}

	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@Override
	public Promotion getModel() {
		return promotion;
	}

	/**
	 * @author joeyy 2014/12/16 function initPromotion if someone don't want to
	 *         be a director
	 * @throws Exception
	 */
	public String initPromotion() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			User admin = new User();
			admin = UserUtil.getUser(request);
			// 取出此人加入的全部俱乐部
			List<Club> clublist = clubService.getAllCLubByUserId(admin
					.getUserId());
			// 循环取出每一个俱乐部的俱乐部Id
			Integer adminclubId = null;
			for (int i = 0; i < clublist.size(); i++) {
				Integer clubId = clublist.get(i).getClubId();
				// 得到此俱乐部的managerId
				Integer managerId = clubService.getClubByClubId(clubId)
						.getManagerId();
				// 如果此人是次俱乐部的管理员则把此俱乐部的clubId设为此次投票的clubId
				if (managerId == admin.getUserId()) {
					adminclubId = clubId;
				}
			}
			int clubId = adminclubId;
			int userId = admin.getUserId();
			List<User> userList = userService.getUserByClubId(clubId, userId);
			HttpSession session = request.getSession();
			session.setAttribute("userList", userList);
			return SUCCESS;

		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}
		return "";

	}

	/**
	 * @author joeyy 2014/12/16 function producePromotion
	 * 
	 */
	public void producePromotion() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			// 设置选举开始时间和过期时间
			Calendar expireTime = Calendar.getInstance();
			promotion.setStartTime(DateUtils.getSysNow());
			// 获取当前时间+10天为选举过期时间
			int nowDay = expireTime.get(Calendar.DAY_OF_MONTH);
			expireTime.set(Calendar.DATE, nowDay + 10);
			promotion.setExpireTime(DateUtils.switchSqlDate(expireTime));
			User admin = new User();
			admin = UserUtil.getUser(request);

			// 取出此人加入的全部俱乐部
			List<Club> clublist = clubService.getAllCLubByUserId(admin
					.getUserId());
			// 循环取出每一个俱乐部的俱乐部Id
			Integer adminclubId = null;
			for (int i = 0; i < clublist.size(); i++) {
				Integer clubId = clublist.get(i).getClubId();
				// 得到此俱乐部的managerId
				Integer managerId = clubService.getClubByClubId(clubId)
						.getManagerId();
				// 如果此人是次俱乐部的管理员则把此俱乐部的clubId设为此次投票的clubId
				if (managerId == admin.getUserId()) {
					adminclubId = clubId;
				}
			}
			promotion.setClubId(adminclubId);
			promotion.setPromotionName(PromotionUtil.getPromotionName(request));
			promotion.setPromotionState(0);
			// 若成功发起请求则给所有俱乐部成员发邮件通知
			if (promotionService.producePromotion(promotion)) {
				out.println("{\"status\":1}");
				final String subject = "您所在的"
						+ clubService.getClubByClubId(adminclubId)
								.getClubName() + "俱乐部的负责人发起了一次更换负责人请求"
						+ DateUtils.getNowDate();
				final String content = "您所在的"
						+ clubService.getClubByClubId(adminclubId)
								.getClubName() + "俱乐部的负责人发起了一次更换负责人请求" + "\n"
						+ "更换原因为:" + promotion.getPromotionReason() + "\n"
						+ "有效期至(" + DateUtils.switchSqlDate(expireTime) + ")"
						+ "\n请登录系统进行投票" + "http://" + request.getRemoteHost()
						+ ":8080" + request.getContextPath()
						+ "/user/login.jsp";

				// 取得此俱乐部的所有成员
				List<User> userlist = userService
						.getAllUserByClubId(adminclubId);
				// 向此俱乐部中所有成员发邮件
				for (int i = 0; i < userlist.size(); i++) {
					final String to = userlist.get(i).getUserEmail();
					new Thread(){
						public void run(){
							EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
									PASSWORD);
						}
					}.start();
				}

			} else {
				out.println("{\"status\":0,\"msg\":\"failed to initiate\"}");
			}

		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}

	}

	/**
	 * @author joeyy 2014/12/22 function doPromotion and judegePromotion
	 * 
	 */
	public void doPromotion() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = response.getWriter();
		Integer voteduser = promotionUser;
		Integer voteuser = UserUtil.getUser(request).getUserId();
		Integer promotionId = promotion.getPromotionId();
		PromotionVoteRecord pvr = new PromotionVoteRecord();
		pvr.setPromotionId(promotionId);
		pvr.setVoteduserId(voteduser);
		pvr.setVoteuserId(voteuser);
		try {
			if (promotionService.isExist(pvr)) {
				out.println("{\"msg\":\"failed to vote,you already have voted\"}");
				return;
			} else {
				promotionService.savePromotionRecord(pvr);
				if ("sloved".equals(promotionService.judgePromotion(pvr))) {
					out.println("{\"msg\":\"succeed to vote and result have sloved \"}");
					final String subject = "您所在的"
							+ clubService.getClubByPromotionId(
									pvr.getPromotionId()).getClubName()
							+ "俱乐部的更换管理员投票已结束(" + DateUtils.getNowDate() + ")";
					final String content = "您所在的"
							+ clubService.getClubByPromotionId(
									pvr.getPromotionId()).getClubName()
							+ "俱乐部的更换管理员投票已结束"
							+ "\n"
							+ "新的负责人为"
							+ userService.getUserByUserId(
									clubService.getClubByPromotionId(
											pvr.getPromotionId())
											.getManagerId()).getUserName()
							+ "\n" + "请前往查看" + "http://"
							+ request.getRemoteHost() + ":8080"
							+ request.getContextPath() + "/user/login.jsp";
					// 取得此俱乐部的所有成员
					List<User> userlist = userService
							.getAllUserByClubId(clubService
									.getClubByPromotionId(pvr.getPromotionId())
									.getClubId());
					// 向此俱乐部中所有成员发邮件
					for (int i = 0; i < userlist.size(); i++) {
						final String to = userlist.get(i).getUserEmail();
						new Thread(){
							public void run(){
								EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
										PASSWORD);
							}
						}.start();
					}

				} else if ("keep".equals(promotionService.judgePromotion(pvr))) {
					out.println("{\"msg\":\"succeed to vote and keep going \"}");
				} else {
					out.println("{\"msg\":\"failed to vote\"}");
				}
			}
		} catch (HibernateException e) {
			LOGGER.warn("exception at" + this.getClass().getName(), e);
		}

	}
}
