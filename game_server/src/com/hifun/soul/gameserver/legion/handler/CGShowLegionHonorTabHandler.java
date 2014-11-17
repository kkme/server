package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.info.LegionTitleInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionHonorTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionHonorTab;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;

@Component
public class CGShowLegionHonorTabHandler implements
		IMessageHandlerWithType<CGShowLegionHonorTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_HONOR_TAB;
	}

	@Override
	public void execute(CGShowLegionHonorTab message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.HONOR, true)) {
			return;
		}
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.HONOR);
		if (buildingInfo == null) {
			return;
		}
		GCShowLegionHonorTab msg = new GCShowLegionHonorTab();
		msg.setLegionBuildingInfo(buildingInfo);
		List<LegionHonorTemplate> honorTemplateList = GameServerAssist
				.getLegionTemplateManager().getHonorTemplateList(
						buildingInfo.getBuildingLevel());
		List<LegionTitleInfo> titleInfoList = new ArrayList<LegionTitleInfo>();
		for (LegionHonorTemplate titleTemplate : honorTemplateList) {
			LegionTitleInfo titleInfo = globalLegionManager.generateTitleInfo(
					titleTemplate.getId(), legion);
			titleInfoList.add(titleInfo);
		}
		msg.setTitleInfos(titleInfoList.toArray(new LegionTitleInfo[0]));
		msg.setSelfMedal(globalLegionManager.getLegionMember(
				human.getHumanGuid()).getMedal());
		human.sendMessage(msg);
	}

}
