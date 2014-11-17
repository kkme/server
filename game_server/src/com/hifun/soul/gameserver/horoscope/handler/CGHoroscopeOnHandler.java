package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeOn;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeUpdateAndRemove;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeOnHandler implements
		IMessageHandlerWithType<CGHoroscopeOn> {
	
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_ON;
	}

	@Override
	public void execute(CGHoroscopeOn message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.GAMBLE, true)){
			return;
		}
		
		HumanHoroscopeManager horoscopeManager = human.getHumanHoroscopeManager();
		HoroscopeBagType bagType = HoroscopeBagType.indexOf(message.getBagType());
		if(bagType == null){
			return;
		}
		int index = message.getIndex();
		// 从星运背包中获取需要装备的星运
		HoroscopeInfo horoscopeInfo = horoscopeManager.getHoroscopeInfo(bagType,index);
		if(horoscopeInfo == null){
			return;
		}
		
		// 判断是否有位置
		if(!horoscopeManager.hasEmptyGrid(HoroscopeBagType.EQUIP_BAG)){
			human.sendErrorMessage(LangConstants.HOROSCOPE_SOUL_TYPE_FULL);
			return;
		}
		
		// 判断是否有相同效果的星运已经装备
		if(horoscopeManager.hasSameHoroscope(horoscopeInfo.getKey())){
			human.sendErrorMessage(LangConstants.HOROSCOPE_SAME);
			return;
		}
		
		// 将星运添加到装备背包
		horoscopeInfo = horoscopeManager.addHoroscopeInfo(HoroscopeBagType.EQUIP_BAG,horoscopeInfo,HoroscopeLogReason.TAKEONOFF_HOROSCOPE,"");
		// 删除来源背包星运
		horoscopeManager.removeHoroscopeInfo(bagType,index,HoroscopeLogReason.TAKEONOFF_HOROSCOPE,"");
		
		// 更新面板
		GCHoroscopeUpdateAndRemove gcMsg = new GCHoroscopeUpdateAndRemove();
		gcMsg.setHoroscopeInfo(horoscopeInfo);
		gcMsg.setRemoveBagType(bagType.getIndex());
		gcMsg.setRemoveBagIndex(index);
		human.sendMessage(gcMsg);
		
	}

}
