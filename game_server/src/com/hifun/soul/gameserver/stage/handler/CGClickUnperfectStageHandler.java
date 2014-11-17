package com.hifun.soul.gameserver.stage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.model.StageSimpleInfo;
import com.hifun.soul.gameserver.stage.msg.CGClickUnperfectStage;
import com.hifun.soul.gameserver.stage.msg.GCClickUnperfectStage;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

/**
 * 点击不完美关卡
 * 
 * @author yandajun
 * 
 */
@Component
public class CGClickUnperfectStageHandler implements
		IMessageHandlerWithType<CGClickUnperfectStage> {
	@Autowired
	private StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_CLICK_UNPERFECT_STAGE;
	}

	@Override
	public void execute(CGClickUnperfectStage message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		int stageId = message.getStageId();
		if (stageId <= 0) {
			return;
		}
		// 关卡模版校验
		StageTemplate stageTemplate = stageTemplateManager
				.getStageTemplate(message.getStageId());
		if (stageTemplate == null) {
			return;
		}
		StageSimpleInfo stageSimpleInfo = stageTemplateManager
				.converStageTemplateToStageSimpleInfo(stageTemplate);
		// 设置其他的简单信息
		int star = human.getHumanStageManager().getStageStar(
				stageSimpleInfo.getStageId());
		stageSimpleInfo.setStar(star);
		stageSimpleInfo.setAutoBattle(stageTemplateManager.canAutoBattle(star));
		GCClickUnperfectStage msg = new GCClickUnperfectStage();
		msg.setStageId(stageId);
		msg.setStageSimpleInfo(stageSimpleInfo);
		msg.setItem(CommonItemBuilder.genCommonItem(stageTemplate
				.getRewardItems().get(0).getItemId()));
		human.sendMessage(msg);
	}

}
