package com.hifun.soul.gameserver.shop.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.msg.CGShowShopItemList;
import com.hifun.soul.gameserver.shop.msg.GCShowShopItemList;
import com.hifun.soul.gameserver.shop.service.ShopTemplateManager;

@Component
public class CGShowShopItemListHandler implements IMessageHandlerWithType<CGShowShopItemList> {

	@Autowired
	private ShopTemplateManager shopTemplateService;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_SHOP_ITEM_LIST;
	}

	@Override
	public void execute(CGShowShopItemList message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SHOP, true)){
			return;
		}
		
		// 页面索引
		int pageIndex = message.getPageIndex();
		List<CommonItem> items = shopTemplateService.getAllItems(human.getLevel(), human.getOccupation().getIndex());
		shopTemplateService.sortShopItemList(items);
		int _totalSize = items.size();
		int _startPos = pageIndex * SharedConstants.SHOP_PAGE_SIZE;
		// 开始位置检查
		if (_startPos < 0 || _startPos >= _totalSize) {
			_startPos = _totalSize;
		}
		int _endPos = (pageIndex + 1) * SharedConstants.SHOP_PAGE_SIZE;
		// 结束位置大于当前总数，则置为总数
		if (_endPos > _totalSize) {
			_endPos = _totalSize;
		}
		List<CommonItem> commonItemList = Lists.newArrayList();
		// 获取起始位置之间的商品信息
		for (int i = _startPos; i < _endPos; i++) {
			CommonItem commonItem = items.get(i);
			if (commonItem == null) {
				continue;
			}				
			commonItemList.add(commonItem);			
		}
		
		GCShowShopItemList gcMsg = new GCShowShopItemList();
		gcMsg.setPageIndex((short)pageIndex);
		gcMsg.setPageCount((short)_totalSize);
		gcMsg.setShopItemList(commonItemList.toArray(new CommonItem[0]));
		human.sendMessage(gcMsg);
	}

}
