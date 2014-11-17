package com.hifun.soul.gameserver.activity.question.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.activity.question.msg.CGAnswerSubmit;
import com.hifun.soul.gameserver.activity.question.msg.GCAnswerUpdate;
import com.hifun.soul.gameserver.activity.question.template.Answer;
import com.hifun.soul.gameserver.activity.question.template.QuestionAnswerTemplate;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.AnswerEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;


@Component
public class CGAnswerSubmitHandler implements IMessageHandlerWithType<CGAnswerSubmit> {
	private static Logger logger = Loggers.ACTIVITY_LOGGER;
	
	@Autowired
	private GameFuncService gameFuncService;
	
	
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ANSWER_SUBMIT;
	}

	@Override
	public void execute(CGAnswerSubmit message) {
		Human human = message.getPlayer().getHuman();	
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ANSWER_QUESTION, true)){
			return;
		}
		HumanCdManager cdManager = human.getHumanCdManager();
		long nextCdTime = cdManager.getSpendTime(CdType.ANSWER_QUESTION, 0);
		if (!cdManager.canAddCd(CdType.ANSWER_QUESTION,nextCdTime)) {
			logger.error("问答Cd时间未冷却，不能答题。humanId:" + human.getHumanGuid());
			return;
		}
		AnswerQuestionManager answerQuestionManager = (AnswerQuestionManager)human.getHumanActivityManager()
				.getActivityManager(ActivityType.ANSWER_QUESTION);
		if(answerQuestionManager.getQuestionIndex()>answerQuestionManager.getDailyQuestionCount()){			
			return;
		}
		GCAnswerUpdate gcMsg = new GCAnswerUpdate();
		QuestionAnswerTemplate template = answerQuestionManager.getQuestionAndAnswerTemplate();
		if (message.getAnswerIndex() < 0 || message.getAnswerIndex() >= template.getAnswers().size()) {
			logger.error("答案索引错误。humanId:" + human.getHumanGuid());
			return;
		}
		Answer answer = template.getAnswers().get(message.getAnswerIndex());
		gcMsg.setResult(message.getAnswerIndex() == template.getRightAnswerIndex()-1);
		if (answer != null) {
			if (message.getIsBless() == true) {
				if (message.getIsConfirm()) {
					if(!answerQuestionManager.buyBlessNum()){						
						return;
					}
				} else if (answerQuestionManager.getUsableBlessNum() < 1) {					
					answerQuestionManager.sendBuyBlessNumConfirmInfo(message.getAnswerIndex());
					return;
				}
				answerQuestionManager.setUsableBlessNum(answerQuestionManager.getUsableBlessNum() - 1);				
				GameConstants constants = ApplicationContext
						.getApplicationContext().getBean(GameConstants.class);
				float rate = constants.getBlessRevenueRate();
				gcMsg.setCoinNum((int) (answer.getCoinNum() * rate));
				gcMsg.setExp((int) (answer.getExp() * rate));
				gcMsg.setScore((int) (answer.getScore() * rate));
				gcMsg.setTechnologyPoint((int) (answer.getTechnologyPoint() * rate));
			} else {
				gcMsg.setCoinNum(answer.getCoinNum());
				gcMsg.setExp(answer.getExp());
				gcMsg.setScore(answer.getScore());
				gcMsg.setTechnologyPoint(answer.getTechnologyPoint());
			}					
			answerQuestionManager.addTotalScore(gcMsg.getScore());
			gcMsg.setTotalScore(answerQuestionManager.getTotalScore());			
			human.getWallet().addMoney(CurrencyType.COIN, gcMsg.getCoinNum(), true, MoneyLogReason.ANSWER_QUESTION, "");
			human.addExperience(gcMsg.getExp(),true,ExperienceLogReason.ANSWER_QUESTION_ADD_EXP, "");
			// 获得军团科技收益加成
			int rewardTechnologyPoint = gcMsg.getTechnologyPoint();
			rewardTechnologyPoint = human.getHumanLegionManager()
					.getLegionTechnologyAmend(LegionTechnologyType.QUESTION,
							rewardTechnologyPoint);
			gcMsg.setTechnologyPoint(rewardTechnologyPoint);
			human.getHumanTechnologyManager().addTechnologyPoints(gcMsg.getTechnologyPoint(),true,TechPointLogReason.DAILY_QUESTION_ADD_TECH_POINT, "");
			human.sendMessage(gcMsg);
			if (answerQuestionManager.getQuestionIndex() < answerQuestionManager.getDailyQuestionCount()) {
				cdManager.addCd(CdType.ANSWER_QUESTION, nextCdTime);
				cdManager.snapCdQueueInfo(CdType.ANSWER_QUESTION);
			}
			//生成新题目
			answerQuestionManager.GenerateNewQuestion();
			answerQuestionManager.sendQuestionInfo();	
			answerQuestionManager.sendScoreExchangeInfo();

			// 发送答题事件
			AnswerEvent answerEvent = new AnswerEvent();
			human.getEventBus().fireEvent(answerEvent);	
			// 答题引导
			human.getHumanGuideManager().showGuide(GuideType.QUESTION.getIndex());
		}

	}
	
	

}
