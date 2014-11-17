package com.hifun.soul.gamedb.cache.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;

import com.hifun.soul.core.orm.HibernateDBServcieImpl;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.logger.Loggers;

/**
 * 玩家数据的差异更新器;
 * <p>
 * FIXME: crazyjohn  差异更新会有问题, 就是当使用CacheEntry的addDelete接口的使用, 如果一个集合中的数据被删除完了, 那么在此处更新器更新的时候无法判断是没有脏数据更新还是, 有脏数据（是一个从指定size变为0的过程）<br>
 * @author crazyjohn
 * 
 */
@Deprecated
public class HumanEntityDiffUpdater implements IEntityUpdater<HumanEntity> {
	private IDBService service;
	private Logger logger = Loggers.DB_MAIN_LOGGER;

	public HumanEntityDiffUpdater(IDBService service) {
		this.service = service;
	}

	@Override
	public boolean update(final HumanEntity entity) {
		// 依次判断entity的对象的属性,如果对象的属性为空,则认为该属性不需要更新
		final List<String> params = new ArrayList<String>();
		final List<Object> values = new ArrayList<Object>();

		// 添加基本属性
		if (entity.getBaseProperties() != null
				&& entity.getBaseProperties().length > 0) {
			params.add("baseProperties");
			values.add(entity.getBaseProperties());
		}

		// 添加其它属性
		if (entity.getOtherProperties() != null
				&& entity.getOtherProperties().length > 0) {
			params.add("otherProperties");
			values.add(entity.getOtherProperties());
		}

		// 添加任务数据
		try {
			if (entity.getHumanQuestData() != null
					&& entity.getHumanQuestData().length > 0) {
				params.add("humanQuestData");
				values.add(entity.getHumanQuestData());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置任务完成数据
		try {
			if (entity.getHumanQuestFinishData() != null
					&& entity.getHumanQuestFinishData().length > 0) {
				params.add("humanQuestFinishData");
				values.add(entity.getHumanQuestFinishData());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置日常任务奖励数据
		try {
			if (entity.getHumanDailyRewardBox() != null
					&& entity.getHumanDailyRewardBox().length > 0) {
				params.add("humanDailyRewardBox");
				values.add(entity.getHumanDailyRewardBox());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置物品数据
		try {
			if (entity.getHumanItems() != null
					&& entity.getHumanItems().length > 0) {
				params.add("humanItems");
				values.add(entity.getHumanItems());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 星运数据
		try {
			if (entity.getHumanHoroscope() != null
					&& entity.getHumanHoroscope().length > 0) {
				params.add("humanHoroscope");
				values.add(entity.getHumanHoroscope());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 占星师数据
		try {
			if (entity.getHumanStargazer() != null
					&& entity.getHumanStargazer().length > 0) {
				params.add("humanStargazer");
				values.add(entity.getHumanStargazer());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 新手引导数据
		try {
			if (entity.getHumanGuide() != null
					&& entity.getHumanGuide().length > 0) {
				params.add("humanGuide");
				values.add(entity.getHumanGuide());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置关卡物品数据
		try {
			if (entity.getHumanStageReward() != null
					&& entity.getHumanStageReward().length > 0) {
				params.add("humanStageReward");
				values.add(entity.getHumanStageReward());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置关卡地图数据
		try {
			if (entity.getHumanStageMapState() != null
					&& entity.getHumanStageMapState().length > 0) {
				params.add("humanStageMapState");
				values.add(entity.getHumanStageMapState());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置携带技能数据
		try {
			if (entity.getHumanCarriedSkills() != null
					&& entity.getHumanCarriedSkills().length > 0) {
				params.add("humanCarriedSkills");
				values.add(entity.getHumanCarriedSkills());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置关卡剧情数据
		try {
			if (entity.getHumanStageDrama() != null
					&& entity.getHumanStageDrama().length > 0) {
				params.add("humanStageDrama");
				values.add(entity.getHumanStageDrama());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置cd数据
		try {
			if (entity.getHumanCd() != null && entity.getHumanCd().length > 0) {
				params.add("humanCd");
				values.add(entity.getHumanCd());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置科技数据
		try {
			if (entity.getHumanTechnology() != null
					&& entity.getHumanTechnology().length > 0) {
				params.add("humanTechnology");
				values.add(entity.getHumanTechnology());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置神魄数据
		try {
			if (entity.getHumanGodsoul() != null
					&& entity.getHumanGodsoul().length > 0) {
				params.add("humanGodsoul");
				values.add(entity.getHumanGodsoul());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置连续登陆奖励
		try {
			if (entity.getLoginRewards() != null
					&& entity.getLoginRewards().length > 0) {
				params.add("loginRewards");
				values.add(entity.getLoginRewards());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置消费提醒设置数据
		try {
			if (entity.getCostNotifys() != null
					&& entity.getCostNotifys().length > 0) {
				params.add("costNotifys");
				values.add(entity.getCostNotifys());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置技能栏位数据
		try {
			if (entity.getHumanSkillSlots() != null
					&& entity.getHumanSkillSlots().length > 0) {
				params.add("humanSkillSlots");
				values.add(entity.getHumanSkillSlots());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 不用设置问答数据

		// 设置冥想数据
		if (entity.getMeditation() != null && entity.getMeditation().length > 0) {
			params.add("meditation");
			values.add(entity.getMeditation());
		}

		// 设置精英副本数据
		if (entity.getEliteStageInfo() != null
				&& entity.getEliteStageInfo().length > 0) {
			params.add("eliteStageInfo");
			values.add(entity.getEliteStageInfo());
		}

		// 设置矿场数据
		if (entity.getMine() != null && entity.getMine().length > 0) {
			params.add("mine");
			values.add(entity.getMine());
		}

		// 设置神秘商店数据
		try {
			if (entity.getSpecialShopItems() != null
					&& entity.getSpecialShopItems().length > 0) {
				params.add("specialShopItems");
				values.add(entity.getSpecialShopItems());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置主城相关数据
		if (entity.getMainCityInfo() != null
				&& entity.getMainCityInfo().length > 0) {
			params.add("mainCityInfo");
			values.add(entity.getMainCityInfo());
		}

		// 关卡星级
		try {
			if (entity.getStageStars() != null
					&& entity.getStageStars().length > 0) {
				params.add("stageStars");
				values.add(entity.getStageStars());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 关卡评星奖励
		try {
			if (entity.getStageStarRewards() != null
					&& entity.getStageStarRewards().length > 0) {
				params.add("stageStarRewards");
				values.add(entity.getStageStarRewards());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 试炼地图
		try {
			if (entity.getRefineMaps() != null
					&& entity.getRefineMaps().length > 0) {
				params.add("refineMaps");
				values.add(entity.getRefineMaps());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 试炼关卡
		try {
			if (entity.getRefineStages() != null
					&& entity.getRefineStages().length > 0) {
				params.add("refineStages");
				values.add(entity.getRefineStages());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 天赋
		try {
			if (entity.getHumanGift() != null
					&& entity.getHumanGift().length > 0) {
				params.add("humanGift");
				values.add(entity.getHumanGift());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 连续登陆特殊奖励
		try {
			if (entity.getSpecialLoginRewards() != null
					&& entity.getSpecialLoginRewards().length > 0) {
				params.add("specialLoginRewards");
				values.add(entity.getSpecialLoginRewards());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 勇者之路
		if (entity.getWarriorInfo() != null
				&& entity.getWarriorInfo().length > 0) {
			params.add("warriorInfo");
			values.add(entity.getWarriorInfo());
		}
		// 黄钻礼包领取状态
		if (entity.getYellowVipRewardState() != null
				&& entity.getYellowVipRewardState().length > 0) {
			params.add("yellowVipRewardState");
			values.add(entity.getYellowVipRewardState());
		}
		// 精灵酒馆精灵数据
		try {
			if (entity.getHumanPubSprite() != null
					&& entity.getHumanPubSprite().length > 0) {
				params.add("humanPubSprite");
				values.add(entity.getHumanPubSprite());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置精灵数据
		try {
			if (entity.getHumanSprite() != null
					&& entity.getHumanSprite().length > 0) {
				params.add("humanSprite");
				values.add(entity.getHumanSprite());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置精灵背包格子
		try {
			if (entity.getHumanSpriteBagCell() != null
					&& entity.getHumanSpriteBagCell().length > 0) {
				params.add("humanSpriteBagCell");
				values.add(entity.getHumanSpriteBagCell());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置精灵装备位
		try {
			if (entity.getHumanSpriteSlot() != null
					&& entity.getHumanSpriteSlot().length > 0) {
				params.add("humanSpriteSlot");
				values.add(entity.getHumanSpriteSlot());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置精灵buff
		try {
			if (entity.getHumanSpriteBuff() != null
					&& entity.getHumanSpriteBuff().length > 0) {
				params.add("humanSpriteBuff");
				values.add(entity.getHumanSpriteBuff());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置星图
		try {
			if (entity.getHumanStarMap() != null
					&& entity.getHumanStarMap().length > 0) {
				params.add("humanStarMap");
				values.add(entity.getHumanStarMap());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		// 设置星座
		try {
			if (entity.getHumanSign() != null
					&& entity.getHumanSign().length > 0) {
				params.add("humanSign");
				values.add(entity.getHumanSign());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}

		// 设置角色直充领奖数据
		try {
			if (entity.getHumanSingleRechargeReward() != null
					&& entity.getHumanSingleRechargeReward().length > 0) {
				params.add("humanSingleRechargeReward");
				values.add(entity.getHumanSingleRechargeReward());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置角色累充领奖数据
		try {
			if (entity.getHumanTotalRechargeReward() != null
					&& entity.getHumanTotalRechargeReward().length > 0) {
				params.add("humanTotalRechargeReward");
				values.add(entity.getHumanTotalRechargeReward());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置角色战神之巅房间数据
		try {
			if (entity.getHumanMarsRoom() != null
					&& entity.getHumanMarsRoom().length > 0) {
				params.add("humanMarsRoom");
				values.add(entity.getHumanMarsRoom());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置角色战神之巅手下败将数据
		try {
			if (entity.getHumanMarsLoser() != null
					&& entity.getHumanMarsLoser().length > 0) {
				params.add("humanMarsLoser");
				values.add(entity.getHumanMarsLoser());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置角色个人目标数据
		try {
			if (entity.getHumanTarget() != null
					&& entity.getHumanTarget().length > 0) {
				params.add("humanTarget");
				values.add(entity.getHumanTarget());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置角色秘药数据
		try {
			if (entity.getHumanNostrum() != null
					&& entity.getHumanNostrum().length > 0) {
				params.add("humanNostrum");
				values.add(entity.getHumanNostrum());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置军团任务数据
		try {
			if (entity.getHumanLegionTask() != null
					&& entity.getHumanLegionTask().length > 0) {
				params.add("humanLegionTask");
				values.add(entity.getHumanLegionTask());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置灵图数据
		try {
			if (entity.getHumanMagicPaper() != null
					&& entity.getHumanMagicPaper().length > 0) {
				params.add("humanMagicPaper");
				values.add(entity.getHumanMagicPaper());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 设置军团矿战战斗奖励数据
		try {
			if (entity.getHumanLegionMineBattleReward() != null
					&& entity.getHumanLegionMineBattleReward().length > 0) {
				params.add("humanLegionMineBattleReward");
				values.add(entity.getHumanLegionMineBattleReward());
			}
		} catch (IOException e) {
			logger.error("Pase humanEntity error", e);
		}
		
		// 构建sql脚本
		if (!params.isEmpty()) {
			final StringBuilder sql = new StringBuilder();
			sql.append("update " + entity.getClass().getSimpleName() + " set ");
			for (int i = 0; i < params.size(); i++) {
				sql.append(" " + params.get(i) + "= ?");
				if (i < (params.size() - 1)) {
					sql.append(",");
				}
			}
			sql.append(" where id = ?");
			if (logger.isDebugEnabled()) {
				logger.debug("Update Sql:" + sql);
			}
			if (service instanceof HibernateDBServcieImpl) {
				HibernateDBServcieImpl hibernateService = (HibernateDBServcieImpl) service;
				hibernateService
						.call(new HibernateDBServcieImpl.HibernateCallback<Void>() {
							@Override
							public Void doCall(Session session) {
								org.hibernate.Query _query = session
										.createQuery(sql.toString());
								int i = 0;
								for (; i < values.size(); i++) {
									_query.setParameter(i, values.get(i));
								}
								_query.setParameter(i, entity.getId());
								_query.executeUpdate();
								return null;
							}
						});
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

}
