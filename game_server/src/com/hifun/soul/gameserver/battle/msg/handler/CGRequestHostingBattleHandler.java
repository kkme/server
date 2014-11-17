package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.msg.CGRequestHostingBattle;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.mall.service.MallService;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 玩家请求托管战斗;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGRequestHostingBattleHandler implements
		IMessageHandlerWithType<CGRequestHostingBattle> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REQUEST_HOSTING_BATTLE;
	}

	@Override
	public void execute(CGRequestHostingBattle message) {
		// 检查是否在战斗状态
		Human human = message.getPlayer().getHuman();
		if (message.getPlayer().getState() != PlayerState.BATTLING) {
			return;
		}
		// 战斗上下文不为空
		Battle battle = human.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		//是否有道具判断
		Item item = human.getBagManager().searchItem(ItemConstantId.BATTLE_ROBOT_ID, BagType.MAIN_BAG);
		if (item != null) {
			boolean useResult = human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1,
					ItemLogReason.COMSUME_USE, "");
			if (useResult) {
				// 托管的处理逻辑
				battle.unitRequestHostingBattle(human);
			}
		} else {
			MallService.sendAskMallItemInfoMessage(human, ItemConstantId.BATTLE_ROBOT_ID);
		}
		
	}

}
