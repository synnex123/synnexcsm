package com.synnex.cms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.EmailUtils;
import com.synnex.cms.utils.UserUtil;


public class ClubManageAction extends ActionSupport implements ModelDriven<ClubDto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ClubManageAction.class);
	private ClubDto clubDto=new ClubDto();
	private ClubService clubService;
	private UserService userService;
	final String SMTP = "SMTP.163.COM";
	final String FORM = "synnexcmsupport@163.com";
	final String USERNAME = "synnexcmsupport@163.com";
	final String PASSWORD = "synnex";
	public void setClubService(ClubService clubService) {
		this.clubService = clubService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public ClubDto getModel() {
		return clubDto;
	}
	/**
	 * @Author Walker Cheng 2014/12/11 function add the new club 
	 * @ajax
	 * @param clubDto
	 * 
	 */
	public void addClub(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			HttpServletRequest request=ServletActionContext.getRequest();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;UTF-8");
			PrintWriter out = response.getWriter();
			List<ClubDto> clubList=new ArrayList<ClubDto>();
			ClubDto clubDto1=new ClubDto();
			User user =new User();
			user=userService.getUserByName(clubDto.getManagerName()); 
			if(user==null){
				out.println("{\"status\":0,\"msg\":\"你输入的负责人不存在\"}");
			}else{
				if(user.getUserType()==0){
					out.println("{\"status\":0,\"msg\":\"你输入的负责人已是其他俱乐部的负责人！请重新选择\"}");
				}else{
					clubDto1.setManagerId(user.getUserId());
					clubDto1.setClubName(clubDto.getClubName());
					clubDto1.setClubLocation(clubDto.getClubLocation());
					clubDto1.setClubDescription(clubDto.getClubDescription());
					clubDto1.setManagerName(clubDto.getManagerName());
					clubList=clubService.searchClubByClubNameAndClubLocation(clubDto1);
					if(clubList.size()!=0){
						out.println("{\"status\":0,\"msg\":\"该俱乐部已经存在！\"}");
					}else {
						user.setUserType(0);
						Boolean result=clubService.addClub(clubDto1,user);
						if(result){
							out.println("{\"status\":1,\"url\":\"init.action?location=chengdu\"}");
							String subject = "俱乐部任命提醒！";
							String content ="Hi"+user.getUserName()+"，你已被任命为新增俱乐部："
									+clubDto.getClubName()+" 的负责人,赶快去看看吧！！"
									+ "\n" + "http://" + request.getRemoteHost() + ":8080"
						            + request.getContextPath() + "/user/login.jsp";
							String to =user.getUserEmail();
							EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
									PASSWORD);
						}else{
							out.println("{\"status\":0,\"msg\":\"俱乐部添加失败！\"}");
						}
					}
				}
			}			
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}
	
	/**
	 * @Author Walker Cheng 2014/12/12 function get the club information 
	 * @param clubLocation
	 * @return list<ClubDto>clubList clubLocation
	 * 
	 */
	public String initDeleteClub(){
		HttpServletRequest request=ServletActionContext.getRequest();
		List<ClubDto> clubList=new ArrayList<ClubDto>();
		if(clubDto.getClubLocation()==null||clubDto.getClubLocation().equals("")){
			clubDto.setClubLocation("chengdu");
		}
		try {
			clubList=clubService.getClubByLocation(clubDto.getClubLocation());
			request.setAttribute("clubList",clubList);
			request.setAttribute("clubLocation", clubDto.getClubLocation());
		} catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * @Author Walker Cheng 
	 * 2014/12/25 
	 * function search  my club information that I have joined in 
	 * @param userId pageIndex
	 * @return List<SearchUserClubDto> clubList  ListNumber pageIndex
	 * 
	 */
	public String searchMyClub(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Integer pageIndex;
		Integer listNumber=0;
		User user =new User();
		List<SearchUserClubDto> clubList=new ArrayList<SearchUserClubDto>();
		if (request.getParameter("pageIndex").equals("")||request.getParameter("pageIndex")==null||request.getParameter("pageIndex").equals("0")) {
			pageIndex=0;
		}else{
		    pageIndex=(Integer.parseInt(request.getParameter("pageIndex"))-1)*5;
		}
		user=UserUtil.getUser(request);
		try {
			clubList=userService.searchMyClubInfoByUserId(user.getUserId(),pageIndex);
		} catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		pageIndex=pageIndex/5+1;
		listNumber=clubList.size();
		request.setAttribute("listNumber",listNumber);
		request.setAttribute("pageIndex",pageIndex);
		request.setAttribute("clubList",clubList);
		return SUCCESS;
	}
	
	/**
	 * @Author Walker Cheng function delete club
	 * 2014/12/12
	 * @ajax
	 * @param ClubDto
	 */
	public void deleteClub(){
		try{
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;UTF-8");
			PrintWriter out = response.getWriter();
			List<ClubDto> resultList=new ArrayList<ClubDto>();
			Boolean result;
			ClubDto clubDto1=new ClubDto();
			ClubDto clubDto2=new ClubDto();
			clubDto1.setClubName(clubDto.getClubName());
			clubDto1.setClubLocation(clubDto.getClubLocation());
			resultList=clubService.searchClubByClubNameAndClubLocation(clubDto1);
			clubDto2=resultList.get(0);
			Integer memberNumber=clubService.queryMemberNumber(clubDto2);
			if(memberNumber==1){
				result=clubService.deleteClub(clubDto2);
				if(result){
					out.println("{\"status\":1,\"url\":\"init.action\"}");
					User user= userService.getUserByUserId(clubDto2.getManagerId());
					String subject = "俱乐部删除提醒！";
					String content ="Hi"+user.getUserName()+"，你所负责的俱乐部："
							+clubDto.getClubName()+"因为人数原因，已被删除！";
					String to =user.getUserEmail();
					EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
							PASSWORD);
				}else{
					out.println("{\"status\":0,\"msg\":\"俱乐部删除失败！\"}");
				}
			}else {
				out.println("{\"status\":0,\"msg\":\"你选择的俱乐部不满足删除条件！\"}");
			}
		}catch(HibernateException e){
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}

}
