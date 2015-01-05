package com.synnex.cms.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.dto.SearchUserClubDto;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.utils.PageInfo;

/**
 * @author joeyy
 * 
 */
/**
 * function register 2014/11/19
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	Session session = null;

	public void save(User user) throws HibernateException {
		try {
			getSession().save(user);
		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @author Pete
	 * 
	 * @modified by Joeyy function for exclude and promotion 2014/12/1
	 */
	public List<User> getUserByClubId(int clubId, int userId)
			throws HibernateException {
		List<User> resultList = new ArrayList<>();
		String hql = "";
		try {
			session = getSession();
			hql = "from User u,UserClub uc where uc.userId = u.userId and u.userId <> :userId and  uc.clubId = :clubId and u.userType <> 0";
			Query query = session.createQuery(hql);
			query.setInteger("clubId", clubId);
			query.setInteger("userId", userId);
			@SuppressWarnings("rawtypes")
			List resultList1 = query.list();
			for (int i = 0; i < resultList1.size(); i++) {
				Object[] row = (Object[]) resultList1.get(i);
				resultList.add((User) row[0]);
			}

		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	/**
	 * @author Pete function for exclude 2014/12/2
	 */
	public User getUserByName(String userName) throws HibernateException {
		String hql = "";
		User user = null;
		try {
			session = getSession();
			hql = "from User u where u.userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			user = (User) query.uniqueResult();
		} catch (HibernateException e) {
			throw e;
		}
		return user;
	}

	/**
	 * @author Pete function for exclude 2014/12/2
	 */
	public User getUserByUserId(int userId) throws HibernateException {
		String hql = "";
		User user = null;
		try {
			session = getSession();
			hql = "from User u where u.userId=:userId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userId);
			user = (User) query.uniqueResult();
		} catch (HibernateException e) {
			throw e;
		}
		return user;
	}

	/**
	 * function login 2014/11/24
	 */
	@SuppressWarnings("unchecked")
	public User checklogin(User user) throws HibernateException {
		List<User> resultlist = null;
		String hql = "";
		String userName = user.getUserName();
		String userPassword = user.getUserPassword();
		try {
			session = getSession();
			hql = "from User u where u.userName =? and u.userPassword =?";
			Query query = session.createQuery(hql);
			query.setString(0, userName);
			query.setString(1, userPassword);
			if (!query.list().isEmpty()) {
				resultlist = query.list();
			} else {
				return null;
			}
			return (User) resultlist.get(0);

		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @author Pete function for exclude 2014/12/2
	 */
	public int countClubMember(int clubId) throws HibernateException {
		int count = -1;
		String hql = "";
		try {
			session = getSession();
			hql = "from User u where u.clubId =:clubId";
			Query query = session.createQuery(hql);
			query.setInteger("clubId", clubId);
			count = query.list().size();
		} catch (HibernateException e) {
			throw e;
		}
		return count;
	}

	/**
	 * function change userpassword 2014/11/25
	 */
	public boolean updateUserInfo(User user) throws HibernateException {
		try {
			session = getSession();
			session.update(user);
		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author joryy 2014/12/08 function down UserType for promotion
	 * 
	 * @return true if it's succeed ,else return false
	 * 
	 * @param UserId
	 *            who'll be downType
	 */
	public boolean downUserType(Integer userId) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update User u set u.userType=1 where u.userId=:userId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userId);
			query.executeUpdate();
		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author joryy 2014/12/08 function GetManagerIdByPromotionId
	 * 
	 * @return managerId
	 * 
	 * @param promotionId
	 */
	public Integer getManagerIdByPromotionId(Integer promotionId)
			throws HibernateException {
		String hql = "";
		Integer userId = null;
		try {
			session = getSession();
			hql = "select u.userId from User u,Club c,Promotion p where p.clubId=c.clubId and c.managerId=u.userId and p.promotionId=:promotionId";
			Query query = session.createQuery(hql);
			query.setInteger("promotionId", promotionId);
			userId = (Integer) query.uniqueResult();
		} catch (HibernateException e) {
			throw e;
		}
		return userId;
	}

	/**
	 * @author joeyy 2014/12/08 function up UserType for promotion
	 * 
	 * @return true if it's succeed ,else return false
	 * 
	 * @param UserId
	 *            who'll be upType
	 */
	public boolean upUserType(Integer userId) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update User u set u.userType=0 where u.userId=:userId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userId);
			query.executeUpdate();
		} catch (HibernateException e) {
			throw e;
		}

		return true;
	}

	/**
	 * @author walker cheng 2014/12/01 function get the club director
	 *         information
	 */
	public List<SearchDto> searchClubDirector(Integer pageIndex)
			throws HibernateException {
		List<SearchDto> resultList = new ArrayList<SearchDto>();
		String hql = null;
		Query query;
		try {
			session = getSession();
			hql = "Select u.userEmail,u.userPhone,u.userPart,u.userType,c.clubId,c.clubName,c.clubLocation,u.userName,u.userId from User u ,Club c where  u.userId=c.managerId";
			query = session.createQuery(hql).setMaxResults(5)
					.setFirstResult(pageIndex);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] listObjects = (Object[]) list.get(i);
				SearchDto searchDto = new SearchDto();
				searchDto.setUserEmail((String) listObjects[0]);
				searchDto.setUserPhone((String) listObjects[1]);
				searchDto.setUserPart((String) listObjects[2]);
				searchDto.setUserType((Integer) listObjects[3]);
				searchDto.setClubId((Integer) listObjects[4]);
				searchDto.setClubName((String) listObjects[5]);
				searchDto.setClubLocation((String) listObjects[6]);
				searchDto.setUserName((String) listObjects[7]);
				searchDto.setUserId((Integer) listObjects[8]);
				resultList.add(searchDto);
			}

		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	/**
	 * @author walker cheng 2014/12/01 function search uesr by userName
	 */
	public List<SearchDto> searchUserByUserName(String userName)
			throws HibernateException {
		List<SearchDto> resultList = new ArrayList<SearchDto>();
		String hql = null;
		Query query;
		try {
			session = getSession();
			hql = "Select u.userEmail,u.userPhone,u.userPart,u.userType,u.userName,u.userId from User u where u.userName=?";
			query = session.createQuery(hql);
			query.setString(0, userName);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] listObjects = (Object[]) list.get(i);
				SearchDto searchDto = new SearchDto();
				searchDto.setUserEmail((String) listObjects[0]);
				searchDto.setUserPhone((String) listObjects[1]);
				searchDto.setUserPart((String) listObjects[2]);
				searchDto.setUserType((Integer) listObjects[3]);
				searchDto.setUserName((String) listObjects[4]);
				searchDto.setUserId((Integer) listObjects[5]);
				resultList.add(searchDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	/**
	 * @author walker cheng 2014/12/01 function search the club information of
	 *         user by userId
	 */
	public List<SearchUserClubDto> searchUserClubInfoByUserId(Integer userId)
			throws HibernateException {
		List<SearchUserClubDto> resultList = new ArrayList<SearchUserClubDto>();
		String hql = null;
		Query query;
		try {
			session = getSession();
			hql = "Select u.userId,c.clubId,c.clubName,c.clubLocation,c.managerId from UserClub u,Club c where u.userId=? and u.clubId=c.clubId ";
			query = session.createQuery(hql);
			query.setInteger(0, userId);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] listObjects = (Object[]) list.get(i);
				SearchUserClubDto searchUserClubDto = new SearchUserClubDto();
				searchUserClubDto.setUserId((Integer) listObjects[0]);
				searchUserClubDto.setClubId((Integer) listObjects[1]);
				searchUserClubDto.setClubName((String) listObjects[2]);
				searchUserClubDto.setClubLocation((String) listObjects[3]);
				searchUserClubDto.setManagerId((Integer) listObjects[4]);
				resultList.add(searchUserClubDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	/**
	 * @author walker cheng 2014/12/10 function search uesr by userType
	 */
	public List<SearchDto> searchUserByUserType(Integer userType,
			Integer pageIndex) throws HibernateException {
		List<SearchDto> resultList = new ArrayList<SearchDto>();
		String hql = null;
		try {
			session = getSession();
			hql = "Select u.userEmail,u.userPhone,u.userPart,u.userType,u.userName,u.userId from User u where u.userType=? ";
			Query query = session.createQuery(hql).setMaxResults(5)
					.setFirstResult(pageIndex);
			query.setInteger(0, userType);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] listObjects = (Object[]) list.get(i);
				SearchDto searchDto = new SearchDto();
				searchDto.setUserEmail((String) listObjects[0]);
				searchDto.setUserPhone((String) listObjects[1]);
				searchDto.setUserPart((String) listObjects[2]);
				searchDto.setUserType((Integer) listObjects[3]);
				searchDto.setUserName((String) listObjects[4]);
				searchDto.setUserId((Integer) listObjects[5]);
				resultList.add(searchDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	/**
	 * @Author Walker Cheng function update the user information by userId
	 *         bucause of delete club 2014/12/12
	 */
	public Boolean updateUserInfoById(Integer userId, Session session)
			throws HibernateException {
		String hql = "update User set userType=?,clubId=? where userId=?";
		try {
			Query query = session.createQuery(hql);
			query.setInteger(0, 1);
			query.setInteger(1, 0);
			query.setInteger(2, userId);
			int count = query.executeUpdate();
			if (count == 0) {
				return false;
			}

		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author joeyy 2014/12/15 function check if user is exist
	 * 
	 * @return true if user is exist ,else return false
	 * 
	 * @param Entity
	 *            User
	 */
	public User checkexist(User user) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "from User u where u.userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", user.getUserName());
			return (User) query.uniqueResult();

		} catch (HibernateException e) {
			throw e;
		}

	}

	/**
	 * @Author Walker Cheng function query the number of member 2014/12/15
	 */
	public Integer queryMemberNumber(Integer clubId) throws HibernateException,
			NumberFormatException {
		String hql = "select count(userId) from UserClub where clubId=?";
		Integer number = 0;
		try {
			session = getSession();
			Query query = session.createQuery(hql);
			query.setInteger(0, clubId);
			number = Integer.parseInt(query.uniqueResult() + "");
		} catch (HibernateException e) {
			throw e;
		} catch (NumberFormatException e1) {
			throw e1;
		}
		return number;
	}

	/**
	 * @author joeyy 2014/12/03 function changepassword
	 */
	public boolean updatepassword(User user) throws HibernateException {
		try {
			session = getSession();
			session.update(user);
		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author joeyy 2014/12/24 function getAllUserByClubId on one club
	 * 
	 * @return List<User> in one club
	 * 
	 * @param clubId
	 */
	public List<User> getAllUserByClubId(Integer clubId)
			throws HibernateException {
		String hql = "";
		List<User> userlist = new ArrayList<>();
		try {
			session = getSession();
			hql = "from User u,UserClub uc where uc.clubId=:clubId and uc.userId=u.userId";
			Query query = session.createQuery(hql);
			query.setInteger("clubId", clubId);
			@SuppressWarnings("rawtypes")
			List userlist1 = query.list();
			for (int i = 0; i < userlist1.size(); i++) {
				Object[] row = (Object[]) userlist1.get(i);
				userlist.add((User) (row[0]));
			}
		} catch (HibernateException e) {
			throw e;
		}
		return userlist;
	}

	/**
	 * @Author Walker Cheng delete the information of UserClub data base due to
	 *         exit the club 2014/12/26
	 */
	public void deleteUserClubInfoDuoToExitClub(UserClub userClub)
			throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "delete from UserClub uc where uc.userId=:userId and uc.clubId=:clubId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userClub.getUserId());
			query.setInteger("clubId", userClub.getClubId());
			query.executeUpdate();
		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @author walker cheng 2014/12/25 function search my club information by
	 *         userId
	 */
	public List<SearchUserClubDto> searchMyClubInfoByUserId(Integer userId,
			Integer pageIndex) throws HibernateException {
		List<SearchUserClubDto> resultList = new ArrayList<SearchUserClubDto>();
		String hql = null;
		try {
			PageInfo pageInfo=(PageInfo)PageInfo.pageInfo.get();
			session = getSession();
			hql = "Select u.userId,c.clubId,c.clubName,c.clubLocation,c.managerId,s.userEmail,s.userName from UserClub u,Club c,User s where u.userId=? and u.clubId=c.clubId and c.managerId=s.userId ";
			String countHql="Select count(*) from UserClub u,Club c,User s where u.userId=? and u.clubId=c.clubId and c.managerId=s.userId ";
			Query queryCount=session.createQuery(countHql);			
			Query query = session.createQuery(hql);
			query.setFirstResult((pageInfo.getCurrentPage()-1)*pageInfo.getPageRecords());
			query.setMaxResults(pageInfo.getPageRecords());
			query.setInteger(0, userId);
			queryCount.setInteger(0, userId);
			int totalPage=((Long)queryCount.uniqueResult()).intValue();
			if(totalPage%pageInfo.getPageRecords()!=0){
				totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords()+1);
			}
			else {
				totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords());
			}
			pageInfo.setTotalPage(totalPage);
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] listObjects = (Object[]) list.get(i);
				SearchUserClubDto searchUserClubDto = new SearchUserClubDto();
				searchUserClubDto.setUserId((Integer) listObjects[0]);
				searchUserClubDto.setClubId((Integer) listObjects[1]);
				searchUserClubDto.setClubName((String) listObjects[2]);
				searchUserClubDto.setClubLocation((String) listObjects[3]);
				searchUserClubDto.setManagerId((Integer) listObjects[4]);
				searchUserClubDto.setManagerEmail((String) listObjects[5]);
				searchUserClubDto.setManagerName((String) listObjects[6]);
				resultList.add(searchUserClubDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByClubId(int clubId) throws HibernateException {
		String hql = "";
		List<User> userlist = new ArrayList<>();
		
		session = getSession();
		
		String countHql= "select count(*) from User u,UserClub uc where uc.clubId=:clubId and uc.userId=u.userId";
		Query queryCount = session.createQuery(countHql);
		queryCount.setInteger("clubId", clubId);
		PageInfo pageInfo=(PageInfo)PageInfo.pageInfo.get();
		int totalPage=((Long)queryCount.uniqueResult()).intValue();
		if(totalPage%pageInfo.getPageRecords()!=0){
			totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords()+1);
		}
		else {
			totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords());
		}
		pageInfo.setTotalPage(totalPage);
		PageInfo.pageInfo.set(pageInfo);
		
		hql = "from User u,UserClub uc where uc.clubId=:clubId and uc.userId=u.userId";
		Query query = session.createQuery(hql);
		query.setInteger("clubId", clubId);
		query.setFirstResult((pageInfo.getCurrentPage()-1)*pageInfo.getPageRecords());
		query.setMaxResults(pageInfo.getPageRecords());
		@SuppressWarnings("rawtypes")
		List userlist1 = query.list();
		for (int i = 0; i < userlist1.size(); i++) {
			Object[] row = (Object[]) userlist1.get(i);
			userlist.add((User) (row[0]));
		}
		
		return userlist;
	}

}
