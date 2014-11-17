package com.hifun.soul.gameserver.mall.service;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.GCAskMallItem;
import com.hifun.soul.gameserver.mall.msg.GCAskMallItemInfo;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;

public class MallService {
	
	/**
	 * 返回购买物品面板消息（只有价格,不带物品信息）
	 * @param human
	 * @param itemId
	 */
	public static void sendAskMallItemMessage(Human human,int itemId){
		MallItemInfo mallItemInfo = GameServerAssist.getMallTemplateManager().getMallItemInfo(itemId);
		if(mallItemInfo == null
				|| !GameServerAssist.getMallTemplateManager().canSee(mallItemInfo.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		
		GCAskMallItem gcMsg = new GCAskMallItem();
		gcMsg.setCurrencyType(mallItemInfo.getCurrencyType());
		gcMsg.setCurrencyNum((int) (mallItemInfo.getNum()*mallItemInfo.getDiscountRate()));
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 返回购买物品面板消息（带物品信息）
	 * @param human
	 * @param itemId
	 */
	public static void sendAskMallItemInfoMessage(Human human,int itemId){
		MallItemInfo mallItemInfo = GameServerAssist.getMallTemplateManager().getMallItemInfo(itemId);
		if(mallItemInfo == null
				|| !GameServerAssist.getMallTemplateManager().canSee(mallItemInfo.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		
		GCAskMallItemInfo gcMsg = new GCAskMallItemInfo();
		gcMsg.setCommonItem(mallItemInfo.getCommonItem());
		gcMsg.setCurrencyType(mallItemInfo.getCurrencyType());
		gcMsg.setCurrencyNum((int) (mallItemInfo.getNum()*mallItemInfo.getDiscountRate()));
		human.sendMessage(gcMsg);
	}
}
