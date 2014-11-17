package com.hifun.soul.gameserver.legion.handler;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.service.DirtFilterService;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGCreateLegion;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;

/**
 * 创建军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGCreateLegionHandler implements
		IMessageHandlerWithType<CGCreateLegion> {
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private DirtFilterService dirtFilterService;

	@Override
	public short getMessageType() {
		return MessageType.CG_CREATE_LEGION;
	}

	@Override
	public void execute(CGCreateLegion message) {
		Human human = message.getPlayer().getHuman();
		// 1 判断功能是否开启
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.LEGION, true)) {
			return;
		}
		// 判断是否达到等级
		LegionConstantsTemplate legionConstantsTemplate = GameServerAssist
				.getLegionTemplateManager().getConstantsTemplate();
		if (human.getLevel() < legionConstantsTemplate
				.getCreateLegionNeedLevel()) {
			human.sendErrorMessage(
					LangConstants.CREATE_LEGION_LEVEL_NOT_ENOUGH,
					legionConstantsTemplate.getCreateLegionNeedLevel());
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 2 是否已加入军团
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion != null) {
			human.sendErrorMessage(LangConstants.CREATE_LEGION_IS_JOINED_LEGION);
			return;
		}
		// 3 名称是否重复、合法
		String legionName = message.getLegionName();
		if (StringUtils.isEmpty(legionName)) {
			human.sendErrorMessage(LangConstants.LEGION_NAME_CAN_NOT_EMPTY);
			return;
		}
		if (legionName.length() > legionConstantsTemplate
				.getLegionNameMaxLength()) {
			human.sendErrorMessage(LangConstants.LEGION_NAME_TOO_LANG,
					legionConstantsTemplate.getLegionNameMaxLength());
			return;
		}
		if (dirtFilterService.containsName(legionName)) {
			human.sendErrorMessage(LangConstants.LEGION_NAME_INPUT_ERROR);
			return;
		}
		if (globalLegionManager.isExistLegionName(legionName)) {
			human.sendErrorMessage(LangConstants.LEGION_NAME_IS_EXIST);
			return;
		}
		// 4 金币是否足够
		int humanCoin = human.getCoin();
		int needCoin = legionConstantsTemplate.getCreateLegionNeedCoin();
		if (humanCoin < needCoin) {
			human.sendErrorMessage(LangConstants.CREATE_LEGION_COIN_NOT_ENOUGH,
					needCoin);
			return;
		}
		// 5 创建军团
		if (human.getWallet().costMoney(CurrencyType.COIN, needCoin,
				MoneyLogReason.CREATE_LEGION, "")) {
			globalLegionManager.createLegion(human, legionName);
		}

	}
}
