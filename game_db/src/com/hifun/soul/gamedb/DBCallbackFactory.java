package com.hifun.soul.gamedb;

import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 回调工厂;<br>
 * factory + null object pattern;<br>
 * 责任很简单,只通过{{@link #getNullDBCallback()} 获取一个空的数据库回调对象;
 * 
 * @author crazyjohn
 * 
 */
public class DBCallbackFactory {
	private static IDBCallback<Object> nullDBCallback = new NullDBCallback();

	/**
	 * 获取一个空的数据库回调对象;
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> IDBCallback<E> getNullDBCallback() {
		return (IDBCallback<E>) nullDBCallback;
	}

	/**
	 * NULL OBJECT PATTERN;
	 * 
	 * @author crazyjohn
	 * 
	 */
	public static class NullDBCallback implements IDBCallback<Object> {

		@Override
		public void onSucceed(Object result) {
			// do nothing

		}

		@Override
		public void onFailed(String errorMsg) {
			// do nothing

		}

	}
}
