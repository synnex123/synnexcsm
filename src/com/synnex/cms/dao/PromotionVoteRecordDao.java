package com.synnex.cms.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.JudgePromotionDto;
import com.synnex.cms.entity.PromotionVoteRecord;

public interface PromotionVoteRecordDao extends BaseDao {
	/**
	 * @author joeyy 2014/12/18 function savepromtionvoterecord
	 */
	public boolean savePromotion(PromotionVoteRecord pvr) throws HibernateException;

	/**
	 * @author joeyy 2014/12/18 function judege if someone have voted
	 * 
	 * @return List<PromotionVoteRecord>
	 * 
	 * @param Entity pvr
	 */
	public List<PromotionVoteRecord> isExist(PromotionVoteRecord pvr)
			throws HibernateException;

	/**
	 * @author joeyy 2014/12/18 function JudgeVoteCount for one person
	 * 
	 * @return List<JudgePromotionDto> contains voted person and vote
	 * 
	 * @param Entity PromotionVoteRecord
	 */
	public List<JudgePromotionDto> judgeVoteCount(PromotionVoteRecord pvr)
			throws HibernateException;

	/**
	 * @author joeyy 2014/12/18 function delete promotionvoterecord when
	 * promotion is end
	 * 
	 * @param promotionId
	 * 
	 * @return true if delete succeed ,else return false
	 */
	public boolean delete(Integer promotionId) throws HibernateException;
	/**function getPromotionVoteRecordByVotedUser 2014/01/06
	 * @author joeyy
	 * 
	 * @param userId
	 * @return Entity PromotionVoteRecord
	 */

	public List<PromotionVoteRecord> getPromotionVoteRecordByVotedUser(Integer userId) throws HibernateException;
/** function  deleteByVoteUserId 2015/01/06
 * @author joeyy
 * 
 * @param userId
 */
	public void deleteByVoteUserId(Integer userId) throws HibernateException;

}
