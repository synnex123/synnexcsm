package com.synnex.cms.daoimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.synnex.cms.dao.PromotionDao;
import com.synnex.cms.dto.PromotionDto;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.User;

/**
 * 
 * @author joeyy
 * 
 */

public class PromotionDaoImpl extends BaseDaoImpl implements PromotionDao {
	Session session = null;

	/**
	 * 2014/12/11 function SavePromotion
	 * 
	 * @return true if save succeed,else return false
	 * 
	 * @param Entity
	 *            Promotion
	 */

	public boolean savepromotion(Promotion promotion) throws HibernateException {
		session = getSession();
		session.save(promotion);
		return true;

	}

	/**
	 * function GetOnGoingPromotionByClubId 2014/12/12
	 * 
	 * @return list of onGoingPromotion
	 * 
	 * @param clubId
	 *            of CurrentUser
	 */
	public List<PromotionDto> getOnGoingPromotionByClubId(Integer clubId)
			throws HibernateException {
		String hql = "";
		List<PromotionDto> resultlist = new ArrayList<PromotionDto>();
		session = getSession();
		hql = "select c.clubName,u.userName,p.promotionReason,p.startTime,p.expireTime,p.promotionId,p.endTime,p.promotionName "
				+ "from User u,Club c,Promotion p "
				+ "where p.clubId=? "
				+ "and p.clubId=c.clubId and p.recommenduserId=u.userId "
				+ "order by p.startTime desc";
		Query query = session.createQuery(hql);
		query.setInteger(0, clubId);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				PromotionDto promotiondto = new PromotionDto();
				promotiondto.setClubName((String) row[0]);
				promotiondto.setRecommenduserName((String) row[1]);
				promotiondto.setPromotionReason((String) row[2]);
				promotiondto.setExpireTime((Timestamp) row[4]);
				promotiondto.setStartTime((Timestamp) row[3]);
				promotiondto.setPromotionId((Integer) row[5]);
				promotiondto.setEndTime((Timestamp) row[6]);
				promotiondto.setPromotionName((String) row[7]);
				resultlist.add(promotiondto);
			}
		} else {
			return null;
		}
		return resultlist;
	}

	/**
	 * function GetOnGoingPromotionByClubId 2014/12/12
	 * 
	 * @return list of User except ClubManager
	 * 
	 * @param promotionId
	 */
	public List<User> getPromotionUserByPromotionId(Integer promotionId)
			throws HibernateException {
		List<User> userlist = new ArrayList<User>();
		String hql = "";
		Integer managerId = null;
		Integer clubId = null;
		session = getSession();
		hql = "select u.userId from User u,Club c,Promotion p where p.promotionId=? and p.clubId=c.clubId and c.managerId=u.userId";
		Query query = session.createQuery(hql);
		query.setInteger(0, promotionId);
		managerId = (Integer) query.uniqueResult();
		hql = "select c.clubId from Club c,Promotion p where c.clubId =p.clubId and p.promotionId=:promotionId";
		Query query3 = session.createQuery(hql);
		query3.setInteger("promotionId", promotionId);
		clubId = (Integer) query3.uniqueResult();
		hql = "from User u,UserClub uc where uc.userId = u.userId and u.userId <> :userId and uc.clubId = :clubId and u.userType =1";
		Query query2 = session.createQuery(hql);
		query2.setInteger("userId", managerId);
		query2.setInteger("clubId", clubId);
		@SuppressWarnings("rawtypes")
		List userlist1 = query2.list();
		for (int i = 0; i < userlist1.size(); i++) {
			Object[] row = (Object[]) userlist1.get(i);
			userlist.add((User) row[0]);
		}
		return userlist;

	}

	/**
	 * function EndPromotion update PromotionInfoByromotionId 2014/12/12
	 * 
	 * @return true if update succeed ,else return false
	 * 
	 * @param promotionId
	 *            ,endtime
	 */
	public boolean endPromotion(Integer promotionId, Timestamp endtime)
			throws HibernateException {
		String hql = "";
		session = getSession();
		hql = "update Promotion p set p.endTime=:endtime,p.promotionState=2 where p.promotionId=:promotionId";
		Query query = session.createQuery(hql);
		query.setTimestamp("endtime", endtime);
		query.setInteger("promotionId", promotionId);
		query.executeUpdate();
		return true;
	}

	/**
	 * function update PromotionInfo if promotion is end or failed 2014/12/12
	 */
	public void updatePromotion(Integer promotionId, Integer promotionState)
			throws HibernateException {
		String hql = "";
		session = getSession();
		hql = "update Promotion p set p.promotionState=:promotionState where p.promotionId=:promotionId";
		Query query = session.createQuery(hql);
		query.setInteger("promotionState", promotionState);
		query.setInteger("promotionId", promotionId);
		query.executeUpdate();

	}

	/**
	 * function Check one club only can have one promotion 2014/12/15
	 */
	public Promotion checkExist(Promotion promotion) throws HibernateException {
		String hql = "";
		Promotion p = new Promotion();
		session = getSession();
		hql = "from Promotion p where p.clubId=:clubId and p.endTime is null and p.expireTime < CURRENT_TIMESTAMP";
		Query query = session.createQuery(hql);
		query.setInteger("clubId", promotion.getClubId());
		p = (Promotion) query.uniqueResult();
		return p;
	}
}
