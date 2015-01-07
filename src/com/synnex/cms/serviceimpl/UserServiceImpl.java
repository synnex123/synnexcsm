package com.synnex.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synnex.cms.dao.ApplyDao;
import com.synnex.cms.dao.ClubDao;
import com.synnex.cms.dao.PromotionVoteRecordDao;
import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private PromotionVoteRecordDao promotionVoteRecordDao;
	public void setPromotionVoteRecordDao(
			PromotionVoteRecordDao promotionVoteRecordDao) {
		this.promotionVoteRecordDao = promotionVoteRecordDao;
	}

	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	public void setClubDao(ClubDao clubDao) {
		this.clubDao = clubDao;
	}
	private ApplyDao applyDao;
	private ClubDao clubDao;
	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	public void setUserDao(UserDao userDao) {
		try{
			this.userDao = userDao;
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}

	/**
	 * @author joeyy
	 * @throws Exception
	 */
	/**
	 * 2014/11/19 function register
	 * 
	 * @author joeyy
	 */
	public boolean save(User user){
		boolean result=false;
		try {
			if (userDao.checkexist(user) != null) {
				result=false;
			} else {
				userDao.save(user);
				result=true;
			}

		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/11/24 function login
	 */
	public User checklogin(User user){
		User result=null;
		try{
			result=userDao.checklogin(user);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/11/25 function change userpassword
	 */
	public boolean updateUserInfo(User user){	
		boolean result=false;
		try{
			result=userDao.updateUserInfo(user);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author walker cheng 2014/12/02 get the user information by userName
	 * @throws Exception
	 */
	public List<Object> search(String userName){
		List<Object> list = new ArrayList<Object>();
		List<SearchDto> list1 = new ArrayList<SearchDto>();
		List<SearchUserClubDto> clubList = new ArrayList<SearchUserClubDto>();
		SearchDto searchDto = new SearchDto();
		try{
			list1 = userDao.searchUserByUserName(userName);
			if (list1.size() == 0) {
				return list;
			}
			searchDto = list1.get(0);
			Integer userId = searchDto.getUserId();
			clubList = userDao.searchUserClubInfoByUserId(userId);
			list.add(searchDto);
			list.add(clubList);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return list;
	}

	/**
	 * @author walker cheng 2014/12/02 get the club director information
	 * @throws Exception
	 */
	public List<SearchDto> searchClubDirector(Integer pageIndex){
		List<SearchDto> result=null;
		try{
			result=userDao.searchClubDirector(pageIndex);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	public List<User> getUserByClubId(int clubId, Integer userId){
		List<User> result=null;
		try{
			result=userDao.getUserByClubId(clubId, userId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author walker cheng 2014/12/02 get the club director information
	 * @throws Exception
	 */
	public List<SearchDto> searchUserByUserType(Integer userType,Integer pageIndex){	
		List<SearchDto> result=null;
		try{
			result=userDao.searchUserByUserType(userType, pageIndex);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author walker cheng 2014/12/11 get the user information by userName
	 * @throws Exception
	 */
	public User getUserByName(String userName){
		User result=null;
		try{
			result=userDao.getUserByName(userName);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/12/10 fucntion changeUserPassword
	 */
	public boolean updatepassword(User user) {
		boolean result=false;
		try{
			result=userDao.updatepassword(user);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	public User getUserByUserId(Integer userId){
		User result=null;
		try{
			result=userDao.getUserByUserId(userId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/12/24 function getAllUserByClubId
	 * 
	 * @return List<User> in one club
	 * 
	 * @param clubId
	 */
	public List<User> getAllUserByClubId(Integer clubId){
		List<User> result=null;
		try{
			result=userDao.getAllUserByClubId(clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}
	/**
	 * @author walker cheng
	 * 2014/12/26
	 * delete the information of UserClub data base due to exit the club
	 * @throws Exception 
	 */
	public void deleteUserClubInfoDuoToExitClub(UserClub userClub){
		try{
			userDao.deleteUserClubInfoDuoToExitClub(userClub);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
	}

/**
	 * @author walker cheng
	 * 2014/12/25
	 * search my club information by userId
	 * @throws Exception 
	 */
	public List<SearchUserClubDto> searchMyClubInfoByUserId(Integer userId,Integer pageIndex){
		List<SearchUserClubDto> result=null;
		try{
			result=userDao.searchMyClubInfoByUserId(userId,pageIndex);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}
	
	/**
	 * @author walker cheng
	 * 2015/01/05
	 * function add system manager
	 * @param userId
	 * @throws Exception 
	 */
	public void addSystemManager(Integer userId) throws HibernateException{
		User user=new User();
		user=userDao.getUserByUserId(userId);
		user.setUserType(10);
		userDao.updateUserInfo(user);
	}
	/**function deleteUserByUserId
	 * @author joeyy
	 * 2015/01/06
	 * @param userId
	 * @return "principal" if this user is principal in club;"votedUser" if this user is in promotion and is votedUser;else return succeed
	 */
	public String deleteUser(Integer userId){
		//若此人是俱乐部负责人则不能注销
		if (userDao.getUserByUserId(userId).getUserType()==0) {
			return "principal";
		}//若此人正在一个选举中并且已被投票则不能注销
		else if(promotionVoteRecordDao.getPromotionVoteRecordByVotedUser(userId).size()!=0){
			return "votedUser";
		}else{
			//删除此人的所有申请
			applyDao.deleteApplyByUserId(userId);
			//删除此人的所有投票信息
			promotionVoteRecordDao.deleteByVoteUserId(userId);
			//删除此人的所有俱乐部关联信息
			UserClub uc=new UserClub();
			uc.setUserId(userId);
			clubDao.deleteUserClubInfo(uc);
			//删除此人对应的userinfo
			userDao.deleteUserByUserId(userId);
			return "success";
		}
	}

}
