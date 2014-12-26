package com.synnex.cms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.PromotionDto;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.service.PromotionService;
import com.synnex.cms.utils.UserUtil;


public class GetPromotionAction extends ActionSupport implements ModelDriven<Promotion>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(GetPromotionAction.class);
	private Promotion promotion = new Promotion();
	private PromotionService promotionService;
	private ClubService clubService;
	private String clubName;
	
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
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
	/*
	 * @author joeyy
	 * 2014/12/18
	 * fucntion getPromotionByUserId
	 */
	public String getPromotion() {
		HttpServletRequest request=ServletActionContext.getRequest();
		User user=UserUtil.getUser(request);
		try {
			//通过userId得到此user加入的所有俱乐部的clubId
			@SuppressWarnings("rawtypes")
			List userclubIdlist = clubService.getClubByUserId(user.getUserId());
			List<PromotionDto> promotionlist=new ArrayList<>();
			for (int i = 0; i < userclubIdlist.size(); i++) {
				//通过clubId获取当前俱乐部的所有选举信息
				List<PromotionDto> promotionlist1=promotionService.GetOnGoingPromotionByClubId((Integer)userclubIdlist.get(i));
				for (int j = 0; j < promotionlist1.size(); j++) {
					//将每次获取到的选举信息放入最后要输出要页面的list里面
					promotionlist.add(promotionlist1.get(j));
				}	
			}
			HttpSession session=request.getSession();
			session.setAttribute("promotionlist", promotionlist);
			return SUCCESS;
			
		} catch (HibernateException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		}
		return "";

	}
	/*
	 * @author joeyy
	 * 2014/12/18
	 * function getPromotionVoteUserByPromotionId
	 * 
	 */
	public String getPromotionVote(){
		try {
			HttpServletRequest request=ServletActionContext.getRequest();
			List<User> promotionuserlist=new ArrayList<User>();
			clubName = new String(clubName.getBytes("iso8859-1"),"UTF-8");
			promotionuserlist = promotionService.GetPromotionUserByPromotionId(promotion.getPromotionId());
			HttpSession session=request.getSession();
			session.setAttribute("promotionuserlist",promotionuserlist);
			request.setAttribute("clubName", clubName);
			request.setAttribute("promotionId", promotion.getPromotionId());
			return SUCCESS;
			
		} catch (HibernateException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		} catch (UnsupportedEncodingException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		}
		return "";
	}
}
