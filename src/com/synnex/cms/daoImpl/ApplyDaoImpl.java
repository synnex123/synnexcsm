package com.synnex.cms.daoImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.synnex.cms.dao.ApplyDao;
import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;

public class ApplyDaoImpl extends BaseDaoImpl implements ApplyDao {
	Session session = null;

	/**
	 * 
	 * @Author Joeyy 2014/11/28 function getApplyByUserId for myapply.jsp
	 */
	public List<ApplyDto> getApplyByUserId(Integer userId, Integer pageIndex,
			Integer applyStatus) throws HibernateException {
		List<ApplyDto> applyList = new ArrayList<ApplyDto>();
		String hql = "";
		try {
			session = getSession();
			hql = "select a.applyId,c.clubName,u.userPhone,a.applyTime,a.applyStatus,a.checkTime "
					+ "from Club c,User u,Apply a "
					+ "where a.requesterId=? and a.clubId=c.clubId "
					+ "and c.managerId=u.userId and a.applyStatus between ? and ? order by a.applyTime desc";
			Query query = session.createQuery(hql).setMaxResults(10)
					.setFirstResult(pageIndex);
			query.setInteger(0, userId);
			if (applyStatus == -1) {
				query.setInteger(1, 0);
				query.setInteger(2, 10);
			} else {
				query.setInteger(1, applyStatus);
				query.setInteger(2, applyStatus);
			}
			@SuppressWarnings("rawtypes")
			List result = query.list();
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				ApplyDto applyDto = new ApplyDto();
				applyDto.setApplyId((Integer) row[0]);
				applyDto.setClubName((String) row[1]);
				applyDto.setManagerPhone((String) row[2]);
				applyDto.setApplyTime((Timestamp) row[3]);
				applyDto.setApplyStatus((Integer) row[4]);
				applyDto.setCheckTime((Timestamp) row[5]);
				applyList.add(applyDto);
			}
		} catch (HibernateException e) {
			throw e;
		}
		return applyList;

	}

	/**
	 * @Author Walker Cheng function save apply 2014/11/28
	 */
	public boolean saveApply(Apply apply) throws HibernateException {
		try {
			session = getSession();
			session.save(apply);

		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * 
	 * @Author Joeyy 2014/11/28 function getApplyDetails
	 */
	public List<ApplyDto> getApplyDetails(Integer applyId) throws HibernateException {
		List<ApplyDto> applyList = new ArrayList<ApplyDto>();
		String hql = "";
		try {
			session = getSession();
			hql = "select u.userName,u.userPhone,u.userPart,u.userEmail,a.applyDes,c.clubName,a.applyStatus,a.applyTime,a.checkRes,a.checkTime,a.requesterId from User u,Apply a,Club c where a.applyId=? and a.requesterId=u.userId and a.clubId=c.clubId";
			Query query = session.createQuery(hql);
			query.setInteger(0, applyId);
			@SuppressWarnings("rawtypes")
			List result = query.list();
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				ApplyDto applyDto = new ApplyDto();
				applyDto.setUserName((String) row[0]);
				applyDto.setUserPhone((String) row[1]);
				applyDto.setUserPart((String) row[2]);
				applyDto.setUserEmail((String) row[3]);
				applyDto.setApplyDes((String) row[4]);
				applyDto.setClubName((String) row[5]);
				applyDto.setApplyStatus((Integer) row[6]);
				applyDto.setApplyTime((Timestamp) row[7]);
				applyDto.setCheckRes((String) row[8]);
				applyDto.setCheckTime((Timestamp) row[9]);
				applyDto.setRequesterId((Integer) row[10]);
				applyDto.setApplyId(applyId);
				applyList.add(applyDto);
			}

		} catch (HibernateException e) {
			throw e;

		}
		return applyList;
	}

	/**
	 * 
	 * @Author Joeyy 2014/12/01 function CancelApply
	 */
	public boolean cancelApply(Integer applyId) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update Apply a set a.applyStatus = 3 where a.applyId=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, applyId);
			query.executeUpdate();
		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @Author Joeyy 2014/12/03 function getApplyByManagerId for checkapply.jsp
	 */
	public List<ApplyDto> getApplyByManagerId(Integer managerId,
			Integer pageIndex, Integer applyStatus) throws HibernateException {
		List<ApplyDto> applyList = new ArrayList<ApplyDto>();
		String hql = "";
		try {
			session = getSession();
			hql = "select u.userName,c.clubName,a.applyTime,a.applyStatus,u.userPhone,u.userEmail,a.checkTime,a.applyId,u.userId,c.clubId "
					+ "from Club c,User u,Apply a"
					+ " where c.managerId=? and c.clubId=a.clubId and a.requesterId=u.userId "
					+ "and a.applyStatus <> 3 and "
					+ "a.applyStatus between ? and ? order by a.applyTime desc";
			Query query = session.createQuery(hql).setMaxResults(10)
					.setFirstResult(pageIndex);
			query.setInteger(0, managerId);
			if (applyStatus == -1) {
				query.setInteger(1, 0);
				query.setInteger(2, 10);
			} else {
				query.setInteger(1, applyStatus);
				query.setInteger(2, applyStatus);
			}
			@SuppressWarnings("rawtypes")
			List result = query.list();
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				ApplyDto applyDto = new ApplyDto();
				applyDto.setUserName((String) row[0]);
				applyDto.setClubName((String) row[1]);
				applyDto.setApplyTime((Timestamp) row[2]);
				applyDto.setApplyStatus((Integer) row[3]);
				applyDto.setUserPhone((String) row[4]);
				applyDto.setUserEmail((String) row[5]);
				applyDto.setCheckTime((Timestamp) row[6]);
				applyDto.setApplyId((Integer) row[7]);
				applyDto.setUserId((Integer) row[8]);
				applyDto.setClubId((Integer) row[9]);
				applyList.add(applyDto);
			}

		} catch (HibernateException e) {
			throw e;
		}
		return applyList;
	}

	/**
	 * @author joeyy 2014/12/22 function processApply update Apply
	 * 
	 * @param applyId,checkTime
	 */
	public boolean passApply(Integer applyId, Timestamp checkTime)
			throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update Apply a set a.applyStatus = 1,a.checkTime = ? where a.applyId=?";
			Query query = session.createQuery(hql);
			query.setTimestamp(0, checkTime);
			query.setInteger(1, applyId);
			query.executeUpdate();

		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @Author Joeyy 2014/12/03 function RejectApplyBy applyId
	 */
	public boolean rejectApply(Integer applyId, String checkRes,
			Timestamp checkTime) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "update Apply a set a.checkRes=?,a.applyStatus = 2,a.checkTime = ? where a.applyId=?";
			Query query = session.createQuery(hql);
			query.setString(0, checkRes);
			query.setTimestamp(1, checkTime);
			query.setInteger(2, applyId);
			query.executeUpdate();

		} catch (HibernateException e) {
			throw e;
		}
		return true;
	}

	/**
	 * @author WalkerCheng function search whether have applied 2014/11/28
	 */
	public Boolean searchWhetherApply(Integer userId, Integer applyStatus,
			Integer clubId) throws HibernateException {
		String hql = "";
		try {
			session = getSession();
			hql = "select a.applyId from Apply a where a.requesterId=? and a.applyStatus=? and a.clubId=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, userId);
			query.setInteger(1, applyStatus);
			query.setInteger(2, clubId);
			@SuppressWarnings("rawtypes")
			List result = query.list();
			if (result.size() == 0) {
				return false;
			}
		} catch (HibernateException e) {
			throw e;

		}
		return true;

	}

	/**
	 * @author joeyy 2014/12/20 function PassApplyInserUserClub
	 * 
	 * @param Entity UserClub
	 */
	public void passApplyInserUser(UserClub uc) throws HibernateException {
		try {
			session = getSession();
			session.save(uc);
		} catch (HibernateException e) {
			throw e;
		}

	}

	/**
	 * @author joeyy 2014/12/22 function getApplyByClubId
	 * 
	 * @return List<Apply>
	 * 
	 * @param clubId
	 */
	@SuppressWarnings("unchecked")
	public List<Apply> getApplyByClubId(Integer clubId) throws HibernateException {
		String hql = "";
		List<Apply> applylist = new ArrayList<>();
		try {
			session = getSession();
			hql = "from Apply a where a.clubId=:clubId";
			Query query = session.createQuery(hql);
			applylist = query.list();
		} catch (HibernateException e) {
			throw e;
		}
		return applylist;
	}

	/**
	 * @author joeyy 2014/12/22 function getSubmittedApplyByClubId
	 * 
	 * @return List<Apply> which is submitted
	 * 
	 * @param clubId
	 */
	@SuppressWarnings("unchecked")
	public List<Apply> getSubmittedApplyByClubId(Integer clubId) throws HibernateException{
		String hql = "";
		List<Apply> applylist = new ArrayList<>();
		try {
			session = getSession();
			hql = "from Apply a where a.clubId=:clubId and a.applyStatus= 0";
			Query query = session.createQuery(hql);
			query.setInteger("clubId", clubId);
			applylist = query.list();
		} catch (HibernateException e) {
			throw e;
		}
		return applylist;

	}
}
