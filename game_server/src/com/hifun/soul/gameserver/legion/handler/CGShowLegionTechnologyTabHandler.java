package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionTechnology;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionTechnologyTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionTechnologyTab;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTypeTemplate;

@Component
public class CGShowLegionTechnologyTabHandler implements
		IMessageHandlerWithType<CGShowLegionTechnologyTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_TECHNOLOGY_TAB;
	}

	@Override
	public void execute(CGShowLegionTechnologyTab message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 校验军团成员是否存在
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.TECHNOLOGY, true)) {
			return;
		}
		// 建筑信息
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.TECHNOLOGY);
		if (buildingInfo == null) {
			return;
		}
		// 返回消息
		GCShowLegionTechnologyTab msg = new GCShowLegionTechnologyTab();
		msg.setLegionBuildingInfo(buildingInfo);
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		msg.setContributeCostCoin(templateManager.getConstantsTemplate()
				.getContributeTechCostCoin());
		List<LegionTechnologyTypeTemplate> typeTemplateList = templateManager
				.getTechnologyTypeTemplates(buildingInfo.getBuildingLevel());
		List<LegionTechnologyInfo> techInfoList = new ArrayList<LegionTechnologyInfo>();
		for (LegionTechnologyTypeTemplate typeTemplate : typeTemplateList) {
			LegionTechnology technology = globalLegionManager.getTechnology(
					legion, typeTemplate.getId());
			LegionTechnologyInfo techInfo = globalLegionManager
					.generateTechnologyInfo(legion, technology);
			techInfoList.add(techInfo);
		}
		msg.setTechnologyInfos(techInfoList
				.toArray(new LegionTechnologyInfo[0]));
		msg.setSelfMedal(legionMember.getMedal());
		human.sendMessage(msg);
	}
}
