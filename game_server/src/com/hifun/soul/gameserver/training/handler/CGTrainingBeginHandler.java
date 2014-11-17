package com.hifun.soul.gameserver.training.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;
import com.hifun.soul.gameserver.training.msg.CGTrainingBegin;

@Component
public class CGTrainingBeginHandler implements IMessageHandlerWithType<CGTrainingBegin> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_TRAINING_BEGIN;
	}

	@Override
	public void execute(CGTrainingBegin message) {
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
		trainingManager.trainingBegin(message.getTrainingType(), message.getTrainingDetailType());
	}

}
