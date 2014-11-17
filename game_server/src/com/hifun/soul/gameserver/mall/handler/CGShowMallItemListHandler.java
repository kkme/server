package com.hifun.soul.gameserver.mall.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.CGShowMallItemList;
import com.hifun.soul.gameserver.mall.msg.GCShowMallItemList;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;
import com.hifun.soul.gameserver.mall.service.MallTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowMallItemListHandler implements
		IMessageHandlerWithType<CGShowMallItemList> {

	@Autowired
	private MallTemplateManager mallService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MALL_ITEM_LIST;
	}

	@Override
	public void execute(CGShowMallItemList message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MALL, true)) {
			return;
		}
		
		// 页面索引
		int pageIndex = message.getPageIndex();
		if(pageIndex < 0){
			return;
		}
		List<MallItemInfo> allCanSeeList = mallService.getMallItemInfos(message.getMallItemType(), human.getLevel(), human.getOccupation().getIndex());
		if(allCanSeeList == null){
			return;
		}
		mallService.sortMallItemList(allCanSeeList);
		// editby:crazyjohn 2013-12-11这里修改为一次下发所有数据;分页由client去做;
		int _totalSize = allCanSeeList.size();
		int _startPos = pageIndex * SharedConstants.MALL_PAGE_SIZE;
		// 开始位置检查
		if (_startPos < 0 || _startPos >= _totalSize) {
			_startPos = _totalSize;
		}
		int _endPos = (pageIndex + 1) * SharedConstants.MALL_PAGE_SIZE;
		// 结束位置大于当前总数，则置为总数
		if (_endPos > _totalSize) {
			_endPos = _totalSize;
		}
		List<MallItemInfo> mallItemList = Lists.newArrayList();
		// 获取起始位置之间的商品信息
		for (int i = _startPos; i < _endPos; i++) {
			MallItemInfo mallItemInfo = allCanSeeList.get(i);
			if (mallItemInfo == null) {
				continue;
			}				
			mallItemList.add(mallItemInfo);			
		}
		
		GCShowMallItemList gcMsg = new GCShowMallItemList();
		gcMsg.setPageIndex((short)pageIndex);
		gcMsg.setPageCount((short)_totalSize);
		gcMsg.setMallItemType(message.getMallItemType());
		gcMsg.setMallItemList(allCanSeeList.toArray(new MallItemInfo[0]));
		human.sendMessage(gcMsg);
	}

}
