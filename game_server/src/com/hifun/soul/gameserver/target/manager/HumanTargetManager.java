package com.hifun.soul.gameserver.target.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanTargetEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.target.TargetRewardState;
import com.hifun.soul.gameserver.target.TargetType;
import com.hifun.soul.gameserver.target.msg.GCUpdateTargetPanel;
import com.hifun.soul.gameserver.target.template.TargetTemplate;
import com.hifun.soul.proto.common.Targets.Target;
import com.hifun.soul.proto.data.entity.Entity.HumanTarget;

/**
 * 角色个人目标业务管理器
 * 
 * @author yandajun
 * 
 */
public class HumanTargetManager implements IHumanPersistenceManager,
		ICachableComponent, IEventListener {
	private Human human;
	/** 目标ID - 领奖状态 */
	private Map<Integer, Integer> targetMap = new HashMap<Integer, Integer>();
	private TargetTemplateManager targetTemplateManager;
	/** 缓存 */
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();

	public HumanTargetManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		targetTemplateManager = GameServerAssist.getTargetTemplateManager();
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanTarget.Builder builder : humanEntity.getBuilder()
				.getTargetBuilderList()) {
			targetMap.put(builder.getTarget().getTargetId(), builder
					.getTarget().getRewardState());
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer targetId : targetMap.keySet()) {
			HumanTargetEntity targetEntity = new HumanTargetEntity();
			targetEntity.getBuilder().setHumanGuid(human.getHumanGuid());
			targetEntity.getBuilder().setTarget(
					Target.newBuilder().setTargetId(targetId)
							.setRewardState(targetMap.get(targetId)));
			humanEntity.getBuilder().addTarget(targetEntity.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 获取目标领奖状态
	 */
	public int getRewardState(int targetId) {
		if (targetMap.get(targetId) == null) {
			return TargetRewardState.NOT_REACH.getIndex();
		}
		return targetMap.get(targetId);
	}

	/**
	 * 更新目标领奖状态
	 */
	public void updateRewardState(int targetId, int rewardState) {
		targetMap.put(targetId, rewardState);
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色升级，更新个人目标领奖状态
		if (event.getType() == EventType.LEVEL_UP_EVENT) {
			Map<Integer, TargetTemplate> targetTemplates = targetTemplateManager
					.getTargetTemplates();
			boolean needUpdate = false;
			for (int targetId : targetTemplates.keySet()) {
				TargetTemplate targetTemplate = targetTemplates.get(targetId);
				if (targetTemplate.getTargetType() == TargetType.LEVEL
						.getIndex()
						&& human.getLevel() >= targetTemplate.getTargetParam()
						&& getRewardState(targetTemplate.getId()) == TargetRewardState.NOT_REACH
								.getIndex()) {
					updateRewardState(targetId,
							TargetRewardState.CAN_GET.getIndex());
					HumanTargetEntity targetEntity = new HumanTargetEntity();
					targetEntity.getBuilder()
							.setHumanGuid(human.getHumanGuid());
					targetEntity.getBuilder().setTarget(
							Target.newBuilder().setTargetId(targetId)
									.setRewardState(targetMap.get(targetId)));
					cache.addUpdate(targetId, targetEntity);
					needUpdate = true;
				}
			}
			// 如果有更新，发送消息
			if (needUpdate) {
				GCUpdateTargetPanel msg = new GCUpdateTargetPanel();
				human.sendMessage(msg);
			}
		}
	}

	/**
	 * 是否完成所有个人目标
	 */
	public boolean isAllTargetsCompleted() {
		Map<Integer, TargetTemplate> targetTemplates = targetTemplateManager
				.getTargetTemplates();
		for (Integer targetId : targetTemplates.keySet()) {
			if (getRewardState(targetId) != TargetRewardState.FINISHED
					.getIndex()) {
				return false;
			}
		}
		return true;
	}
}
