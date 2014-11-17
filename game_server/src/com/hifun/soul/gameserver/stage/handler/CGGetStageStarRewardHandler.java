package com.hifun.soul.gameserver.stage.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.StageStarRewardState;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo;
import com.hifun.soul.gameserver.stage.msg.CGGetStageStarReward;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageStarRewardTemplate;
import com.hifun.soul.proto.common.Stages.StageStarRewardData;

@Component
public class CGGetStageStarRewardHandler implements
		IMessageHandlerWithType<CGGetStageStarReward> {
	@Autowired
	private StageTemplateManager stageTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_STAGE_STAR_REWARD;
	}

	@Override
	public void execute(CGGetStageStarReward message) {
		Human human = message.getPlayer().getHuman();
		HumanStageManager stageManager = human.getHumanStageManager();
		HumanBagManager bagManager = human.getBagManager();
		// 找到下一个评星领奖的信息
		StageStarRewardData.Builder stageStarRewardData = stageManager.getNextStageStarRewardType(stageTemplateManager.getStageStarRewardTypes());
		// 如果没有可以领取的奖励直接return
		if(stageStarRewardData == null
				|| stageStarRewardData.getRewardState() != StageStarRewardState.CANGET.getIndex()){
			return;
		}
		// 根据星星数量找到对应的奖励模版
		StageStarRewardTemplate rewardTemplate = stageTemplateManager.getStageStarRewardTemplate(stageStarRewardData.getStar());
		if(rewardTemplate == null){
			return;
		}
		// 判断背包是否有足够的格子
		List<StageStarRewardItemInfo> stageStarRewardItemInfos = rewardTemplate.getStageStarRewardItemInfos();
		if(bagManager.getFreeSize(BagType.MAIN_BAG) < stageStarRewardItemInfos.size()) {
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		// 如果可以领取奖励则领取奖励
		for(StageStarRewardItemInfo stageStarRewardItemInfo : stageStarRewardItemInfos){
			bagManager.putItems(BagType.MAIN_BAG, stageStarRewardItemInfo.getItemId(), stageStarRewardItemInfo.getItemNum(), ItemLogReason.STAGE_STAR_REWARD, "");
		}
		// 设置奖励的领取状态
		stageManager.updateStageStarRewardState(stageStarRewardData.getStar(),StageStarRewardState.GETTED.getIndex());
		// 根据是否还有评星奖励下发消息
		stageManager.updateStageStarRewardInfos();
	}

}
