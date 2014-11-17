package com.hifun.soul.gameserver.gift.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.gift.GiftType;
import com.hifun.soul.gameserver.gift.model.GiftInfo;
import com.hifun.soul.gameserver.gift.template.GiftLevelTemplate;
import com.hifun.soul.gameserver.gift.template.GiftTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;

@Scope("singleton")
@Component
public class GiftTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, GiftTemplate> giftTemplates = new HashMap<Integer, GiftTemplate>();
	List<GiftTemplate> attactGiftTemplates = new ArrayList<GiftTemplate>();
	List<GiftTemplate> defenceGiftTemplates = new ArrayList<GiftTemplate>();
	List<GiftTemplate> enduranceGiftTemplates = new ArrayList<GiftTemplate>();
	private Map<Integer, List<GiftTemplate>> allGifts = new HashMap<Integer, List<GiftTemplate>>();

	private Map<Integer, GiftLevelTemplate> giftLevelTemplates = new HashMap<Integer, GiftLevelTemplate>();

	@Override
	public void init() {
		giftTemplates = templateService.getAll(GiftTemplate.class);
		for (GiftTemplate template : giftTemplates.values()) {
			if (template.getType() == GiftType.ATTACT_GIFT.getIndex()) {
				attactGiftTemplates.add(template);
			} else if (template.getType() == GiftType.DEFENCE_GIFT.getIndex()) {
				defenceGiftTemplates.add(template);
			} else if (template.getType() == GiftType.ENDURANCE_GIFT.getIndex()) {
				enduranceGiftTemplates.add(template);
			}
		}
		allGifts.put(GiftType.ATTACT_GIFT.getIndex(), attactGiftTemplates);
		allGifts.put(GiftType.DEFENCE_GIFT.getIndex(), defenceGiftTemplates);
		allGifts.put(GiftType.ENDURANCE_GIFT.getIndex(), enduranceGiftTemplates);
		giftLevelTemplates = templateService.getAll(GiftLevelTemplate.class);
	}

	/**
	 * 获取天赋模板
	 * 
	 */
	public Map<Integer, GiftTemplate> getGiftTemplates() {
		return giftTemplates;
	}

	public GiftTemplate getGiftTemplate(int giftId) {
		return giftTemplates.get(giftId);
	}

	/**
	 * 获取所有攻击系天赋信息
	 * 
	 * @return
	 */
	public List<GiftInfo> getAttactGifts(Human human,
			Map<Integer, Integer> giftLevelMap) {
		List<GiftInfo> infoList = new ArrayList<GiftInfo>();
		for (GiftTemplate template : attactGiftTemplates) {
			infoList.add(genGiftInfoByTemplate(human, template,
					giftLevelMap.get(template.getId())));
		}
		return infoList;
	}

	/**
	 * 获取所有防御系天赋信息
	 * 
	 * @return
	 */
	public List<GiftInfo> getDefenceGifts(Human human,
			Map<Integer, Integer> giftLevelMap) {
		List<GiftInfo> infoList = new ArrayList<GiftInfo>();
		for (GiftTemplate template : defenceGiftTemplates) {
			infoList.add(genGiftInfoByTemplate(human, template,
					giftLevelMap.get(template.getId())));
		}
		return infoList;
	}

	/**
	 * 获取所有续航系天赋信息
	 * 
	 * @return
	 */
	public List<GiftInfo> getEnduranceGifts(Human human,
			Map<Integer, Integer> giftLevelMap) {
		List<GiftInfo> infoList = new ArrayList<GiftInfo>();
		for (GiftTemplate template : enduranceGiftTemplates) {
			infoList.add(genGiftInfoByTemplate(human, template,
					giftLevelMap.get(template.getId())));
		}
		return infoList;
	}

	/**
	 * 生成天赋信息
	 * 
	 * @param giftTemplate
	 * @return
	 */
	public GiftInfo genGiftInfoByTemplate(Human human,
			GiftTemplate giftTemplate, Integer giftLevel) {
		if (giftLevel == null) {
			giftLevel = 0;
		}
		for (Integer id : giftLevelTemplates.keySet()) {
			GiftLevelTemplate levelTemplate = giftLevelTemplates.get(id);
			if (levelTemplate.getGiftId() == giftTemplate.getId()
					&& levelTemplate.getGiftLevel() == giftLevel) {
				GiftInfo gift = new GiftInfo();
				gift.setCostGiftPoint(levelTemplate.getCostGiftPoint());
				gift.setActiveState(human.getHumanGiftManager().getGiftState(
						giftTemplate.getId()));
				gift.setIcon(giftTemplate.getIcon());
				gift.setId(giftTemplate.getId());
				gift.setName(giftTemplate.getName());
				gift.setNextGiftId(giftTemplate.getNextGiftId());
				gift.setCurrentPropertyId(levelTemplate.getPropertyId());
				gift.setCurrentPropertyValue(levelTemplate.getPropertyValue());
				gift.setPropertyValueType(levelTemplate.getAmendMethod());
				gift.setDesc(giftTemplate.getDesc());
				gift.setType(giftTemplate.getType());
				gift.setCurrentLevel(giftLevel);
				gift.setMaxLevel(getGiftMaxLevel(giftTemplate.getId()));
				gift.setOpenLevel(giftTemplate.getOpenLevel());
				GiftLevelTemplate nextLevelTemplate = getLevelTemplate(
						giftTemplate.getId(), giftLevel + 1);
				if (nextLevelTemplate != null) {
					gift.setNextPropertyId(nextLevelTemplate.getPropertyId());
					gift.setNextPropertyValue(nextLevelTemplate
							.getPropertyValue());
				}
				CommonItem costItem = CommonItemBuilder
						.genCommonItem(levelTemplate.getCostItemId());
				gift.setCostItem(costItem);
				gift.setCostItemNum(levelTemplate.getCostItemNum());
				gift.setBagItemNum(human.getBagManager().getItemCount(
						levelTemplate.getCostItemId()));
				gift.setUpgradeNeedLevel(levelTemplate.getNeedLevel());
				return gift;
			}
		}
		return null;
	}

	public GiftLevelTemplate getLevelTemplate(int giftId, int giftLevel) {
		for (Integer id : giftLevelTemplates.keySet()) {
			GiftLevelTemplate levelTemplate = giftLevelTemplates.get(id);
			if (levelTemplate.getGiftId() == giftId
					&& levelTemplate.getGiftLevel() == giftLevel) {
				return levelTemplate;
			}
		}
		return null;
	}

	public int getGiftMaxLevel(int giftId) {
		int maxLevel = 0;
		for (Integer id : giftLevelTemplates.keySet()) {
			GiftLevelTemplate levelTemplate = giftLevelTemplates.get(id);
			if (levelTemplate.getGiftId() == giftId) {
				maxLevel++;
			}
		}
		// 由于有0级，所以减1
		return maxLevel - 1;
	}
}
