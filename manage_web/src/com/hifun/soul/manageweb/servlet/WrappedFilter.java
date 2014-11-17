package com.hifun.soul.manageweb.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import com.hifun.soul.manageweb.Managers;

public class WrappedFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 初始化管理器
		Managers.init();
		super.init(filterConfig);
	}
}
