package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.core.annotation.NotThreadSafe;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.human.Human;

/**
 * 战斗相关的回调;<br>
 * 各个业务系统需要调用战斗, 而且有战斗后处理逻辑的需要去实现此接口;<br>
 * 比如竞技场, 好友切磋;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleCallback {

	/**
	 * 挑战者胜利;
	 * 
	 * @param challenger
	 * @param beChallenged
	 */
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged);

	/**
	 * 被挑战者胜利;
	 * 
	 * @param challenger
	 * @param beChallenged
	 */
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged);

	/**
	 * 开始战斗之前的回调;<br>
	 * 会被相应的场景线程调用;
	 * 
	 * @param challenger
	 * @param beChallenged
	 */
	@NotThreadSafe
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged);

	/**
	 * 获取战斗类型;
	 * 
	 * @return 返回战斗类型;
	 */
	public BattleType getBattleType();

	/**
	 * 获取先攻的战斗单元;<br>
	 * 根据策划规则, 战斗单元先攻属性高的先出手;如果相等的话rand;<br>
	 * 
	 * @return
	 */
	public IBattleUnit getFirstActionUnit();

	/**
	 * 战斗结束时候的回调接口;
	 */
	public void onBattleFinished();

	/**
	 * 设置挑战者;
	 * 
	 * @param unit
	 */
	public <U extends IBattleUnit> void setChallengedUnit(U unit);

	/**
	 * 某个战斗单元中间退出;
	 * 
	 * @param unit
	 */
	public void middleQuitGuy(IBattleUnit unit);
	
	public void onBattleRoundout();
	
	/**
	 * 进入战斗超时;
	 * 
	 * @param challenger
	 * @param beChallenged
	 */
	public void onEnterBattleFailed();
	
	/**
	 * 完全退出战斗
	 * <pre>该方法在双方都已经将战斗状态切换到其他状态后执行</pre>
	 */
	public void onBattleExited();
}
