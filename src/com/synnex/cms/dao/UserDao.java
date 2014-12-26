package com.synnex.cms.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;

/**
 * @author joeyy
 * 
 */
/*
 * function register 2014/11/19
 */
public interface UserDao extends BaseDao {
	public void save(User user) throws HibernateException;

	/*
	 * @author Pete
	 * 
	 * @modified by Joeyy function for exclude and promotion 2014/12/1
	 */
	public List<User> getUserByClubId(int clubId, int userId)
			throws HibernateException;

	/*
	 * @author Pete function for exclude 2014/12/2
	 */
	public User getUserByName(String userName) throws HibernateException;

	/*
	 * @author Pete function for exclude 2014/12/2
	 */
	public User getUserByUserId(int userId) throws HibernateException;

	/*
	 * function login 2014/11/24
	 */
	public User checklogin(User user) throws HibernateException;

	/*
	 * @author Pete function for exclude 2014/12/2
	 */
	public int countClubMember(int clubId) throws HibernateException;

	/*
	 * function change userpassword 2014/11/25
	 */
	public boolean updateUserInfo(User user) throws HibernateException;

	/*
	 * @author joryy 2014/12/08 function down UserType for promotion
	 * 
	 * @return true if it's succeed ,else return false
	 * 
	 * @param UserId who'll be downType
	 */
	public boolean downUserType(Integer userId) throws HibernateException;

	/*
	 * @author joryy 2014/12/08 function GetManagerIdByPromotionId
	 * 
	 * @return managerId
	 * 
	 * @param promotionId
	 */
	public Integer GetManagerIdByPromotionId(Integer promotionId)
			throws HibernateException;

	/*
	 * @author joeyy 2014/12/08 function up UserType for promotion
	 * 
	 * @return true if it's succeed ,else return false
	 * 
	 * @param UserId who'll be upType
	 */
	public boolean UpUserType(Integer userId) throws HibernateException;

	/**
	 * @author walker cheng 2014/12/01 function get the club director
	 *         information
	 */
	public List<SearchDto> searchClubDirector(Integer pageIndex)
			throws HibernateException;

	/**
	 * @author walker cheng 2014/12/01 function search uesr by userName
	 */
	public List<SearchDto> searchUserByUserName(String userName)
			throws HibernateException;

	/**
	 * @author walker cheng 2014/12/01 function search the club information of
	 *         user by userId
	 */
	public List<SearchUserClubDto> searchUserClubInfoByUserId(Integer userId)
			throws HibernateException;

	/**
	 * @author walker cheng 2014/12/10 function search uesr by userType
	 */
	public List<SearchDto> searchUserByUserType(Integer userType,
			Integer pageIndex) throws HibernateException;

	/*
	 * @Author Walker Cheng function update the user information by userId
	 * bucause of delete club 2014/12/12
	 */
	public Boolean updateUserInfoById(Integer userId, Session session)
			throws HibernateException;

	/*
	 * @author joeyy 2014/12/15 function check if user is exist
	 * 
	 * @return true if user is exist ,else return false
	 * 
	 * @param Entity User
	 */
	public User checkexist(User user) throws HibernateException;

	/*
	 * @Author Walker Cheng function query the number of member 2014/12/15
	 */
	public Integer queryMemberNumber(Integer clubId) throws HibernateException,
			NumberFormatException;

	/*
	 * @author joeyy 2014/12/03 function changepassword
	 */
	public boolean updatepassword(User user) throws HibernateException;

	/*
	 * @author joeyy 2014/12/24 function getAllUserByClubId on one club
	 * 
	 * @return List<User> in one club
	 * 
	 * @param clubId
	 */
	public List<User> getAllUserByClubId(Integer clubId)
			throws HibernateException;

	/*
	 * @Author Walker Cheng delete the information of UserClub data base due to
	 * exit the club 2014/12/26
	 */
	public void deleteUserClubInfoDuoToExitClub(UserClub userClub)
			throws HibernateException;

	/**
	 * @author walker cheng 2014/12/25 function search my club information by
	 *         userId
	 */
	public List<SearchUserClubDto> searchMyClubInfoByUserId(Integer userId,
			Integer pageIndex) throws HibernateException;

}
