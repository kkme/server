package com.hifun.soul.core.session;

import org.apache.mina.common.IoSession;

import com.hifun.soul.common.StatisticsLoggerHelper;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.ISliceMessage;

/**
 * mina会话，封装了mina原生的<code>IoSession</code>，以及其他一些 应用程序自定义的业务逻辑
 * 
 */
public abstract class MinaSession implements ISession {
	protected volatile IoSession session;

	public MinaSession(IoSession s) {
		session = s;
	}

	@Override
	public boolean isConnected() {
		if (session != null) {
			return session.isConnected();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(IMessage msg) {
		if (session != null) {
			if(msg instanceof ISliceMessage){
				final ISliceMessage<BaseSessionMessage<?>> _slices = (ISliceMessage<BaseSessionMessage<?>>) msg;
				for(final BaseSessionMessage<?> _msg:_slices.getSliceMessages()){
					// 统计消息数据
					StatisticsLoggerHelper.logMessageSent(msg);
					session.write(_msg);
				}
			}else{
				StatisticsLoggerHelper.logMessageSent(msg);
				session.write(msg);
			}
		}
	}

	@Override
	public void close() {
		if (session != null) {
			session.close();
		}
	}
	
	public IoSession getIoSession(){
		return session;
	}

	public boolean closeOnException() {
		// TODO Auto-generated method stub
		return true;
	}

}
