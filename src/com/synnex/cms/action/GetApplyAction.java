package com.synnex.cms.action;

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
import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ApplyService;
import com.synnex.cms.utils.UserUtil;


public class GetApplyAction extends ActionSupport implements ModelDriven<ApplyDto>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(GetApplyAction.class);
	private ApplyDto applyDto =new ApplyDto();
	private ApplyService applyService;
	private Integer pageIndex;
	private String mark;
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}
	@Override
	public ApplyDto getModel() {	
		return applyDto;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * @author joeyy
	 * 2014/12/18
	 * function getMyApplyByUserId
	 * @params userId,pageIndex,applyStatus 
	 * 
	 */
	public String getMyApply(){
        HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		try {
			if (0==pageIndex||null==pageIndex) {
				pageIndex=0;
			}else{
			pageIndex=(pageIndex-1)*10;
			}
			Integer applyStatus=applyDto.getApplyStatus();
			if (applyStatus==null) {
				applyStatus=-1;
			}
			List<ApplyDto> applylist = new ArrayList<ApplyDto>();
			User user = new User();
			user=UserUtil.getUser(request);
			Integer userId=user.getUserId();
			applylist = applyService.getApplyByUserId(userId,pageIndex,applyStatus);
			Integer applysize = applylist.size();
			request.setAttribute("applysize", applysize);
			session.setAttribute("applylist", applylist);
			request.setAttribute("pageIndex", pageIndex);
		
			
		} catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return SUCCESS;

	}
	/**
	 * @author joeyy
	 * 2014/12/18
	 * function getApplyDetail
	 * @return applydetails("userdetail" if user is normal type ,"managerdetail" if user is master)
	 * @params applyId
	 * 
	 */
	public String getApplyDetail(){
		try {
			HttpServletRequest request=ServletActionContext.getRequest();
			List<ApplyDto> applydetails=applyService.getApplyDetails(applyDto.getApplyId());
			if ("user".equals(mark)) {
				request.setAttribute("applydetails", applydetails);
				return "userdetail";
			}else{
				request.setAttribute("checkapplydetails", applydetails);
				return "managerdetail";
			}
			
		} catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return "";


	}
	/**
	 * @author joeyy
	 * 2014/12/18
	 * function getApplyByManagerId for checkapply.jsp
	 * @return List<ApplyDto> By ManagerId
	 * @params managerId,pageIndex,applyStatus
	 * 
	 */
	public String getApplyByManagerId(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		try {
			if (0==pageIndex||null==pageIndex) {
				pageIndex=0;
			}else{
			pageIndex=(pageIndex-1)*10;
			}
			Integer applyStatus=applyDto.getApplyStatus();
			if (applyStatus==null) {
				applyStatus=-1;
			}
			List<ApplyDto> checkapplylist = new ArrayList<ApplyDto>();
			User user = new User();
			user=UserUtil.getUser(request);
			Integer managerId=user.getUserId();
			checkapplylist = applyService.getApplyByManagerId(managerId, pageIndex,applyStatus);
			Integer checkapplysize = checkapplylist.size();
			request.setAttribute("checkapplysize", checkapplysize);
			session.setAttribute("checkapplylist", checkapplylist);
			request.setAttribute("pageIndex", pageIndex);		
			return SUCCESS;	
		} catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return "";
	}
	}

