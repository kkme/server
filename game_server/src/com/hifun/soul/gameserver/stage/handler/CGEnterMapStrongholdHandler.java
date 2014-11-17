package com.hifun.soul.gameserver.stage.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageSimpleInfo;
import com.hifun.soul.gameserver.stage.msg.CGEnterMapStronghold;
import com.hifun.soul.gameserver.stage.msg.GCEnterMapStronghold;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;

/**
 * 请求进入指定据点的处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGEnterMapStrongholdHandler implements
		IMessageHandlerWithType<CGEnterMapStronghold> {
	@Autowired
	private StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_MAP_STRONGHOLD;
	}

	@Override
	public void execute(CGEnterMapStronghold message) {
		Human human = message.getPlayer().getHuman();
		GCEnterMapStronghold gcEnterStronghold = new GCEnterMapStronghold();
		HumanStageManager humanStageManager = human.getHumanStageManager();
		// 能量和选定的关卡
		gcEnterStronghold.setEnergy(human.getEnergy());
		gcEnterStronghold.setSelectStageId(humanStageManager.getNextStageId());
		gcEnterStronghold.setStrongholdId(message.getStrongholdId());
		// 构建关卡列表
		List<StageSimpleInfo> stageSimpleInfoList = stageTemplateManager
				.getStrongholdStageInfo(message.getStrongholdId(),
						humanStageManager.getNextStageId());
		StageSimpleInfo[] stageSimpleInfos = new StageSimpleInfo[stageSimpleInfoList
				.size()];
		// 设置其他的简单信息
		for (int i = 0; i < stageSimpleInfos.length; i++) {
			stageSimpleInfos[i] = stageSimpleInfoList.get(i);
			int star = humanStageManager.getStageStar(stageSimpleInfos[i]
					.getStageId());
			stageSimpleInfos[i].setStar(star);
			stageSimpleInfos[i].setAutoBattle(stageTemplateManager
					.canAutoBattle(star));
		}
		gcEnterStronghold.setStageSimpleInfos(stageSimpleInfos);
		human.sendMessage(gcEnterStronghold);
	}

}
