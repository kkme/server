package com.hifun.soul.gameserver.skill.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.mall.service.MallService;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.skill.msg.CGResetSkills;

@Component
public class CGResetSkillsHandler implements
		IMessageHandlerWithType<CGResetSkills> {
	
	@Override
	public short getMessageType() {
		return MessageType.CG_RESET_SKILLS;
	}

	@Override
	public void execute(CGResetSkills message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		Item item = human.getBagManager().searchItem(ItemConstantId.RESET_SKILL_POINT_ITEM_ID, BagType.MAIN_BAG);
		if(item!=null){
			boolean useResult = human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1, ItemLogReason.COMSUME_USE, "");
			if(useResult){
				human.getSkillManager().resetSkills();
			}
		}else {
			MallService.sendAskMallItemInfoMessage(human, ItemConstantId.RESET_SKILL_POINT_ITEM_ID);
		}
	}

}
