package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeGamble;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeGambleHandler implements
		IMessageHandlerWithType<CGHoroscopeGamble> {
	@Autowired
	private HoroscopeTemplateManager horoscopeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_GAMBLE;
	}

	@Override
	public void execute(CGHoroscopeGamble message) {
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
		HumanHoroscopeManager manager = human.getHumanHoroscopeManager();
		// 占星
		manager.horoscopeGamble(horoscopeTemplateManager, message.getStargazerId(), true);
	}

}
