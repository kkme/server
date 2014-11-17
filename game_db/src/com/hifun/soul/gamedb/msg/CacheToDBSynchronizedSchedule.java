package com.hifun.soul.gamedb.msg;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.core.msg.SysInternalMessage;

/**
 * 缓存到DB的同步消息;
 * 
 * @author crazyjohn
 * 
 */
public class CacheToDBSynchronizedSchedule extends SysInternalMessage {
	IHeartBeatable object;
	
	public CacheToDBSynchronizedSchedule(IHeartBeatable object) {
		this.object = object;
	}
	@Override
	public void execute() {
		object.heartBeat();
	}

}
