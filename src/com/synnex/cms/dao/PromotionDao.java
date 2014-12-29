package com.synnex.cms.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.PromotionDto;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.User;

/**
 * 
 * @author joeyy
 * 
 */

public interface PromotionDao extends BaseDao {
	/**
	 * 2014/12/11 function SavePromotion
	 * 
	 * @return true if save succeed,else return false
	 * 
	 * @param Entity Promotion
	 */

	public boolean savepromotion(Promotion promotion) throws HibernateException;

	/**
	 * function GetOnGoingPromotionByClubId 2014/12/12
	 * 
	 * @return list of onGoingPromotion
	 * 
	 * @param clubId of CurrentUser
	 */
	public List<PromotionDto> getOnGoingPromotionByClubId(Integer clubId) throws HibernateException;

	/**
	 * function GetOnGoingPromotionByClubId 2014/12/12
	 * 
	 * @return list of User except ClubManager
	 * 
	 * @param promotionId
	 */
	public List<User> getPromotionUserByPromotionId(Integer promotionId) throws HibernateException;

	/**
	 * function EndPromotion update PromotionInfoByromotionId 2014/12/12
	 * 
	 * @return true if update succeed ,else return false
	 * 
	 * @param promotionId,endtime
	 */
	public boolean endPromotion(Integer promotionId,Timestamp endtime) throws HibernateException;

	/**
	 * function update PromotionInfo if promotion is end or failed 2014/12/12
	 */
	public void updatePromotion(Integer promotionId,Integer promotionState) throws HibernateException;

	/**
	 * function Check one club only can have one promotion 2014/12/15
	 */
	public Promotion checkExist(Promotion promotion) throws HibernateException;
}
