package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给技能点的gm命令
 * 
 * @author magicstone
 *
 */
public class GiveAnswerScoreCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}
		
		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (commands.length < 1) {
			return;
		}

		int scores = Integer.parseInt(commands[0]);
		
		try {
			AnswerQuestionManager manager = (AnswerQuestionManager)human.getHumanActivityManager().getActivityManager(ActivityType.ANSWER_QUESTION);
			manager.addTotalScore(scores);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("Give answer score error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_ANSWER_SCORE;
	}

}
