package com.hifun.soul.gameserver.battle.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.annotation.NotThreadSafe;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.PendingBattleRequest;
import com.hifun.soul.gameserver.battle.callback.IBattleCallback;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCWaittingBattleResponse;
import com.hifun.soul.gameserver.battle.unit.HumanGuarder;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.godsoul.manager.HumanGodsoulManager;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.HumanSkillManager;
import com.hifun.soul.gameserver.legion.manager.HumanLegionManager;
import com.hifun.soul.gameserver.mock.GameMockUtil;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.nostrum.manager.HumanNostrumManager;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.predict.manager.HumanPredictManager;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.sign.manager.HumanSignManager;
import com.hifun.soul.gameserver.sprite.manager.HumanSpriteManager;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.title.manager.HumanTitleManager;

/**
 * 战斗管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class BattleManager implements IBattleManager {
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private IDataService dataService;
	/** 所有的战斗;是一个并发的容器 */
	private Map<Integer, Battle> battles = new ConcurrentHashMap<Integer, Battle>();
	/** 挂起的战斗请求 */
	private Map<Long, PendingBattleRequest> pendingRequests = new HashMap<Long, PendingBattleRequest>();

	@Override
	public boolean canStartBattle(Human oneUnit, IBattleUnit otherUnit) {
		// 打自己
		if (isSelf(oneUnit.getHumanGuid(), otherUnit.getUnitGuid())) {
			return false;
		}
		// 如果挑战者已经下线则不进入战斗
		if(oneUnit == null
				|| oneUnit.getPlayer() == null){
			if(otherUnit != null){
				Human other = sceneManager.getSceneHumanManager().getHumanByGuid(otherUnit.getUnitGuid());
				if(other != null){
					other.sendErrorMessage(LangConstants.PLAYER_OFFLINE);
				}
			}
			return false;
		}
		// 开始战斗之前的检查; 如果此玩家已经在战斗中, 则不可以再开始了
		if (oneUnit.getPlayer().getState() == PlayerState.BATTLING) {
			return false;
		}
		return true;
	}

	/**
	 * 添加一场战斗;
	 * 
	 * @param aBattle
	 */
	private void addBattle(Battle aBattle) {
		this.battles.put(System.identityHashCode(aBattle), aBattle);
	}

	@Override
	public void removeBattle(Battle battle) {
		this.battles.remove(System.identityHashCode(battle));
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub

	}

	@Override
	public void killBattle(Battle battle) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSize() {
		return this.battles.size();
	}

	@Override
	public boolean startBattleWithHumanGuarder(Human challenger, HumanGuarder guarder, PVPBattleCallback callback) {
		callback.setChallengedHuman(false);
		return startABattle(challenger, guarder, callback);
	}

	/**
	 * 开始一场战斗;<br>
	 * 场景线程调用;
	 * 
	 * @param challenger
	 * @param otherGuy
	 * @param callback
	 * @return
	 */
	@NotThreadSafe
	protected boolean startABattle(Human challenger, IBattleUnit otherGuy, IBattleCallback callback) {
		if (!canStartBattle(challenger, otherGuy)) {
			return false;
		}
		Battle aBattle = Battle.buildBattle(challenger, otherGuy);
		// 是否是跟镜像在打
		if (otherGuy instanceof HumanGuarder) {
			aBattle.setBattleWithGuarder();
		}
		// 构建战斗上下文;
		challenger.buildBattleContext(aBattle);
		otherGuy.buildBattleContext(aBattle);
		// 添加战斗回调
		aBattle.registerBattleFinishCallback(callback);
		// 战斗前处理
		callback.beforeBattleStart(challenger, otherGuy);
		callback.setChallengedUnit(otherGuy);
		this.addBattle(aBattle);
		// 开启战斗
		aBattle.sendBattleInfo(challenger);
		// 战斗进入初始状态; 战斗单元进入战斗状态;
		challenger.enterBattleState();
		otherGuy.enterBattleState();
		return true;
	}

	@Override
	public boolean startBattleWithMapMonster(Human human, Monster monster, IBattleCallback callback) {
		return startABattle(human, monster, callback);
	}

	@Override
	public boolean startBattleWithOnlineHuman(Human challenger, Human beAttacked, PVPBattleCallback callback) {
		callback.setChallengedHuman(true);
		return startABattle(challenger, beAttacked, callback);
	}

	@Override
	public void registerPendingBattleRequest(PendingBattleRequest pendingBattleRequest) {
		pendingRequests.put(pendingBattleRequest.getChallengerGuid(), pendingBattleRequest);
	}

	@Override
	public PendingBattleRequest getBattleRequest(long challengerGuid) {
		return this.pendingRequests.get(challengerGuid);
	}

	@Override
	public PendingBattleRequest removePendingBattleRequest(long challengerGuid) {
		return this.pendingRequests.remove(challengerGuid);
	}

	@Override
	public void pvpBattleEnter(final Human human, final long guid, final PVPBattleCallback battleFinishCallback) {
		if (isSelf(human.getHumanGuid(), guid)) {
			return;
		}
		final Human beAttacked = sceneManager.getSceneHumanManager().getHumanByGuid(guid);
		if (beAttacked == null) {			
			// 此时角色不在线, 添加不在线时候跟镜像战斗的逻辑;
			dataService.query(DataQueryConstants.QUERY_CHARACTER_BATTLE_IFNO, new String[] { "humanGuid" },
					new Object[] { guid }, new IDBCallback<List<?>>() {

						@Override
						public void onSucceed(List<?> result) {
							if (result.isEmpty()) {
								return;
							}
							HumanEntity humanEntity = (HumanEntity) result.get(0);
							startBattleWithHumanGuarder(human, buildBattleGuarder(guid, humanEntity),
									battleFinishCallback);
						}

						@Override
						public void onFailed(String errorMsg) {
							// do nothing
						}
					});
			return;
		}
		// 如果角色在线, 而且正在战斗中, 则直接跟镜像打;
		if (beAttacked.getPlayer().getState() == PlayerState.BATTLING || 
				beAttacked.getPlayer().getState() == PlayerState.HOSTING_BATTLING) {
			startBattleWithHumanGuarder(human, beAttacked.getBattleGuarder(), battleFinishCallback);
			return;
		}
		// 如果玩家在扫荡中, 也直接跟镜像打
		if (beAttacked.getPlayer().getState() == PlayerState.AUTOBATTLEING) {
			startBattleWithHumanGuarder(human, beAttacked.getBattleGuarder(), battleFinishCallback);
			return;
		}
		if(battleFinishCallback.requireSendJoinBattleRequest()){
			// 通知已经发送战斗邀请, 开始倒计时
			human.sendMessage(new GCWaittingBattleResponse());
			// 发送战斗邀请
			battleFinishCallback.sendJoinBattleRequest(beAttacked);	
			// 添加一個挂起的战斗请求
			registerPendingBattleRequest(new PendingBattleRequest(human.getHumanGuid(), guid, System.currentTimeMillis(),
					battleFinishCallback));
			// 超时任务, 过时与镜像AI开始战斗;
			GameServerAssist.getGameWorld().scheduleOnece(new SysInternalMessage() {
				@Override
				public void execute() {
					// 移除挂起战斗请求
					PendingBattleRequest pendingRequest = getBattleRequest(human.getHumanGuid());
					if (pendingRequest == null) {
						return;
					}
					if (pendingRequest.getBeAttackedGuid() != beAttacked.getHumanGuid()) {
						return;
					}
					removePendingBattleRequest(human.getHumanGuid());
					// 超时直接开始战斗;
					startBattleWithHumanGuarder(human, beAttacked.getBattleGuarder(), battleFinishCallback);
	
				}
			}, SharedConstants.WAITING_BATTLE_REQUEST_TIMES);
		}else{
			this.startBattleWithOnlineHuman(human, beAttacked,
					battleFinishCallback);
		}
	}
	
	private boolean isSelf(long oneGuid, long otherGuid) {
		// 打自己
		if (oneGuid == otherGuid) {
			return true;
		}
		return false;
	}

	public void pvpMatchBattleEnter(final Human human, final long guid, final PVPBattleCallback battleFinishCallback) {
		final Human beAttacked = sceneManager.getSceneHumanManager().getHumanByGuid(guid);
		if (beAttacked == null) {
			battleFinishCallback.onEnterBattleFailed();
			return;
		}
		// 如果角色在线, 而且正在战斗中, 则匹配失败;
		if (beAttacked.getPlayer().getState() == PlayerState.BATTLING) {	
			battleFinishCallback.onEnterBattleFailed();
			return;
		}
		// 如果玩家在扫荡中, 也直接匹配失败
		if (beAttacked.getPlayer().getState() == PlayerState.AUTOBATTLEING) {	
			battleFinishCallback.onEnterBattleFailed();
			return;
		}
		this.startBattleWithOnlineHuman(human, beAttacked,
				battleFinishCallback);
	}

	/**
	 * 构建守卫者;
	 * 
	 * @param guid
	 * @param humanEntity
	 * @return
	 */
	private HumanGuarder buildBattleGuarder(long guid, HumanEntity humanEntity) {
		Human mockHuman = GameMockUtil.buildHuman();
		HumanPropertyManager propertyManager = new HumanPropertyManager(mockHuman);
		HumanSkillManager skillManager = new HumanSkillManager(mockHuman);
		HumanBagManager bagManager = new HumanBagManager(mockHuman);
		HumanHoroscopeManager horoscopeManager = new HumanHoroscopeManager(mockHuman);
		HumanTechnologyManager technologyManager = new HumanTechnologyManager(mockHuman);
		HumanTitleManager titleManager = new HumanTitleManager(mockHuman);
		HumanNostrumManager nostrumManager = new HumanNostrumManager(mockHuman);
		HumanGodsoulManager godsoulManager = new HumanGodsoulManager(mockHuman);
		HumanPredictManager predictManager = new HumanPredictManager(mockHuman);
		HumanGiftManager giftManager = new HumanGiftManager(mockHuman);
		HumanSignManager signManager = new HumanSignManager(mockHuman);
		HumanSpriteManager spriteManager = new HumanSpriteManager(mockHuman);
		HumanLegionManager legionManager = new HumanLegionManager(mockHuman);
		// 初始化属性管理器;
		propertyManager.onLoad(humanEntity);
		// 设置职业;
		mockHuman.setOccupation(Occupation.typeOf(propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.OCCUPATION)));
		mockHuman.setLevel(propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEVEL));
		// 计算初始属性;
		propertyManager.recalculateInitProperties(mockHuman);
		// 神魄加成(先加载一下装备位数据，装备属性加成时需要)
		godsoulManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		mockHuman.setHumanGodsoulManager(godsoulManager);
		// 装备加成
		bagManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 星运加成
		horoscopeManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 科技加成
		technologyManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 军衔加成
		titleManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 秘药加成
		nostrumManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 预见加成
		predictManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 天赋加成
		giftManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 星图加成
		signManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 精灵加成
		spriteManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 军团头衔加成
		legionManager.onBattlePropertiesLoad(humanEntity, propertyManager);
		// 重新计算所有属性
		propertyManager.recalculateAllProperties();
		// 加载技能;
		skillManager.onLoad(humanEntity);
		HumanGuarder guarder = new HumanGuarder(guid, propertyManager, skillManager);
		guarder.setName(humanEntity.getName());
		return guarder;
	}

	@Override
	public boolean startBattleWithBossWarMonster(Human human, Monster monster, IBattleCallback bossWarCallback) {
		return this.startBattleWithMapMonster(human, monster, bossWarCallback);
	}

}
