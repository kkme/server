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
import com.hifun.soul.gameserver.horoscope.HoroscopeSoulType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeOff;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeUpdateAndRemove;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeOffHandler implements
	IMessageHandlerWithType<CGHoroscopeOff> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_OFF;
	}

	@Override
	public void execute(CGHoroscopeOff message) {
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
		HoroscopeSoulType horoscopeSoulType = HoroscopeSoulType.indexOf(message.getIndex());
		if(horoscopeSoulType == null){
			return;
		}
		
		HoroscopeInfo horoscopeInfo = 
				horoscopeManager.getHoroscopeInfo(HoroscopeBagType.EQUIP_BAG,horoscopeSoulType.getIndex());
		if(horoscopeInfo == null){
			return;
		}
		
		// 判断主背包是否已满
		if(!horoscopeManager.hasEmptyGrid(HoroscopeBagType.MAIN_BAG)){
			human.sendErrorMessage(LangConstants.HOROSCOPE_MAINBAG_FULL);
			return;
		}
		
		horoscopeInfo = horoscopeManager.addHoroscopeInfo(
				HoroscopeBagType.MAIN_BAG,horoscopeInfo,HoroscopeLogReason.TAKEONOFF_HOROSCOPE,"");
		horoscopeManager.removeHoroscopeInfo(
				HoroscopeBagType.EQUIP_BAG,horoscopeSoulType.getIndex(),HoroscopeLogReason.TAKEONOFF_HOROSCOPE,"");
		
		// 更新面板
		GCHoroscopeUpdateAndRemove gcMsg = new GCHoroscopeUpdateAndRemove();
		gcMsg.setHoroscopeInfo(horoscopeInfo);
		gcMsg.setRemoveBagType(HoroscopeBagType.EQUIP_BAG.getIndex());
		gcMsg.setRemoveBagIndex(horoscopeSoulType.getIndex());
		human.sendMessage(gcMsg);
		
	}

}
