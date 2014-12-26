package com.synnex.cms.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.UserClub;

/**
 * @author petep
 */
/*
 * @author petep function getClubByLocation 2014/11/25
 */
public interface ClubDao extends BaseDao {
	/*
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getClubByLocation(String location) throws HibernateException;

	/*
	 * @Author Walker Cheng function getclub 2014/11/28
	 */
	public Club getClubByClubId(Integer clubId) throws HibernateException;

	/*
	 * @author joeyy 2014/12/12 function GetClubIdByPromotionId
	 * 
	 * @return clubID
	 * 
	 * @param promotionId
	 */
	public Integer getClubIdByPromotionId(Integer promotionId) throws HibernateException;

	/*
	 * @author joeyy 2014/12/12 function UpdateClubInfo for change manager
	 * 
	 * @return true if update succeed ,else return false
	 * 
	 * @param Entity club
	 */
	public boolean updateClubInfoChangeManager(Club club) throws HibernateException;

	/*
	 * @author joeyy 2014/12/12 function CountClubMemberByPromotionId
	 * 
	 * @return The number of the club
	 * 
	 * @param promotionId
	 */
	public Integer countClubMemberByPromotionId(Integer promotionId)
			throws HibernateException;

	/*
	 * @author joeyy 2014/12/12 function getAllClubByUserId modified by joeyy
	 * 2014/12/22
	 * 
	 * @return List<Integer> clubId for user
	 * 
	 * @params UserId
	 */
	@SuppressWarnings("rawtypes")
	public List getClubByUserId(Integer userId) throws HibernateException;

	/**
	 * @author walker cheng 2014/12/11 function search the club by clubName and
	 *         clubLocation
	 */
	public List<ClubDto> searchClubByClubNameAndClubLocation(ClubDto clubDto)
			throws HibernateException;

	/*
	 * @Author Walker Cheng function add new club and return clubId 2014/12/11
	 */
	public Integer addClub(ClubDto clubDto) throws HibernateException;

	/*
	 * @Author Walker Cheng function add the new Infornation due to add new club
	 * 2014/12/19
	 */
	public void addUserClubInfo(UserClub userClub) throws HibernateException;

	/*
	 * @Author Walker Cheng function delete the club 2014/12/12
	 */
	public void deleteClub(Club club) throws HibernateException;

	/*
	 * @Author Walker Cheng function delete the club 2014/12/12
	 */
	public void deleteUserClubInfo(UserClub userClub) throws HibernateException;

	/*
	 * @author joeyy 2014/12/16 function getAllClub
	 * 
	 * @return List<Club>(AllClub)
	 */
	public List<Club> getAllClub() throws HibernateException;

	/*
	 * @author joeyy 2014/12/16 function getAllClubByUserId
	 * 
	 * @return List<Club> for one user
	 * 
	 * @param userId
	 */
	public List<Club> getAllCLubByUserId(Integer userId) throws HibernateException;

	/*
	 * @author joeyy 2014/12/24 fucntion getClubByPromotionId
	 * 
	 * @return Entity Club
	 * 
	 * @param promotionId
	 */
	public Club getClubByPromotionId(Integer promotionId) throws HibernateException;

}
