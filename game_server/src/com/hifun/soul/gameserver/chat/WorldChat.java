package com.hifun.soul.gameserver.chat;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.player.Player;


/**
 * 全服聊天
 * @author magicstone
 */
public class WorldChat extends AbstractChatStrategy {

	@Override
	protected int getChatInterval() {
		return GameServerAssist.getGameConstants().getWorldChatInterval();
	}

	@Override
	protected int getChatLength() {
		return GameServerAssist.getGameConstants().getWorldChatLength();
	}

	@Override
	protected Player[] getDestPlayers(long passportId) {
		return GameServerAssist.getGameWorld().getAllPlayers().toArray(new Player[0]);
	}

	@Override
	protected ChatType getChatType() {
		return ChatType.WORLD;
	}
	
	@Override
	protected boolean canChat(Human human) {
		// 判断是否有聊天道具
		if(human.getBagManager().getItemCount(ItemConstantId.HORN_ID) <= 0){
			human.sendGenericMessage(LangConstants.NO_HORN);
			return false;
		}
		
		if(human.getBagManager().removeItemByItemId(ItemConstantId.HORN_ID, 1, ItemLogReason.CHAT_COST, "")){
			return true;
		}
		else{
			return false;
		}
	}

}
