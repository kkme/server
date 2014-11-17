package com.hifun.soul.gameserver.item.handler;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.text.LinkType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.HumanInfoHelper;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.msg.CGShowSomething;
import com.hifun.soul.gameserver.item.msg.GCShowItem;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;

@Component
public class CGShowSomethingHandler implements IMessageHandlerWithType<CGShowSomething> {
	private static Logger logger = Loggers.CHAT_LOGGER;
	@Override
	public short getMessageType() {		
		return MessageType.CG_SHOW_SOMETHING;
	}

	@Override
	public void execute(CGShowSomething message) {
		Human human = message.getPlayer().getHuman();
		int linkType= message.getLinkType();
		if(LinkType.CHARACTER.getIndex() == linkType){
			HumanInfoHelper.sendHumanBaseInfoMessage(human,Long.parseLong(message.getKey(), 16));
		}else if(LinkType.COMMON_ITEM.getIndex() == linkType){
			int itemId = Integer.parseInt(message.getKey());
			sendItemMessage(human,ItemFactory.creatNewItem(human, itemId));
		}else if(LinkType.SPECIAL_ITEM.getIndex() == linkType){
			sendSpecialItemMessage(human,message.getHumanGuid(),message.getKey());
		}
	}
	

	private void sendItemMessage(Human human,Item item){
		CommonItem commonItem = CommonItemBuilder.converToCommonItem(item);
		if(commonItem==null){
			return;
		}
		GCShowItem gcMsg = new GCShowItem();
		gcMsg.setItem(commonItem);
		human.sendMessage(gcMsg);
	}
	
	
	/**
	 * 下发特殊物品消息
	 * @param human
	 * @param itemUuid
	 */
	private void sendSpecialItemMessage(final Human human, long humanGuid, final String itemUuid) {
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_ITEMS_BY_HUMAN_ID, new String[] { "humanGuid" },
				new Object[] { humanGuid }, new IDBCallback<List<?>>() {
			
					@Override
					public void onSucceed(List<?> result) {
						if (result == null || result.isEmpty()) {
							return;
						}
						for(int i=0;i<result.size();i++){
							HumanItem humanItem = (HumanItem)result.get(i);
							if(humanItem.getItem().getUuid().equals(itemUuid)){
								sendItemMessage(human,ItemFactory.convertHumanItemToItem(human, humanItem));								
								break;
							}
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error("qury item info failed ! errorMsg:", errorMsg);
					}

				});
	}

}
