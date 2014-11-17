package com.hifun.soul.gameserver.stage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageInfo;
import com.hifun.soul.gameserver.stage.msg.CGStageInfo;
import com.hifun.soul.gameserver.stage.msg.GCStageInfo;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;

@Component
public class CGStageInfoHandler implements IMessageHandlerWithType<CGStageInfo> {

	@Autowired
	private StageTemplateManager stageTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_STAGE_INFO;
	}

	@Override
	public void execute(CGStageInfo message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanStageManager humanStageManager = human.getHumanStageManager();
		// 判断是否可以查看关卡信息
		if(!humanStageManager.canSeeStage(message.getStageId())){
			return;
		}
		
		// 获取关卡信息
		StageInfo stageInfo = stageTemplateManager.getStageInfo(message.getStageId());
		stageInfo.setStar(humanStageManager.getStageStar(message.getStageId()));
		GCStageInfo gcMsg = new GCStageInfo();
		gcMsg.setStageInfo(stageInfo);
		
		human.sendMessage(gcMsg);
	}

}
