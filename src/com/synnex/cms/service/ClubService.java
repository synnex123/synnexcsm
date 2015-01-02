package com.synnex.cms.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.dto.SearchDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;


/**
 * function getClubByLocation for main view 2014/11/25
 */
public interface ClubService {

	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getClubByLocation(String location) throws HibernateException;
	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getAllClubByLocation(String location) throws HibernateException;

	public List<Club> getAllClub() throws HibernateException;

	/**
	 * @Author Walker Cheng function get Club Information  
	 * 2014/11/28
	 * @return Club
	 * @param clubId
	 * @throws Exception
	 */
	public Club getClubByClubId(Integer clubId) throws HibernateException;

	/**
	 * @author joeyy 2014/12/16 function getClubIdByUserId
	 * 
	 * @return List<Integer> clubId
	 * 
	 * @param userId
	 */
	@SuppressWarnings("rawtypes")
	public List getClubByUserId(Integer userId) throws HibernateException;

	/**
	 * @Author Walker Cheng function search club by clubName and clubLocation
	 * 2014/12/11
	 * @return List<ClubDto>
	 * @param ClubDto
	 * @throws Exception
	 */
	public List<ClubDto> searchClubByClubNameAndClubLocation(ClubDto clubDto)
			throws Exception;

	/**
	 * @Author Walker Cheng function add the new club and return the success or
	 * fail; 2014/12/11
	 */
	public Boolean addClub(ClubDto clubDto, User user) throws Exception;

	/**
	 * @author walker cheng function query the number of member 2014/12/15
	 * @throws Exception
	 */
	public Integer queryMemberNumber(ClubDto clubDto) throws HibernateException,NumberFormatException;
	/**
	 * @Author Walker Cheng function delete club
	 * 2014/12/12
	 * @return Boolean
	 * @param ClubDto
	 * @throws Exception
	 */
	public Boolean deleteClub(ClubDto clubDto) throws HibernateException;

	public List<Club> getAllCLubByUserId(Integer userId) throws HibernateException;

	/**
	 * @author joeyy 2014/12/24 fucntion getClubByPromotionId
	 * 
	 * @return Entity Club
	 * 
	 * @param promotionId
	 */
	public Club getClubByPromotionId(Integer promotionId) throws HibernateException;
	public List<SearchDto> getClubMembers(int clubId) throws HibernateException;

}
