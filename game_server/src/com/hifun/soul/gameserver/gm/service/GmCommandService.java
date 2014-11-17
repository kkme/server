package com.hifun.soul.gameserver.gm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.gameserver.gm.ClientAdminCmdProcessor;
import com.hifun.soul.gameserver.gm.command.ChangeArenaRankCmd;
import com.hifun.soul.gameserver.gm.command.ChooseBattleActionCmd;
import com.hifun.soul.gameserver.gm.command.ClearBagCmd;
import com.hifun.soul.gameserver.gm.command.ClearEngergyCmd;
import com.hifun.soul.gameserver.gm.command.GiveAnswerScoreCmd;
import com.hifun.soul.gameserver.gm.command.GiveAuraCmd;
import com.hifun.soul.gameserver.gm.command.GiveExperienceCmd;
import com.hifun.soul.gameserver.gm.command.GiveGiftPointCmd;
import com.hifun.soul.gameserver.gm.command.GiveHonourCmd;
import com.hifun.soul.gameserver.gm.command.GiveHoroscopeCmd;
import com.hifun.soul.gameserver.gm.command.GiveItemCommand;
import com.hifun.soul.gameserver.gm.command.GiveMoneyCmd;
import com.hifun.soul.gameserver.gm.command.GivePrestigeCmd;
import com.hifun.soul.gameserver.gm.command.GiveSkillPointCmd;
import com.hifun.soul.gameserver.gm.command.GiveStarSoulCmd;
import com.hifun.soul.gameserver.gm.command.GiveTechnologyPointCmd;
import com.hifun.soul.gameserver.gm.command.GiveWarriorCmd;
import com.hifun.soul.gameserver.gm.command.OpenBossWarPanelCmd;
import com.hifun.soul.gameserver.gm.command.PassMapCmd;
import com.hifun.soul.gameserver.gm.command.PassStageCmd;
import com.hifun.soul.gameserver.gm.command.ResetArenaBattleTimeCmd;
import com.hifun.soul.gameserver.gm.command.ResetArenaRankRewardCmd;
import com.hifun.soul.gameserver.gm.command.ResetHumanPropertiesCmd;
import com.hifun.soul.gameserver.gm.command.SendBulletinCommand;
import com.hifun.soul.gameserver.gm.command.StartBossWarCmd;
import com.hifun.soul.gameserver.gm.command.StopBossWarCmd;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * gm命令管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class GmCommandService implements IInitializeRequired {

	@Autowired
	private ClientAdminCmdProcessor<MinaGameClientSession> cmdProcessor;
	
	private void registerCmd() {
		cmdProcessor.registerCommand(new SendBulletinCommand());
		cmdProcessor.registerCommand(new GiveItemCommand());
		cmdProcessor.registerCommand(new GiveMoneyCmd());
		cmdProcessor.registerCommand(new GiveTechnologyPointCmd());
		cmdProcessor.registerCommand(new GiveExperienceCmd());
		cmdProcessor.registerCommand(new ClearBagCmd());
		cmdProcessor.registerCommand(new PassMapCmd());
		cmdProcessor.registerCommand(new ChangeArenaRankCmd());
		cmdProcessor.registerCommand(new ResetArenaBattleTimeCmd());
		cmdProcessor.registerCommand(new ResetArenaRankRewardCmd());
		cmdProcessor.registerCommand(new StartBossWarCmd());
		cmdProcessor.registerCommand(new StopBossWarCmd());
		cmdProcessor.registerCommand(new OpenBossWarPanelCmd());
		cmdProcessor.registerCommand(new ChooseBattleActionCmd());
		cmdProcessor.registerCommand(new GiveSkillPointCmd());
		cmdProcessor.registerCommand(new ResetHumanPropertiesCmd());
		cmdProcessor.registerCommand(new ClearEngergyCmd());
		cmdProcessor.registerCommand(new GiveHonourCmd());
		cmdProcessor.registerCommand(new GiveAnswerScoreCmd());
		cmdProcessor.registerCommand(new GiveHoroscopeCmd());
		cmdProcessor.registerCommand(new PassStageCmd());
		cmdProcessor.registerCommand(new GiveGiftPointCmd());
		cmdProcessor.registerCommand(new GiveWarriorCmd());
		cmdProcessor.registerCommand(new GiveStarSoulCmd());
		cmdProcessor.registerCommand(new GivePrestigeCmd());
		cmdProcessor.registerCommand(new GiveAuraCmd());
	}

	/**
	 * 获取gm命令的执行器
	 * 
	 * @return
	 */
	public ClientAdminCmdProcessor<MinaGameClientSession> getProcessor() {
		return cmdProcessor;
	}

	@Override
	public void init() {
		registerCmd();
	}
}
