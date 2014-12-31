package com.synnex.cms.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;

/**
 * 
 * @author joeyy 2014/11/26
 */
/**
 * @Author Joeyy 2014/11/26 function getApplyByUserId for myapply.jsp
 */
public interface ApplyService {

	public List<ApplyDto> getApplyByUserId(Integer userId, Integer pageIndex,
			Integer applyStatus) throws HibernateException;

	/**
	 * @author WalkerCheng function saveApply 2014/11/28
	 */
	public boolean saveApply(Apply apply) throws HibernateException;

	/**
	 * @Author Joeyy function getApplyDetails 2014/12/01
	 */
	public List<ApplyDto> getApplyDetails(Integer applyId) throws HibernateException;

	/**
	 * @Author Joeyy function CancelApply 2014/12/01
	 * 
	 * @return true if canceled succeed,else return false
	 * 
	 * @param applyId
	 */
	public boolean cancelApply(Integer applyId) throws HibernateException;

	/**
	 * @Author Joeyy function getApplyByManagerId for checkapply 2014/12/01
	 */
	public List<ApplyDto> getApplyByManagerId(Integer managerId,
			Integer pageIndex, Integer applyStatus) throws HibernateException;

	/**
	 * function PassApply modified by joeyy 2014/12/18 2014/12/03
	 * 
	 * @author Joeyy 
	 * 
	 * @params applyId,checkTime,userId,clubId
	 */
	public boolean passApply(Integer applyId, Timestamp checkTime, UserClub uc)
			throws HibernateException;

	/**
	 * @Author Joeyy function rejectApply 2014/12/03
	 * 
	 * @return true if reject succeed,else return false
	 * 
	 * @params applyId,checkResponse,checkTime
	 */
	public boolean rejectApply(Integer applyId, String checkRes,
			Timestamp checkTime) throws HibernateException;

	/**
	 * @author WalkerCheng function search whether have applied 
	 * 2014/11/28
	 * @return Boolean 
	 * @param userId applyStatus clubId 
	 * @throws Exception
	 */

	public boolean searchWhetherApply(Integer userId, Integer applyStatus,
			Integer clubId) throws HibernateException;

	/**
	 * @author joeyy 2014/12/22 function getApplyByClubId
	 * 
	 * @return List<Apply>
	 * 
	 * @param clubId
	 */
	public List<Apply> getApplyByClubId(Integer clubId) throws HibernateException;

	/**
	 * @author joeyy 2014/12/22 function getSubmittedApplyByClubId
	 * 
	 * @return List<Apply> which is submitted
	 * 
	 * @param clubId
	 */
	public List<Apply> getSubmittedApplyByClubId(Integer clubId)
			throws HibernateException;

}
