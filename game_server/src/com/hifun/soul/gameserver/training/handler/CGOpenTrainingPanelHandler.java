package com.hifun.soul.gameserver.training.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;
import com.hifun.soul.gameserver.training.msg.CGOpenTrainingPanel;
import com.hifun.soul.gameserver.training.msg.GCUpdateTrainingPanel;

@Component
public class CGOpenTrainingPanelHandler implements IMessageHandlerWithType<CGOpenTrainingPanel> {
	
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_TRAINING_PANEL;
	}

	@Override
	public void execute(CGOpenTrainingPanel message) {
		if(message.getPlayer() == null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.TRAINING, true)){
			return;
		}
		HumanTrainingManager trainingManager = human.getHumanTrainingManager();		
		GCUpdateTrainingPanel gcMsg = new GCUpdateTrainingPanel();
		gcMsg.setNormalTrainings(trainingManager.getNormalTrainings());
		gcMsg.setVipTrainings(trainingManager.getVipTrainings());		
		gcMsg.setNormalTrainingTimeRemain(trainingManager.getNormalTrainingTimeRemain());
		gcMsg.setVipTrainingConsumableCrystalNum(trainingManager.getVipTrainingConsumableCrystalNum());
		gcMsg.setMaxNormalTrainingTime(GameServerAssist.getGameConstants().getNormalTrainingMaxTime());
		gcMsg.setMaxVipTrainingCrystalNum(GameServerAssist.getGameConstants().getVipTraingMaxCrystalConsume());
		human.sendMessage(gcMsg);
		// 下发CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.TRAINING);
		human.getHumanGuideManager().showGuide(GuideType.OPEN_TRAIN_PANEL.getIndex());
	}

}
