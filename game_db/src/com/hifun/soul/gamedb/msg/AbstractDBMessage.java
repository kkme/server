package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 数据库消息的抽象基类;<br>
 * FIXME: crazyjohn 子类数据库消息的泛型使用有问题：实体泛型类型和回调泛型类型之间的问题
 * 
 * <pre>
 * 1. 执行方法{{@link #execute(IDBService)} 设计成不支持被子类覆盖;
 * 2. 执行方法{{@link #execute(IDBService)} 是一个Template Method;
 * 3. 暴露给子类的只有{{@link #doRealDBAction(IDBService, DBCallbackMessage)}方法;
 * 4. 子类通过去覆盖{{@link #doRealDBAction(IDBService, DBCallbackMessage)}方法实现不同的业务;
 * </pre>
 * 
 * @author crazyjohn
 */
public abstract class AbstractDBMessage<ResultType> extends SysInternalMessage
		implements IDBMessage {
	/** 回调对象 */
	IDBCallback<ResultType> callback;

	public AbstractDBMessage(IDBCallback<ResultType> callback) {
		this.callback = callback;
	}

	@Override
	public final DBCallbackMessage<ResultType> execute(IDBAgent dbAgent) {
		DBCallbackMessage<ResultType> callbackMsg = buildCallbackMessage();
		try {
			doRealDBAction(dbAgent, callbackMsg);
		} catch (Exception e) {
			callbackMsg.actionFailed(e.getMessage());
		}
		return callbackMsg;
	}

	/**
	 * 构建一个回调消息
	 * 
	 * @return
	 */
	protected DBCallbackMessage<ResultType> buildCallbackMessage() {
		DBCallbackMessage<ResultType> callbackMsg = new DBCallbackMessage<ResultType>();
		callbackMsg.setDBCallback((IDBCallback<ResultType>) this.callback);
		return callbackMsg;
	}

	/**
	 * 执行真正的数据库动作
	 * 
	 * @param dbAgent
	 * @param callbackMsg
	 */
	protected abstract void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<ResultType> callbackMsg);

	@Override
	public void execute() {
		// 不支持自执行
		throw new UnsupportedOperationException();
	}
}
