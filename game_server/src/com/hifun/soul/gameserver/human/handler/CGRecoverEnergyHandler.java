package com.hifun.soul.gameserver.human.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.CGRecoverEnergy;
import com.hifun.soul.gameserver.human.msg.GCRecoverEnergy;

@Component
public class CGRecoverEnergyHandler implements
		IMessageHandlerWithType<CGRecoverEnergy> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RECOVER_ENERGY;
	}

	@Override
	public void execute(CGRecoverEnergy message) {
		Human human = message.getPlayer().getHuman();
		// 判断是否还有恢复次数
		if (human.getTotalRecoverEnergyNum() <= 0) {
			human.sendWarningMessage(LangConstants.HAS_NO_ENERGY_RECOVER_NUM);
			return;
		}
		// 判断体力是否已满
		int maxEnergy = GameServerAssist.getGameConstants().getMaxEnergy();
		if (human.getEnergy() >= maxEnergy) {
			human.sendWarningMessage(LangConstants.ENERGY_IS_FULL);
			return;
		}
		// 是否在CD中
		HumanCdManager cdManager = human.getHumanCdManager();
		long spendTime = cdManager.getSpendTime(CdType.ENERGY_RECOVER, 0);
		if (!cdManager.canAddCd(CdType.ENERGY_RECOVER, spendTime)) {
			human.sendWarningMessage(LangConstants.CD_LIMIT);
			return;
		}

		// 恢复体力
		int addEnergy = GameServerAssist.getGameConstants()
				.getEnergyHandRecoverNum();
		if (human.getEnergy() + addEnergy > maxEnergy) {
			addEnergy = maxEnergy - human.getEnergy();
		}
		human.setEnergy(addEnergy + human.getEnergy(),
				EnergyLogReason.ENERGY_HAND_RECOVER, "");
		human.setDayRecoverEnergyNum(human.getDayRecoverEnergyNum() + 1);
		human.setTotalRecoverEnergyNum(human.getTotalRecoverEnergyNum() - 1);
		human.sendSuccessMessage(LangConstants.ENERGY_RECOVER_NUM, addEnergy);
		// 返回消息
		GCRecoverEnergy msg = new GCRecoverEnergy();
		msg.setRemainRecoverNum(human.getTotalRecoverEnergyNum());
		human.sendMessage(msg);
		// 添加CD
		cdManager.addCd(CdType.ENERGY_RECOVER, spendTime);
		cdManager.snapCdQueueInfo(CdType.ENERGY_RECOVER);
	}
}
