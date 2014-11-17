package com.hifun.soul.gameserver.gem.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.gem.GemTemplateManager;
import com.hifun.soul.gameserver.gem.msg.CGGemSynthesis;
import com.hifun.soul.gameserver.gem.msg.GCGemSynthesis;
import com.hifun.soul.gameserver.gem.template.GemUpgradeTemplate;
import com.hifun.soul.gameserver.gem.template.GiftChipTemplate;
import com.hifun.soul.gameserver.gem.template.MagicPaperUpgradeTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;

/**
 * 宝石合成消息处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGGemSynthesisHandler implements
		IMessageHandlerWithType<CGGemSynthesis> {
	private static Logger logger = Loggers.ITEM_LOGGER;
	@Autowired
	private GemTemplateManager templateManager;

	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_GEM_SYNTHESIS;
	}

	@Override
	public void execute(CGGemSynthesis message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GEM_COMPOUND,
				true)) {
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		if (bagManager.isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		int gemItemIndex = message.getGemItemBagIndex();
		Item mainItem = bagManager.getItem(BagType.MAIN_BAG, gemItemIndex);
		if (mainItem == null
				|| mainItem.getType() != ItemDetailType.GEM.getIndex()
				&& mainItem.getType() != ItemDetailType.MAGIC_PAPER.getIndex()
				&& mainItem.getType() != ItemDetailType.GIFT_CHIP.getIndex()) {
			return;
		}
		// 校验模板
		if (!checkTemplate(human, mainItem)) {
			return;
		}
		// 校验幸运石和守护石
		if (!checkStone(human, message)) {
			return;
		}
		// 消费
		if (!costMoney(human, mainItem)) {
			return;
		}
		// 合成处理
		synthesis(human, mainItem, message);
	}

	/**
	 * 校验模板
	 */
	private boolean checkTemplate(Human human, Item mainItem) {
		int itemId = mainItem.getItemId();
		if (mainItem.getType() == ItemDetailType.GEM.getIndex()) {
			GemUpgradeTemplate gemTemplate = templateManager
					.getGemUpgradeTemplate(itemId);
			// 检查是否已经是最高级
			if (itemId == gemTemplate.getSuccessGemItemId()) {
				human.sendGenericMessage(LangConstants.GEM_ITEM_MAX_GRADE);
				return false;
			}
			int itemCount = human.getBagManager().getItemCount(itemId);
			if (itemCount < gemTemplate.getNeedNum()) {
				// 材料不足
				return false;
			}
			CurrencyType costCurrencyType = CurrencyType.indexOf(gemTemplate
					.getCurrencyType());
			int moneyNum = gemTemplate.getCostNum();
			if (!human.getWallet().isEnough(costCurrencyType, moneyNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
						costCurrencyType.getDesc());
				return false;
			}
		} else if (mainItem.getType() == ItemDetailType.MAGIC_PAPER.getIndex()) {
			MagicPaperUpgradeTemplate magicPaperTemplate = templateManager
					.getMagicPaperUpgradeTemplate(itemId);
			// 检查是否已经是最高级
			if (itemId == magicPaperTemplate.getSuccessMagicPaperItemId()) {
				human.sendGenericMessage(LangConstants.MAGIC_PAPER_ITEM_MAX_GRADE);
				return false;
			}
			int itemCount = human.getBagManager().getItemCount(itemId);
			if (itemCount < magicPaperTemplate.getNeedNum()) {
				// 材料不足
				return false;
			}
			CurrencyType costCurrencyType = CurrencyType
					.indexOf(magicPaperTemplate.getCurrencyType());
			int moneyNum = magicPaperTemplate.getCostNum();
			if (!human.getWallet().isEnough(costCurrencyType, moneyNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
						costCurrencyType.getDesc());
				return false;
			}
		} else if (mainItem.getType() == ItemDetailType.GIFT_CHIP.getIndex()) {
			GiftChipTemplate giftChipTemplate = templateManager
					.getGiftChipTemplate(itemId);
			// 检查是否已经是最高级
			if (itemId == giftChipTemplate.getSuccessGiftChipItemId()) {
				human.sendGenericMessage(LangConstants.GIFT_CHIP_ITEM_MAX_GRADE);
				return false;
			}
			int itemCount = human.getBagManager().getItemCount(itemId);
			if (itemCount < giftChipTemplate.getNeedNum()) {
				// 材料不足
				return false;
			}
			CurrencyType costCurrencyType = CurrencyType
					.indexOf(giftChipTemplate.getCurrencyType());
			int moneyNum = giftChipTemplate.getCostNum();
			if (!human.getWallet().isEnough(costCurrencyType, moneyNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
						costCurrencyType.getDesc());
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验幸运石和守护石
	 */
	private boolean checkStone(Human human, CGGemSynthesis message) {
		int guardStoneId = message.getGuardStoneId();
		int fortuneStoneId = message.getFortuneStoneId();
		Item guardStoneItem = null;
		Item fortuneStoneItem = null;
		if (guardStoneId != -1) {
			int guardStoneCount = human.getBagManager().getItemCount(
					guardStoneId);
			if (guardStoneCount <= 0) {
				human.sendErrorMessage(LangConstants.NO_ENOUGH_GUARD_STONE);
				return false;
			}
			guardStoneItem = human.getBagManager().searchItem(guardStoneId,
					BagType.MAIN_BAG);
			if (guardStoneItem == null) {
				return false;
			}
			if (guardStoneItem.getType() != ItemDetailType.GUARDSTONE
					.getIndex()) {
				logger.error("用于宝石合成的守护石类型不正确。");
				return false;
			}
			if (guardStoneItem.getOverlapNum() < 1) {
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,
						guardStoneItem.getName());
				return false;
			}
			// 使用防降级道具
			human.getBagManager().removeItemByItemId(guardStoneId, 1,
					ItemLogReason.SYNTHESIS_GEM, "");
		}
		if (fortuneStoneId != -1) {
			int fortuneStoneCount = human.getBagManager().getItemCount(
					fortuneStoneId);
			if (fortuneStoneCount <= 0) {
				human.sendErrorMessage(LangConstants.NO_ENOUGH_FORTUNE_STONE);
				return false;
			}
			fortuneStoneItem = human.getBagManager().searchItem(fortuneStoneId,
					BagType.MAIN_BAG);
			if (fortuneStoneItem.getType() != ItemDetailType.FORTUNESTONE
					.getIndex()) {
				logger.error("用于宝石合成的幸运石类型不正确。");
				return false;
			}
			if (fortuneStoneItem.getOverlapNum() < 1) {
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,
						fortuneStoneItem.getName());
				return false;
			}
		}
		return true;
	}

	/**
	 * 消费
	 */
	private boolean costMoney(Human human, Item mainItem) {
		if (mainItem.getType() == ItemDetailType.GEM.getIndex()) {
			GemUpgradeTemplate gemTemplate = templateManager
					.getGemUpgradeTemplate(mainItem.getItemId());
			CurrencyType costCurrencyType = CurrencyType.indexOf(gemTemplate
					.getCurrencyType());
			int moneyNum = gemTemplate.getCostNum();
			if (human.getWallet().costMoney(costCurrencyType, moneyNum,
					MoneyLogReason.GEM_SYNTHESIS, "") == false) {
				return false;
			}
		} else if (mainItem.getType() == ItemDetailType.MAGIC_PAPER.getIndex()) {
			MagicPaperUpgradeTemplate magicPaperTemplate = templateManager
					.getMagicPaperUpgradeTemplate(mainItem.getItemId());
			CurrencyType costCurrencyType = CurrencyType
					.indexOf(magicPaperTemplate.getCurrencyType());
			int moneyNum = magicPaperTemplate.getCostNum();
			if (human.getWallet().costMoney(costCurrencyType, moneyNum,
					MoneyLogReason.MAGIC_PAPER_SYNTHESIS, "") == false) {
				return false;
			}
		} else if (mainItem.getType() == ItemDetailType.GIFT_CHIP.getIndex()) {
			GiftChipTemplate giftChipTemplate = templateManager
					.getGiftChipTemplate(mainItem.getItemId());
			CurrencyType costCurrencyType = CurrencyType
					.indexOf(giftChipTemplate.getCurrencyType());
			int moneyNum = giftChipTemplate.getCostNum();
			if (human.getWallet().costMoney(costCurrencyType, moneyNum,
					MoneyLogReason.GIFT_CHIP_SYNTHESIS, "") == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 道具合成处理
	 */
	private void synthesis(Human human, Item mainItem, CGGemSynthesis message) {
		if (mainItem.getType() == ItemDetailType.GEM.getIndex()) {
			GemUpgradeTemplate gemTemplate = templateManager
					.getGemUpgradeTemplate(mainItem.getItemId());
			float successRate = gemTemplate.getSuccessRate();
			float downgradeRate = gemTemplate.getFaildRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
			Item fortuneStoneItem = human.getBagManager().searchItem(
					message.getFortuneStoneId(), BagType.MAIN_BAG);
			if (fortuneStoneItem != null) {
				// 成功率增加
				ConsumeItemFeature stoneFeature = (ConsumeItemFeature) fortuneStoneItem
						.getFeature();
				successRate += stoneFeature.getExtraSuccessRate();
				// 使用增加成功率道具
				human.getBagManager().removeItemByItemId(
						fortuneStoneItem.getItemId(), 1,
						ItemLogReason.SYNTHESIS_GEM, "");
			}
			int needRemoveNum = gemTemplate.getNeedNum();
			if (MathUtils.shake(successRate
					/ SharedConstants.DEFAULT_FLOAT_BASE)) {
				gemSysthesisSuccess(human, mainItem, needRemoveNum,
						gemTemplate.getSuccessGemItemId());
			} else {
				// 失败
				if (message.getGuardStoneId() != -1) {
					// 使用防降级道具未降级
					gemSysthesisFailed(human, mainItem, needRemoveNum);
				} else if (MathUtils.shake(downgradeRate)) {
					// 降级
					gemSysthesisDegrade(human, mainItem, needRemoveNum,
							gemTemplate.getFaildGemItemId());
				} else {
					// 未降级
					gemSysthesisFailed(human, mainItem, needRemoveNum);
				}
			}
		} else if (mainItem.getType() == ItemDetailType.MAGIC_PAPER.getIndex()) {
			MagicPaperUpgradeTemplate magicPaperTemplate = templateManager
					.getMagicPaperUpgradeTemplate(mainItem.getItemId());
			float successRate = magicPaperTemplate.getSuccessRate();
			float downgradeRate = magicPaperTemplate.getFaildRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
			Item fortuneStoneItem = human.getBagManager().searchItem(
					message.getFortuneStoneId(), BagType.MAIN_BAG);
			if (fortuneStoneItem != null) {
				// 成功率增加
				ConsumeItemFeature stoneFeature = (ConsumeItemFeature) fortuneStoneItem
						.getFeature();
				successRate += stoneFeature.getExtraSuccessRate();
				// 使用增加成功率道具
				human.getBagManager().removeItemByItemId(
						fortuneStoneItem.getItemId(), 1,
						ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
			}
			int needRemoveNum = magicPaperTemplate.getNeedNum();
			if (MathUtils.shake(successRate
					/ SharedConstants.DEFAULT_FLOAT_BASE)) {
				magicPaperSysthesisSuccess(human, mainItem, needRemoveNum,
						magicPaperTemplate.getSuccessMagicPaperItemId());
			} else {
				// 失败
				if (message.getGuardStoneId() != -1) {
					// 使用防降级道具未降级
					magicPaperSysthesisFailed(human, mainItem, needRemoveNum);
				} else if (MathUtils.shake(downgradeRate)) {
					// 降级
					magicPaperSysthesisDegrade(human, mainItem, needRemoveNum,
							magicPaperTemplate.getFaildMagicPaperItemId());
				} else {
					// 未降级
					magicPaperSysthesisFailed(human, mainItem, needRemoveNum);
				}
			}

		} else if (mainItem.getType() == ItemDetailType.GIFT_CHIP.getIndex()) {
			GiftChipTemplate giftChipTemplate = templateManager
					.getGiftChipTemplate(mainItem.getItemId());
			float successRate = giftChipTemplate.getSuccessRate();
			float downgradeRate = giftChipTemplate.getFaildRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
			Item fortuneStoneItem = human.getBagManager().searchItem(
					message.getFortuneStoneId(), BagType.MAIN_BAG);
			if (fortuneStoneItem != null) {
				// 成功率增加
				ConsumeItemFeature stoneFeature = (ConsumeItemFeature) fortuneStoneItem
						.getFeature();
				successRate += stoneFeature.getExtraSuccessRate();
				// 使用增加成功率道具
				human.getBagManager().removeItemByItemId(
						fortuneStoneItem.getItemId(), 1,
						ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
			}
			int needRemoveNum = giftChipTemplate.getNeedNum();
			if (MathUtils.shake(successRate
					/ SharedConstants.DEFAULT_FLOAT_BASE)) {
				giftChipSysthesisSuccess(human, mainItem, needRemoveNum,
						giftChipTemplate.getSuccessGiftChipItemId());
			} else {
				// 失败
				if (message.getGuardStoneId() != -1) {
					// 使用防降级道具未降级
					giftChipSysthesisFailed(human, mainItem, needRemoveNum);
				} else if (MathUtils.shake(downgradeRate)) {
					// 降级
					giftChipSysthesisDegrade(human, mainItem, needRemoveNum,
							giftChipTemplate.getFaildGiftChipItemId());
				} else {
					// 未降级
					giftChipSysthesisFailed(human, mainItem, needRemoveNum);
				}
			}

		}

	}

	/**
	 * 发送宝石合成消息
	 * 
	 * @param human
	 * @param resultCode
	 * @param item
	 */
	private void sendGemSynthesisMessage(Human human, Item item) {
		if (item == null) {
			return;
		}
		// 更新下次升级面板
		human.getHumanGemManager().sendGemSynthesisMessage(item);
	}

	/**
	 * 发送灵图合成消息
	 * 
	 * @param human
	 * @param item
	 */
	private void sendMagicPaperSynthesisMessage(Human human, Item item) {
		if (item == null) {
			return;
		}
		// 更新下次升级面板
		human.getHumanGemManager().sendMagicPaperSynthesisMessage(item);
	}

	/**
	 * 发送天赋碎片合成消息
	 * 
	 * @param human
	 * @param item
	 */
	private void sendGiftChipSynthesisMessage(Human human, Item item) {
		if (item == null) {
			return;
		}
		// 更新下次升级面板
		human.getHumanGemManager().sendGiftChipSynthesisMessage(item);
	}

	private void gemSysthesisSuccess(Human human, Item mainItem,
			int needRemoveNum, int successGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item successGemItem = ItemFactory.creatNewItem(human, successGemItemId);
		if (successGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			human.sendSuccessMessage(LangConstants.GEM_SYNTHESIS_SUCCESS,
					successGemItem.getName());
			// 消耗当前宝石
			bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum,
					ItemLogReason.SYNTHESIS_GEM, "");
			bagManager.putItem(BagType.MAIN_BAG, successGemItem, false,
					ItemLogReason.SYNTHESIS_GEM, "");
			mainItem = bagManager.searchItem(mainItem.getItemId(),
					BagType.MAIN_BAG);
			GCGemSynthesis gcMsg = new GCGemSynthesis();
			CommonItem[] items = new CommonItem[0];
			gcMsg.setItem(items); // 合成成功只通知客户端合成成功结果，不需要合成的物品信息。
			human.sendMessage(gcMsg);
			if (mainItem != null) {
				sendGemSynthesisMessage(human, mainItem);
			} else {
				successGemItem = bagManager.searchItem(
						successGemItem.getItemId(), BagType.MAIN_BAG);
				sendGemSynthesisMessage(human, successGemItem);
			}
		}
	}

	private void gemSysthesisFailed(Human human, Item mainItem,
			int needRemoveNum) {
		HumanBagManager bagManager = human.getBagManager();
		bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum - 1,
				ItemLogReason.SYNTHESIS_GEM, "");
		human.sendGenericMessage(LangConstants.GEM_SYNTHESIS_FAILED,
				mainItem.getName());
		mainItem = bagManager
				.searchItem(mainItem.getItemId(), BagType.MAIN_BAG);
		if (mainItem != null) {
			sendGemSynthesisMessage(human, mainItem);
		}
	}

	private void gemSysthesisDegrade(Human human, Item mainItem,
			int needRemoveNum, int failedGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item failedGemItem = ItemFactory.creatNewItem(human, failedGemItemId);
		if (failedGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			bagManager.removeItemByItemId(mainItem.getItemId(),
					needRemoveNum - 1, ItemLogReason.SYNTHESIS_GEM, "");
			bagManager.putItem(BagType.MAIN_BAG, failedGemItem, false,
					ItemLogReason.SYNTHESIS_GEM, "");
			failedGemItem = bagManager.searchItem(failedGemItem.getItemId(),
					BagType.MAIN_BAG);
			human.sendGenericMessage(LangConstants.GEM_SYNTHESIS_DEGRADE,
					failedGemItem.getName());
			sendGemSynthesisMessage(human, failedGemItem);
		}
	}

	private void magicPaperSysthesisSuccess(Human human, Item mainItem,
			int needRemoveNum, int successGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item successGemItem = ItemFactory.creatNewItem(human, successGemItemId);
		if (successGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			human.sendSuccessMessage(
					LangConstants.MAGIC_PAPER_SYNTHESIS_SUCCESS,
					successGemItem.getName());
			// 消耗当前宝石
			bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum,
					ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
			bagManager.putItem(BagType.MAIN_BAG, successGemItem, false,
					ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
			mainItem = bagManager.searchItem(mainItem.getItemId(),
					BagType.MAIN_BAG);
			GCGemSynthesis gcMsg = new GCGemSynthesis();
			CommonItem[] items = new CommonItem[0];
			gcMsg.setItem(items); // 合成成功只通知客户端合成成功结果，不需要合成的物品信息。
			human.sendMessage(gcMsg);
			if (mainItem != null) {
				sendMagicPaperSynthesisMessage(human, mainItem);
			} else {
				successGemItem = bagManager.searchItem(
						successGemItem.getItemId(), BagType.MAIN_BAG);
				sendMagicPaperSynthesisMessage(human, successGemItem);
			}
		}
	}

	private void magicPaperSysthesisFailed(Human human, Item mainItem,
			int needRemoveNum) {
		HumanBagManager bagManager = human.getBagManager();
		bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum - 1,
				ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
		human.sendGenericMessage(LangConstants.MAGIC_PAPER_SYNTHESIS_FAILED,
				mainItem.getName());
		mainItem = bagManager
				.searchItem(mainItem.getItemId(), BagType.MAIN_BAG);
		if (mainItem != null) {
			sendMagicPaperSynthesisMessage(human, mainItem);
		}
	}

	private void magicPaperSysthesisDegrade(Human human, Item mainItem,
			int needRemoveNum, int failedGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item failedGemItem = ItemFactory.creatNewItem(human, failedGemItemId);
		if (failedGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			bagManager.removeItemByItemId(mainItem.getItemId(),
					needRemoveNum - 1, ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
			bagManager.putItem(BagType.MAIN_BAG, failedGemItem, false,
					ItemLogReason.SYNTHESIS_MAGIC_PAPER, "");
			failedGemItem = bagManager.searchItem(failedGemItem.getItemId(),
					BagType.MAIN_BAG);
			human.sendGenericMessage(
					LangConstants.MAGIC_PAPER_SYNTHESIS_DEGRADE,
					failedGemItem.getName());
			sendMagicPaperSynthesisMessage(human, failedGemItem);
		}
	}

	private void giftChipSysthesisSuccess(Human human, Item mainItem,
			int needRemoveNum, int successGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item successGemItem = ItemFactory.creatNewItem(human, successGemItemId);
		if (successGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			human.sendSuccessMessage(LangConstants.GIFT_CHIP_SYNTHESIS_SUCCESS,
					successGemItem.getName());
			// 消耗当前宝石
			bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum,
					ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
			bagManager.putItem(BagType.MAIN_BAG, successGemItem, false,
					ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
			mainItem = bagManager.searchItem(mainItem.getItemId(),
					BagType.MAIN_BAG);
			GCGemSynthesis gcMsg = new GCGemSynthesis();
			CommonItem[] items = new CommonItem[0];
			gcMsg.setItem(items); // 合成成功只通知客户端合成成功结果，不需要合成的物品信息。
			human.sendMessage(gcMsg);
			if (mainItem != null) {
				sendGiftChipSynthesisMessage(human, mainItem);
			} else {
				successGemItem = bagManager.searchItem(
						successGemItem.getItemId(), BagType.MAIN_BAG);
				sendGiftChipSynthesisMessage(human, successGemItem);
			}
		}
	}

	private void giftChipSysthesisFailed(Human human, Item mainItem,
			int needRemoveNum) {
		HumanBagManager bagManager = human.getBagManager();
		bagManager.removeItemByItemId(mainItem.getItemId(), needRemoveNum - 1,
				ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
		human.sendGenericMessage(LangConstants.GIFT_CHIP_SYNTHESIS_FAILED,
				mainItem.getName());
		mainItem = bagManager
				.searchItem(mainItem.getItemId(), BagType.MAIN_BAG);
		if (mainItem != null) {
			sendMagicPaperSynthesisMessage(human, mainItem);
		}
	}

	private void giftChipSysthesisDegrade(Human human, Item mainItem,
			int needRemoveNum, int failedGemItemId) {
		HumanBagManager bagManager = human.getBagManager();
		Item failedGemItem = ItemFactory.creatNewItem(human, failedGemItemId);
		if (failedGemItem == null) {
			logger.error("创建物品出错。");
		} else {
			bagManager.removeItemByItemId(mainItem.getItemId(),
					needRemoveNum - 1, ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
			bagManager.putItem(BagType.MAIN_BAG, failedGemItem, false,
					ItemLogReason.SYNTHESIS_GIFT_CHIP, "");
			failedGemItem = bagManager.searchItem(failedGemItem.getItemId(),
					BagType.MAIN_BAG);
			human.sendGenericMessage(LangConstants.GIFT_CHIP_SYNTHESIS_DEGRADE,
					failedGemItem.getName());
			sendMagicPaperSynthesisMessage(human, failedGemItem);
		}
	}
}
