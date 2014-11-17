package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.List;

import com.hifun.soul.gamedb.agent.IDataPreloadableAgent;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;

/**
 * 利用查询加载数据到缓存的类。
 * <p>
 * 
 * 该数据预加载类是CommonQuery的修饰类，该类需要配合<tt>IDataPreloadableAgent</tt><br>
 * 的实现类来使用，将查询的结果预加载到<tt>IDataPreloadableAgent</tt>的实现类中。
 * <p>
 * 
 * 
 * @see IDataPreloadableAgent
 * 
 * @author crazyjohn
 */
public class DataPreloadQuery<T> extends XQLExecutorDecorator {

	private IDataPreloadableAgent<T> preloadAgent = null;

	public DataPreloadQuery(IXQLExecutor commonExecute,
			IDataPreloadableAgent<T> preloadAgent) {
		super(commonExecute);
		this.preloadAgent = preloadAgent;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 该方法第一次执行时，将利用查询将结果一次性加载到缓存中。
	 */
	@Override
	public List<T> execute(String queryName, String[] params, Object[] values) {
		List<T> allData = preloadAgent.getAllDataFromCache();
		if (allData == null) {
			allData = preloadAgent.loadDataToCache(super.execute(queryName,
					params, values));
		}
		return allData;
	}

}
