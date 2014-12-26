package com.synnex.cms.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.service.UserService;

public class UserSearchAction extends ActionSupport implements ModelDriven<SearchDto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(UserSearchAction.class);
	private UserService userService;
	private SearchDto searchDto=new SearchDto();
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public SearchDto getModel() {
		return searchDto;
	}
	@SuppressWarnings("unchecked")
	public String searchUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Integer pageIndex;
		Integer listNumber=0;
		if ("".equals(request.getParameter("pageIndex"))||request.getParameter("pageIndex")==null||"0".equals(request.getParameter("pageIndex"))) {
			pageIndex=0;
		}else{
		    pageIndex=(Integer.parseInt(request.getParameter("pageIndex"))-1)*5;
		}
		SearchDto searchDto1=new SearchDto();
		List<SearchDto> resultList=new ArrayList<SearchDto>();
		List<Object> mixList=new ArrayList<Object>();
		List<SearchUserClubDto> clubList=new ArrayList<SearchUserClubDto>();
		try {
			if(searchDto.getUserName().equals("*")){
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
				listNumber=resultList.size();
				request.setAttribute("listNumber",listNumber);
				request.setAttribute("pageIndex",pageIndex);
				request.setAttribute("userName","*");
				request.setAttribute("userType",searchDto.getUserType());
				request.setAttribute("resultList",resultList);
				return SUCCESS;
			}else if(searchDto.getUserName().equals("")){
				request.setAttribute("errorMsg", "请输入用户名后再查询！");
				return ERROR;
			}else {
					mixList=userService.search(searchDto.getUserName());
					if(mixList.size()==0){
						request.setAttribute("errorMsg", "你查询的用户不存在，请核对后在输入！");
						return ERROR;
					}else{
						searchDto1=(SearchDto)mixList.get(0);
						clubList=(ArrayList<SearchUserClubDto>)mixList.get(1);
						request.setAttribute("searchDto",searchDto1);
						request.setAttribute("clubList",clubList);
					}
					return "success1";
			}
		}catch (HibernateException e) {
			logger.warn("exception at"+this.getClass().getName(), e);
		}
		return ERROR;
	}
}
