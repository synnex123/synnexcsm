package com.synnex.cms.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.PromotionDto;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.PromotionVoteRecord;
import com.synnex.cms.entity.User;

/**
 * 
 * @author joeyy
 * 
 */

public interface PromotionService {
	/*
	 * function producepromotion 2014/12/11
	 * 
	 * @return true if save is succeed,else return false
	 * 
	 * @param Entity promotion
	 */
	public boolean producePromotion(Promotion promotion) throws HibernateException;

	/*
	 * function GetOnGoingPromotionById and update promotion 2014/12/11
	 * 
	 * @return promotion infomation
	 * 
	 * @param clubId of loginuser
	 */
	public List<PromotionDto> getOnGoingPromotionByClubId(Integer clubId)
			throws HibernateException;

	/*
	 * function GetPromotionUserByPromotionId 2014/12/09
	 * 
	 * @return userlist except club's manager
	 * 
	 * @param promotionId
	 */
	public List<User> getPromotionUserByPromotionId(Integer promotionId)
			throws HibernateException;

	/*
	 * function SavePromotion 2014/12/09
	 * 
	 * @return true if save is succeed,else return false
	 * 
	 * @param Entity PromotionVoteRecord used to record voteuserId,voteduserId
	 * and promotionId
	 */
	public boolean savePromotionRecord(PromotionVoteRecord pvr)
			throws HibernateException;

	/*
	 * function judge if promotion is exist 2014/12/08
	 * 
	 * @return true if it is exist,else return false
	 * 
	 * @param Entity PromotionVoteRecord
	 */
	public boolean isExist(PromotionVoteRecord pvr) throws HibernateException;

	/*
	 * function judge promotion's result and update information by this result
	 * 2014/12/12
	 * 
	 * @return result is processed or keep going
	 * 
	 * @param Entity PromotionVoteRecord
	 */
	public String judgePromotion(PromotionVoteRecord pvr) throws HibernateException;

}
