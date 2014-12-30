package com.synnex.cms.serviceimpl;

import java.util.List;

import org.hibernate.HibernateException;

import com.synnex.cms.dao.ClubDao;
import com.synnex.cms.dao.UserDao;
import com.synnex.cms.dto.ClubDto;
import com.synnex.cms.entity.Club;
import com.synnex.cms.entity.User;
import com.synnex.cms.entity.UserClub;
import com.synnex.cms.service.ClubService;

/**
 * @author petep 2014/11/25
 */
/**
 * function getClubByLocation for main view 2014/11/25
 */
public class ClubServiceImpl implements ClubService {
	private ClubDao clubDao;
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setClubDao(ClubDao clubDao) {
		this.clubDao = clubDao;
	}

	/**
	 * @Author Pete Peng function getClub 2014/12/17
	 */
	public List<ClubDto> getClubByLocation(String location) throws HibernateException {
			return clubDao.getClubByLocation(location);
	
	}

	public List<Club> getAllClub() throws HibernateException {
		return clubDao.getAllClub();
	}

	/**
	 * @Author Walker Cheng function getClub 2014/11/28
	 */
	public Club getClubByClubId(Integer clubId) throws HibernateException {
		return clubDao.getClubByClubId(clubId);
	}

	/**
	 * @author joeyy 2014/12/16 function getClubIdByUserId
	 * 
	 * @return List<Integer> clubId
	 * 
	 * @param userId
	 */
	@SuppressWarnings("rawtypes")
	public List getClubByUserId(Integer userId) throws HibernateException {

		return clubDao.getClubByUserId(userId);
	}

	/**
	 * @Author Walker Cheng function search club by clubName and clubLocation
	 * 2014/12/11
	 */
	public List<ClubDto> searchClubByClubNameAndClubLocation(ClubDto clubDto)
			throws HibernateException {
		return clubDao.searchClubByClubNameAndClubLocation(clubDto);
	}

	/**
	 * @Author Walker Cheng function add the new club and return the success or
	 * fail; 2014/12/11
	 */
	public Boolean addClub(ClubDto clubDto, User user) throws HibernateException {
		UserClub userClub = new UserClub();
		Integer clubId = clubDao.addClub(clubDto);
		userDao.updateUserInfo(user);
		userClub.setClubId(clubId);
		userClub.setUserId(clubDto.getManagerId());
		clubDao.addUserClubInfo(userClub);
		return true;
	}

	/**
	 * @author walker cheng function query the number of member 2014/12/15
	 * @throws Exception
	 */
	public Integer queryMemberNumber(ClubDto clubDto) throws HibernateException,NumberFormatException {
		Integer clubId = clubDto.getClubId();
		Integer memberNumber = 0;
		memberNumber = userDao.queryMemberNumber(clubId);
		return memberNumber;
	}

	/**
	 * @author walker cheng function delete the club 2014/12/12
	 * @throws Exception
	 */
	public Boolean deleteClub(ClubDto clubDto) throws HibernateException {
		Club club = new Club();
		UserClub userClub = new UserClub();
		User user = new User();
		club.setClubName(clubDto.getClubName());
		club.setClubId(clubDto.getClubId());
		club.setClubLocation(clubDto.getClubLocation());
		club.setClubDescription(clubDto.getClubDescription());
		club.setManagerId(clubDto.getManagerId());
		clubDao.deleteClub(club);
		userClub.setClubId(clubDto.getClubId());
		userClub.setUserId(clubDto.getManagerId());
		clubDao.deleteUserClubInfo(userClub);
		user = userDao.getUserByUserId(clubDto.getManagerId());
		user.setUserType(1);
		userDao.updateUserInfo(user);
		return true;
	}

	public List<Club> getAllCLubByUserId(Integer userId) throws HibernateException {

		return clubDao.getAllCLubByUserId(userId);
	}

	/**
	 * @author joeyy 2014/12/24 fucntion getClubByPromotionId
	 * 
	 * @return Entity Club
	 * 
	 * @param promotionId
	 */
	public Club getClubByPromotionId(Integer promotionId) throws HibernateException {
		return clubDao.getClubByPromotionId(promotionId);
	}

}
