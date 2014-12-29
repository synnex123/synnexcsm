package com.synnex.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.synnex.cms.entity.User;
import com.synnex.cms.utils.UserUtil;

/**
 * @author joeyy
 * fucntion for forced return to login.jsp if session is out of time
 *
 */
public class SessionFilter implements Filter {

	
    /**
     * Default constructor. 
     */
    public SessionFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req=(HttpServletRequest)request;
		User user=UserUtil.getUser(req);
		if (null==user) {
			request.setAttribute("errmsg", "登录超时，请重新登录");
			request.getRequestDispatcher("../user/login.jsp").forward(request, response);
		}else{
			chain.doFilter(request, response);	
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
