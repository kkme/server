package com.hifun.soul.gameserver.legion.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.info.LegionFuncInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGOpenLegionPanel;
import com.hifun.soul.gameserver.legion.msg.GCOpenLegionPanel;
import com.hifun.soul.gameserver.legion.msg.LegionListInfo;
import com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;

/**
 * 打开军团面板
 * 
 * @author yandajun
 * 
 */
@Component
public class CGOpenLegionPanelHandler implements
		IMessageHandlerWithType<CGOpenLegionPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_LEGION_PANEL;
	}

	@Override
	public void execute(CGOpenLegionPanel message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GCOpenLegionPanel gcMsg = new GCOpenLegionPanel();
		// 如果没有加入任何军团，默认显示所有军团列表
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			List<Legion> legionList = globalLegionManager.getLegionList();
			LegionListInfo[] legions = globalLegionManager.legionToListInfo(
					human, legionList);
			gcMsg.setLegions(legions);
			gcMsg.setLegionMembers(new LegionMemberListInfo[0]);
			gcMsg.setOwnLegion(new Legion());
			LegionConstantsTemplate constantsTemplate = GameServerAssist
					.getLegionTemplateManager().getConstantsTemplate();
			gcMsg.setCreateLegionNeedCoin(constantsTemplate
					.getCreateLegionNeedCoin());
			gcMsg.setCreateLegionNeedLevel(constantsTemplate
					.getCreateLegionNeedLevel());
			gcMsg.setFuncInfos(new LegionFuncInfo[0]);
			human.sendMessage(gcMsg);
			// 新手引导
			human.getHumanGuideManager().showGuide(
					GuideType.OPEN_LEGION_PANEL.getIndex());
		} else {
			// 如果已加入军团，默认显示该军团信息 + 军团成员列表
			LegionMember legionMember = globalLegionManager
					.getLegionMember(human.getHumanGuid());
			if (legionMember == null) {
				return;
			}
			boolean canEditNotice = GameServerAssist.getLegionTemplateManager()
					.getLegionRightTemplate(legionMember.getPosition())
					.getEditNotice() == 1;
			gcMsg.setCanEditNotice(canEditNotice);
			// 设置军团捐赠魔晶的信息
			LegionConstantsTemplate constantsTemplate = GameServerAssist
					.getLegionTemplateManager().getConstantsTemplate();
			legion.setDonateGetLegionExp(constantsTemplate
					.getDonateGetLegionExp());
			legion.setDonateGetMedal(constantsTemplate.getDonateGetMedal());
			legion.setDonateGetSelfContri(constantsTemplate
					.getDonateGetSelfContri());
			gcMsg.setOwnLegion(legion);
			gcMsg.setLegionMembers(globalLegionManager
					.generateMemberListInfo(human));
			gcMsg.setLegions(new LegionListInfo[0]);
			gcMsg.setCreateLegionNeedCoin(constantsTemplate
					.getCreateLegionNeedCoin());
			gcMsg.setCreateLegionNeedLevel(constantsTemplate
					.getCreateLegionNeedLevel());
			List<LegionFuncInfo> funcInfoList = GameServerAssist
					.getLegionTemplateManager().getLegionFuncInfos();
			gcMsg.setFuncInfos(funcInfoList.toArray(new LegionFuncInfo[0]));
			gcMsg.setDonateNeedVip(constantsTemplate.getDonateNeedVip());
			human.sendMessage(gcMsg);
		}
	}
}
