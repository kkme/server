package com.hifun.soul.gameserver.gem.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gem.GemTemplateManager;
import com.hifun.soul.gameserver.gem.msg.CGSelectGemToSynthesis;
import com.hifun.soul.gameserver.gem.template.GemUpgradeTemplate;
import com.hifun.soul.gameserver.gem.template.GiftChipTemplate;
import com.hifun.soul.gameserver.gem.template.MagicPaperUpgradeTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;

@Component
public class CGSelectGemToSynthesisHandler implements
		IMessageHandlerWithType<CGSelectGemToSynthesis> {
	private static Logger logger = Loggers.ITEM_LOGGER;
	@Autowired
	private GemTemplateManager gemTemplates;

	@Autowired
	// private GameFuncService gameFuncService;
	@Override
	public short getMessageType() {
		return MessageType.CG_SELECT_GEM_TO_SYNTHESIS;
	}

	@Override
	public void execute(CGSelectGemToSynthesis message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.GEM_COMPOUND, true)) {
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		BagType bagType = BagType.indexOf(message.getBagType());
		int index = message.getMainGemBagIndex();
		Item item = bagManager.getItem(bagType, index);
		if (item == null) {
			return;
		}
		if (item.getType() != ItemDetailType.GEM.getIndex()
				&& item.getType() != ItemDetailType.MAGIC_PAPER.getIndex()
				&& item.getType() != ItemDetailType.GIFT_CHIP.getIndex()) {
			logger.info("选择的物品不能用于合成。humanId:" + human.getHumanGuid());
			return;
		}
		if (item.getType() == ItemDetailType.GEM.getIndex()) {
			GemUpgradeTemplate gemTemplate = gemTemplates
					.getGemUpgradeTemplate(item.getItemId());
			if (gemTemplate == null) {
				logger.error("宝石合成模板未找到。humanId:" + human.getHumanGuid()
						+ "; itemId:" + item.getItemId());
				return;
			}
			human.getHumanGemManager().sendGemSynthesisMessage(item);
		} else if (item.getType() == ItemDetailType.MAGIC_PAPER.getIndex()) {
			MagicPaperUpgradeTemplate magicPaperTemplate = gemTemplates
					.getMagicPaperUpgradeTemplate(item.getItemId());
			if (magicPaperTemplate == null) {
				logger.error("灵图合成模板未找到。humanId:" + human.getHumanGuid()
						+ "; itemId:" + item.getItemId());
				return;
			}
			human.getHumanGemManager().sendMagicPaperSynthesisMessage(item);
		} else if (item.getType() == ItemDetailType.GIFT_CHIP.getIndex()) {
			GiftChipTemplate gitChipTemplate = gemTemplates
					.getGiftChipTemplate(item.getItemId());
			if (gitChipTemplate == null) {
				logger.error("天赋碎片合成模板未找到。humanId:" + human.getHumanGuid()
						+ "; itemId:" + item.getItemId());
				return;
			}
			human.getHumanGemManager().sendGiftChipSynthesisMessage(item);
		}

	}

}
