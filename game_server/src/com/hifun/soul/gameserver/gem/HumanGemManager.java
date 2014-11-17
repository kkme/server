package com.hifun.soul.gameserver.gem;

import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gem.msg.GCUpdateGemSynthesisPanel;
import com.hifun.soul.gameserver.gem.template.GemUpgradeTemplate;
import com.hifun.soul.gameserver.gem.template.GiftChipTemplate;
import com.hifun.soul.gameserver.gem.template.MagicPaperUpgradeTemplate;
import com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;

/**
 * 宝石收获管理器
 * 
 * @author magicstone
 * 
 */
public class HumanGemManager {
	private static Logger logger = Loggers.HARVEST_LOGGER;
	protected Human human;

	public HumanGemManager(Human human) {
		this.human = human;
		initTemplate();
	}

	/**
	 * 初始化模板数据
	 */
	public void initTemplate() {
	}

	public void sendGemSynthesisMessage(Item gemItem) {
		if (gemItem == null) {
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		GemUpgradeTemplate template = GameServerAssist.getGemTemplateManager()
				.getGemUpgradeTemplate(gemItem.getItemId());
		List<KeyValuePair<Integer, Integer>> currentGemAttribute = ((MaterialItemFeature) gemItem
				.getFeature()).getGemAttributes();
		KeyValuePair<Integer, Integer>[] upgradeAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		KeyValuePair<Integer, Integer>[] currentAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		boolean isMaxGrade = false;
		Item upgradeGemItem = ItemFactory.creatNewItem(human,
				template.getSuccessGemItemId());
		if (gemItem.getItemId() == template.getSuccessGemItemId()) {
			isMaxGrade = true;
			currentAttribute = KeyValuePair
					.newKeyValuePairArray(currentGemAttribute.size());
			for (int i = 0; i < currentAttribute.length; i++) {
				currentAttribute[i] = new KeyValuePair<Integer, Integer>(
						currentGemAttribute.get(i).getKey(),
						currentGemAttribute.get(i).getValue().intValue());
			}
		} else {
			if (upgradeGemItem == null) {
				logger.error("宝石强化出错，创建宝石失败。humanId:" + human.getHumanGuid()
						+ "; itemId:" + template.getSuccessGemItemId());
				return;
			}
			List<KeyValuePair<Integer, Integer>> upgradeGemAttribute = ((MaterialItemFeature) upgradeGemItem
					.getFeature()).getGemAttributes();
			upgradeAttribute = KeyValuePair
					.newKeyValuePairArray(upgradeGemAttribute.size());
			for (int i = 0; i < upgradeAttribute.length; i++) {
				upgradeAttribute[i] = new KeyValuePair<Integer, Integer>(
						upgradeGemAttribute.get(i).getKey(),
						upgradeGemAttribute.get(i).getValue().intValue());
			}
			currentAttribute = KeyValuePair
					.newKeyValuePairArray(upgradeGemAttribute.size());
			for (int i = 0; i < currentAttribute.length; i++) {
				if (currentGemAttribute.get(i) != null) {
					currentAttribute[i] = new KeyValuePair<Integer, Integer>(
							currentGemAttribute.get(i).getKey(),
							currentGemAttribute.get(i).getValue().intValue());
				} else {
					currentAttribute[i] = new KeyValuePair<Integer, Integer>(
							upgradeGemAttribute.get(i).getKey(), 0);
				}
			}
		}

		CommonItem commonItem = CommonItemBuilder.converToCommonItem(gemItem);
		int needNum = template.getNeedNum();
		int hasNum = bagManager.getItemCount(gemItem.getItemId());

		// 发送消息
		GCUpdateGemSynthesisPanel gcMsg = new GCUpdateGemSynthesisPanel();
		gcMsg.setReachMaxGrade(isMaxGrade);
		gcMsg.setBaseSuccessRate(template.getSuccessRate());
		gcMsg.setCostMoney(template.getCostNum());
		gcMsg.setCurrencyType(template.getCurrencyType());
		gcMsg.setMaterialItem(commonItem);
		gcMsg.setNeedMaterialNum(needNum);
		gcMsg.setHasMaterialNum(hasNum);
		gcMsg.setCurrentyProperties(currentAttribute);
		gcMsg.setUpgradeProperties(upgradeAttribute);
		boolean isCanBuy = false;
		HonourShopItemInfo honourShopItemInfo = GameServerAssist
				.getHonourShopTemplateManager().getHonourShopItemInfo(
						commonItem.getItemId());
		if (GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.HONOUR_SHOP, false)
				&& honourShopItemInfo != null
				&& GameServerAssist.getHonourShopTemplateManager().canSee(
						honourShopItemInfo, human.getLevel())
				&& GameServerAssist.getHonourShopTemplateManager().canBuy(
						honourShopItemInfo, human.getArenaHonor())) {
			isCanBuy = true;
		}
		gcMsg.setIsMallBuy(isCanBuy);
		CommonItem upgradeItem = CommonItemBuilder
				.converToCommonItem(upgradeGemItem);
		gcMsg.setUpgradeItem(upgradeItem);
		human.sendMessage(gcMsg);
	}

	public void sendMagicPaperSynthesisMessage(Item gemItem) {
		if (gemItem == null) {
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		MagicPaperUpgradeTemplate template = GameServerAssist
				.getGemTemplateManager().getMagicPaperUpgradeTemplate(
						gemItem.getItemId());
		KeyValuePair<Integer, Integer>[] upgradeAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		KeyValuePair<Integer, Integer>[] currentAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		boolean isMaxGrade = false;
		if (gemItem.getItemId() == template.getSuccessMagicPaperItemId()) {
			isMaxGrade = true;

		}
		CommonItem commonItem = CommonItemBuilder.converToCommonItem(gemItem);
		int needNum = template.getNeedNum();
		int hasNum = bagManager.getItemCount(gemItem.getItemId());

		// 发送消息
		GCUpdateGemSynthesisPanel gcMsg = new GCUpdateGemSynthesisPanel();
		gcMsg.setReachMaxGrade(isMaxGrade);
		gcMsg.setBaseSuccessRate(template.getSuccessRate());
		gcMsg.setCostMoney(template.getCostNum());
		gcMsg.setCurrencyType(template.getCurrencyType());
		gcMsg.setMaterialItem(commonItem);
		gcMsg.setNeedMaterialNum(needNum);
		gcMsg.setHasMaterialNum(hasNum);
		gcMsg.setCurrentyProperties(currentAttribute);
		gcMsg.setUpgradeProperties(upgradeAttribute);
		boolean isCanBuy = false;
		CommonItem shopItem = GameServerAssist.getShopTemplateManager()
				.getShopItem(gemItem.getItemId());
		if (GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.SHOP, false)
				&& shopItem != null
				&& GameServerAssist.getShopTemplateManager().canSee(shopItem,
						human.getLevel(), human.getOccupation().getIndex())) {
			isCanBuy = true;
		}
		gcMsg.setIsMallBuy(isCanBuy);
		Item upgradeGemItem = ItemFactory.creatNewItem(human,
				template.getSuccessMagicPaperItemId());
		CommonItem upgradeItem = CommonItemBuilder
				.converToCommonItem(upgradeGemItem);
		gcMsg.setUpgradeItem(upgradeItem);
		human.sendMessage(gcMsg);
	}

	public void sendGiftChipSynthesisMessage(Item gemItem) {
		if (gemItem == null) {
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		GiftChipTemplate template = GameServerAssist.getGemTemplateManager()
				.getGiftChipTemplate(gemItem.getItemId());
		KeyValuePair<Integer, Integer>[] upgradeAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		KeyValuePair<Integer, Integer>[] currentAttribute = KeyValuePair
				.newKeyValuePairArray(0);
		boolean isMaxGrade = false;
		if (gemItem.getItemId() == template.getSuccessGiftChipItemId()) {
			isMaxGrade = true;

		}
		CommonItem commonItem = CommonItemBuilder.converToCommonItem(gemItem);
		int needNum = template.getNeedNum();
		int hasNum = bagManager.getItemCount(gemItem.getItemId());

		// 发送消息
		GCUpdateGemSynthesisPanel gcMsg = new GCUpdateGemSynthesisPanel();
		gcMsg.setReachMaxGrade(isMaxGrade);
		gcMsg.setBaseSuccessRate(template.getSuccessRate());
		gcMsg.setCostMoney(template.getCostNum());
		gcMsg.setCurrencyType(template.getCurrencyType());
		gcMsg.setMaterialItem(commonItem);
		gcMsg.setNeedMaterialNum(needNum);
		gcMsg.setHasMaterialNum(hasNum);
		gcMsg.setCurrentyProperties(currentAttribute);
		gcMsg.setUpgradeProperties(upgradeAttribute);
		boolean isCanBuy = false;
		CommonItem shopItem = GameServerAssist.getShopTemplateManager()
				.getShopItem(gemItem.getItemId());
		if (GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.SHOP, false)
				&& shopItem != null
				&& GameServerAssist.getShopTemplateManager().canSee(shopItem,
						human.getLevel(), human.getOccupation().getIndex())) {
			isCanBuy = true;
		}
		gcMsg.setIsMallBuy(isCanBuy);
		Item upgradeGemItem = ItemFactory.creatNewItem(human,
				template.getSuccessGiftChipItemId());
		CommonItem upgradeItem = CommonItemBuilder
				.converToCommonItem(upgradeGemItem);
		gcMsg.setUpgradeItem(upgradeItem);
		human.sendMessage(gcMsg);
	}
}
