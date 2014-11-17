package com.hifun.soul.gameserver.vip.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.i18n.impl.SysLangServiceImpl;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.recharge.template.RechargeTemplate;
import com.hifun.soul.gameserver.vip.msg.CGShowChargeActivity;
import com.hifun.soul.gameserver.vip.msg.GCShowChargeActivity;

@Component
public class CGShowChargeActivityHandler implements
		IMessageHandlerWithType<CGShowChargeActivity> {
	@Autowired
	private TemplateService templateService;
	@Autowired
	private SysLangServiceImpl sysLangService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_CHARGE_ACTIVITY;
	}

	@Override
	public void execute(CGShowChargeActivity message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		Map<Integer,RechargeTemplate> templates = templateService.getAll(RechargeTemplate.class);
		if(templates != null){
			List<String> activitys = new ArrayList<String>();
			for(RechargeTemplate template : templates.values()){
				String content = sysLangService.read(LangConstants.CHARGE_INFO, template.getRechargeNum(), template.getRewardNum());
				activitys.add(content);
			}
			GCShowChargeActivity gcMsg = new GCShowChargeActivity();
			gcMsg.setActivitys(activitys.toArray(new String[0]));
			human.sendMessage(gcMsg);
		}
	}

}
