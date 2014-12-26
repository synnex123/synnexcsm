package com.synnex.cms.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dao.ApplyDao;
import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.ApplyService;

/**
 * 
 * @author joeyy 2014/11/26
 */
/*
 * @Author Joeyy 2014/11/26 function getApplyByUserId for myapply.jsp
 */
public class ApplyServiceImpl implements ApplyService {
	private ApplyDao applyDao;

	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	public List<ApplyDto> getApplyByUserId(Integer userId, Integer pageIndex,
			Integer applyStatus) throws HibernateException {
		return applyDao.getApplyByUserId(userId, pageIndex, applyStatus);
	}

	/*
	 * @author WalkerCheng function saveApply 2014/11/28
	 */
	public boolean saveApply(Apply apply) throws HibernateException {
		return applyDao.saveApply(apply);
	}

	/*
	 * @Author Joeyy function getApplyDetails 2014/12/01
	 */
	public List<ApplyDto> getApplyDetails(Integer applyId) throws HibernateException {
		return applyDao.getApplyDetails(applyId);
	}

	/*
	 * @Author Joeyy function CancelApply 2014/12/01
	 * 
	 * @return true if canceled succeed,else return false
	 * 
	 * @param applyId
	 */
	public boolean cancelApply(Integer applyId) throws HibernateException {
		return applyDao.cancelApply(applyId);

	}

	/*
	 * @Author Joeyy function getApplyByManagerId for checkapply 2014/12/01
	 */
	public List<ApplyDto> getApplyByManagerId(Integer managerId,
			Integer pageIndex, Integer applyStatus) throws HibernateException {
		return applyDao.getApplyByManagerId(managerId, pageIndex, applyStatus);

	}

	/*
	 * @author Joeyy function PassApply modified by joeyy 2014/12/18 2014/12/03
	 * 
	 * @params applyId,checkTime,userId,clubId
	 */
	public boolean passApply(Integer applyId, Timestamp checkTime, UserClub uc)
			throws HibernateException {
		applyDao.passApplyInserUser(uc);
		applyDao.passApply(applyId, checkTime);
		return true;

	}

	/*
	 * @Author Joeyy function rejectApply 2014/12/03
	 * 
	 * @return true if reject succeed,else return false
	 * 
	 * @params applyId,checkResponse,checkTime
	 */
	public boolean rejectApply(Integer applyId, String checkRes,
			Timestamp checkTime) throws HibernateException {
		return applyDao.rejectApply(applyId, checkRes, checkTime);
	}

	/*
	 * @author WalkerCheng function search whether have applied 2014/11/28
	 */

	public boolean searchWhetherApply(Integer userId, Integer applyStatus,
			Integer clubId) throws HibernateException {
		return applyDao.searchWhetherApply(userId, applyStatus, clubId);
	}

	/*
	 * @author joeyy 2014/12/22 function getApplyByClubId
	 * 
	 * @return List<Apply>
	 * 
	 * @param clubId
	 */
	public List<Apply> getApplyByClubId(Integer clubId) throws HibernateException {
		return applyDao.getApplyByClubId(clubId);

	}

	/*
	 * @author joeyy 2014/12/22 function getSubmittedApplyByClubId
	 * 
	 * @return List<Apply> which is submitted
	 * 
	 * @param clubId
	 */
	public List<Apply> getSubmittedApplyByClubId(Integer clubId)
			throws HibernateException {
		return applyDao.getSubmittedApplyByClubId(clubId);

	}

}
