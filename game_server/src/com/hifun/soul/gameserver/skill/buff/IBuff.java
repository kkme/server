package com.hifun.soul.gameserver.skill.buff;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.counter.IRoundListener;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;

/**
 * buff接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBuff extends IRoundListener{

	/**
	 * 获取目标;
	 * 
	 * @return
	 */
	public IBattleUnit getTarget();

	/**
	 * 获取buff类型;
	 * 
	 * @return
	 */
	public BuffType getType();

	/**
	 * 获取buffId;
	 * 
	 * @return
	 */
	public int getBuffId();

	/**
	 * 回合结束以后的回调;
	 */
	public void onRoundFinished();

	/**
	 * 移除buff时候的回调;
	 */
	public void onRemove(boolean sendStop);

	/**
	 * 添加buff时候的回调;
	 */
	public void onAdd(boolean sendStart);


	/**
	 * 获取剩余的生命回合周期;
	 * 
	 * @return
	 */
	public int getLeftRound();

	/**
	 * 是否需要被移除;
	 * 
	 * @return true表示需要;
	 */
	public boolean needRemove();

	/**
	 * 在战斗单元行动之前进行调用, 做一些比如每回合行动前先减血(中毒)之类的事情;
	 */
	public void beforeAction();

	/***
	 * 获取最大的叠加次数;
	 * 
	 * @return
	 */
	public int getOverlyingCount();

	/**
	 * 是否可叠加;
	 * 
	 * @return;
	 */
	public boolean isOverlapable();
	
	public BuffInfo toBuffInfo();
	
	public BuffTemplate getBuffTemplate();

}
