package com.hifun.soul.gamedb.cache.converter;

import java.util.Collection;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.HumanCacheData;
import com.hifun.soul.gamedb.entity.HumanBasePropertiesEntity;
import com.hifun.soul.gamedb.entity.HumanCarriedSkillEntity;
import com.hifun.soul.gamedb.entity.HumanCdEntity;
import com.hifun.soul.gamedb.entity.HumanCostNotifyEntity;
import com.hifun.soul.gamedb.entity.HumanDailyQuestRewardBoxEntity;
import com.hifun.soul.gamedb.entity.HumanEliteStageEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanGiftEntity;
import com.hifun.soul.gamedb.entity.HumanGodsoulEntity;
import com.hifun.soul.gamedb.entity.HumanGuideEntity;
import com.hifun.soul.gamedb.entity.HumanHoroscopeEntity;
import com.hifun.soul.gamedb.entity.HumanItemEntity;
import com.hifun.soul.gamedb.entity.HumanLegionMineBattleRewardEntity;
import com.hifun.soul.gamedb.entity.HumanLegionTaskEntity;
import com.hifun.soul.gamedb.entity.HumanLoginRewardEntity;
import com.hifun.soul.gamedb.entity.HumanMagicPaperEntity;
import com.hifun.soul.gamedb.entity.HumanMainCityEntity;
import com.hifun.soul.gamedb.entity.HumanMarsLoserEntity;
import com.hifun.soul.gamedb.entity.HumanMarsRoomEntity;
import com.hifun.soul.gamedb.entity.HumanMatchBattleEntity;
import com.hifun.soul.gamedb.entity.HumanMeditationEntity;
import com.hifun.soul.gamedb.entity.HumanMineEntity;
import com.hifun.soul.gamedb.entity.HumanNostrumEntity;
import com.hifun.soul.gamedb.entity.HumanOtherPropertiesEntity;
import com.hifun.soul.gamedb.entity.HumanPubSpriteEntity;
import com.hifun.soul.gamedb.entity.HumanQuestDataEntity;
import com.hifun.soul.gamedb.entity.HumanQuestFinishDataEntity;
import com.hifun.soul.gamedb.entity.HumanQuestionEntity;
import com.hifun.soul.gamedb.entity.HumanRefineMapEntity;
import com.hifun.soul.gamedb.entity.HumanRefineStageEntity;
import com.hifun.soul.gamedb.entity.HumanSignEntity;
import com.hifun.soul.gamedb.entity.HumanSingleRechargeRewardEntity;
import com.hifun.soul.gamedb.entity.HumanSkillSlotEntity;
import com.hifun.soul.gamedb.entity.HumanSpecialLoginRewardEntity;
import com.hifun.soul.gamedb.entity.HumanSpecialShopItemEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteBagCellEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteBuffEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteEntity;
import com.hifun.soul.gamedb.entity.HumanSpriteSlotEntity;
import com.hifun.soul.gamedb.entity.HumanStageDramaEntity;
import com.hifun.soul.gamedb.entity.HumanStageMapStateEntity;
import com.hifun.soul.gamedb.entity.HumanStageRewardEntity;
import com.hifun.soul.gamedb.entity.HumanStageStarEntity;
import com.hifun.soul.gamedb.entity.HumanStageStarRewardEntity;
import com.hifun.soul.gamedb.entity.HumanStarMapEntity;
import com.hifun.soul.gamedb.entity.HumanStargazerEntity;
import com.hifun.soul.gamedb.entity.HumanTargetEntity;
import com.hifun.soul.gamedb.entity.HumanTechnologyEntity;
import com.hifun.soul.gamedb.entity.HumanTotalRechargeRewardEntity;
import com.hifun.soul.gamedb.entity.HumanWarriorEntity;
import com.hifun.soul.gamedb.entity.HumanYellowVipRewardStateEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;
import com.hifun.soul.proto.data.entity.Entity.HumanCd;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox;
import com.hifun.soul.proto.data.entity.Entity.HumanGift;
import com.hifun.soul.proto.data.entity.Entity.HumanGodsoul;
import com.hifun.soul.proto.data.entity.Entity.HumanGuide;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionMineBattleReward;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionTask;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanMagicPaper;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsLoser;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsRoom;
import com.hifun.soul.proto.data.entity.Entity.HumanNostrum;
import com.hifun.soul.proto.data.entity.Entity.HumanPubSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage;
import com.hifun.soul.proto.data.entity.Entity.HumanSign;
import com.hifun.soul.proto.data.entity.Entity.HumanSingleRechargeReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSkillSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialShopItem;
import com.hifun.soul.proto.data.entity.Entity.HumanSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBagCell;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBuff;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanStageDrama;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState;
import com.hifun.soul.proto.data.entity.Entity.HumanStageReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStar;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStarReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStarMap;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer;
import com.hifun.soul.proto.data.entity.Entity.HumanTarget;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;
import com.hifun.soul.proto.data.entity.Entity.HumanTotalRechargeReward;

/**
 * 角色缓存和数据实体的转换器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanCacheToEntityConverter implements
		ICacheConverter<HumanCacheData, HumanEntity> {

	@Override
	public HumanEntity convert(HumanCacheData src) {
		return this.converter(src, false);
	}

	@Override
	public HumanCacheData reverseConvert(HumanEntity src) {
		HumanCacheData result = new HumanCacheData();
		result.setHumanGuid(src.getId());

		// 添加基本属性
		HumanBasePropertiesEntity basePropEntity = new HumanBasePropertiesEntity(
				src.getBuilder().getBasePropertiesBuilder());

		result.update(basePropEntity);

		// 添加其它属性
		HumanOtherPropertiesEntity otherPropEntity = new HumanOtherPropertiesEntity(
				src.getBuilder().getOtherPropertiesBuilder());
		result.update(otherPropEntity);

		// 添加任务数据
		for (HumanQuestData.Builder eachQuest : src.getBuilder()
				.getHumanQuestBuilderList()) {
			HumanQuestDataEntity questEntity = new HumanQuestDataEntity(
					eachQuest);
			result.add(questEntity);
		}

		// 设置任务完成数据
		for (HumanQuestFinishData.Builder eachQuest : src.getBuilder()
				.getHumanFinishQuestBuilderList()) {
			HumanQuestFinishDataEntity questEntity = new HumanQuestFinishDataEntity(
					eachQuest);
			result.add(questEntity);
		}

		// 设置日常任务奖励数据
		for (HumanDailyQuestRewardBox.Builder eachBox : src.getBuilder()
				.getDailyRewardBoxBuilderList()) {
			HumanDailyQuestRewardBoxEntity boxEntity = new HumanDailyQuestRewardBoxEntity(
					eachBox);
			result.add(boxEntity);
		}

		// 设置物品数据
		for (HumanItem item : src.getBuilder().getItemList()) {
			HumanItemEntity itemEntity = new HumanItemEntity(item.toBuilder());
			result.add(itemEntity);
		}

		// 星运数据
		for (HumanHoroscope horoscope : src.getBuilder().getHoroscopeList()) {
			HumanHoroscopeEntity horoscopeEntity = new HumanHoroscopeEntity(
					horoscope.toBuilder());
			result.add(horoscopeEntity);
		}

		// 占星师数据
		for (HumanStargazer stargazer : src.getBuilder().getStargazerList()) {
			HumanStargazerEntity stargazerEntity = new HumanStargazerEntity(
					stargazer.toBuilder());
			result.add(stargazerEntity);
		}

		// 新手引导数据
		for (HumanGuide guide : src.getBuilder().getGuideList()) {
			HumanGuideEntity guideEntity = new HumanGuideEntity(
					guide.toBuilder());
			result.add(guideEntity);
		}

		// 设置关卡物品数据
		for (HumanStageReward stageReward : src.getBuilder()
				.getStageRewardList()) {
			HumanStageRewardEntity stageRewardEntity = new HumanStageRewardEntity(
					stageReward.toBuilder());
			result.add(stageRewardEntity);
		}

		// 设置关卡地图数据
		for (HumanStageMapState stageMapState : src.getBuilder()
				.getStageMapStateList()) {
			HumanStageMapStateEntity stageMapRewardEntity = new HumanStageMapStateEntity(
					stageMapState.toBuilder());
			result.add(stageMapRewardEntity);
		}

		// 设置携带技能数据
		for (HumanCarriedSkill skillData : src.getBuilder().getSkillList()) {
			HumanCarriedSkillEntity skillEntity = new HumanCarriedSkillEntity(
					skillData.toBuilder());
			result.add(skillEntity);
		}

		// 设置关卡剧情数据
		for (HumanStageDrama stageDrama : src.getBuilder().getStageDramaList()) {
			HumanStageDramaEntity stageDramaEntity = new HumanStageDramaEntity(
					stageDrama.toBuilder());
			result.add(stageDramaEntity);
		}

		// 设置cd数据
		for (HumanCd cd : src.getBuilder().getCdList()) {
			HumanCdEntity cdEntity = new HumanCdEntity(cd.toBuilder());
			result.add(cdEntity);
		}

		// 设置科技数据
		for (HumanTechnology technology : src.getBuilder().getTechnologyList()) {
			HumanTechnologyEntity technologyEntity = new HumanTechnologyEntity(
					technology.toBuilder());
			result.add(technologyEntity);
		}

		// 设置神魄数据
		for (HumanGodsoul godsoul : src.getBuilder().getGodsoulList()) {
			HumanGodsoulEntity godsoulEntity = new HumanGodsoulEntity(
					godsoul.toBuilder());
			result.add(godsoulEntity);
		}

		// 设置连续登陆奖励
		for (HumanLoginReward humanLoginReward : src.getBuilder()
				.getLoginRewardList()) {
			HumanLoginRewardEntity loginRewardEntity = new HumanLoginRewardEntity(
					humanLoginReward.toBuilder());
			result.add(loginRewardEntity);
		}

		// 设置消费提醒设置数据
		for (HumanCostNotify costNotify : src.getBuilder().getCostNotifyList()) {
			HumanCostNotifyEntity costNotifyEntity = new HumanCostNotifyEntity(
					costNotify.toBuilder());
			result.add(costNotifyEntity);
		}
		// 设置技能栏位数据
		for (HumanSkillSlot slot : src.getBuilder().getSlotList()) {
			HumanSkillSlotEntity slotEntity = new HumanSkillSlotEntity(
					slot.toBuilder());
			result.add(slotEntity);
		}
		// 设置问答数据
		if (src.getQuestionEntity() != null) {
			result.add(src.getQuestionEntity());
		}

		// 设置冥想数据
		HumanMeditationEntity meditationEntity = new HumanMeditationEntity(src
				.getBuilder().getMeditationBuilder());
		meditationEntity.getBuilder().setHumanGuid(src.getId());
		result.update(meditationEntity);

		// 设置精英副本数据
		HumanEliteStageEntity eliteStageEntity = new HumanEliteStageEntity(src
				.getBuilder().getEliteStageInfoBuilder());
		eliteStageEntity.getBuilder().setHumanGuid(src.getId());
		result.update(eliteStageEntity);

		// 设置矿场数据
		HumanMineEntity mineEntity = new HumanMineEntity(src.getBuilder()
				.getMineBuilder());
		mineEntity.getBuilder().setHumanGuid(src.getId());
		result.update(mineEntity);

		// 设置神秘商店数据
		for (HumanSpecialShopItem specialShopItem : src.getBuilder()
				.getSpecialShopItemList()) {
			HumanSpecialShopItemEntity specialShopItemEntity = new HumanSpecialShopItemEntity(
					specialShopItem.toBuilder());
			result.add(specialShopItemEntity);
		}

		// 设置主城相关数据
		HumanMainCityEntity mainCityEntity = new HumanMainCityEntity(src
				.getBuilder().getMainCityInfoBuilder());
		mainCityEntity.getBuilder().setHumanGuid(src.getId());
		result.update(mainCityEntity);

		// 关卡星级
		for (HumanStageStar stageStar : src.getBuilder().getStageStarList()) {
			HumanStageStarEntity stageStarEntity = new HumanStageStarEntity(
					stageStar.toBuilder());
			result.add(stageStarEntity);
		}

		// 关卡评星奖励
		for (HumanStageStarReward stageStarReward : src.getBuilder()
				.getStageStarRewardList()) {
			HumanStageStarRewardEntity stageStarRewardEntity = new HumanStageStarRewardEntity(
					stageStarReward.toBuilder());
			result.add(stageStarRewardEntity);
		}

		// 设置天赋数据
		for (HumanGift gift : src.getBuilder().getGiftList()) {
			HumanGiftEntity buffEntity = new HumanGiftEntity(gift.toBuilder());
			result.add(buffEntity);
		}

		// 试炼地图信息
		for (HumanRefineMap refineMap : src.getBuilder().getRefineMapList()) {
			HumanRefineMapEntity refineMapEntity = new HumanRefineMapEntity(
					refineMap.toBuilder());
			result.add(refineMapEntity);
		}

		// 试炼关卡信息
		for (HumanRefineStage refineStage : src.getBuilder()
				.getRefineStageList()) {
			HumanRefineStageEntity refineStageEntity = new HumanRefineStageEntity(
					refineStage.toBuilder());
			result.add(refineStageEntity);
		}

		// 设置匹配战个人数据
		if (src.getMatchBattleEntity() != null) {
			result.add(src.getMatchBattleEntity());
		}

		// 试炼关卡信息
		for (HumanSpecialLoginReward specialLoginReward : src.getBuilder()
				.getSpecialLoginRewardList()) {
			HumanSpecialLoginRewardEntity specialLoginRewardEntity = new HumanSpecialLoginRewardEntity(
					specialLoginReward.toBuilder());
			result.add(specialLoginRewardEntity);
		}
		// 勇者之路数据
		HumanWarriorEntity warriorEntity = new HumanWarriorEntity(src
				.getBuilder().getWarriorInfoBuilder());
		warriorEntity.getBuilder().setHumanGuid(src.getId());
		result.update(warriorEntity);

		// 黄钻礼包领取状态
		HumanYellowVipRewardStateEntity yellowVipStateEntity = new HumanYellowVipRewardStateEntity(
				src.getBuilder().getYellowVipRewardStateBuilder());
		yellowVipStateEntity.getBuilder().setHumanGuid(src.getId());
		result.update(yellowVipStateEntity);

		// 精灵酒馆精灵数据
		for (HumanPubSprite pubSprite : src.getBuilder().getPubSpiteList()) {
			HumanPubSpriteEntity pubSpriteEntity = new HumanPubSpriteEntity(
					pubSprite.toBuilder());
			result.add(pubSpriteEntity);
		}
		// 精灵数据
		for (HumanSprite sprite : src.getBuilder().getSpriteList()) {
			HumanSpriteEntity spriteEntity = new HumanSpriteEntity(
					sprite.toBuilder());
			result.add(spriteEntity);
		}
		// 精灵背包格子数据
		for (HumanSpriteBagCell cell : src.getBuilder().getCellList()) {
			HumanSpriteBagCellEntity cellEntity = new HumanSpriteBagCellEntity(
					cell.toBuilder());
			result.add(cellEntity);
		}
		// 精灵装备位数据
		for (HumanSpriteSlot slot : src.getBuilder().getEquipSlotList()) {
			HumanSpriteSlotEntity slotEntity = new HumanSpriteSlotEntity(
					slot.toBuilder());
			result.add(slotEntity);
		}
		// 精灵buff数据
		for (HumanSpriteBuff buff : src.getBuilder().getBuffList()) {
			HumanSpriteBuffEntity buffEntity = new HumanSpriteBuffEntity(
					buff.toBuilder());
			result.add(buffEntity);
		}
		// 设置星图数据
		for (HumanStarMap starMap : src.getBuilder().getStarMapList()) {
			HumanStarMapEntity buffEntity = new HumanStarMapEntity(
					starMap.toBuilder());
			result.add(buffEntity);
		}
		// 设置星座数据
		for (HumanSign.Builder sign : src.getBuilder().getSignBuilderList()) {
			HumanSignEntity buffEntity = new HumanSignEntity(sign);
			result.add(buffEntity);
		}
		// 设置充值领奖数据
		for (HumanSingleRechargeReward singleRechargeReward : src.getBuilder().getSingleRechargeRewardList()) {
			HumanSingleRechargeRewardEntity buffEntity = new HumanSingleRechargeRewardEntity(singleRechargeReward.toBuilder());
			result.add(buffEntity);
		}
		// 设置累计充值领奖数据
		for (HumanTotalRechargeReward totalRechargeReward : src.getBuilder().getTotalRechargeRewardList()) {
			HumanTotalRechargeRewardEntity buffEntity = new HumanTotalRechargeRewardEntity(totalRechargeReward.toBuilder());
			result.add(buffEntity);
		}
		// 设置战神之巅房间数据
		for (HumanMarsRoom marsRoom : src.getBuilder().getMarsRoomList()) {
			HumanMarsRoomEntity buffEntity = new HumanMarsRoomEntity(marsRoom.toBuilder());
			result.add(buffEntity);
		}
		// 设置战神之巅手下败将数据
		for (HumanMarsLoser marsLoser : src.getBuilder().getMarsLoserList()) {
			HumanMarsLoserEntity buffEntity = new HumanMarsLoserEntity(marsLoser.toBuilder());
			result.add(buffEntity);
		}
		// 设置个人目标数据
		for (HumanTarget target : src.getBuilder().getTargetList()) {
			HumanTargetEntity buffEntity = new HumanTargetEntity(target.toBuilder());
			result.add(buffEntity);
		}
		// 设置秘药数据
		for (HumanNostrum nostrum : src.getBuilder().getNostrumList()) {
			HumanNostrumEntity buffEntity = new HumanNostrumEntity(nostrum.toBuilder());
			result.add(buffEntity);
		}
		// 设置军团任务数据
		for (HumanLegionTask legionTask : src.getBuilder().getLegionTaskList()) {
			HumanLegionTaskEntity buffEntity = new HumanLegionTaskEntity(legionTask.toBuilder());
			result.add(buffEntity);
		}
		// 设置灵图数据
		for (HumanMagicPaper magicPaper : src.getBuilder().getMagicPaperList()) {
			HumanMagicPaperEntity buffEntity = new HumanMagicPaperEntity(magicPaper.toBuilder());
			result.add(buffEntity);
		}
		// 设置军团矿战战斗奖励数据
		for (HumanLegionMineBattleReward legionMineBattleReward : src.getBuilder().getBattleRewardList()) {
			HumanLegionMineBattleRewardEntity buffEntity = new HumanLegionMineBattleRewardEntity(legionMineBattleReward.toBuilder());
			result.add(buffEntity);
		}

		return result;
	}

	@Override
	public HumanEntity converter(HumanCacheData src, boolean isForUpdate) {
		HumanEntity result = new HumanEntity();
		result.setId(src.getHumanGuid());
		int modifiedCount = 0;
		// 设置基本属性
		HumanBasePropertiesEntity basePropEntity = src
				.getEntity(HumanBasePropertiesEntity.class);
		if (isModifiedEntity(src, basePropEntity) || !isForUpdate) {
			result.getBuilder().setBaseProperties(basePropEntity.getBuilder());
			modifiedCount++;
		}
		// 设置其它属性
		HumanOtherPropertiesEntity otherPropEntity = src
				.getEntity(HumanOtherPropertiesEntity.class);
		if (isModifiedEntity(src, otherPropEntity) || !isForUpdate) {
			result.getBuilder()
					.setOtherProperties(otherPropEntity.getBuilder());
			modifiedCount++;
		}
		// 设置任务数据
		Collection<HumanQuestDataEntity> questEntities = src
				.getEntites(HumanQuestDataEntity.class);
		if (src.isModified(HumanQuestDataEntity.class) || !isForUpdate) {
			for (HumanQuestDataEntity eachQuest : questEntities) {
				// TODO: crazyjohn 这里是否有必要克隆???
				result.getBuilder().addHumanQuest(
						eachQuest.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置任务完成数据
		Collection<HumanQuestFinishDataEntity> questFinishEntities = src
				.getEntites(HumanQuestFinishDataEntity.class);
		if (src.isModified(HumanQuestFinishDataEntity.class) || !isForUpdate) {
			for (HumanQuestFinishDataEntity eachQuest : questFinishEntities) {
				result.getBuilder().addHumanFinishQuest(
						eachQuest.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置日常任务奖励数据
		Collection<HumanDailyQuestRewardBoxEntity> dailyRewardBoxEntities = src
				.getEntites(HumanDailyQuestRewardBoxEntity.class);
		if (src.isModified(HumanDailyQuestRewardBoxEntity.class)
				|| !isForUpdate) {
			for (HumanDailyQuestRewardBoxEntity eachBox : dailyRewardBoxEntities) {
				result.getBuilder().addDailyRewardBox(
						eachBox.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置物品数据
		if (src.isModified(HumanItemEntity.class) || !isForUpdate) {
			for (HumanItemEntity itemEntity : src
					.getEntites(HumanItemEntity.class)) {
				result.getBuilder().addItem(itemEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置星运
		if (src.isModified(HumanHoroscopeEntity.class) || !isForUpdate) {
			for (HumanHoroscopeEntity horoscopeEntity : src
					.getEntites(HumanHoroscopeEntity.class)) {
				result.getBuilder().addHoroscope(
						horoscopeEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置占星师
		if (src.isModified(HumanStargazerEntity.class) || !isForUpdate) {
			for (HumanStargazerEntity stargazerEntity : src
					.getEntites(HumanStargazerEntity.class)) {
				result.getBuilder().addStargazer(
						stargazerEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置新手引导数据
		if (src.isModified(HumanGuideEntity.class) || !isForUpdate) {
			for (HumanGuideEntity guideEntity : src
					.getEntites(HumanGuideEntity.class)) {
				result.getBuilder().addGuide(guideEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置关卡奖励数据
		if (src.isModified(HumanStageRewardEntity.class) || !isForUpdate) {
			for (HumanStageRewardEntity stageRewardEntity : src
					.getEntites(HumanStageRewardEntity.class)) {
				result.getBuilder().addStageReward(
						stageRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置关卡地图数据
		if (src.isModified(HumanStageMapStateEntity.class) || !isForUpdate) {
			for (HumanStageMapStateEntity stageMapStateEntity : src
					.getEntites(HumanStageMapStateEntity.class)) {
				result.getBuilder().addStageMapState(
						stageMapStateEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置技能数据
		if (src.isModified(HumanCarriedSkillEntity.class) || !isForUpdate) {
			for (HumanCarriedSkillEntity skillEntity : src
					.getEntites(HumanCarriedSkillEntity.class)) {
				result.getBuilder().addSkill(skillEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置关卡剧情数据
		if (src.isModified(HumanStageDramaEntity.class) || !isForUpdate) {
			for (HumanStageDramaEntity stageDramaEntity : src
					.getEntites(HumanStageDramaEntity.class)) {
				result.getBuilder().addStageDrama(
						stageDramaEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置cd数据
		if (src.isModified(HumanCdEntity.class) || !isForUpdate) {
			for (HumanCdEntity cdEntity : src.getEntites(HumanCdEntity.class)) {
				result.getBuilder().addCd(cdEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置科技数据
		if (src.isModified(HumanTechnologyEntity.class) || !isForUpdate) {
			for (HumanTechnologyEntity technologyEntity : src
					.getEntites(HumanTechnologyEntity.class)) {
				result.getBuilder().addTechnology(
						technologyEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置神魄数据
		if (src.isModified(HumanGodsoulEntity.class) || !isForUpdate) {
			for (HumanGodsoulEntity godsoulEntity : src
					.getEntites(HumanGodsoulEntity.class)) {
				result.getBuilder().addGodsoul(
						godsoulEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置连续登陆奖励
		if (src.isModified(HumanLoginRewardEntity.class) || !isForUpdate) {
			for (HumanLoginRewardEntity loginRewardEntity : src
					.getEntites(HumanLoginRewardEntity.class)) {
				result.getBuilder().addLoginReward(
						loginRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置消费提醒设置数据
		if (src.isModified(HumanCostNotifyEntity.class) || !isForUpdate) {
			for (HumanCostNotifyEntity costNotifyEntity : src
					.getEntites(HumanCostNotifyEntity.class)) {
				result.getBuilder().addCostNotify(
						costNotifyEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置技能栏位数据
		if (src.isModified(HumanSkillSlotEntity.class) || !isForUpdate) {
			for (HumanSkillSlotEntity slotEntity : src
					.getEntites(HumanSkillSlotEntity.class)) {
				result.getBuilder().addSlot(slotEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置问答数据
		HumanQuestionEntity questionEntity = src
				.getEntity(HumanQuestionEntity.class);
		if (this.isModifiedEntity(src, questionEntity) || !isForUpdate) {
			result.setQuestionEntity(src.getEntity(HumanQuestionEntity.class));
		}
		// 设置冥想数据
		HumanMeditationEntity meditationEntity = src
				.getEntity(HumanMeditationEntity.class);
		if (this.isModifiedEntity(src, meditationEntity) || !isForUpdate) {
			result.getBuilder().setMeditation(
					meditationEntity.getBuilder().clone());
			modifiedCount++;
		}
		// 设置精英副本数据
		HumanEliteStageEntity eliteStageEntity = src
				.getEntity(HumanEliteStageEntity.class);
		if (this.isModifiedEntity(src, eliteStageEntity) || !isForUpdate) {
			result.getBuilder().setEliteStageInfo(
					eliteStageEntity.getBuilder().clone());
			modifiedCount++;
		}
		// 设置矿场数据
		HumanMineEntity mineEntity = src.getEntity(HumanMineEntity.class);
		if (this.isModifiedEntity(src, mineEntity) || !isForUpdate) {
			result.getBuilder().setMine(mineEntity.getBuilder().clone());
			modifiedCount++;
		}

		// 设置神秘商店数据
		if (src.isModified(HumanSpecialShopItemEntity.class) || !isForUpdate) {
			for (HumanSpecialShopItemEntity specialShopEntity : src
					.getEntites(HumanSpecialShopItemEntity.class)) {
				result.getBuilder().addSpecialShopItem(
						specialShopEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置主城相关数据
		HumanMainCityEntity mainCityEntity = src
				.getEntity(HumanMainCityEntity.class);
		if (this.isModifiedEntity(src, mainCityEntity) || !isForUpdate) {
			result.getBuilder().setMainCityInfo(
					mainCityEntity.getBuilder().clone());
			modifiedCount++;
		}

		// 设置评星等级
		if (src.isModified(HumanStageStarEntity.class) || !isForUpdate) {
			for (HumanStageStarEntity stageStarEntity : src
					.getEntites(HumanStageStarEntity.class)) {
				result.getBuilder().addStageStar(
						stageStarEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置关卡评星建立
		if (src.isModified(HumanStageStarRewardEntity.class) || !isForUpdate) {
			for (HumanStageStarRewardEntity stageStarRewardEntity : src
					.getEntites(HumanStageStarRewardEntity.class)) {
				result.getBuilder().addStageStarReward(
						stageStarRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置天赋数据
		if (src.isModified(HumanGiftEntity.class) || !isForUpdate) {
			for (HumanGiftEntity giftEntity : src
					.getEntites(HumanGiftEntity.class)) {
				result.getBuilder().addGift(
						giftEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置试炼地图
		if (src.isModified(HumanRefineMapEntity.class) || !isForUpdate) {
			for (HumanRefineMapEntity refineMapEntity : src
					.getEntites(HumanRefineMapEntity.class)) {
				result.getBuilder().addRefineMap(
						refineMapEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 设置试炼关卡
		if (src.isModified(HumanRefineStageEntity.class) || !isForUpdate) {
			for (HumanRefineStageEntity refineStageEntity : src
					.getEntites(HumanRefineStageEntity.class)) {
				result.getBuilder().addRefineStage(
						refineStageEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		// 匹配战个人数据
		HumanMatchBattleEntity matchBattleEntity = src
				.getEntity(HumanMatchBattleEntity.class);
		if (this.isModifiedEntity(src, matchBattleEntity) || !isForUpdate) {
			result.setMatchBattleEntity(src
					.getEntity(HumanMatchBattleEntity.class));
		}

		// 连续登陆特殊奖励
		if (src.isModified(HumanSpecialLoginRewardEntity.class) || !isForUpdate) {
			for (HumanSpecialLoginRewardEntity specialLoginRewardEntity : src
					.getEntites(HumanSpecialLoginRewardEntity.class)) {
				result.getBuilder().addSpecialLoginReward(
						specialLoginRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置勇者之路数据
		HumanWarriorEntity warriorEntity = src
				.getEntity(HumanWarriorEntity.class);
		if (this.isModifiedEntity(src, warriorEntity) || !isForUpdate) {
			result.getBuilder().setWarriorInfo(
					warriorEntity.getBuilder().clone());
			modifiedCount++;
		}
		// 设置黄钻礼包领取状态数据
		HumanYellowVipRewardStateEntity yellowVipRewardEntity = src
				.getEntity(HumanYellowVipRewardStateEntity.class);
		if (this.isModifiedEntity(src, yellowVipRewardEntity) || !isForUpdate) {
			result.getBuilder().setYellowVipRewardState(
					yellowVipRewardEntity.getBuilder().clone());
			modifiedCount++;
		}

		// 设置酒馆精灵数据
		if (src.isModified(HumanPubSpriteEntity.class) || !isForUpdate) {
			for (HumanPubSpriteEntity pubSpriteEntity : src
					.getEntites(HumanPubSpriteEntity.class)) {
				result.getBuilder().addPubSpite(
						pubSpriteEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置精灵数据
		if (src.isModified(HumanSpriteEntity.class) || !isForUpdate) {
			for (HumanSpriteEntity spriteEntity : src
					.getEntites(HumanSpriteEntity.class)) {
				result.getBuilder()
						.addSprite(spriteEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置精灵背包格子
		if (src.isModified(HumanSpriteBagCellEntity.class) || !isForUpdate) {
			for (HumanSpriteBagCellEntity cellEntity : src
					.getEntites(HumanSpriteBagCellEntity.class)) {
				result.getBuilder().addCell(cellEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置精灵装备位
		if (src.isModified(HumanSpriteSlotEntity.class) || !isForUpdate) {
			for (HumanSpriteSlotEntity slotEntity : src
					.getEntites(HumanSpriteSlotEntity.class)) {
				result.getBuilder().addEquipSlot(
						slotEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置精灵buff
		if (src.isModified(HumanSpriteBuffEntity.class) || !isForUpdate) {
			for (HumanSpriteBuffEntity buffEntity : src
					.getEntites(HumanSpriteBuffEntity.class)) {
				result.getBuilder().addBuff(buffEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置星图
		if (src.isModified(HumanStarMapEntity.class) || !isForUpdate) {
			for (HumanStarMapEntity starMapEntity : src
					.getEntites(HumanStarMapEntity.class)) {
				result.getBuilder().addStarMap(
						starMapEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		// 设置星座
		if (src.isModified(HumanSignEntity.class) || !isForUpdate) {
			for (HumanSignEntity signEntity : src
					.getEntites(HumanSignEntity.class)) {
				result.getBuilder().addSign(
						signEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置充值领奖信息
		if (src.isModified(HumanSingleRechargeRewardEntity.class) || !isForUpdate) {
			for (HumanSingleRechargeRewardEntity singleRechargeRewardEntity : src
					.getEntites(HumanSingleRechargeRewardEntity.class)) {
				result.getBuilder().addSingleRechargeReward(
						singleRechargeRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置累计充值领奖信息
		if (src.isModified(HumanTotalRechargeRewardEntity.class) || !isForUpdate) {
			for (HumanTotalRechargeRewardEntity totalRechargeRewardEntity : src
					.getEntites(HumanTotalRechargeRewardEntity.class)) {
				result.getBuilder().addTotalRechargeReward(
						totalRechargeRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置战神之巅房间信息
		if (src.isModified(HumanMarsRoomEntity.class) || !isForUpdate) {
			for (HumanMarsRoomEntity marsRoomEntity : src
					.getEntites(HumanMarsRoomEntity.class)) {
				result.getBuilder().addMarsRoom(
						marsRoomEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置战神之巅手下败将信息
		if (src.isModified(HumanMarsLoserEntity.class) || !isForUpdate) {
			for (HumanMarsLoserEntity marsLoserEntity : src
					.getEntites(HumanMarsLoserEntity.class)) {
				result.getBuilder().addMarsLoser(
						marsLoserEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置个人目标信息
		if (src.isModified(HumanTargetEntity.class) || !isForUpdate) {
			for (HumanTargetEntity targetEntity : src
					.getEntites(HumanTargetEntity.class)) {
				result.getBuilder().addTarget(
						targetEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置秘药信息
		if (src.isModified(HumanNostrumEntity.class) || !isForUpdate) {
			for (HumanNostrumEntity nostrumEntity : src
					.getEntites(HumanNostrumEntity.class)) {
				result.getBuilder().addNostrum(
						nostrumEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置军团任务信息
		if (src.isModified(HumanLegionTaskEntity.class) || !isForUpdate) {
			for (HumanLegionTaskEntity legionTaskEntity : src
					.getEntites(HumanLegionTaskEntity.class)) {
				result.getBuilder().addLegionTask(
						legionTaskEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置灵图信息
		if (src.isModified(HumanMagicPaperEntity.class) || !isForUpdate) {
			for (HumanMagicPaperEntity magicPaperEntity : src
					.getEntites(HumanMagicPaperEntity.class)) {
				result.getBuilder().addMagicPaper(
						magicPaperEntity.getBuilder().clone());
			}
			modifiedCount++;
		}
		
		// 设置军团矿战战斗奖励信息
		if (src.isModified(HumanLegionMineBattleRewardEntity.class) || !isForUpdate) {
			for (HumanLegionMineBattleRewardEntity legionMineBattleRewardEntity : src
					.getEntites(HumanLegionMineBattleRewardEntity.class)) {
				result.getBuilder().addBattleReward(
						legionMineBattleRewardEntity.getBuilder().clone());
			}
			modifiedCount++;
		}

		if (modifiedCount <= 0) {
			return null;
		}
		return result;
	}

	/**
	 * 实体是否已经修改过了;
	 * 
	 * @param src
	 * @param entity
	 * @return
	 */
	private boolean isModifiedEntity(HumanCacheData src, IEntity entity) {
		if (entity == null) {
			return false;
		}
		return src.isModified(entity.getClass());
	}

}
