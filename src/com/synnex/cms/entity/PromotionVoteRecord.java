package com.synnex.cms.entity;

import java.io.Serializable;

public class PromotionVoteRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer promotionId;
	private Integer voteuserId;
	private Integer voteduserId;
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public Integer getVoteuserId() {
		return voteuserId;
	}
	public void setVoteuserId(Integer voteuserId) {
		this.voteuserId = voteuserId;
	}
	public Integer getVoteduserId() {
		return voteduserId;
	}
	public void setVoteduserId(Integer voteduserId) {
		this.voteduserId = voteduserId;
	}

	
	

}
