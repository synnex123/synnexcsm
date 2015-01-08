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
import com.synnex.cms.utils.PageInfo;
import com.synnex.cms.utils.UserUtil;


public class GetApplyAction extends ActionSupport implements ModelDriven<ApplyDto>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetApplyAction.class);
	private ApplyDto applyDto =new ApplyDto();
	private ApplyService applyService;
	private Integer pageIndex;
	private String mark;
	private int currentPage;
	private int totalPage;
	private int pageRecords=5;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageRecords() {
		return pageRecords;
	}
	public void setPageRecords(int pageRecords) {
		this.pageRecords = pageRecords;
	}
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
			PageInfo page=new PageInfo();
			if(0==currentPage){
				currentPage=1;
			}	
			page.setCurrentPage(currentPage);
			page.setPageRecords(pageRecords);
		 	PageInfo.pageInfo.set(page);
			Integer applyStatus=applyDto.getApplyStatus();
			if (applyStatus==null) {
				applyStatus=-1;
			}
			List<ApplyDto> applylist = new ArrayList<ApplyDto>();
			User user = new User();
			user=UserUtil.getUser(request);
			Integer userId=user.getUserId();
			applylist = applyService.getApplyByUserId(userId,pageIndex,applyStatus);
			totalPage=page.getTotalPage();
			currentPage=page.getCurrentPage();
			session.setAttribute("totalPage", totalPage);
			session.setAttribute("currentPage", currentPage);
			session.setAttribute("applylist", applylist);
			request.setAttribute("pageIndex", currentPage);
		
			
		} catch (Exception e) {
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
			
		} catch (Exception e) {
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
			PageInfo page=new PageInfo();
			if(0==currentPage){
				currentPage=1;
			}	
			page.setCurrentPage(currentPage);
			page.setPageRecords(pageRecords);
		 	PageInfo.pageInfo.set(page);
			Integer applyStatus=applyDto.getApplyStatus();
			if (applyStatus==null) {
				applyStatus=-1;
			}
			List<ApplyDto> checkapplylist = new ArrayList<ApplyDto>();
			User user = new User();
			user=UserUtil.getUser(request);
			Integer managerId=user.getUserId();
			checkapplylist = applyService.getApplyByManagerId(managerId, pageIndex,applyStatus);
			totalPage=page.getTotalPage();
			currentPage=page.getCurrentPage();
			session.setAttribute("totalPage", totalPage);
			session.setAttribute("currentPage", currentPage);
			session.setAttribute("checkapplylist", checkapplylist);
			request.setAttribute("pageIndex",currentPage);		
			return SUCCESS;	
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return "";
	}
	}

