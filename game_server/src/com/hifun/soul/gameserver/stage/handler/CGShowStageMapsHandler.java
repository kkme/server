package com.hifun.soul.gameserver.stage.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.StageMapType;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageMapInfo;
import com.hifun.soul.gameserver.stage.msg.CGShowStageMaps;
import com.hifun.soul.gameserver.stage.msg.GCShowStageMaps;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;

@Component
public class CGShowStageMapsHandler implements
		IMessageHandlerWithType<CGShowStageMaps> {
	
	@Autowired
	private StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_STAGE_MAPS;
	}

	@Override
	public void execute(CGShowStageMaps message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanStageManager stageManager = human.getHumanStageManager();
		int nextStageId = stageManager.getNextStageId();
		int mapId = stageTemplateManager.getMapIdByStageId(nextStageId);
		
		GCShowStageMaps gcMsg = new GCShowStageMaps();
		gcMsg.setMapId(mapId);
		List<StageMapInfo> stageMapInfos = new ArrayList<StageMapInfo>();
		for(StageMapType stageMapType : StageMapType.values()){
			StageMapInfo stageMapInfo = stageTemplateManager.getStageMapInfo(stageMapType.getIndex());
			if(stageMapInfo == null){
				continue;
			}
			stageMapInfo.setPassCount(stageTemplateManager.getPassStageCount(nextStageId, stageMapType.getIndex()));
			stageMapInfo.setTotalCount(stageTemplateManager.getStageCount(stageMapType.getIndex()));
			stageMapInfo.setMapState(stageManager.getStageMapState(stageMapType.getIndex()).getIndex());
			stageMapInfos.add(stageMapInfo);
		}
		gcMsg.setStageMapInfos(stageMapInfos.toArray(new StageMapInfo[0]));
		
		human.sendMessage(gcMsg);
	}

}
