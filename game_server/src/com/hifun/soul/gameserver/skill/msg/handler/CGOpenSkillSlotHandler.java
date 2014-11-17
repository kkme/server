package com.hifun.soul.gameserver.skill.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.msg.CGOpenSkillSlot;
import com.hifun.soul.gameserver.skill.msg.GCOpenSkillSlotResult;
import com.hifun.soul.gameserver.skill.slot.SkillSlot;

/**
 * 等级满足条件下开启技能栏;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenSkillSlotHandler implements
		IMessageHandlerWithType<CGOpenSkillSlot> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_SKILL_SLOT;
	}

	@Override
	public void execute(CGOpenSkillSlot message) {
		// 先判断等级是否满足
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SKILL, true)){
			return;
		}
		SkillSlot slot = human.getSkillManager().getCanOpenSkillSlot();
		if (slot == null) {
			return;
		}
		int needLevel = slot.getTemplate().getHumanLevel();
		if (human.getLevel() < needLevel) {
			human.sendErrorMessage(LangConstants.NOT_OPEN_SLOT_CONDITION);
			return;
		}
		// 金币是否足够
		if (!human.getWallet().isEnough(CurrencyType.COIN,
				slot.getTemplate().getCostCoin())) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
					CurrencyType.COIN.getDesc());
			return;
		}
		boolean openResult = human.getSkillManager().openSlot(
				message.getSlotIndex());
		if (!openResult) {
			return;
		}
		// 花费金币
		boolean costResult = human.getWallet().costMoney(CurrencyType.COIN,
				slot.getTemplate().getCostCoin(),
				MoneyLogReason.OPEN_SKILL_SLOT, "");
		if (!costResult) {
			return;
		}
		// 发送开启结果
		GCOpenSkillSlotResult openMsg = new GCOpenSkillSlotResult();
		openMsg.setResult(true);
		openMsg.setSlotIndex(message.getSlotIndex());
		human.sendMessage(openMsg);
	}

}
