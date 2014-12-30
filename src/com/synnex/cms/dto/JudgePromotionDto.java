package com.synnex.cms.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @author joeyy
 *function for data transfer judgePromotion
 */
public class JudgePromotionDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer countPromotion;
	private Integer voteduserId;
	public Integer getCountPromotion() {
		return countPromotion;
	}
	public void setCountPromotion(Integer countPromotion) {
		this.countPromotion = countPromotion;
	}
	public Integer getVoteduserId() {
		return voteduserId;
	}
	public void setVoteduserId(Integer voteduserId) {
		this.voteduserId = voteduserId;
	}
	
}
