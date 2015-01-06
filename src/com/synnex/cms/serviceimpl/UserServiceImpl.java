package com.synnex.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
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
	
	/**
	 * @Author Walker Cheng 
	 * function get the club information that I am responsible for
	 * 2015/01/06
	 * @param userId
	 * @return Club
	 */
	public Club searchMyResponsibleClubById(Integer userId){
		Club result=null;
		try{
			result=userDao.searchMyResponsibleClubById(userId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

}
