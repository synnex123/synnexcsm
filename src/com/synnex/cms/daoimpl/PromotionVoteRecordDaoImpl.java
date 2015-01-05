package com.synnex.cms.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.synnex.cms.dao.PromotionVoteRecordDao;
import com.synnex.cms.dto.JudgePromotionDto;
import com.synnex.cms.entity.PromotionVoteRecord;

public class PromotionVoteRecordDaoImpl extends BaseDaoImpl implements
		PromotionVoteRecordDao {
	Session session = null;

	/**
	 * @author joeyy 2014/12/18 function savepromtionvoterecord
	 */
	public boolean savePromotion(PromotionVoteRecord pvr)
			throws HibernateException {
		session = getSession();
		session.save(pvr);
		return true;
	}

	/**
	 * @author joeyy 2014/12/18 function judege if someone have voted
	 * 
	 * @return List<PromotionVoteRecord>
	 * 
	 * @param Entity
	 *            pvr
	 */
	@SuppressWarnings("unchecked")
	public List<PromotionVoteRecord> isExist(PromotionVoteRecord pvr)
			throws HibernateException {
		String hql = "";
		List<PromotionVoteRecord> result = new ArrayList<PromotionVoteRecord>();
		session = getSession();
		hql = "from PromotionVoteRecord p where p.promotionId=:promotionId and p.voteuserId=:voteId";
		Query query = session.createQuery(hql);
		query.setInteger("promotionId", pvr.getPromotionId());
		query.setInteger("voteId", pvr.getVoteuserId());
		result = query.list();
		return result;
	}

	/**
	 * @author joeyy 2014/12/18 function JudgeVoteCount for one person
	 * 
	 * @return List<JudgePromotionDto> contains voted person and vote
	 * 
	 * @param Entity
	 *            PromotionVoteRecord
	 */
	public List<JudgePromotionDto> judgeVoteCount(PromotionVoteRecord pvr)
			throws HibernateException {
		String hql = "";
		@SuppressWarnings("rawtypes")
		List result = null;
		List<JudgePromotionDto> judgeresult = new ArrayList<JudgePromotionDto>();
		session = getSession();
		hql = "select count(*) as countvote ,voteduserId from PromotionVoteRecord where promotionId=:promotionId group by voteduserId order by countvote DESC";
		Query query = session.createQuery(hql);
		query.setInteger("promotionId", pvr.getPromotionId());
		result = query.list();
		for (int i = 0; i < result.size(); i++) {
			Object[] row = (Object[]) result.get(i);
			JudgePromotionDto jpDto = new JudgePromotionDto();
			jpDto.setCountPromotion(((java.lang.Long) row[0]).intValue());
			jpDto.setVoteduserId((Integer) row[1]);
			judgeresult.add(jpDto);
		}
		return judgeresult;
	}

	/**
	 * @author joeyy 2014/12/18 function delete promotionvoterecord when
	 *         promotion is end
	 * 
	 * @param promotionId
	 * 
	 * @return true if delete succeed ,else return false
	 */
	public boolean delete(Integer promotionId) throws HibernateException {
		String hql = "";
		session = getSession();
		hql = "delete from PromotionVoteRecord pvr where pvr.promotionId=:promotionId";
		Query query = session.createQuery(hql);
		query.setInteger("promotionId", promotionId);
		Integer status = query.executeUpdate();
		if (status == 1) {
			return true;
		} else {
			return false;
		}
	}
}
