package com.hifun.soul.gameserver.horoscope.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.model.human.CharacterInfo;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGShowHoroscopePanel;
import com.hifun.soul.gameserver.horoscope.msg.GCShowHoroscopePanel;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowHoroscopePanelHandler implements
		IMessageHandlerWithType<CGShowHoroscopePanel> {

	@Autowired
	private HoroscopeTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_HOROSCOPE_PANEL;
	}

	@Override
	public void execute(CGShowHoroscopePanel message) {
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
		// 打开星运面板
		GCShowHoroscopePanel gcMsg = new GCShowHoroscopePanel();
		
		CharacterInfo charInfo = new CharacterInfo();
		charInfo.setGuid(human.getHumanGuid());
		charInfo.setLevel(human.getLevel());
		charInfo.setName(human.getName());
		charInfo.setOccupation(human.getOccupation().getIndex());
		
		gcMsg.setCharacterInfo(charInfo);
		gcMsg.setMainBagHoroscopeInfos(manager.getMainBagHoroscopeInfos());
		gcMsg.setStorageBagHoroscopeInfos(manager.getStorageBagHoroscopeInfos());
		gcMsg.setEquipBagHoroscopeInfos(manager.getEquipBagHoroscopeInfos());
		gcMsg.setStargazerInfos(manager.getStargazers(templateManager));
		gcMsg.setHoroscopeSoulInfos(templateManager.getHoroscopeSoulInfos(human.getLevel()));
		gcMsg.setFreeTimes(manager.getHoroscopeGambleTime());
		gcMsg.setOpenSize(manager.getHoroscopeStorageSize());
		human.sendMessage(gcMsg);
		
		human.getHumanGuideManager().showGuide(GuideType.OPEN_HOROSCOPE_PANEL.getIndex());
	}

}
