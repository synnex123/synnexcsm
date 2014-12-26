package com.synnex.cms.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.synnex.cms.dao.ClubDao;
import com.synnex.cms.dao.PromotionDao;
import com.synnex.cms.dao.PromotionVoteRecordDao;
import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.JudgePromotionDto;
import com.synnex.cms.dto.PromotionDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.Promotion;
import com.synnex.cms.entity.PromotionVoteRecord;
import com.synnex.cms.entity.User;
import com.synnex.cms.service.PromotionService;
import com.synnex.cms.utils.DateUtils;

/**
 * 
 * @author joeyy
 * 
 */

public class PromotionServiceImpl implements PromotionService {
	private PromotionDao promotionDao;
	private PromotionVoteRecordDao promotionVoteRecordDao;
	private ClubDao clubDao;
	private UserDao userDao;
	public void setPromotionDao(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
	}
	public void setPromotionVoteRecordDao(
			PromotionVoteRecordDao promotionVoteRecordDao) {
		this.promotionVoteRecordDao = promotionVoteRecordDao;
	}

	public void setClubDao(ClubDao clubDao) {
		this.clubDao = clubDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	Session session = null;

	/*
	 * function producepromotion 2014/12/11
	 * 
	 * @return true if save is succeed,else return false
	 * 
	 * @param Entity promotion
	 */
	public boolean producePromotion(Promotion promotion) throws HibernateException {
		if (promotionDao.checkExist(promotion) != null) {
			return false;
		} else {
			return promotionDao.savepromotion(promotion);
		}
	}

	/*
	 * function GetOnGoingPromotionById and update promotion 2014/12/11
	 * 
	 * @return promotion infomation
	 * 
	 * @param clubId of loginuser
	 */
	public List<PromotionDto> getOnGoingPromotionByClubId(Integer clubId)
			throws HibernateException {
		List<PromotionDto> resultlist = promotionDao
				.getOnGoingPromotionByClubId(clubId);
		// 取出返回的list中的结束时间和过期时间
		for (int i = 0; i < resultlist.size(); i++) {
			Timestamp nowtime = DateUtils.getSysNow();
			Timestamp endtime = resultlist.get(i).getEndTime();
			Timestamp expiretime = resultlist.get(i).getExpireTime();
			Integer promotionId = resultlist.get(i).getPromotionId();
			// 如果存在endtime或者选举已过期则从list中移除这一条并将状态设置为对应的状态
			if (endtime != null || expiretime.getTime() <= nowtime.getTime()) {
				if (endtime != null) {
					try {
						Integer promotionState = 2;
						promotionDao.updatePromotion(promotionId,
								promotionState);
					} catch (Exception e) {
						throw e;
					}
				}
				if (expiretime.getTime() <= nowtime.getTime()) {
					try {
						Integer promotionState = 4;
						promotionDao.updatePromotion(promotionId,
								promotionState);
					} catch (Exception e) {
						throw e;
					}
				}
				// 如果有移除的元素则将i-1，若不这么做那么此for循环无法将所有元素过一遍这个方法
				if (resultlist.remove(i) != null) {
					i--;
				}
			}
		}
		return resultlist;
	}

	/*
	 * function GetPromotionUserByPromotionId 2014/12/09
	 * 
	 * @return userlist except club's manager
	 * 
	 * @param promotionId
	 */
	public List<User> getPromotionUserByPromotionId(Integer promotionId)
			throws HibernateException {
		List<User> userlist = null;
			userlist = promotionDao.getPromotionUserByPromotionId(promotionId);
	
		return userlist;

	}

	/*
	 * function SavePromotion 2014/12/09
	 * 
	 * @return true if save is succeed,else return false
	 * 
	 * @param Entity PromotionVoteRecord used to record voteuserId,voteduserId
	 * and promotionId
	 */
	public boolean savePromotionRecord(PromotionVoteRecord pvr)
			throws HibernateException {
		//
		if (promotionVoteRecordDao.savePromotion(pvr)) {
			// 对投票结果进行统计计算
			return true;
		} else {
			return false;
		}
	}

	/*
	 * function judge if promotion is exist 2014/12/08
	 * 
	 * @return true if it is exist,else return false
	 * 
	 * @param Entity PromotionVoteRecord
	 */
	public boolean isExist(PromotionVoteRecord pvr) throws HibernateException {
		List<PromotionVoteRecord> result = new ArrayList<PromotionVoteRecord>();
		result = promotionVoteRecordDao.isExist(pvr);
		if (result.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * function judge promotion's result and update information by this result
	 * 2014/12/12
	 * 
	 * @return result is processed or keep going
	 * 
	 * @param Entity PromotionVoteRecord
	 */
	public String judgePromotion(PromotionVoteRecord pvr) throws HibernateException {
		/*
		 * 每次投票完成后对投票结果进行判断
		 * 
		 * 如果得票数第一的人的票数-得票第二的人的票数>没有投票的人 那么前者则当选负责人 如果只有一个人得票,得票数大于总人数的50%则直接当选
		 * 如果没有满足上诉条件则继续本次投票
		 */
		Integer countclubmember;
		// 对（得票数第一的人的票数）和（对应的人）两项数据进行封装
		JudgePromotionDto judge1 = new JudgePromotionDto();
		// 对（得票数第二的人的票数）和（对应的人）两项数据进行封装
		JudgePromotionDto judge2 = new JudgePromotionDto();
		try {
			// 取出该次选举对应的俱乐部的总人数
			countclubmember = clubDao.countClubMemberByPromotionId(pvr
					.getPromotionId());
			// 取出得票数和对应的得票人用List集合存储
			List<JudgePromotionDto> judgeresult = promotionVoteRecordDao
					.judgeVoteCount(pvr);
			// 如果有两个人以上都有票数
			if (judgeresult.size() >= 2) {
				judge1 = judgeresult.get(0);
				judge2 = judgeresult.get(1);
				// 如果得票数第一的人的票数-得票第二的人的票数>没有投票的人那么前者则当选负责人
				if (judge1.getCountPromotion() - judge2.getCountPromotion() > (countclubmember - (judge1
						.getCountPromotion() + judge2.getCountPromotion()))) {
					try {
						// 将得票第一的人的userType设为0
						userDao.UpUserType(judge1.getVoteduserId());
						Integer oldmanagerId = userDao
								.GetManagerIdByPromotionId(pvr.getPromotionId());
						// 将原俱乐部负责人的userType设为1
						userDao.downUserType(oldmanagerId);
						// 将promotion的endtime设为当前操作时间
						promotionDao.endPromotion(pvr.getPromotionId(),
								DateUtils.getSysNow());
						// 从promotionvoterecord中删除对应promotionId的所有数据
						promotionVoteRecordDao.delete(pvr.getPromotionId());
						Integer clubId = clubDao.getClubIdByPromotionId(pvr
								.getPromotionId());
						Club club = new Club();
						club.setClubId(clubId);
						club.setManagerId(judge1.getVoteduserId());
						// 将clubinfo表中的俱乐部负责人修改成新负责人
						clubDao.updateClubInfoChangeManager(club);
					} catch (Exception e) {
						throw e;
					}
					return "sloved";
				}
				// 如果没有满足上诉条件则继续本次投票
				else {
					return "keep";
				}
			}// 如果只有一个人得票
			else if (judgeresult.size() == 1) {
				judge1 = judgeresult.get(0);
				judge2 = null;
				// 如果得票数大于总人数的50%则直接当选
				if (judge1.getCountPromotion() > countclubmember * 0.5) {
					try {
						// 将得票第一的人的userType设为0
						userDao.UpUserType(judge1.getVoteduserId());
						Integer oldmanagerId = userDao
								.GetManagerIdByPromotionId(pvr.getPromotionId());
						// 将原俱乐部负责人的userType设为1
						userDao.downUserType(oldmanagerId);
						// 将promotion的endtime设为当前操作时间
						promotionDao.endPromotion(pvr.getPromotionId(),
								DateUtils.getSysNow());
						// 从promotionvoterecord中删除对应promotionId的所有数据
						promotionVoteRecordDao.delete(pvr.getPromotionId());
						Integer clubId = clubDao.getClubIdByPromotionId(pvr
								.getPromotionId());
						Club club = new Club();
						club.setClubId(clubId);
						club.setManagerId(judge1.getVoteduserId());
						// 将clubinfo表中的俱乐部负责人修改成新负责人
						clubDao.updateClubInfoChangeManager(club);
					} catch (Exception e) {
						throw e;
					}
					return "sloved";
				}
				// 如果没有满足上诉条件则继续本次投票
				else {
					return "keep";
				}
			} else {
				return "keep";
			}
		} catch (HibernateException e) {
			throw e;
		}

	}

}
