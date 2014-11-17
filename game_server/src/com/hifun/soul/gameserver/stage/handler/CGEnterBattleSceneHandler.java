package com.hifun.soul.gameserver.stage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.StageMapState;
import com.hifun.soul.gameserver.stage.StageMapType;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.msg.CGEnterBattleScene;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

@Component
public class CGEnterBattleSceneHandler implements
		IMessageHandlerWithType<CGEnterBattleScene> {
	@Autowired
	private StageTemplateManager stageTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_BATTLE_SCENE;
	}

	@Override
	public void execute(CGEnterBattleScene message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 战斗地图信息
		HumanStageManager humanStageManager = human.getHumanStageManager();
		int mapId = message.getMapId();
		int nextStageId = humanStageManager.getNextStageId();
		if(mapId <= 0){
			StageTemplate stageTemplate = stageTemplateManager.getStageTemplate(nextStageId);
			if(stageTemplate == null){
				mapId = StageMapType.getLastMapId();
			}
			else{
				mapId = stageTemplate.getMapId();
			}
		}
		if(humanStageManager.getNextMapId() >= 0){
			mapId = humanStageManager.getNextMapId();
			humanStageManager.setNextMapId(-1);
		}
		if(message.getIsNeedCheckReward()){
			// 判断是否有未领取的过关奖励
			StageMapState stageMapState = humanStageManager.getStageMapState(mapId);
			if(stageMapState == StageMapState.PASSED){
				humanStageManager.sendPassStageRewardInfo(mapId);
			}
		}
		humanStageManager.updateBattleScene(stageTemplateManager, mapId);
	}

}
