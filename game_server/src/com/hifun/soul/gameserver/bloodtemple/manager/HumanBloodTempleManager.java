package com.hifun.soul.gameserver.bloodtemple.manager;

import java.util.LinkedList;
import java.util.List;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.BloodTempleLogEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleLog;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo;
import com.hifun.soul.gameserver.bloodtemple.msg.GCBuyBloodTempleWrestleNum;
import com.hifun.soul.gameserver.bloodtemple.msg.GCShowBloodTempleEnemy;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.BloodTempleChallengeEvent;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.title.template.HumanTitleTemplate;

public class HumanBloodTempleManager implements ILoginManager, IEventListener {
	private Human human;
	private BloodTempleTemplateManager templateManager;
	private IDataService dataService;
	private LinkedList<BloodTempleEnemyInfo> enemyInfoList;

	public HumanBloodTempleManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		this.human.getEventBus().addListener(EventType.TITLE_UP_EVENT, this);
		templateManager = GameServerAssist.getBloodTempleTemplateManager();
		dataService = GameServerAssist.getDataService();
		enemyInfoList = new LinkedList<BloodTempleEnemyInfo>();
	}

	@Override
	public void onLogin() {
		// 加载敌人列表信息
		dataService.query(
				DataQueryConstants.QUERY_BLOOD_TEMPLE_LOG_BY_HUMAN_ID,
				new String[] { "secondId" },
				new Object[] { human.getHumanGuid() },
				new IDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						if (!result.isEmpty()) {
							@SuppressWarnings("unchecked")
							List<BloodTempleLogEntity> logEntityList = (List<BloodTempleLogEntity>) result;
							for (BloodTempleLogEntity entity : logEntityList) {
								BloodTempleEnemyInfo enemyInfo = new BloodTempleEnemyInfo();
								enemyInfo.setEnemyId(entity.getFirstId());
								enemyInfo.setEnemyLevel(entity.getFirstLevel());
								enemyInfo.setEnemyName(entity.getFirstName());
								enemyInfo.setLootTime(entity.getLootTime());
								enemyInfoList.add(enemyInfo);
							}
						}
					}

					@Override
					public void onFailed(String errorMsg) {
					}
				});
	}

	/**
	 * 购买角斗次数
	 */
	public void buyWrestleNum() {
		// 花费魔晶
		int costNum = templateManager.getBuyWrestleNumCost(human
				.getBloodTempleBuyNum() + 1);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.BLOOD_TEMPLE_BUY_WRESTLE_NUM, "")) {
			human.setBloodTempleBuyNum(human.getBloodTempleBuyNum() + 1);
			human.setBloodTempleRemainNum(human.getBloodTempleRemainNum() + 1);
			GCBuyBloodTempleWrestleNum msg = new GCBuyBloodTempleWrestleNum();
			msg.setNextBuyNumCost(templateManager.getBuyWrestleNumCost(human
					.getBloodTempleBuyNum() + 1));
			msg.setRemainWrestleNum(human.getBloodTempleRemainNum());
			human.sendMessage(msg);
		}
	}

	/**
	 * 发送敌人列表信息
	 */
	public void sendBloodTempleEnemyListInfo() {
		for (BloodTempleEnemyInfo enemyInfo : enemyInfoList) {
			enemyInfo.setLootTimeInterval((int) (GameServerAssist
					.getSystemTimeService().now() - enemyInfo.getLootTime()));
		}
		GCShowBloodTempleEnemy msg = new GCShowBloodTempleEnemy();
		msg.setEnemies(enemyInfoList.toArray(new BloodTempleEnemyInfo[0]));
		human.sendMessage(msg);
	}

	/**
	 * 添加敌人信息
	 */
	public void addEnemyInfo(BloodTempleLog bloodTempleLog) {
		BloodTempleEnemyInfo enemyInfo = new BloodTempleEnemyInfo();
		enemyInfo.setEnemyId(bloodTempleLog.getFirstId());
		enemyInfo.setEnemyLevel(bloodTempleLog.getFirstLevel());
		enemyInfo.setEnemyName(bloodTempleLog.getFirstName());
		enemyInfo.setLootTime(bloodTempleLog.getLootTime());
		if (enemyInfoList.size() >= SharedConstants.BLOOD_TEMPLE_ENEMY_NUM) {
			enemyInfoList.removeFirst();
		}
		enemyInfoList.addLast(enemyInfo);
	}

	/**
	 * 重置角色每日在嗜血神殿的数据
	 */
	public void resetHumanBloodTempleData() {
		human.setBloodTempleBuyNum(0);
		human.setBloodTempleRemainNum(SharedConstants.BLOOD_TEMPLE_FREE_BUY_NUM);
	}

	public void setLastResetDataTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_BLOOD_TEMPLE_DATA_TIME, lastTime);
	}

	public long getLastResetDataTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_BLOOD_TEMPLE_DATA_TIME);
	}

	/**
	 * 发送嗜血神殿挑战事件
	 */
	public void fireChallengeEvent() {
		human.getEventBus().fireEvent(new BloodTempleChallengeEvent());
	}

	@Override
	public void onEvent(IEvent event) {
		// 获取角色所在的房间
		GlobalBloodTempleManager globalManager = GameServerAssist
				.getGlobalBloodTempleManager();
		BloodTempleRoom humanRoom = globalManager.getHumanBloodTempleRoom(human
				.getHumanGuid());
		if (humanRoom != null) {
			// 角色升级
			if (event.getType() == EventType.LEVEL_UP_EVENT) {
				humanRoom.setOwnerLevel(human.getLevel());
				globalManager.updateBloodTempleRoom(humanRoom);
			}
			// 角色升衔
			if (event.getType() == EventType.TITLE_UP_EVENT) {
				humanRoom.setOwnerTitleId(human.getCurrentTitle());
				HumanTitleTemplate titleTemplate = GameServerAssist
						.getTitleTemplateManager().getHumanTitleTemplate(
								human.getCurrentTitle());
				if (titleTemplate != null) {
					humanRoom.setOwnerTitleName(titleTemplate.getTitleName());
				}
			}
		}
	}
}
