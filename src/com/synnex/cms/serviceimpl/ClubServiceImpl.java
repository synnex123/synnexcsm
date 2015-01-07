package com.synnex.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synnex.cms.dao.ApplyDao;
import com.synnex.cms.dao.ClubDao;
import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.ClubService;

/**
 * @author petep 2014/11/25
 */
/**
 * function getClubByLocation for main view 2014/11/25
 */
public class ClubServiceImpl implements ClubService {
	private ClubDao clubDao;
	private UserDao userDao;
	private ApplyDao applyDao;
	private static Logger LOGGER = LoggerFactory.getLogger(ClubServiceImpl.class);
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setClubDao(ClubDao clubDao) {
		this.clubDao = clubDao;
	}
	
	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getClubByLocation(String location){
		List<ClubDto> result=null;
		try{
			result=clubDao.getClubByLocation(location);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}
	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto>  getAllClubByLocation(String location){
		List<ClubDto> result=null;
		try{
			result=clubDao.getAllClubByLocation(location);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}

	public List<Club> getAllClub() {
		List<Club> result=null;
		try{
			result=clubDao.getAllClub();
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}

	/**
	 * @Author Walker Cheng function getClub 2014/11/28
	 */
	public Club getClubByClubId(Integer clubId){	
		Club result=null;
		try{
			result=clubDao.getClubByClubId(clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}

	/**
	 * @author joeyy 2014/12/16 function getClubIdByUserId
	 * 
	 * @return List<Integer> clubId
	 * 
	 * @param userId
	 */
	@SuppressWarnings("rawtypes")
	public List getClubByUserId(Integer userId){
		List result=null;
		try{
			result=clubDao.getClubByUserId(userId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}

	/**
	 * @Author Walker Cheng function search club by clubName and clubLocation
	 * 2014/12/11
	 */
	public List<ClubDto> searchClubByClubNameAndClubLocation(ClubDto clubDto){
		List<ClubDto> result=null;
		try{
			result=clubDao.searchClubByClubNameAndClubLocation(clubDto);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;
	}

	/**
	 * @Author Walker Cheng function add the new club and return the success or
	 * fail; 2014/12/11
	 */
	public Boolean addClub(ClubDto clubDto, User user){
		try{
			UserClub userClub = new UserClub();
			Integer clubId = clubDao.addClub(clubDto);
			userDao.updateUserInfo(user);
			userClub.setClubId(clubId);
			userClub.setUserId(clubDto.getManagerId());
			clubDao.addUserClubInfo(userClub);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return true;
	}

	/**
	 * @author walker cheng function query the number of member 2014/12/15
	 * @throws Exception
	 */
	public Integer queryMemberNumber(ClubDto clubDto) {
		Integer clubId = clubDto.getClubId();
		Integer memberNumber = 0;
		try{
			memberNumber = userDao.queryMemberNumber(clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}catch (NumberFormatException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return memberNumber;
	}

	/**
	 * @author walker cheng function delete the club 2014/12/12
	 * @throws Exception
	 */
	public Boolean deleteClub(ClubDto clubDto){
		try{
			Club club = new Club();
			UserClub userClub = new UserClub();
			User user = new User();
			club.setClubName(clubDto.getClubName());
			club.setClubId(clubDto.getClubId());
			club.setClubLocation(clubDto.getClubLocation());
			club.setClubDescription(clubDto.getClubDescription());
			club.setManagerId(clubDto.getManagerId());
			club.setClubUrl(clubDto.getClubUrl());
			clubDao.deleteClub(club);
			userClub.setClubId(clubDto.getClubId());
			userClub.setUserId(clubDto.getManagerId());
			clubDao.deleteUserClubInfo(userClub);
			applyDao.deleteApplyByClubId(clubDto.getClubId());
			user = userDao.getUserByUserId(clubDto.getManagerId());
			user.setUserType(1);
			userDao.updateUserInfo(user);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return true;
	}

	public List<Club> getAllCLubByUserId(Integer userId){
		List<Club> result=null;
		try{
			result=clubDao.getAllCLubByUserId(userId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;	
	}

	/**
	 * @author joeyy 2014/12/24 fucntion getClubByPromotionId
	 * 
	 * @return Entity Club
	 * 
	 * @param promotionId
	 */
	public Club getClubByPromotionId(Integer promotionId){
		Club result=null;
		try{
			result=clubDao.getClubByPromotionId(promotionId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return result;	
	}
	/**
	 * @author petep 2015/1/2 fucntion getClubMembers
	 * 
	 * @return List<SearchDto>
	 * 
	 * @param clubId
	 */
	public List<SearchDto> getClubMembers(int clubId){
		List<SearchDto> userList=new ArrayList<SearchDto>();
		try{
			List<User> clubUsers=userDao.getUserByClubId(clubId);
			Club club=clubDao.getClubByClubId(clubId);
			for(User user : clubUsers){
				SearchDto searchDto=new SearchDto();
				searchDto.setClubId(clubId);
				searchDto.setClubName(club.getClubName());
				searchDto.setUserId(user.getUserId());
				searchDto.setUserName(user.getUserName());
				searchDto.setUserPart(user.getUserPart());
				searchDto.setUserPhone(user.getUserPhone());
				searchDto.setUserType(user.getUserType());
				searchDto.setUserEmail(user.getUserEmail());
				if(user.getUserId()==club.getManagerId()){
					searchDto.setUserTypeMsg("管理员");
				}
				else{
					searchDto.setUserTypeMsg("普通成员");
				}
				userList.add(searchDto);
			}
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
		return userList;
	}
	
	
	/**
	 * @Author Walker Cheng 
	 * function update the club information due to  Modify the Program by principal
	 * 2015/01/06
	 * @param Club
	 */
	public void updateClub(Club club){
		try {
			clubDao.updateClub(club);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		
	}
}
