package com.hifun.soul.gameserver.abattoir.manager;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.abattoir.msg.GCBuyAbattoirWrestleNum;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.AbattoirChallengeEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;

public class HumanAbattoirManager implements ILoginManager {
	private Human human;
	private AbattoirTemplateManager templateManager;

	public HumanAbattoirManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		templateManager = GameServerAssist.getAbattoirTemplateManager();
	}

	@Override
	public void onLogin() {

	}

	/**
	 * 购买角斗次数
	 */
	public void buyWrestleNum() {
		// 花费魔晶
		int costNum = templateManager.getBuyWrestleNumCost(human
				.getAbattoirBuyNum() + 1);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.ABATTOIR_BUY_WRESTLE_NUM, "")) {
			human.setAbattoirBuyNum(human.getAbattoirBuyNum() + 1);
			human.setAbattoirRemainNum(human.getAbattoirRemainNum() + 1);
			GCBuyAbattoirWrestleNum msg = new GCBuyAbattoirWrestleNum();
			msg.setNextBuyNumCost(templateManager.getBuyWrestleNumCost(human
					.getAbattoirBuyNum() + 1));
			msg.setRemainWrestleNum(human.getAbattoirRemainNum());
			human.sendMessage(msg);
		}
	}

	/**
	 * 重置角色每日在角斗场的数据
	 */
	public void resetHumanAbattoirData() {
		human.setAbattoirBuyNum(0);
		human.setAbattoirRemainNum(SharedConstants.ABATTOIR_FREE_BUY_NUM);
	}

	public void setLastResetDataTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_ABATTOIR_DATA_TIME, lastTime);
	}

	public long getLastResetDataTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_ABATTOIR_DATA_TIME);
	}

	/**
	 * 发送角斗场挑战事件
	 */
	public void fireChallengeEvent() {
		human.getEventBus().fireEvent(new AbattoirChallengeEvent());
	}
}
