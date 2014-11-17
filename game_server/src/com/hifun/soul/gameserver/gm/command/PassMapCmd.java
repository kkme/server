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
public class PassMapCmd implements IAdminCommand<MinaGameClientSession> {
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
		int mapId = Integer.parseInt(commands[0]);
		try {
			HumanStageManager stageManager = human.getHumanStageManager();
			// 设置当前地图状态
			stageManager.setStageMapState(mapId, StageMapState.PASSED);
			// 设置下一个关卡
			int lastStageId = GameServerAssist.getStageTemplateManager().getMapLastStageId(mapId);
			StageTemplate stageTemplate = GameServerAssist.getStageTemplateManager().getStageTemplate(lastStageId);
			if(stageTemplate == null){
				return;
			}
			stageManager.setNextStageId(stageTemplate.getNextStageId());
			// 开启下一个地图
			StageMapInfo stageMapInfo = GameServerAssist.getStageTemplateManager().getStageMapInfo(mapId);
			if(stageMapInfo == null){
				return;
			}
			stageManager.setStageMapState(stageMapInfo.getNextMapId(), StageMapState.OPEN);
		} catch (Exception e) {
			Loggers.GM_LOGGER.error("pass map error", e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_PASS_MAP;
	}

}
