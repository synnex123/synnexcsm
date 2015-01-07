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
import com.sun.mail.iap.Response;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.ClubService;
import com.synnex.cms.service.UserService;
import com.synnex.cms.utils.EmailUtils;
import com.synnex.cms.utils.PageInfo;
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
	private int currentPage;
	private int totalPage;
	private int location;
	private int pageRecords=5;
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
							out.println("{\"status\":1,\"url\":\"init.action\"}");
							final String subject = "俱乐部任命提醒！";
							final String content ="Hi,"+user.getUserName()+"，你已被任命为新增俱乐部："
									+clubDto.getClubName()+" 的负责人,请你尽快在编辑俱乐部中添加俱乐部主页信息！！"
									+ "\n" + "http://" + request.getRemoteHost() + ":8080"
						            + request.getContextPath() + "/user/login.jsp";
							final String to =user.getUserEmail();	
							new Thread(){
								public void run(){
									EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
											PASSWORD);
								}
							}.start();
						}else{
							out.println("{\"status\":0,\"msg\":\"俱乐部添加失败！\"}");
						}
					}
				}
			}			
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
			clubList=clubService.getAllClubByLocation(clubDto.getClubLocation());
			request.setAttribute("clubList",clubList);
			request.setAttribute("clubLocation", clubDto.getClubLocation());
		} catch (Exception e) {
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
		Integer pageIndex=0;
		Integer listNumber=0;
		User user =new User();
		List<SearchUserClubDto> clubList=new ArrayList<SearchUserClubDto>();
		PageInfo page=new PageInfo();
		if(0==currentPage){
			currentPage=1;
		}	
		page.setCurrentPage(currentPage);
		page.setPageRecords(pageRecords);
	 	PageInfo.pageInfo.set(page);
		user=UserUtil.getUser(request);
		try {
			clubList=userService.searchMyClubInfoByUserId(user.getUserId(),pageIndex);
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		totalPage=page.getTotalPage();
		currentPage=page.getCurrentPage();
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageIndex",currentPage);
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
					final String subject = "俱乐部删除提醒！";
					final String content ="Hi,"+user.getUserName()+"，你所负责的俱乐部："
							+clubDto.getClubName()+" 因为人数原因，已被删除！";
					final String to =user.getUserEmail();
					new Thread(){
						public void run(){
							EmailUtils.send(SMTP, FORM, to, subject, content, USERNAME,
									PASSWORD);
						}
					}.start();
				}else{
					out.println("{\"status\":0,\"msg\":\"俱乐部删除失败！\"}");
				}
			}else {
				out.println("{\"status\":0,\"msg\":\"你选择的俱乐部不满足删除条件！\"}");
			}
		} catch (IOException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}
	/**
	 * @Author Pete Peng function getClubMembers
	 * 2011/1/2
	 * 
	 */
	public String getClubMembers(){
		List<SearchDto> userList;
		PageInfo page=new PageInfo();
		if(currentPage==0)currentPage=1;
		page.setCurrentPage(currentPage);
		page.setPageRecords(pageRecords);
		PageInfo.pageInfo.set(page);
		location=Integer.parseInt(ServletActionContext.getRequest().getParameter("location"));
		userList=clubService.getClubMembers(location);
		totalPage=page.getTotalPage();
		ServletActionContext.getRequest().getSession().setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession().setAttribute("currentPage", currentPage);
		ServletActionContext.getRequest().getSession().setAttribute("userList", userList);
		return "success";
	}
	
	/**
	 * @Author Walker Cheng 
	 * function get the club information that I am responsible for
	 * 2015/01/06
	 */
	public String initEditClub(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Club club=new Club();
		User user=new User();
		user=UserUtil.getUser(request);
		club=userService.searchMyResponsibleClubById(user.getUserId());
		request.setAttribute("club", club);
		return SUCCESS;
	}
	
	/**
	 * @Author Walker Cheng 
	 * function update the club information due to  Modify the Program by principal
	 * 2015/01/06
	 */
	public void editClub(){
		HttpServletResponse response=ServletActionContext.getResponse();
		try{
			PrintWriter out=response.getWriter();
			Club club=new Club();
			club.setClubId(clubDto.getClubId());
			club.setClubDescription(clubDto.getClubDescription());
			club.setClubLocation(clubDto.getClubLocation());
			club.setClubName(clubDto.getClubName());
			club.setClubUrl(clubDto.getClubUrl());
			club.setManagerId(clubDto.getManagerId());
			clubService.updateClub(club);
			out.println("{\"status\":1}");
		} catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}
	
}
