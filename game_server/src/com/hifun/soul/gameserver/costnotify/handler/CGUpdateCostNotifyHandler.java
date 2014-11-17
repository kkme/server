package com.hifun.soul.gameserver.costnotify.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.costnotify.CostNotifyInfo;
import com.hifun.soul.gameserver.costnotify.CostNotifyType;
import com.hifun.soul.gameserver.costnotify.manager.HumanCostNotifyManager;
import com.hifun.soul.gameserver.costnotify.msg.CGUpdateCostNotify;
import com.hifun.soul.gameserver.costnotify.msg.GCUpdateCostNotify;
import com.hifun.soul.gameserver.costnotify.template.CostNotifyTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGUpdateCostNotifyHandler implements
		IMessageHandlerWithType<CGUpdateCostNotify> {

	@Autowired
	private TemplateService templateService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_UPDATE_COST_NOTIFY;
	}

	@Override
	public void execute(CGUpdateCostNotify message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		CostNotifyInfo[] costNotifyInfos = message.getCostNotifyInfos();
		if(costNotifyInfos == null
				|| costNotifyInfos.length == 0){
			return;
		}
		
		List<CostNotifyInfo> costNotifyInfoList = new ArrayList<CostNotifyInfo>();
		HumanCostNotifyManager costNotifyManager = human.getHumanCostNotifyManager();
		for(CostNotifyInfo costNotifyInfo : costNotifyInfos){
			if(costNotifyInfo != null
					&& CostNotifyType.indexOf(costNotifyInfo.getCostNotifyType()) != null){
				CostNotifyTemplate template = templateService.get(costNotifyInfo.getCostNotifyType(), CostNotifyTemplate.class);
				if(template != null){
					costNotifyInfo.setName(template.getName());
					costNotifyInfo.setDesc(template.getDesc());
					costNotifyManager.updateCostNotifyInfo(costNotifyInfo.getCostNotifyType(), costNotifyInfo);
					costNotifyInfoList.add(costNotifyInfo);
				}
			}
		}
		
		// 更新改变的消费状态设置
		GCUpdateCostNotify gcMsg = new GCUpdateCostNotify();
		gcMsg.setCostNotifyInfos(costNotifyInfoList.toArray(new CostNotifyInfo[0]));
		human.sendMessage(gcMsg);
		human.sendSuccessMessage(LangConstants.SAVE_SUCCESS);
	}

}
