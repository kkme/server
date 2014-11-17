package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gamedb.DBCallbackFactory.NullDBCallback;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;

/**
 * 数据库回调消息;<br>
 * <p>
 * 由{@link IDBMessage#execute(com.hifun.soul.core.orm.IDBService)} 执行后的返回值产生;
 * 此消息需要投递到主线程中进行处理;
 * 
 * @author crazyjohn
 * 
 * @param <ResultType>
 */
public class DBCallbackMessage<ResultType> extends SysInternalMessage {
	/** 数据库执行结果 */
	private ResultType result;
	/** 数据库回调 */
	private IDBCallback<ResultType> callback;
	/** 数据库执行是否成功的标记位, 默认为true */
	private boolean isSucceed = true;
	/** 错误消息 */
	private String errorInfo;

	public void setDBCallback(IDBCallback<ResultType> callback) {
		this.callback = callback;
	}

	public void actionFailed(String errorInfo) {
		this.isSucceed = false;
		this.errorInfo = errorInfo;
	}

	/**
	 * 是否需要執行回调
	 * 
	 * @return true表示需要; false 表示不需要;
	 */
	public boolean needToCallback() {
		// 回调不为空, 且不是NullDBCallback
		return this.callback != null && !(this.callback instanceof NullDBCallback);
	}

	@Override
	public void execute() {
		if (this.callback == null) {
			return;
		}
		if (!this.isSucceed) {
			this.callback.onFailed(errorInfo);
			return;
		}
		this.callback.onSucceed(result);
	}

	public void setResult(ResultType result) {
		this.result = result;
	}

	public ResultType getResult() {
		return this.result;
	}

	/**
	 * 引发回调的数据库操作是否由主线程发起;
	 * 
	 * @return true 表示由主线程发起; false 表示没有从主线程发起;
	 */
	public boolean isSendByMainThread() {
		return (callback instanceof IMainThreadDBCallback);
	}
}
