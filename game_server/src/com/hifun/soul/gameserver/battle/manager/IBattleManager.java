package com.hifun.soul.gameserver.battle.manager;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.PendingBattleRequest;
import com.hifun.soul.gameserver.battle.callback.IBattleCallback;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.unit.HumanGuarder;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

/**
 * 战斗管理器接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleManager {

	/**
	 * 是否可以开始战斗;
	 * 
	 * @param oneUnit
	 * @param otherUnit
	 * @return
	 */
	public boolean canStartBattle(Human oneUnit, IBattleUnit otherUnit);

	/**
	 * 跟怪开始战斗;
	 * 
	 * @param human
	 * @param monster
	 * @return
	 */
	public boolean startBattleWithMapMonster(Human human, Monster monster, IBattleCallback callback);

	/**
	 * 跟Boss战的boss开始战斗;
	 * 
	 * @param human
	 * @param monster
	 * @param bossWarCallback
	 * @return
	 */
	public boolean startBattleWithBossWarMonster(Human human, Monster monster,
			IBattleCallback bossWarCallback);

	/**
	 * 跟玩家的守卫者进行战斗;
	 * 
	 * @param challenger
	 * @param guarder
	 * @param callback
	 * @return
	 */
	public boolean startBattleWithHumanGuarder(Human challenger,
			HumanGuarder guarder, PVPBattleCallback callback);

	/**
	 * 跟玩家进行在线的pvp;
	 * 
	 * @param challenger
	 * @param beAttacked
	 * @param battleFinishCallback
	 * @return
	 */
	public boolean startBattleWithOnlineHuman(Human challenger,
			Human beAttacked, PVPBattleCallback battleFinishCallback);

	/**
	 * 移除战斗;
	 * 
	 * @param battle
	 */
	public void removeBattle(Battle battle);

	/**
	 * 检查是否有无效的战斗实体;
	 */
	public void check();

	/**
	 * 杀掉一个无效的战斗实体;
	 * 
	 * @param battle
	 */
	public void killBattle(Battle battle);

	public int getSize();

	/**
	 * 注册一个挂起的战斗请求;
	 * 
	 * @param pendingBattleRequest
	 */
	public void registerPendingBattleRequest(
			PendingBattleRequest pendingBattleRequest);

	/**
	 * 獲取戰鬥请求;
	 * 
	 * @param challengerGuid
	 * @return
	 */
	public PendingBattleRequest getBattleRequest(long challengerGuid);

	/**
	 * 移除战斗请求;
	 * 
	 * @param challengerGuid
	 */
	public PendingBattleRequest removePendingBattleRequest(long challengerGuid);

	/**
	 * 所有PVP对战的入口, 包括PVP_OLINE, PVP_GUARDER, PVP_OFFLINE;<br>
	 * <ol>
	 * <li>业务系统如果需要调用战斗接口, 都统一从此处进入;</li>
	 * <li>它会帮忙处理所有可能的战斗逻辑, 比如被挑战者不在线的逻辑等</li>
	 * <li>业务系统调用时候需要传入业务相关的战斗后回调, 进行相应的战斗后处理</li>
	 * <li>比如各个业务的发奖励可能不一样, 包括要处理角色在线不在线的情况等;这些都可以写到相应的callback里</li>
	 * </ol>
	 * 
	 * @param human
	 * @param bechallengedGuid
	 * @param battleFinishCallback
	 */
	public void pvpBattleEnter(Human human, long bechallengedGuid,
			PVPBattleCallback battleFinishCallback);
}
