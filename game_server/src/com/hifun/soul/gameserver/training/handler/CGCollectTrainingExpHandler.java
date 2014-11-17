package com.hifun.soul.gameserver.training.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;
import com.hifun.soul.gameserver.training.msg.CGCollectTrainingExp;
@Component
public class CGCollectTrainingExpHandler implements IMessageHandlerWithType<CGCollectTrainingExp> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_COLLECT_TRAINING_EXP;
	}

	@Override
	public void execute(CGCollectTrainingExp message) {
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
		trainingManager.CollectTrainingExp(message.getTrainingType(), message.getTrainingDetailType());
	}

}
