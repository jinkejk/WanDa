package com.wanda.interceptor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wanda.util.OpenOfficeServerUtil;

/**
 * 服务器启动和关闭时执行的代码
 * @author jinkejk
 *
 */
public class StartAndShutup implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//启动openoffice服务
		System.out.println(sce.getServletContext().getInitParameter("openOfficePath"));
        OpenOfficeServerUtil.start(sce.getServletContext().getInitParameter("openOfficePath"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//关闭openoffice服务
        OpenOfficeServerUtil.shutDown();
	}

}
