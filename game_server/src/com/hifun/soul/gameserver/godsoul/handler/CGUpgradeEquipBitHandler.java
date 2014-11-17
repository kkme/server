package com.hifun.soul.gameserver.godsoul.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.godsoul.manager.GodsoulTemplateManager;
import com.hifun.soul.gameserver.godsoul.msg.CGUpgradeEquipBit;
import com.hifun.soul.gameserver.godsoul.template.EquipBitLevelTemplate;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperLevelTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 强化装备位
 * 
 * @author yandajun
 * 
 */
@Component
public class CGUpgradeEquipBitHandler implements
		IMessageHandlerWithType<CGUpgradeEquipBit> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_EQUIP_BIT;
	}

	@Override
	public void execute(CGUpgradeEquipBit message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GODSOUL, true)) {
			return;
		}
		GodsoulTemplateManager godsoulTemplateManager = GameServerAssist
				.getGodsoulTemplateManager();
		int equipType = message.getEquipBitType();
		int currentEquipBitLevel = human.getHumanGodsoulManager()
				.getEquipBitLevel(equipType);
		// 判断是否升到顶级
		if (currentEquipBitLevel >= godsoulTemplateManager
				.getEquipBitMaxLevel()) {
			human.sendErrorMessage(LangConstants.UPGRADE_EQUIP_BIT_TO_MAX);
			return;
		}
		// 灵图等级决定神魄强化上限
		int currentPaperLevel = human.getHumanGodsoulManager()
				.getMagicPaperCurrentLevel(equipType);
		MagicPaperLevelTemplate magicPaperTemplate = GameServerAssist
				.getGodsoulTemplateManager().getMagicPaperTemplate(equipType,
						currentPaperLevel);
		if (magicPaperTemplate == null
				|| currentEquipBitLevel >= magicPaperTemplate
						.getMaxEquipBitLevel()) {
			human.sendWarningMessage(LangConstants.UPGRADE_EQUIP_BIT_TO_PAPER_MAX);
			return;
		}
		EquipBitLevelTemplate equipBitLevelTemplate = godsoulTemplateManager
				.getEquipBitLevelTemplate(currentEquipBitLevel);
		int needHumanLevel = equipBitLevelTemplate.getNeedHumanLevel();
		// 判断等级是否足够
		if (human.getLevel() < needHumanLevel) {
			human.sendErrorMessage(LangConstants.UPGRADE_EQUIP_BIT_LEVEL_NOT_ENOUGH);
			return;
		}
		// 判断金币是否足够
		int needCoin = human.getHumanGodsoulManager().getNeedCoin(equipType,
				currentEquipBitLevel);
		if (human.getCoin() < needCoin) {
			human.sendErrorMessage(LangConstants.UPGRADE_EQUIP_BIT_COIN_NOT_ENOUGH);
			return;
		}
		// 根据魔晶选择成功率
		if (message.getIsSelectedCrystal()) {
			// 花费魔晶 + 金币
			if (!human.getWallet().costMoney(CurrencyType.COIN, needCoin,
					MoneyLogReason.GODSOUL_UPGRADE, "")
					&& !human.getWallet().costMoney(
							CurrencyType.CRYSTAL,
							GameServerAssist.getGameConstants()
									.getUpgradeCostCrystal(),
							MoneyLogReason.GODSOUL_UPGRADE, "")) {
				return;
			}
		} else {
			// 花费金币
			if (!human.getWallet().costMoney(CurrencyType.COIN, needCoin,
					MoneyLogReason.GODSOUL_UPGRADE, "")) {
				return;
			}
		}
		human.getHumanGodsoulManager().upgradeEquipBit(
				message.getEquipBitType(), message.getIsSelectedCrystal());
	}

}
