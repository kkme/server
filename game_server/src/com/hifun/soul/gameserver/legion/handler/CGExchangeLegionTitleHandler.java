package com.hifun.soul.gameserver.legion.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionHonor;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGExchangeLegionTitle;
import com.hifun.soul.gameserver.legion.msg.GCExchangeLegionTitle;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

@Component
public class CGExchangeLegionTitleHandler implements
		IMessageHandlerWithType<CGExchangeLegionTitle> {

	@Override
	public short getMessageType() {
		return MessageType.CG_EXCHANGE_LEGION_TITLE;
	}

	@Override
	public void execute(CGExchangeLegionTitle message) {
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
		// 是否已有相同头衔，并且有效
		if (human.getLegionTitleId() == message.getTitleId()
				&& human.isLegionTitleValid()) {
			human.sendErrorMessage(LangConstants.LEGION_TITLE_IS_USING);
			return;
		}
		// 校验最低职位
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 头衔模板
		LegionHonorTemplate titleTemplate = GameServerAssist
				.getLegionTemplateManager().getHonorTemplate(
						message.getTitleId());
		if (legionMember.getPosition() < titleTemplate.getNeedPosition()) {
			human.sendErrorMessage(LangConstants.LEGION_TITLE_POSITION_NOT_ENOUGH);
			return;
		}
		// 是否达到人数上限
		LegionHonor legionHonor = globalLegionManager.getHonor(legion,
				message.getTitleId());
		if (legionHonor == null) {
			return;
		}
		if (legionHonor.getExchangeNum() >= titleTemplate.getMaxNum()) {
			human.sendErrorMessage(LangConstants.LEGION_TITLE_HAS_NONE);
			return;
		}
		// 消耗勋章兑换头衔
		int costMedal = titleTemplate.getCostMedal();
		if (legionMember.costMedal(human, costMedal, false)) {
			legionHonor.setExchangeNum(legionHonor.getExchangeNum() + 1);
			globalLegionManager.updateHonor(legionHonor);
			human.setLegionTitleId(message.getTitleId());
			long now = GameServerAssist.getSystemTimeService().now();
			long newEndTime = now + TimeUtils.DAY
					* titleTemplate.getValidDays();
			human.setLegionTitleValid(true);
			human.getPropertyManager().setLongPropertyValue(
					HumanLongProperty.LEGION_TITLE_END_TIME, newEndTime);

			// 属性加成
			if (titleTemplate.isAllProperty()) {
				Map<Integer, Integer> propertyMap = human.getPropertyManager()
						.getAllProperties();
				for (Integer propertyId : propertyMap.keySet()) {
					AmendMethod amendType = AmendMethod.valueOf(titleTemplate
							.getAmendMethod());
					human.getPropertyManager().amendProperty(human, amendType,
							propertyId, propertyMap.get(propertyId),
							PropertyLogReason.LEGION_TITLE, "");
				}
			} else {
				human.getHumanLegionManager().amendLegionTitleProperty(
						human.getPropertyManager(), human.getLegionTitleId());
			}

			// 返回消息
			GCExchangeLegionTitle msg = new GCExchangeLegionTitle();
			msg.setLegionTitleInfo(globalLegionManager.generateTitleInfo(
					message.getTitleId(), legion));
			msg.setSelfMedal(legionMember.getMedal());
			human.sendMessage(msg);

			// 下发头衔信息
			human.getHumanLegionManager().sendHumanLegionTitleInfo();
		}
	}

}
