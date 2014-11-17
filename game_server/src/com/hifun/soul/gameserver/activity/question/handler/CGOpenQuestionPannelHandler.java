package com.hifun.soul.gameserver.activity.question.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.activity.question.msg.CGOpenQuestionPannel;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGOpenQuestionPannelHandler implements IMessageHandlerWithType<CGOpenQuestionPannel> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_QUESTION_PANNEL;
	}

	@Override
	public void execute(CGOpenQuestionPannel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ANSWER_QUESTION, true)){
			return;
		}
		AnswerQuestionManager answerQuestionManager = (AnswerQuestionManager)human.getHumanActivityManager()
				.getActivityManager(ActivityType.ANSWER_QUESTION);
				
		answerQuestionManager.sendQuestionInfo();
		answerQuestionManager.sendScoreExchangeInfo();
		human.getHumanCdManager().snapCdQueueInfo(CdType.ANSWER_QUESTION);
		
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_QUESTION_PANEL.getIndex());
	}

}
