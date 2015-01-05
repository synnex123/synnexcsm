package com.synnex.cms.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synnex.cms.action.InitApplyAction;
import com.synnex.cms.dao.ApplyDao;
import com.synnex.cms.dto.ApplyDto;
import com.synnex.cms.entity.Apply;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.ApplyService;

/**
 * 
 * @author joeyy 2014/11/26
 */
/**
 * @Author Joeyy 2014/11/26 function getApplyByUserId for myapply.jsp
 */
public class ApplyServiceImpl implements ApplyService {
	private ApplyDao applyDao;
	private static Logger LOGGER = LoggerFactory.getLogger(ApplyServiceImpl.class);
	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	public List<ApplyDto> getApplyByUserId(Integer userId, Integer pageIndex,
			Integer applyStatus){
		List<ApplyDto> applyList=null;
		try{
			applyList=applyDao.getApplyByUserId(userId, pageIndex, applyStatus);
			
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return applyList;
	}

	/**
	 * @author WalkerCheng function saveApply 2014/11/28
	 */
	public boolean saveApply(Apply apply){
		boolean result=false;
		try{
			result=applyDao.saveApply(apply);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @Author Joeyy function getApplyDetails 2014/12/01
	 */
	public List<ApplyDto> getApplyDetails(Integer applyId){
		List<ApplyDto> result=null;
		try{
			result=applyDao.getApplyDetails(applyId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @Author Joeyy function CancelApply 2014/12/01
	 * 
	 * @return true if canceled succeed,else return false
	 * 
	 * @param applyId
	 */
	public boolean cancelApply(Integer applyId){
		boolean result=false;
		try{
			result=applyDao.cancelApply(applyId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @Author Joeyy function getApplyByManagerId for checkapply 2014/12/01
	 */
	public List<ApplyDto> getApplyByManagerId(Integer managerId,
			Integer pageIndex, Integer applyStatus){
		List<ApplyDto> result=null;
		try{
			result=applyDao.getApplyByManagerId(managerId, pageIndex, applyStatus);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author Joeyy function PassApply modified by joeyy 2014/12/18 2014/12/03
	 * 
	 * @params applyId,checkTime,userId,clubId
	 */
	public boolean passApply(Integer applyId, Timestamp checkTime, UserClub uc){
		try{
			applyDao.passApplyInserUser(uc);
			applyDao.passApply(applyId, checkTime);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return true;
	}

	/**
	 * @Author Joeyy function rejectApply 2014/12/03
	 * 
	 * @return true if reject succeed,else return false
	 * 
	 * @params applyId,checkResponse,checkTime
	 */
	public boolean rejectApply(Integer applyId, String checkRes,
			Timestamp checkTime){
		boolean result=false;
		try{
			result=applyDao.rejectApply(applyId, checkRes, checkTime);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author WalkerCheng function search whether have applied 2014/11/28
	 */

	public boolean searchWhetherApply(Integer userId, Integer applyStatus,
			Integer clubId){
		boolean result=false;
		try{
			result=applyDao.searchWhetherApply(userId, applyStatus, clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/12/22 function getApplyByClubId
	 * 
	 * @return List<Apply>
	 * 
	 * @param clubId
	 */
	public List<Apply> getApplyByClubId(Integer clubId){
		List<Apply> result=null;
		try{
			result=applyDao.getApplyByClubId(clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @author joeyy 2014/12/22 function getSubmittedApplyByClubId
	 * 
	 * @return List<Apply> which is submitted
	 * 
	 * @param clubId
	 */
	public List<Apply> getSubmittedApplyByClubId(Integer clubId){
		List<Apply> result=null;
		try{
			result=applyDao.getSubmittedApplyByClubId(clubId);
		}catch (HibernateException e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		catch (Exception e) {
			LOGGER.warn("exception at"+this.getClass().getName(), e);
		}
		return result;
	}

}
