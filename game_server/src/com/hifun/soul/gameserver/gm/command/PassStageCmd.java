package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.StageMapState;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageMapInfo;
import com.hifun.soul.gameserver.stage.template.StageTemplate;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 给科技点的gm命令
 * 
 * @author magicstone
 *
 */
public class PassStageCmd implements IAdminCommand<MinaGameClientSession> {
	
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

		int stageId = Integer.parseInt(commands[0]);
		// 战斗关卡对应的模版信息
		StageTemplate stageTemplate = GameServerAssist.getStageTemplateManager()
				.getStageTemplate(stageId);
		try {
			HumanStageManager stageManager = human.getHumanStageManager();
			stageManager.setNextStageId(stageId);
			
			int mapId = stageTemplate.getMapId();
			// 设置关卡地图状态
			if (GameServerAssist.getStageTemplateManager().isMapLastStage(stageId)
					&& stageManager.getStageMapState(mapId) != StageMapState.PASSED
					&& stageManager.getStageMapState(mapId) != StageMapState.GETTED) {
				// 设置当前地图状态
				stageManager.setStageMapState(mapId, StageMapState.PASSED);
				// 发送通关奖励
				stageManager.sendPassStageRewardInfo(mapId);
				// 开启下一张地图
				StageMapInfo stageMapInfo = GameServerAssist.getStageTemplateManager()
						.getStageMapInfo(mapId);
				if (stageMapInfo != null && stageMapInfo.getNextMapId() > 0) {
					stageManager.setStageMapState(
							stageMapInfo.getNextMapId(), StageMapState.OPEN);
				}
			}
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("pass map error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_PASS_STAGE;
	}

}
