package com.hifun.soul.gameserver.horoscope.handler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeCompoundAuto;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeCompoundAuto;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.horoscope.sorter.HoroscopeInfoSorter;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeCompoundAutoHandler implements
		IMessageHandlerWithType<CGHoroscopeCompoundAuto> {
	private Logger logger = Loggers.HOROSCOPE_LOGGER;
	
	@Autowired
	private HoroscopeTemplateManager horoscopeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_COMPOUND_AUTO;
	}

	@Override
	public void execute(CGHoroscopeCompoundAuto message) {
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
		int i=0;
		while(true){
			if(i >= 1000){
				logger.error("horoscope compound num larger than 1000! ");
				break;
			}
			
			HoroscopeInfo[] allHoroscopeInfos = horoscopeManager.getMainBagHoroscopeInfos();
			Arrays.sort(allHoroscopeInfos,new HoroscopeInfoSorter());
			HoroscopeInfo[] horoscopeInfos = getCanCompoundHoroscopeInfos(allHoroscopeInfos);
			if(horoscopeInfos == null){
				break;
			}
			
			if(!horoscopeManager.horoscopeCompound(horoscopeTemplateManager, horoscopeInfos[1], horoscopeInfos[0], false, true)){
				break;
			}
			
			i++;
		}
		
		// 更新面板
		GCHoroscopeCompoundAuto gcMsg = new GCHoroscopeCompoundAuto();
		gcMsg.setMainBagHoroscopeInfos(horoscopeManager.getMainBagHoroscopeInfos());
		human.sendMessage(gcMsg);
	}
	
	private HoroscopeInfo[] getCanCompoundHoroscopeInfos(HoroscopeInfo[] horoscopeInfos) {
		HoroscopeInfo[] tempHoroscopeInfos = new HoroscopeInfo[2];
		
		int i=0;
		for(int j=0; j<horoscopeInfos.length&&i<2; j++){
			if(horoscopeInfos[j] == null){
				continue;
			}
			if(horoscopeInfos[j].getNextHoroscopeId() > 0 || i>0){
				tempHoroscopeInfos[i] = horoscopeInfos[j];
				i++;
			}
		}
		
		if(i <= 1){
			return null;
		}
		
		return tempHoroscopeInfos;
	}

}
