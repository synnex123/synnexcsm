package com.synnex.cms.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.synnex.cms.action.UserLoginAction;
import com.synnex.cms.entity.User;

public class CheckLoginInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String LOGIN_PAGE = "login_page";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CheckLoginInterceptor.class);

	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation actionInvocation) {

		// 对LoginAction不做该项拦截
		Object action = actionInvocation.getAction();
		if (action instanceof UserLoginAction) {
			try {
				return actionInvocation.invoke();
			} catch (Exception e) {
				LOGGER.warn("exception at" + this.getClass().getName(), e);
			}
		}

		// 确认Session中是否存在LOGIN
		Map session = actionInvocation.getInvocationContext().getSession();
		User user = (User) session.get("user");
		if (user != null) {
			// 存在的情况下进行后续操作。
			try {
				return actionInvocation.invoke();
			} catch (Exception e) {
				LOGGER.warn("exception at" + this.getClass().getName(), e);
			}
		} else {
			ActionContext.getContext()
					.put("errmsg", "Your login has timed out");
			// 否则终止后续操作，返回LOGIN
			return LOGIN_PAGE;
		}
		return LOGIN_PAGE;
	}
}
