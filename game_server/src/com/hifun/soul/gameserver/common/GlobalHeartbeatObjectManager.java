package com.hifun.soul.gameserver.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.gameserver.activity.GlobalActivityManager;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 全局心跳对象管理器
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class GlobalHeartbeatObjectManager implements IHeartBeatable,IInitializeRequired {
	/** 公告管理器 */
	@Autowired
	private BulletinManager bulletinManager;
	/** 全局定时任务管理器 */
	@Autowired
	private GlobalTimeTaskManager taskManager;
	/** 全局活动管理器 */
	@Autowired
	private GlobalActivityManager activityManager;
	
	@Override
	public void init(){
		taskManager.init();
	}
	
	@Override
	public void heartBeat() {
		taskManager.heartBeat();
		bulletinManager.heartBeat();
		activityManager.heartBeat();
	}
	

}
