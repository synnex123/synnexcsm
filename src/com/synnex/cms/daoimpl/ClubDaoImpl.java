package com.synnex.cms.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.synnex.cms.action.InitAction;
import com.synnex.cms.dao.ClubDao;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.utils.PageInfo;

public class ClubDaoImpl extends BaseDaoImpl implements ClubDao {
	Session session = null;
	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getClubByLocation(String location)
			throws HibernateException {
		List<ClubDto> clubList = new ArrayList<ClubDto>();
		String hql = "";
		try {
			PageInfo pageInfo=(PageInfo)InitAction.pageInfo.get();
			session = getSession();
			hql = "select c.clubId,c.clubName,u.userName,u.userPhone,c.clubLocation from Club c,User u "
					+ "where c.clubLocation=:location and c.managerId=u.userId";
			Query query = session.createQuery(hql);
			String countHql="select count(*) from Club c,User u "
					+ "where c.clubLocation=:location and c.managerId=u.userId";
			Query queryCount=session.createQuery(countHql);
			queryCount.setString("location", location);
			int totalPage=((Long)queryCount.uniqueResult()).intValue();
			if(totalPage%pageInfo.getPageRecords()!=0){
				totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords()+1);
			}
			else {
				totalPage=(int)((totalPage-totalPage%pageInfo.getPageRecords())/pageInfo.getPageRecords());
			}
			pageInfo.setTotalPage(totalPage);
			InitAction.pageInfo.set(pageInfo);
			query.setString("location", location);
			query.setFirstResult((pageInfo.getCurrentPage()-1)*pageInfo.getPageRecords());
			query.setMaxResults(pageInfo.getPageRecords());
			@SuppressWarnings("rawtypes")
			List result = query.list();
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				ClubDto clubDto = new ClubDto();
				clubDto.setClubId((Integer) row[0]);
				clubDto.setClubName((String) row[1]);
				clubDto.setManagerName((String) row[2]);
				clubDto.setManagerPhone((String) row[3]);
				clubDto.setClubLocation((String) row[4]);
				clubList.add(clubDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return clubList;
	}
	

	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getAllClubByLocation(String location)
			throws HibernateException {
		List<ClubDto> clubList = new ArrayList<ClubDto>();
		String hql = "";
		try {
			session = getSession();
			hql = "select c.clubId,c.clubName,u.userName,u.userPhone,c.clubLocation from Club c,User u "
					+ "where c.clubLocation=:location and c.managerId=u.userId";
			Query query = session.createQuery(hql);
			query.setString("location", location);
			@SuppressWarnings("rawtypes")
			List result = query.list();
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				ClubDto clubDto = new ClubDto();
				clubDto.setClubId((Integer) row[0]);
				clubDto.setClubName((String) row[1]);
				clubDto.setManagerName((String) row[2]);
				clubDto.setManagerPhone((String) row[3]);
				clubDto.setClubLocation((String) row[4]);
				clubList.add(clubDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return clubList;
	}

	/**
	 * @Author Walker Cheng function getclub 2014/11/28
	 * 
	 */
	public Club getClubByClubId(Integer clubId) throws HibernateException {
		Club club = null;
		try {
			session = getSession();
			club = (Club) session.get(Club.class, clubId);

		} catch (HibernateException e) {
			throw e;
		}
		return club;
	}

	/**
	 * @author joeyy 2014/12/12 function GetClubIdByPromotionId
	 * @return clubID
	 * @param promotionId
	 * 
	 */
	public Integer getClubIdByPromotionId(Integer promotionId)
			throws HibernateException {
		String hql = "";
		Integer clubId = null;
		try {
			session = getSession();
			hql = "select c.clubId from Club c,Promotion p where p.promotionId=:promotionId and "
					+ "p.clubId = c.clubId";
			Query query = session.createQuery(hql);
			query.setInteger("promotionId", promotionId);
			clubId = (Integer) query.uniqueResult();
		} catch (HibernateException e) {
			throw e;
		}
		return clubId;

	}

	/**
	 * @author joeyy 2014/12/12 function UpdateClubInfo for change manager
	 * @return true if update succeed ,else return false
	 * @param Entity
	 *            club
	 * 
	 */
	public boolean updateClubInfoChangeManager(Club club)
			throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update Club c set c.managerId=:managerId where c.clubId=:clubId";
			Query query = session.createQuery(hql);
			query.setInteger("managerId", club.getManagerId());
			query.setInteger("clubId", club.getClubId());
			query.executeUpdate();
		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author joeyy 2014/12/12 function CountClubMemberByPromotionId
	 * @return The number of the club
	 * @param promotionId
	 */
	public Integer countClubMemberByPromotionId(Integer promotionId)
			throws HibernateException {
		String hql = "";
		Integer countmember;
		try {
			session = getSession();
			hql = "select count(*) from UserClub uc,Promotion p where uc.clubId=p.clubId and p.promotionId=:promotionId";
			Query query = session.createQuery(hql);
			query.setInteger("promotionId", promotionId);
			countmember = ((java.lang.Long) query.uniqueResult()).intValue();

		} catch (HibernateException e) {
			throw e;
		}

		return countmember;
	}

	/**
	 * @author joeyy 2014/12/12 function getAllClubByUserId modified by joeyy
	 *         2014/12/22
	 * @return List<Integer> clubId for user
	 * @params UserId
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public List getClubByUserId(Integer userId) throws HibernateException {
		String hql = "";
		List list = new ArrayList<>();
		try {
			session = getSession();
			hql = "select uc.clubId from UserClub uc where uc.userId=:userId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userId);
			list = query.list();
		} catch (HibernateException e) {
			throw e;

		}

		return list;
	}

	/**
	 * @author walker cheng 2014/12/11 function search the club by clubName and
	 *         clubLocation
	 */
	public List<ClubDto> searchClubByClubNameAndClubLocation(ClubDto clubDto)
			throws HibernateException {
		List<ClubDto> clubList = new ArrayList<ClubDto>();
		String hql = "";
		try {
			session = getSession();
			hql = "select clubId, clubName,managerId,clubDescription,clubLocation from Club where clubName=? and clubLocation=?";
			Query query = session.createQuery(hql);
			query.setString(0, clubDto.getClubName());
			query.setString(1, clubDto.getClubLocation());
			@SuppressWarnings("rawtypes")
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] objects = (Object[]) list.get(i);
				ClubDto clubDto2 = new ClubDto();
				clubDto2.setClubId((Integer) objects[0]);
				clubDto2.setClubName((String) objects[1]);
				clubDto2.setManagerId((Integer) objects[2]);
				clubDto2.setClubDescription((String) objects[3]);
				clubDto2.setClubLocation((String) objects[4]);
				clubList.add(clubDto2);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return clubList;
	}

	/**
	 * @Author Walker Cheng function add new club and return clubId 2014/12/11
	 * 
	 */
	public Integer addClub(ClubDto clubDto) throws HibernateException {
		Club club = new Club();
		Integer clubId = 0;
		try {
			session = getSession();
			club.setClubName(clubDto.getClubName());
			club.setClubLocation(clubDto.getClubLocation());
			club.setClubDescription(clubDto.getClubDescription());
			club.setManagerId(clubDto.getManagerId());
			session.save(club);
			clubId = club.getClubId();
		} catch (HibernateException e) {
			throw e;
		}
		return clubId;
	}

	/**
	 * @Author Walker Cheng function add the new Infornation due to add new club
	 *         2014/12/19
	 * 
	 */
	public void addUserClubInfo(UserClub userClub) throws HibernateException {
		try {
			session = getSession();
			session.save(userClub);
		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @Author Walker Cheng function delete the club 2014/12/12
	 * 
	 */
	public void deleteClub(Club club) throws HibernateException {
		try {
			session = getSession();
			session.delete(club);
		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @Author Walker Cheng function delete the club 2014/12/12
	 * 
	 */
	public void deleteUserClubInfo(UserClub userClub) throws HibernateException {
		try {
			session = getSession();
			session.delete(userClub);
		} catch (HibernateException e) {
			throw e;
		}
	}

	/**
	 * @author joeyy 2014/12/16 function getAllClub
	 * @return List<Club>(AllClub)
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Club> getAllClub() throws HibernateException {
		String hql = "";
		List<Club> clublist = new ArrayList<>();
		try {
			session = getSession();
			hql = "from Club c";
			Query query = session.createQuery(hql);
			clublist = query.list();
		} catch (HibernateException e) {
			throw e;
		}
		return clublist;
	}

	/**
	 * @author joeyy 2014/12/16 function getAllClubByUserId
	 * @return List<Club> for one user
	 * @param userId
	 */
	public List<Club> getAllCLubByUserId(Integer userId)
			throws HibernateException {
		String hql = "";
		List<Club> clublist = new ArrayList<Club>();
		try {
			session = getSession();
			hql = "from UserClub uc,Club c where uc.userId=:userId and uc.clubId=c.clubId";
			Query query = session.createQuery(hql);
			query.setInteger("userId", userId);
			@SuppressWarnings("rawtypes")
			List clublist1 = query.list();
			for (int i = 0; i < clublist1.size(); i++) {
				Object[] row = (Object[]) clublist1.get(i);
				clublist.add((Club) row[1]);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return clublist;
	}

	/**
	 * @author joeyy 2014/12/24 fucntion getClubByPromotionId
	 * @return Entity Club
	 * @param promotionId
	 */
	public Club getClubByPromotionId(Integer promotionId)
			throws HibernateException {
		String hql = "";
		Club club = null;
		try {
			session = getSession();
			hql = "from Club c,Promotion p where p.promotionId=:promotionId and p.clubId=c.clubId";
			Query query = session.createQuery(hql);
			query.setInteger("promotionId", promotionId);
			@SuppressWarnings("rawtypes")
			List resultlist = query.list();
			for (int i = 0; i < resultlist.size(); i++) {
				Object[] row = (Object[]) resultlist.get(i);
				club = (Club) row[0];
			}
		} catch (HibernateException e) {
			throw e;
		}
		return club;
	}

}
