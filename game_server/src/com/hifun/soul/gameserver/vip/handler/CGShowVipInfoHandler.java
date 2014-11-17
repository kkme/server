package com.hifun.soul.gameserver.vip.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.vip.msg.CGShowVipInfo;
import com.hifun.soul.gameserver.vip.msg.GCShowVipInfo;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;

/**
 * 请求显示单个VIP信息处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGShowVipInfoHandler implements IMessageHandlerWithType<CGShowVipInfo> {
	@Autowired
	private VipPrivilegeTemplateManager templateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_VIP_INFO;
	}

	@Override
	public void execute(CGShowVipInfo message) {
		GCShowVipInfo gcMsg = new GCShowVipInfo();
		gcMsg.setDesc(templateManager.getVipTemplate(message.getLevel()).getDesc());
		message.getPlayer().sendMessage(gcMsg);
	}

}
