package com.synnex.cms.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;

public interface ApplyDao extends BaseDao {

	/**
	 * 
	 * @Author Joeyy 2014/11/28 function getApplyByUserId for myapply.jsp
	 */
	public List<ApplyDto> getApplyByUserId(Integer userId, Integer pageIndex,
			Integer applyStatus) throws HibernateException;

	/**
	 * @Author Walker Cheng function save apply 2014/11/28
	 */
	public boolean saveApply(Apply apply) throws HibernateException;

	/**
	 * 
	 * @Author Joeyy 2014/11/28 function getApplyDetails
	 */
	public List<ApplyDto> getApplyDetails(Integer applyId) throws HibernateException;

	/**
	 * 
	 * @Author Joeyy 2014/12/01 function CancelApply
	 */
	public boolean cancelApply(Integer applyId) throws HibernateException;

	/**
	 * @Author Joeyy 2014/12/03 function getApplyByManagerId for checkapply.jsp
	 */
	public List<ApplyDto> getApplyByManagerId(Integer managerId,
			Integer pageIndex, Integer applyStatus) throws HibernateException;

	/**
	 * @author joeyy 2014/12/22 function processApply update Apply
	 * 
	 * @param applyId,checkTime
	 */
	public boolean passApply(Integer applyId, Timestamp checkTime)
			throws HibernateException;

	/**
	 * @Author Joeyy 2014/12/03 function RejectApplyBy applyId
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
	public Boolean searchWhetherApply(Integer userId, Integer applyStatus,
			Integer clubId) throws HibernateException;

	/**
	 * @author joeyy 2014/12/20 function PassApplyInserUserClub
	 * 
	 * @param Entity UserClub
	 */
	public void passApplyInserUser(UserClub uc) throws HibernateException;

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
	public List<Apply> getSubmittedApplyByClubId(Integer clubId) throws HibernateException;
	/**function deleteApplyByUserId 2014/01/06
	 * @author joeyy
	 * @param userId
	 */

	public void deleteApplyByUserId(Integer userId) throws HibernateException;
	
	/**function deleteApplyByClubId 
	 * 2014/12/12
	 * @author walker cheng
	 * @param clubId
	 */
	public void deleteApplyByClubId(Integer clubId) throws HibernateException;
}
