package com.synnex.cms.action;



import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ApplyService;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.utils.UserUtil;

public class InitApplyAction extends ActionSupport implements ModelDriven<Club>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7316643920452188939L;
	Logger logger = LoggerFactory.getLogger(InitApplyAction.class);
	private Club club = new Club();
	private ClubService clubService;
	private ApplyService applyService;
	
	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}
	public void setClubService(ClubService clubService) {
		this.clubService = clubService;
	}
	@Override
	public Club getModel() {
		return club;
	}
	/*
	 * @author walkerc
	 * 2014/12/04
	 * @modified by joeyy
	 * 2014/12/22
	 * function judege if user is menmber of this club
	 */
	public String initApply(){
		HttpServletRequest request=ServletActionContext.getRequest();
		User user=UserUtil.getUser(request);
		Boolean resultOfSearch;
		try {
			//判断用户当前所在俱乐部中是否有当前选择加入的俱乐部
			if(clubService.getClubByUserId(user.getUserId()).contains(club.getClubId())){
				request.setAttribute("errorMsg", "你已经加入当前俱乐部");
				return ERROR;
			}else {
				resultOfSearch=applyService.searchWhetherApply(user.getUserId(), 0,club.getClubId());
				if(resultOfSearch){
					request.setAttribute("errorMsg", "你已经发出了一个此俱乐部的申请，若要再次申请，请先撤销先前的申请！");
					return ERROR;
				}else{
					club=clubService.getClubByClubId(club.getClubId());
					request.setAttribute("club", club);
				}
			}	
			return SUCCESS;
			
		} catch (HibernateException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		}
		return "";

	}	
	}
	
