package com.hifun.soul.gameserver.stage.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.TriggerType;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageDramaInfo;
import com.hifun.soul.gameserver.stage.msg.CGClickStage;
import com.hifun.soul.gameserver.stage.msg.GCClickStage;
import com.hifun.soul.gameserver.stage.msg.GCStageDramaInfo;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

@Component
public class CGClickStageHandler implements
		IMessageHandlerWithType<CGClickStage> {
	private Logger logger = Loggers.STAGE_LOGGER;

	@Autowired
	private StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_CLICK_STAGE;
	}

	@Override
	public void execute(CGClickStage message) {
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
		// 判断是否可以攻打该关卡
		HumanStageManager humanStageManager = human.getHumanStageManager();
		if (!humanStageManager.canAttackStage(stageId)) {
			return;
		}
		// 关卡模版校验
		StageTemplate stageTemplate = stageTemplateManager
				.getStageTemplate(message.getStageId());
		if (stageTemplate == null) {
			logger.error(String.format(
					"StageTemplate can not find! humanGuid=%s; stageId=%s",
					human.getHumanGuid(), message.getStageId()));
			return;
		}
		// 判断等级是否足够
		if (stageTemplate.getMinLevel() > human.getLevel()) {
			human.sendGenericMessage(LangConstants.LEVEl_NOT_ENOUGH,
					stageTemplate.getMinLevel());
			return;
		}
		List<StageDramaInfo> stageDramaInfos = null;
		// 判断是否有要发送的剧情
		if (!humanStageManager.isTrigger(stageId, TriggerType.STAGE_BEFORE)) {
			stageDramaInfos = stageTemplateManager.getStageDramaInfos(stageId,
					TriggerType.STAGE_BEFORE.getIndex());
			humanStageManager.triggerDrama(stageId, TriggerType.STAGE_BEFORE);
		}
		if (stageDramaInfos == null) {
			stageDramaInfos = new ArrayList<StageDramaInfo>();
		}
		// 下发剧情信息
		GCStageDramaInfo gcDramaInfoMsg = new GCStageDramaInfo();
		gcDramaInfoMsg.setStageId(stageId);
		gcDramaInfoMsg.setStageDramaInfos(stageDramaInfos
				.toArray(new StageDramaInfo[0]));
		gcDramaInfoMsg.setBeforeBattle(true);
		human.sendMessage(gcDramaInfoMsg);
		// 下发关卡奖励物品
		GCClickStage gcClickStage = new GCClickStage();
		gcClickStage.setStageId(stageId);
		gcClickStage.setItem(CommonItemBuilder.genCommonItem(stageTemplate
				.getRewardItems().get(0).getItemId()));
		human.sendMessage(gcClickStage);
	}

}
