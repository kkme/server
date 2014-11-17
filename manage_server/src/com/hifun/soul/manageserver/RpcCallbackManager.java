package com.hifun.soul.manageserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.async.IAsyncCallbackMessage;

/**
 * RPC回调对象管理器
 * 
 * @author crazyjohn
 * 
 */
@Component
public class RpcCallbackManager {
	private Map<Long, RpcCallback<? extends Message>> callbackMap = new HashMap<Long, RpcCallback<? extends Message>>();

	private static long idCounter = 0;

	/**
	 * 注册回调对象
	 * 
	 * @param msg
	 *            异步消息
	 * @param callback
	 *            回调对象
	 * @exception NullPointerException
	 *                ,如果callback为空
	 */
	public void registerCallback(IAsyncCallbackMessage msg,
			RpcCallback<? extends Message> callback) {
		if (callback == null) {
			throw new NullPointerException();
		}
		idCounter++;
		if (idCounter < 0) {
			idCounter = 0;
		}
		msg.setContextId(idCounter);
		callbackMap.put(idCounter, callback);
	}

	/**
	 * 返回回调对象
	 * 
	 * @param contextId
	 * @return 如果回调对象不存在,则返回null
	 */
	@SuppressWarnings("unchecked")
	public <ReturnType extends Message>RpcCallback<ReturnType> getCallbackContext(long contextId) {
		return (RpcCallback<ReturnType>) callbackMap.remove(contextId);
	}
}
