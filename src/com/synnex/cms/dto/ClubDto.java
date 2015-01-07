package com.synnex.cms.dto;

import java.io.Serializable;

/**
 * 
 * 
 * function for data transfer Club,User
 *
 */
public class ClubDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer clubId;
	private String clubName;
	private String clubLocation;
	//reflect userName
	private String managerName;
	//reflect userPhone
	private String managerPhone;
	private String clubDescription;
	private Integer managerId;
	private String clubUrl;
	public String getClubDescription() {
		return clubDescription;
	}
	public void setClubDescription(String clubDescription) {
		this.clubDescription = clubDescription;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getClubId() {
		return clubId;
	}
	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public String getClubLocation() {
		return clubLocation;
	}
	public void setClubLocation(String clubLocation) {
		this.clubLocation = clubLocation;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getClubUrl() {
		return clubUrl;
	}
	public void setClubUrl(String clubUrl) {
		this.clubUrl = clubUrl;
	}
	
	
	

}
