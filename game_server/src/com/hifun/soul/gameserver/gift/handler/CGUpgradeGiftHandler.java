package com.hifun.soul.gameserver.gift.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gift.GiftActiveState;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.gift.msg.CGUpgradeGift;
import com.hifun.soul.gameserver.gift.template.GiftLevelTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.template.ItemTemplate;

@Component
public class CGUpgradeGiftHandler implements
		IMessageHandlerWithType<CGUpgradeGift> {

	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_GIFT;
	}

	@Override
	public void execute(CGUpgradeGift message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		HumanGiftManager giftManager = human.getHumanGiftManager();
		// 判断状态是否正确
		int giftId = message.getGiftId();
		if (giftManager.getGiftState(giftId) == GiftActiveState.LOCKED
				.getIndex()) {
			human.sendErrorMessage(LangConstants.GIFT_POINT_NOT_ENOUGH);
			return;
		}
		// 判断是否达到满级
		int maxGiftLevel = GameServerAssist.getGiftTemplateManager()
				.getGiftMaxLevel(giftId);
		if (giftManager.getGiftLevel(giftId) > maxGiftLevel) {
			human.sendErrorMessage(LangConstants.GIFT_TO_MAX_LEVEL);
			return;
		}
		GiftLevelTemplate levelTemplate = GameServerAssist
				.getGiftTemplateManager().getLevelTemplate(giftId,
						giftManager.getGiftLevel(giftId));
		// 判断等级是否足够
		if (human.getLevel() < levelTemplate.getNeedLevel()) {
			human.sendErrorMessage(LangConstants.HUMAN_LEVEL_NOT_ENGOUGH,
					levelTemplate.getNeedLevel());
			return;
		}
		// 判断天赋点是否足够

		if (giftManager.getGiftPointRemain() < levelTemplate.getCostGiftPoint()) {
			human.sendErrorMessage(LangConstants.GIFT_POINT_NOT_ENOUGH);
			return;
		}
		// 判断道具是否足够
		int itemId = levelTemplate.getCostItemId();
		ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager()
				.getItemTemplate(itemId);
		if (itemTemplate == null) {
			return;
		}
		int itemNum = levelTemplate.getCostItemNum();
		if (human.getBagManager().getItemCount(itemId) < itemNum) {
			human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,
					itemTemplate.getName());
			return;
		}
		// 升级天赋
		giftManager.upgradeGift(giftId);
	}
}
