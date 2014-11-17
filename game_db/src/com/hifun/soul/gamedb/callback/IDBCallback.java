package com.hifun.soul.gamedb.callback;

/**
 * 数据库服务回调接口;
 * 
 * @author crazyjohn
 * 
 * @param <ResultType>
 *            数据服务执行后的返回值;
 */
public interface IDBCallback<ResultType> {

	/**
	 * 当数据服务成功执行后;
	 * 
	 * @param result
	 *            数据服务执行结果;
	 */
	public void onSucceed(ResultType result);

	/**
	 * 当数据服务执行失败后
	 */
	public void onFailed(String errorMsg);
}
