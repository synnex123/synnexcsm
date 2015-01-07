package com.synnex.cms.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;

public interface UserService {
	/**
	 * 2014/11/19 function register
	 * 
	 * @author joeyy
	 */
	public boolean save(User user) ;

	/**
	 * @author joeyy 2014/11/24 function login
	 */
	public User checklogin(User user) ;

	/**
	 * @author joeyy 2014/11/25 function change userpassword
	 */
	public boolean updateUserInfo(User user) ;

	/**
	 * @author walker cheng 2014/12/02 get the user information by userName
	 * @param userName
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> search(String userName);

	/**
	 * @author walker cheng 2014/12/02 get the club director information
	 * @param pageIndex
	 * @return List<SearchDto>
	 * @throws Exception
	 */
	public List<SearchDto> searchClubDirector(Integer pageIndex);

	public List<User> getUserByClubId(int clubId, Integer userId);

	/**
	 * @author walker cheng 2014/12/10 function search uesr by userType
	 * @param userType pageIndex
	 * @return List<SearchDto>
	 * @throws Exception
	 */
	public List<SearchDto> searchUserByUserType(Integer userType,
			Integer pageIndex);

	/**
	 * @author walker cheng 2014/12/11 get the user information by userName
	 * @throws Exception
	 */
	public User getUserByName(String userName);

	/**
	 * @author joeyy 2014/12/10 fucntion changeUserPassword
	 */
	public boolean updatepassword(User user);
	
	/**
	 * @author walker cheng 
	 * function get the user information by user Id
	 * 2014/12/16
	 * @param userId
	 * @return enetity User
	 * @throws Exception
	 */
	public User getUserByUserId(Integer userId);

	/**
	 * @author joeyy 2014/12/24 function getAllUserByClubId
	 * 
	 * @return List<User> in one club
	 * 
	 * @param clubId
	 */
	public List<User> getAllUserByClubId(Integer clubId);
	/**
	 * @author walker cheng
	 * 2014/12/26
	 * function delete the information of UserClub data base due to exit the club
	 * @param entity UserClub
	 * @throws Exception 
	 */
	public void deleteUserClubInfoDuoToExitClub(UserClub userClub);

/**
	 * @author walker cheng
	 * 2014/12/25
	 * search my club information by userId
	 * @param userId pageIndex
	 * @return List<SearchUserClubDto>
	 * @throws Exception 
	 */
	public List<SearchUserClubDto> searchMyClubInfoByUserId(Integer userId,Integer pageIndex);
	/**
	 * @author walker cheng
	 * 2015/01/05
	 * function add system manager
	 * @param userId
	 * @throws Exception 
	 */
	public void addSystemManager(Integer userId) throws HibernateException;
	/**
	 * function deleteUser
	 * @author joeyy
	 * 2015/01/06
	 * 
	 * @param userId
	 * @return 
	 */
	public String deleteUser(Integer userId);
}
