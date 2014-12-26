package com.synnex.cms.dto;

public class SearchUserClubDto {
	private Integer userId;
	private Integer clubId;
	private String clubName;
	private String clubLocation;
	private Integer managerId;
	private String managerEmail;
	private String managerName;
	
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	

}
