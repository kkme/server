package com.hifun.soul.gameserver.activity;


/**
 * 具体的活动管理器接口
 * 
 * @author magicstone
 *
 */
public interface IHumanActivityManager {

	/**
	 * 获取活动类型
	 * @return
	 */
	ActivityType getActivityType();
	
	/**
	 * 打开具体活动面板的处理
	 */
	void onOpenClick();
	
}
