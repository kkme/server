package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

public class QuestionHelper implements IHelper {
	private AnswerQuestionManager manager;
	
	public QuestionHelper(AnswerQuestionManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.QUESTION.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.ANSWER_QUESTION, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否正在cd中
		else if(manager.getOwner().getHumanCdManager().getRemainCd(CdType.ANSWER_QUESTION) > 0){
			return HelpStateType.RUNING.getIndex();
		}
		// 判断是否还有次数
		else if(getTotalTimes() - getUsedTimes() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return manager.getDailyQuestionCount() - manager.getOwner().getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.REMAIN_QUESTION_NUM);
	}

	@Override
	public int getTotalTimes() {
		return manager.getDailyQuestionCount();
	}

}
