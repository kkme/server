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
import com.hifun.soul.gameserver.legion.info.LegionMeditationInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionMeditationTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionMeditationTab;
import com.hifun.soul.gameserver.legion.template.LegionMeditationTemplate;

@Component
public class CGShowLegionMeditationTabHandler implements
		IMessageHandlerWithType<CGShowLegionMeditationTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_MEDITATION_TAB;
	}

	@Override
	public void execute(CGShowLegionMeditationTab message) {
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
				LegionBuildingType.MEDITATION, true)) {
			return;
		}
		GCShowLegionMeditationTab msg = new GCShowLegionMeditationTab();
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.MEDITATION);
		msg.setLegionBuildingInfo(buildingInfo);
		msg.setIsMeditationed(human.isLegionMeditationed());
		// 冥想方式信息
		List<LegionMeditationInfo> meditationInfoList = new ArrayList<LegionMeditationInfo>();
		for (Integer meditationType : templateManager.getMeditationTemplates()
				.keySet()) {
			LegionMeditationTemplate template = templateManager
					.getMeditationTemplates().get(meditationType);
			LegionMeditationInfo meditationInfo = new LegionMeditationInfo();
			meditationInfo.setMeditationType(meditationType);
			meditationInfo.setMeditationName(template.getName());
			meditationInfo.setVipLevel(template.getVipLevel());
			meditationInfo.setCurrencyType(template.getCurrencyType());
			meditationInfo.setCurrencyNum(template.getCostNum());
			meditationInfo.setMeditation(GameServerAssist
					.getLegionTemplateManager().getLegionMeditation(
							human.getLevel(), meditationType));
			meditationInfo.setMedal(template.getContribution());
			meditationInfoList.add(meditationInfo);
		}
		msg.setMeditationInfos(meditationInfoList
				.toArray(new LegionMeditationInfo[0]));
		// 冥想日志
		int size = legion.getMeditationLogList().size();
		String[] meditationLogs = new String[size];
		for (int i = 0; i < size; i++) {
			meditationLogs[i] = legion.getMeditationLogList().get(i)
					.getContent();
		}
		msg.setMeditationLogs(meditationLogs);
		human.sendMessage(msg);
	}
}
