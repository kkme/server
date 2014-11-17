package com.hifun.soul.gameserver.legion.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionApply;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionApplyTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionApplyTab;
import com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;

/**
 * 展示申请标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowLegionApplyTabHandler implements
		IMessageHandlerWithType<CGShowLegionApplyTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_APPLY_TAB;
	}

	@Override
	public void execute(CGShowLegionApplyTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		List<LegionApply> legionApplyList = globalLegionManager
				.getLegionApplyList(legion.getId());
		GCShowLegionApplyTab gcMsg = new GCShowLegionApplyTab();
		int size = legionApplyList.size();
		LegionApplyListInfo[] legionApplies = new LegionApplyListInfo[size];
		for (int i = 0; i < size; i++) {
			LegionApply legionApply = legionApplyList.get(i);
			LegionApplyListInfo legionApplyListInfo = globalLegionManager
					.legionApplyToInfo(legionApply);
			int position = legion.getMemberListMap().get(human.getHumanGuid())
					.getPosition();
			LegionRightTemplate legionRightTemplate = GameServerAssist
					.getLegionTemplateManager()
					.getLegionRightTemplate(position);
			// 审核权限
			legionApplyListInfo.setCheckButtionVisible(legionRightTemplate
					.getCheckApply() == 1);
			legionApplies[i] = legionApplyListInfo;
		}
		gcMsg.setLegionApplies(legionApplies);
		human.sendMessage(gcMsg);
	}

}
