package com.synnex.cms.entity;

import java.io.Serializable;

public class Club implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer clubId;
	private String clubName;
	private Integer managerId;
	private String clubDescription;
	private String clubLocation;
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
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getClubDescription() {
		return clubDescription;
	}
	public void setClubDescription(String clubDescription) {
		this.clubDescription = clubDescription;
	}
	public String getClubLocation() {
		return clubLocation;
	}
	public void setClubLocation(String clubLocation) {
		this.clubLocation = clubLocation;
	}
	
	

}
