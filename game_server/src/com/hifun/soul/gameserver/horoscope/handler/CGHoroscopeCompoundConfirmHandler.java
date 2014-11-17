package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeCompoundConfirm;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeCompoundConfirmHandler implements
		IMessageHandlerWithType<CGHoroscopeCompoundConfirm> {

	@Autowired
	private HoroscopeTemplateManager horoscopeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_COMPOUND_CONFIRM;
	}

	@Override
	public void execute(CGHoroscopeCompoundConfirm message) {
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
		
		HoroscopeBagType fromBagType = HoroscopeBagType.indexOf(message.getFromBagType());
		int fromBagIndex = message.getFromBagIndex();
		HoroscopeBagType toBagType = HoroscopeBagType.indexOf(message.getToBagType());
		int toBagIndex = message.getToBagIndex();
		// 判断背包类型是否正确
		if(fromBagType == null
				|| fromBagIndex < 0
				|| toBagType == null
				|| toBagIndex < 0){
			return;
		}
		
		// 判断是否是相同位置
		if(fromBagType == toBagType
				&& fromBagIndex == toBagIndex){
			return;
		}
		
		HumanHoroscopeManager manager = human.getHumanHoroscopeManager();
		HoroscopeInfo fromHoroscopeInfo = manager.getHoroscopeInfo(fromBagType, fromBagIndex);
		HoroscopeInfo toHoroscopeInfo = manager.getHoroscopeInfo(toBagType, toBagIndex);
		if(fromHoroscopeInfo == null
				|| toHoroscopeInfo == null){
			return;
		}
		
		// 星运合成
		manager.horoscopeCompound(horoscopeTemplateManager, fromHoroscopeInfo, toHoroscopeInfo, true, false);
	}

}
