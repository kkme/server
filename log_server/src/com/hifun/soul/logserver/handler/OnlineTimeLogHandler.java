package com.hifun.soul.logserver.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.common.IoHandlerAdapter;

import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.dao.LogDao;
import com.hifun.soul.logserver.model.OnlineTimeLog;

public class OnlineTimeLogHandler extends IoHandlerAdapter implements IMessageHandlerWithType<OnlineTimeLog> {

	@Override
	public short getMessageType() {
		return MessageType.LOG_ONLINETIME_RECORD;
	}

	@Override
	public void execute(OnlineTimeLog message) {
		String _logName = LogDao.getLogNameByBeanClass(message.getClass());
		if (_logName == null) {
			throw new IllegalStateException("Can't find the log name for class [" + message.getClass() + "]");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		String timeStamp = format.format(new Date());
		message.setTableName(_logName + "_" + timeStamp);
		message.setCreateTime(System.currentTimeMillis());
		Object obj = LogDao.getExistedObject(_logName, message);
		if(obj == null){
			LogDao.insert(_logName, message);
		}
		else{
			LogDao.update(_logName, message);
		}
	}

}
