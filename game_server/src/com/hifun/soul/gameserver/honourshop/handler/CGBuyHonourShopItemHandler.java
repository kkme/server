package com.hifun.soul.gameserver.honourshop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.honourshop.msg.CGBuyHonourShopItem;
import com.hifun.soul.gameserver.honourshop.msg.GCBuyHonourShopItem;
import com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo;
import com.hifun.soul.gameserver.honourshop.service.HonourShopTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 购买荣誉商店物品请求;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGBuyHonourShopItemHandler implements
		IMessageHandlerWithType<CGBuyHonourShopItem> {

	@Autowired
	private HonourShopTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_HONOUR_SHOP_ITEM;
	}

	@Override
	public void execute(CGBuyHonourShopItem message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.HONOUR_SHOP,
				true)) {
			return;
		}

		// 客户端参数校验
		int itemId = message.getItemId();
		if (itemId < 1) {
			return;
		}
		int num = message.getNum();
		if (num < 1) {
			return;
		}

		// 判断是否是荣誉商店出售物品
		HonourShopItemInfo honourShopItemInfo = templateManager
				.getHonourShopItemInfo(itemId);
		if (honourShopItemInfo == null
				|| !templateManager
						.canSee(honourShopItemInfo, human.getLevel())) {
			return;
		}

		// 判断荣誉是否足够
		int totalHonorNum = honourShopItemInfo.getNeedHonour() * num;
		if (human.getArenaHonor() < totalHonorNum) {
			String honorDesc = GameServerAssist.getSysLangService().read(
					LangConstants.HONOR);
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, honorDesc);
			return;
		}

		// 判断背包是否可以放得下
		if (!human.getBagManager().canContain(itemId, num)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}

		// 扣除荣誉
		human.setArenaHonor(human.getArenaHonor() - totalHonorNum);
		// 记录荣誉变更日志
		GameServerAssist.getLogService().sendHonourLog(human,
				HonourLogReason.HONOR_SHOP_BUY_ITEM, "",
				honourShopItemInfo.getNeedHonour(), human.getArenaHonor());
		// 给物品
		human.getBagManager().putItems(BagType.MAIN_BAG, itemId, num,
				ItemLogReason.HONOUR_SHOP, "");

		GCBuyHonourShopItem gcMsg = new GCBuyHonourShopItem();
		gcMsg.setSuccess(true);
		gcMsg.setLeftHonour(human.getArenaHonor());
		human.sendMessage(gcMsg);
	}
}
