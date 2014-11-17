package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.List;

import com.hifun.soul.gamedb.agent.query.IXQLExecutor;

/**
 * 执行器的装饰器基类;<br>
 * 包装了一个{@link IXQLExecutor} ;
 * 
 * @author crazyjohn
 * 
 */
public abstract class XQLExecutorDecorator implements IXQLExecutor {
	/** 被包装的执行器 */
	protected IXQLExecutor wrappedExecutor;

	public XQLExecutorDecorator(IXQLExecutor wrappedExecutor) {
		this.wrappedExecutor = wrappedExecutor;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		return wrappedExecutor.execute(queryName, params, values);
	}

}
