package com.synnex.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
	public boolean save(User user) throws HibernateException {
		try {
			if (userDao.checkexist(user) != null) {
				return false;
			} else {
				userDao.save(user);
				return true;
			}

		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @author joeyy 2014/11/24 function login
	 */
	public User checklogin(User user) throws HibernateException {
		return userDao.checklogin(user);
	}

	/**
	 * @author joeyy 2014/11/25 function change userpassword
	 */
	public boolean updateUserInfo(User user) throws HibernateException {
		return userDao.updateUserInfo(user);
	}

	/**
	 * @author walker cheng 2014/12/02 get the user information by userName
	 * @throws Exception
	 */
	public List<Object> search(String userName) throws HibernateException {
		List<Object> list = new ArrayList<Object>();
		List<SearchDto> list1 = new ArrayList<SearchDto>();
		List<SearchUserClubDto> clubList = new ArrayList<SearchUserClubDto>();
		SearchDto searchDto = new SearchDto();
		list1 = userDao.searchUserByUserName(userName);
		if (list1.size() == 0) {
			return list;
		}
		searchDto = list1.get(0);
		Integer userId = searchDto.getUserId();
		clubList = userDao.searchUserClubInfoByUserId(userId);
		list.add(searchDto);
		list.add(clubList);
		return list;
	}

	/**
	 * @author walker cheng 2014/12/02 get the club director information
	 * @throws Exception
	 */
	public List<SearchDto> searchClubDirector(Integer pageIndex)
			throws HibernateException {
		return userDao.searchClubDirector(pageIndex);
	}

	public List<User> getUserByClubId(int clubId, Integer userId)
			throws HibernateException {
		return userDao.getUserByClubId(clubId, userId);
	}

	/**
	 * @author walker cheng 2014/12/02 get the club director information
	 * @throws Exception
	 */
	public List<SearchDto> searchUserByUserType(Integer userType,
			Integer pageIndex) throws HibernateException {
		return userDao.searchUserByUserType(userType, pageIndex);
	}

	/**
	 * @author walker cheng 2014/12/11 get the user information by userName
	 * @throws Exception
	 */
	public User getUserByName(String userName) throws HibernateException {
		return userDao.getUserByName(userName);
	}

	/**
	 * @author joeyy 2014/12/10 fucntion changeUserPassword
	 */
	public boolean updatepassword(User user) throws HibernateException {
		return userDao.updatepassword(user);
	}

	public User getUserByUserId(Integer userId) throws HibernateException {
		return userDao.getUserByUserId(userId);
	}

	/**
	 * @author joeyy 2014/12/24 function getAllUserByClubId
	 * 
	 * @return List<User> in one club
	 * 
	 * @param clubId
	 */
	public List<User> getAllUserByClubId(Integer clubId) throws HibernateException{

		return userDao.getAllUserByClubId(clubId);
	}
	/**
	 * @author walker cheng
	 * 2014/12/26
	 * delete the information of UserClub data base due to exit the club
	 * @throws Exception 
	 */
	public void deleteUserClubInfoDuoToExitClub(UserClub userClub) throws HibernateException{
		userDao.deleteUserClubInfoDuoToExitClub(userClub);
	}

/**
	 * @author walker cheng
	 * 2014/12/25
	 * search my club information by userId
	 * @throws Exception 
	 */
	public List<SearchUserClubDto> searchMyClubInfoByUserId(Integer userId,Integer pageIndex) throws HibernateException{
		return userDao.searchMyClubInfoByUserId(userId,pageIndex);
	}

}
