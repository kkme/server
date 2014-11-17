package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.gmquestion.QuestionType;
import com.hifun.soul.gameserver.gmquestion.msg.CGShowQuestions;
import com.hifun.soul.gameserver.gmquestion.msg.CGSubmitQuestion;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class GmQuestionTask extends LoopExecuteTask {

	public GmQuestionTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGSubmitQuestion cgSummitQuestion1 = new CGSubmitQuestion();
		cgSummitQuestion1.setQuestionType(QuestionType.BUG.getIndex());
		cgSummitQuestion1.setContent("bug");
		getRobot().sendMessage(cgSummitQuestion1);
		
//		CGSubmitQuestion cgSummitQuestion2 = new CGSubmitQuestion();
//		cgSummitQuestion2.setQuestionType(QuestionType.COMPLAIN.getIndex());
//		cgSummitQuestion2.setContent("投诉");
//		getRobot().sendMessage(cgSummitQuestion2);
//		
//		CGSubmitQuestion cgSummitQuestion3 = new CGSubmitQuestion();
//		cgSummitQuestion3.setQuestionType(QuestionType.SUGGEST.getIndex());
//		cgSummitQuestion3.setContent("建议");
//		getRobot().sendMessage(cgSummitQuestion3);
//		
//		CGSubmitQuestion cgSummitQuestion4 = new CGSubmitQuestion();
//		cgSummitQuestion4.setQuestionType(QuestionType.OTHERS.getIndex());
//		cgSummitQuestion4.setContent("其他");
//		getRobot().sendMessage(cgSummitQuestion4);
		
		
		CGShowQuestions cgShowQuestions = new CGShowQuestions();
		getRobot().sendMessage(cgShowQuestions);
		
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
