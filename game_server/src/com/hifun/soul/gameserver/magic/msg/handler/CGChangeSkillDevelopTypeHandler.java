package com.hifun.soul.gameserver.magic.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.magic.msg.CGChangeSkillDevelopType;
import com.hifun.soul.gameserver.magic.msg.GCChangeSkillDevelopType;
import com.hifun.soul.gameserver.player.Player;

/**
 * 请求换系;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGChangeSkillDevelopTypeHandler implements
		IMessageHandlerWithType<CGChangeSkillDevelopType> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CHANGE_SKILL_DEVELOP_TYPE;
	}

	@Override
	public void execute(CGChangeSkillDevelopType message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		final Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ATTACH_MAGIC, true)) {
			return;
		}
		// 是否有足够的钱
		int costCrystal = GameServerAssist.getGameConstants()
				.getChangeSkillDevelopCostCrystal();
		if (!human.getWallet().isEnough(CurrencyType.CRYSTAL, costCrystal)) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
					CurrencyType.CRYSTAL);
			return;
		}
		// 扣钱
		if (!human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.CHANGE_DEVELOP_TYPE, "")) {
			return;
		}
		// 转系
		doChangeSkillDevelopType(human, message.getSkillDevelopType());
	}

	/**
	 * 转系;
	 * 
	 * @param human
	 * @param skillDevelopType
	 */
	private void doChangeSkillDevelopType(Human human, int skillDevelopType) {
		human.setSkillDevelopType(skillDevelopType);
		// 重新计算属性
		human.getPropertyManager().recalculateInitProperties(human);
		GCChangeSkillDevelopType changeMessage = new GCChangeSkillDevelopType();
		changeMessage.setSkillDevelopType(skillDevelopType);
		human.sendMessage(changeMessage);
	}

}
