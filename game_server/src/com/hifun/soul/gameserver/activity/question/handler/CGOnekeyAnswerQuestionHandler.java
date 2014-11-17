package com.hifun.soul.gameserver.activity.question.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.activity.question.msg.CGOnekeyAnswerQuestion;
import com.hifun.soul.gameserver.activity.question.msg.GCAnswerUpdate;
import com.hifun.soul.gameserver.activity.question.template.Answer;
import com.hifun.soul.gameserver.activity.question.template.QuestionAnswerTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.AnswerEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;

@Component
public class CGOnekeyAnswerQuestionHandler implements
		IMessageHandlerWithType<CGOnekeyAnswerQuestion> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ONEKEY_ANSWER_QUESTION;
	}

	@Override
	public void execute(CGOnekeyAnswerQuestion message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human,
				GameFuncType.ANSWER_QUESTION, true)) {
			return;
		}
		// 判断vip等级
		if (human.getVipLevel() < GameServerAssist.getGameConstants()
				.getOnekyAnswerVipLevel()) {
			human.sendWarningMessage(LangConstants.ONEKEY_ANSWER_NOT_OPEN);
			return;
		}

		AnswerQuestionManager answerQuestionManager = (AnswerQuestionManager) human
				.getHumanActivityManager().getActivityManager(
						ActivityType.ANSWER_QUESTION);
		// 判断是否还有题目
		if (answerQuestionManager.getRemainQuestionNum() <= 0) {
			return;
		}
		// 消耗魔晶
		int costCrystal = answerQuestionManager.getOnekeyAnswerCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.ONEKEY_ANSWER_QUESTION, "")) {
			// 计算剩余题目，按打正确并且加倍算
			int multiple = SharedConstants.ONEKEY_ANSWER_MULTIPLE;
			int coinNum = 0;
			int exp = 0;
			int score = 0;
			int techPoint = 0;
			for (QuestionAnswerTemplate template : answerQuestionManager
					.getOnekeyAnswerQuestionTemplates()) {
				Answer answer = template.getAnswers().get(
						template.getRightAnswerIndex() - 1);
				if (answer != null) {
					coinNum += answer.getCoinNum() * multiple;
					exp += answer.getExp() * multiple;
					score += answer.getScore() * multiple;
					techPoint += answer.getTechnologyPoint() * multiple;

					// 发送答题事件
					AnswerEvent answerEvent = new AnswerEvent();
					human.getEventBus().fireEvent(answerEvent);
				}
			}
			// 增加积分
			answerQuestionManager.addTotalScore(score);
			// 一键答题处理
			answerQuestionManager.onekeyAnswer();

			// 返回消息
			GCAnswerUpdate answerMsg = new GCAnswerUpdate();
			human.getWallet().addMoney(CurrencyType.COIN, coinNum, true,
					MoneyLogReason.ONEKEY_ANSWER_QUESTION, "");
			human.addExperience(answerMsg.getExp(), true,
					ExperienceLogReason.ANSWER_QUESTION_ADD_EXP, "");
			// 获得军团科技收益加成
			techPoint = human.getHumanLegionManager().getLegionTechnologyAmend(
					LegionTechnologyType.QUESTION, techPoint);
			human.getHumanTechnologyManager().addTechnologyPoints(techPoint,
					true, TechPointLogReason.DAILY_QUESTION_ADD_TECH_POINT, "");
			answerMsg.setScore(score);
			answerMsg.setCoinNum(coinNum);
			answerMsg.setExp(exp);
			answerMsg.setTechnologyPoint(techPoint);
			answerMsg.setTotalScore(answerQuestionManager.getTotalScore());
			answerMsg.setResult(true);
			human.sendMessage(answerMsg);

			// 更新面板
			answerQuestionManager.sendQuestionInfo();
			answerQuestionManager.sendScoreExchangeInfo();
		}

	}
}
