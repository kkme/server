package com.hifun.soul.gameserver.horoscope.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeIntroduce;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeIntroduce;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeIntroduceTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeIntroduceHandler implements
		IMessageHandlerWithType<CGHoroscopeIntroduce> {

	@Autowired
	private TemplateService templateService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_INTRODUCE;
	}

	@Override
	public void execute(CGHoroscopeIntroduce message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		List<HoroscopeInfo> horoscopeInfos = new ArrayList<HoroscopeInfo>();
		Map<Integer,HoroscopeIntroduceTemplate> templates = templateService.getAll(HoroscopeIntroduceTemplate.class);
		for(HoroscopeIntroduceTemplate template : templates.values()){
			HoroscopeInfo horoscopeInfo = new HoroscopeInfo();
			horoscopeInfo.setHoroscopeId(template.getId());
			horoscopeInfo.setName(template.getName());
			horoscopeInfo.setDesc(template.getDesc());
			horoscopeInfo.setColor(template.getColor());
			
			horoscopeInfos.add(horoscopeInfo);
		}
		
		GCHoroscopeIntroduce gcMsg = new GCHoroscopeIntroduce();
		gcMsg.setHoroscopeInfo(horoscopeInfos.toArray(new HoroscopeInfo[0]));
		human.sendMessage(gcMsg);
	}

}
