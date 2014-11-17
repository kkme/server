package com.hifun.soul.gameserver.honourshop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo;
import com.hifun.soul.gameserver.honourshop.sorter.HonourShopItemSorter;
import com.hifun.soul.gameserver.honourshop.template.HonourShopTemplate;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;

/**
 * 荣誉商城服务类
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class HonourShopTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	@Autowired
	private ItemTemplateManager itemTemplateService;
	private Map<Integer, HonourShopItemInfo> honourShopItemMap = new HashMap<Integer, HonourShopItemInfo>();
	/** 排序 */
	private HonourShopItemSorter honourShopItemSorter = new HonourShopItemSorter();

	@Override
	public void init() {
		Map<Integer, HonourShopTemplate> horoscopeShopItems = templateService
				.getAll(HonourShopTemplate.class);
		initHonourShopItemInfoMap(horoscopeShopItems, itemTemplateService);
	}

	private void initHonourShopItemInfoMap(
			Map<Integer, HonourShopTemplate> horoscopeShopItems,
			ItemTemplateManager itemTemplateService) {
		for (HonourShopTemplate template : horoscopeShopItems.values()) {
			HonourShopItemInfo honourShopItemInfo = new HonourShopItemInfo();
			honourShopItemInfo.setItemId(template.getItemId());
			honourShopItemInfo.setNeedHonour(template.getNeedHonour());
			honourShopItemInfo.setItemType(template.getItemType());
			honourShopItemInfo.setVisibleLevel(template.getVisibleLevel());
			CommonItem commonItem = CommonItemBuilder.genCommonItem(template
					.getItemId());
			if (commonItem == null) {
				throw new NullPointerException("荣誉商店售卖物品配置错误,未找到itemId="
						+ template.getItemId() + "的物品。");
			}
			honourShopItemInfo.setCommonItem(commonItem);
			honourShopItemMap.put(template.getItemId(), honourShopItemInfo);
		}
	}

	public HonourShopItemInfo getHonourShopItemInfo(int itemId) {
		return honourShopItemMap.get(itemId);
	}

	public HonourShopItemSorter getHonourShopItemSorter() {
		return honourShopItemSorter;
	}

	/**
	 * 得到所有荣誉商店需要显示的道具信息
	 * 
	 * @param human
	 * @return
	 */
	public List<HonourShopItemInfo> getHonourShopItemInfos(int level) {
		List<HonourShopItemInfo> canSeeItems = new ArrayList<HonourShopItemInfo>();
		for (HonourShopItemInfo item : honourShopItemMap.values()) {
			if (canSee(item,level)) {
				canSeeItems.add(item);
			}
		}

		return canSeeItems;
	}

	/**
	 * 判断荣誉商店物品的可见性
	 * 
	 * @param honourShopItemInfo
	 * @param honour
	 * @return
	 */
	public boolean canSee(HonourShopItemInfo honourShopItemInfo, int level) {
		if (honourShopItemInfo == null) {
			return false;
		}

		return level >= honourShopItemInfo.getVisibleLevel();
	}

	/**
	 * 判断荣誉商店物品是否可以购买
	 * 
	 * @param honourShopItemInfo
	 * @param honour
	 * @return
	 */
	public boolean canBuy(HonourShopItemInfo honourShopItemInfo, int honour) {
		if (honourShopItemInfo == null) {
			return false;
		}

		return honour >= honourShopItemInfo.getNeedHonour();
	}

	/**
	 * 对目标list进行排序
	 * 
	 * @param displayList
	 */
	public void sortHonourShopItemList(List<HonourShopItemInfo> mallItemList) {
		Collections.sort(mallItemList, getHonourShopItemSorter());
	}
}
