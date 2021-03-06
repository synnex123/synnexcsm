package com.synnex.cms.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.synnex.cms.entity.User;

public class CheckAuthorityInterceptor extends AbstractInterceptor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CheckAuthorityInterceptor.class);
	List<String> managerActionName = new ArrayList<String>();
	List<String> superManagerActionName = new ArrayList<String>();
	List<String> userActionName = new ArrayList<String>();
	static String[] u = { "UserChangePassword", "InitUpdateUserInfo",
			"UpdateUserInfo", "Logout", "UserSearch", "initApply",
			"applyManage", "applyManagecancelApply", "processApply",
			"getMyApply", "getApplyDetail", "searchMyClub", "init", "exitClub",
			"getPromotion", "getClubMembers", "getPromotionVote",
			"doPromotion", "cancelApply", "UserLogin", "CheckPassword",
			"saveApply" };
	static String[] m = { "rejectApply", "getApplyByManagerId",
			"producePromotion", "initPromotion", "processApply",
			"initEditClub", "editClub" };
	static String[] s = { "AddClub", "InitDeleteClub", "DeleteClub",
			"AddSystemManager", "InitAddSystemManager", "deleteUser" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actionInvocation){

		for (String i : u) {
			userActionName.add(i);
		}
		for (String i : m) {
			managerActionName.add(i);
		}
		for (String i : s) {
			superManagerActionName.add(i);
		}

		managerActionName.addAll(userActionName);
		superManagerActionName.addAll(managerActionName);
		String actionName = actionInvocation.getProxy().getActionName();
		Map session = actionInvocation.getInvocationContext().getSession();
		User user = (User) session.get("user");
		if (user == null) {
			try {
				return actionInvocation.invoke();
			} catch (Exception e) {
				LOGGER.warn("exception at"+this.getClass().getName(),e);
			}
		} else if (user.getUserType() == 1) {
			if (userActionName.contains(actionName)) {
				try {
					return actionInvocation.invoke();
				} catch (Exception e) {
					LOGGER.warn("exception at"+this.getClass().getName(),e);
				}
			} else {
				return "noAuthority";
			}
		} else if (user.getUserType() == 0) {
			if (managerActionName.contains(actionName)) {
				try {
					return actionInvocation.invoke();
				} catch (Exception e) {
					LOGGER.warn("exception at"+this.getClass().getName(),e);
				}
			} else {
				return "noAuthority";
			}

		} else if (user.getUserType() == 10) {
			if (superManagerActionName.contains(actionName)) {
				try {
					return actionInvocation.invoke();
				} catch (Exception e) {
					LOGGER.warn("exception at"+this.getClass().getName(),e);
				}
			} else {
				return "noAuthority";
			}
		} else {
			return "noAuthority";
		}
		return "noAuthority";
	}
}
