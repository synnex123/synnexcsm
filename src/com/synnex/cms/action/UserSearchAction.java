package com.synnex.cms.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.PageInfo;

public class UserSearchAction extends ActionSupport implements ModelDriven<SearchDto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(UserSearchAction.class);
	private UserService userService;
	private SearchDto searchDto=new SearchDto();
	private int currentPage;
	private int totalPage;
	private int pageRecords=5;
	private int location;
	
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
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
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public SearchDto getModel() {
		return searchDto;
	}
	@SuppressWarnings("unchecked")
	/**
	 * @author walkerc 
	 * 2014/12/02 
	 * function search User
	 * @params userName userType
	 */
	public String searchUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Integer pageIndex=1;
		Integer listNumber=0;
		PageInfo page=new PageInfo();
		if(0==currentPage){
			currentPage=1;
		}	
		page.setCurrentPage(currentPage);
		page.setPageRecords(pageRecords);
	 	PageInfo.pageInfo.set(page);
		SearchDto searchDto1=new SearchDto();
		List<SearchDto> resultList=new ArrayList<SearchDto>();
		List<Object> mixList=new ArrayList<Object>();
		List<SearchUserClubDto> clubList=new ArrayList<SearchUserClubDto>();
		String purpose=null;
		if(!("".equals(request.getParameter("purpose"))||request.getParameter("purpose")==null)){
			purpose=request.getParameter("purpose");
		}
		try {
			if(searchDto.getUserName().equals("")){
				if(searchDto.getUserType()==null){
					request.setAttribute("errorMsg", "请输入你想查询的用户类型！");
					return ERROR;
				}else if(searchDto.getUserType()==0){
					resultList=userService.searchClubDirector(pageIndex);
					request.setAttribute("viewNeed","nihao");
				}else if(searchDto.getUserType()==1){
					resultList=userService.searchUserByUserType(1,pageIndex);
				}else if(searchDto.getUserType()==10){
					resultList=userService.searchUserByUserType(10,pageIndex);
				}
				totalPage=page.getTotalPage();
				currentPage=page.getCurrentPage();
				request.setAttribute("totalPage", totalPage);
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("pageIndex",currentPage);
				request.setAttribute("userType",searchDto.getUserType());
				request.setAttribute("resultList",resultList);
				return SUCCESS;
			}else {
					mixList=userService.search(searchDto.getUserName());
					if(mixList.size()==0){
						request.setAttribute("errorMsg", "你查询的用户不存在，请核对后在输入！");
						return ERROR;
					}else{
						if(request.getParameter("viewNeed")!=null){
							request.setAttribute("userType", searchDto.getUserType());
							request.setAttribute("viewNeed",request.getParameter("viewNeed"));
						}
						searchDto1=(SearchDto)mixList.get(0);
						clubList=(ArrayList<SearchUserClubDto>)mixList.get(1);
						request.setAttribute("searchDto",searchDto1);
						request.setAttribute("clubList",clubList);
						request.setAttribute("purpose", purpose);
					}
					return "success1";
			}
		}catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return ERROR;
	}
	
	/**
	 * @author walkerc 
	 * 2015/01/05
	 *  function get the information of ordinary user  
	 * @params userType
	 */
	public String initAddSystemManager(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Integer pageIndex=1;
		Integer listNumber=0;
		List<SearchDto> resultList=new ArrayList<SearchDto>();
		try {
			if(location==1)searchDto.setUserType(location);
			PageInfo page=new PageInfo();
			if(0==currentPage){
				currentPage=1;
			}	
			page.setCurrentPage(currentPage);
			page.setPageRecords(pageRecords);
		 	PageInfo.pageInfo.set(page);
		    resultList=userService.searchUserByUserType(searchDto.getUserType(),pageIndex);
		    totalPage=page.getTotalPage();
			currentPage=page.getCurrentPage();
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageIndex",currentPage);
			request.setAttribute("userType",searchDto.getUserType());
			request.setAttribute("resultList",resultList);
		}catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return SUCCESS;
	}
}
