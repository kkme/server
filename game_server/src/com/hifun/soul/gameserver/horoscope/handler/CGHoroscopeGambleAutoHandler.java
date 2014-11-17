package com.hifun.soul.gameserver.horoscope.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.StargazerType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeGambleAuto;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeGambleAuto;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeGambleAutoHandler implements
		IMessageHandlerWithType<CGHoroscopeGambleAuto> {
	private Logger logger = Loggers.HOROSCOPE_LOGGER;
	@Autowired
	private HoroscopeTemplateManager horoscopeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_GAMBLE_AUTO;
	}

	@Override
	public void execute(CGHoroscopeGambleAuto message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.AUTO_GAMBLE, true)){
			return;
		}
		
		// 自动占星
		HumanHoroscopeManager horoscopeManager = human.getHumanHoroscopeManager();
		int i=0;
		while(horoscopeManager.hasEmptyGrid(HoroscopeBagType.MAIN_BAG)){
			if(i >= 1000){
				logger.error("horoscope gamble num larger than 1000! ");
				break;
			}
			
			StargazerType stargazerType = horoscopeManager.getLastStargazer();
			if(!horoscopeManager.horoscopeGamble(horoscopeTemplateManager, stargazerType.getIndex(),true)){
				break;
			}
			
			i++;
		}
		
		// 更新面板
		GCHoroscopeGambleAuto gcMsg = new GCHoroscopeGambleAuto();
		gcMsg.setStargazerInfos(horoscopeManager.getStargazers(horoscopeTemplateManager));
		gcMsg.setMainBagHoroscopeInfos(horoscopeManager.getMainBagHoroscopeInfos());
		human.sendMessage(gcMsg);
	}

}
