package com.hifun.soul.gameserver.activity.question.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.activity.question.msg.CGExchangeScore;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGExchangeScoreHandler implements IMessageHandlerWithType<CGExchangeScore> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_EXCHANGE_SCORE;
	}

	@Override
	public void execute(CGExchangeScore message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ANSWER_QUESTION, true)){
			return;
		}
		AnswerQuestionManager answerQuestionManager = (AnswerQuestionManager)human.getHumanActivityManager().getActivityManager(ActivityType.ANSWER_QUESTION);
		answerQuestionManager.ExchangeScore(message.getExchangeIndex());		
	}

}
