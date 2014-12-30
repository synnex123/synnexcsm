package com.synnex.cms.interceptor;

import java.util.Map;

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
 
    @SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation actionInvocation) throws Exception {
 

        // 对LoginAction不做该项拦截
        Object action = actionInvocation.getAction();
        if (action instanceof UserLoginAction) {
            return actionInvocation.invoke();
        }
 
        // 确认Session中是否存在LOGIN
		Map session = actionInvocation.getInvocationContext().getSession();
        User user = (User) session.get("user");
        if (user != null) {
            // 存在的情况下进行后续操作。
            return actionInvocation.invoke();
        } else {
        	ActionContext.getContext().put("errmsg", "Your login has timed out");
            // 否则终止后续操作，返回LOGIN
            return LOGIN_PAGE;
        }
    }
}
