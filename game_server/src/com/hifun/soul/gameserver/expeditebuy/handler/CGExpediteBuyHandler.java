package com.hifun.soul.gameserver.expeditebuy.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.expeditebuy.msg.CGExpediteBuy;
import com.hifun.soul.gameserver.expeditebuy.msg.GCExpediteBuy;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;
import com.hifun.soul.gameserver.mall.service.MallTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGExpediteBuyHandler implements
		IMessageHandlerWithType<CGExpediteBuy> {
	
	@Autowired
	private MallTemplateManager mallTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_EXPEDITE_BUY;
	}

	@Override
	public void execute(CGExpediteBuy message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		int itemId = message.getItemId();
		
		MallItemInfo mallItemInfo = mallTemplateManager.getMallItemInfo(itemId);
		if(mallItemInfo == null
				|| !mallTemplateManager.canSee(mallItemInfo.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		
		GCExpediteBuy gcMsg = new GCExpediteBuy();
		gcMsg.setItemId(itemId);
		gcMsg.setName(mallItemInfo.getCommonItem().getName());
		gcMsg.setDesc(mallItemInfo.getCommonItem().getDesc());
		gcMsg.setIcon(mallItemInfo.getCommonItem().getIcon());
		gcMsg.setPrice(mallItemInfo.getNum());
		gcMsg.setMaxOverlap(mallItemInfo.getCommonItem().getMaxOverlap());
		human.sendMessage(gcMsg);
	}

}
