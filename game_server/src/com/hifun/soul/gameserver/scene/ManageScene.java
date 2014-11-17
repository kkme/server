package com.hifun.soul.gameserver.scene;

import com.hifun.soul.common.LogReasons.BasicPlayerLogReason;
import com.hifun.soul.common.LogReasons.OnlineTimeLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.annotation.NotThreadSafe;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.HumanOffLineEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;

/**
 * 只是负责管理玩家的场景;<br>
 * 就是相当于一个玩家的容器;
 * 
 * @author crazyjohn
 * 
 */

public class ManageScene extends Scene {
	private IDataService dataService;

	public ManageScene(int maxPlayerCount) {
		super(maxPlayerCount);
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * 玩家离开场景;
	 * 
	 * @param player
	 */
	public void playerLeveScene(final Player player) {
		final Human human = player.getHuman();
		// 如果Human对象不为空投递给场景处理;
		if (human != null) {
			// 根据不同的状态做出不同响应
			this.putMessage(new SysInternalMessage() {
				@Override
				public void execute() {
					// 扫荡中退出扫荡
					if(player.getState() == PlayerState.AUTOBATTLEING) {
						human.getHumanAutoBattleManager().cancelAutoBattle();
					}
					// 战斗中
					if (PlayerState.isInBattleState(player.getState())) {
						leveBattle(human);
					}
					
					// 角色为空这时候玩家还没有进场景;
					if (human != null) {
						//若正在匹配战中需要处理
						human.getHumanMatchBattleManager().leaveMatchBattleScene();
						humanLeveScene(human);
					}
					// 移除玩家
					removePlayer(player);
				}
			});
		} else {
			removePlayer(player);
		}

	}

	/**
	 * 离开场景;
	 * 
	 * @param human
	 */
	protected void humanLeveScene(Human human) {
		long now = GameServerAssist.getSystemTimeService().now();
		long loginTime = human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LOGIN_TIME);
		long onlineTime = now - loginTime;
		// 发送退出日志
		GameServerAssist.getLogService().sendBasicPlayerLog(human,
				BasicPlayerLogReason.REASON_NORMAL_LOGOUT, "", human
						.getPlayer().getSession().getIp(), human.getCrystal(),
				human.getCoin(), human.getExperience(), human.getEnergy(),onlineTime);
		GameServerAssist.getLogService().sendOnlineTimeLog(human, OnlineTimeLogReason.GM_KICK, "", (int)(onlineTime/1000), 0, loginTime, now);
		
		// 移除玩家
		getHumanManager().removeHuman(human.getHumanGuid());
		try {
			if (human != null) {
				// 设置最后一次登出时间;
				human.setLastLogoutTime(now);
				saveHuman(human);
				// 发送离线事件
				HumanOffLineEvent event = new HumanOffLineEvent();
				event.setOffLineTime(now);
				human.getEventBus().fireEvent(event);
			}
			human.setPlayer(null);
		} catch (Exception e) {
			Loggers.SCENE_LOGGER.error(
					String.format("Sava human error, humanGuid: %d",
							human.getHumanGuid()), e);
		}
	}

	/**
	 * 离开战斗;
	 * 
	 * @param human
	 */
	protected void leveBattle(Human human) {
		IBattleContext context = human.getBattleContext();
		if (context != null) {
			try {
				Battle battle = context.getBattle();
				if (battle != null) {
					battle.onBattleUnitQuit(human);
				}
			} catch (Exception e) {
				Loggers.SCENE_LOGGER.error(String.format(
						"Human exit battle error, humanGuid: %d",
						human.getHumanGuid()), e);
			}
		}
	}

	/**
	 * 对角色进行持久化操作, 角色下线的时候调用;
	 * 
	 * @param human
	 */
	private void saveHuman(Human human) {
		HumanEntity humanEntity = human.onPersistence();
		// 设置基础属性
		HumanBaseProperties.Builder baseProp = HumanBaseProperties.newBuilder();
		baseProp.setCoins(human.getCoin());
		baseProp.setEnergy(human.getEnergy());
		baseProp.setHomeLevel(human.getHomeLevel());
		baseProp.setHumanGuid(human.getHumanGuid());
		baseProp.setLevel(human.getLevel());
		baseProp.setName(human.getName());
		baseProp.setOccupation(human.getOccupation().getType());
		baseProp.setPassportId(human.getPlayer().getPassportId());
		humanEntity.getBuilder().setBaseProperties(baseProp);
		dataService.update(humanEntity);
	}

	/**
	 * 玩家离开时候的处理逻辑; 只能由主线程调用;
	 * 
	 * @param player
	 */
	@NotThreadSafe
	public void removePlayer(Player player) {
		Loggers.SCENE_LOGGER.info(String.format(
				"Player leave scene, passportId: %d, account: %s",
				player.getPassportId(), player.getAccount()));
		this.getPlayerManager().removeOnlineAccount(player.getPassportId());
		player.setHuman(null);
		player.setSession(null);
	}

}
