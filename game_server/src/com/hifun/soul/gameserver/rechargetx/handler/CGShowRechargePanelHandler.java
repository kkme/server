package com.hifun.soul.gameserver.rechargetx.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rechargetx.msg.CGShowRechargePanel;
import com.hifun.soul.gameserver.rechargetx.msg.GCShowRechargePanel;
import com.hifun.soul.gameserver.rechargetx.service.RechargeTXTemplateManager;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGShowRechargePanelHandler implements
		IMessageHandlerWithType<CGShowRechargePanel> {
	@Autowired
	private VipPrivilegeTemplateManager vipTemplateManager;
	@Autowired
	private RechargeTXTemplateManager rechargeTxTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_RECHARGE_PANEL;
	}

	@Override
	public void execute(CGShowRechargePanel message) {
		Human human = message.getPlayer().getHuman();
		GCShowRechargePanel gcMsg = new GCShowRechargePanel();
		int vipLevel = human.getVipLevel();
		int rechargeNum = human.getRechargeNum();
		int nextVipLevel = vipLevel;
		gcMsg.setVipLevel(vipLevel);
		gcMsg.setRechargeNum(rechargeNum);
		VipPrivilegeTemplate nextLevelTemplate = vipTemplateManager.getVipTemplate(vipLevel+1);
		int nextLevelNeedNum = 0;
		if(nextLevelTemplate != null){
			nextLevelNeedNum = nextLevelTemplate.getRechargeNum()-rechargeNum;
			nextVipLevel = vipLevel+1;
		}
		gcMsg.setNextVipLevel(nextVipLevel);
		gcMsg.setNeedRechargeNum(nextLevelNeedNum);
		gcMsg.setRechargeInfos(rechargeTxTemplateManager.getRechargeInfos());
		gcMsg.setVipInfos(rechargeTxTemplateManager.getVipInfos());
		human.sendMessage(gcMsg);
	}

}
