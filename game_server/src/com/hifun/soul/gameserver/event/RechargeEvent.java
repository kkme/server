package com.hifun.soul.gameserver.event;

import com.hifun.soul.gameserver.recharge.Recharge;

/**
 * 充值事件;
 * 
 * @author yandajun
 * 
 */
public class RechargeEvent implements IEvent {
	private Recharge recharge;

	@Override
	public EventType getType() {
		return EventType.RECHARGE_EVENT;
	}

	public Recharge getRecharge() {
		return recharge;
	}

	public void setRecharge(Recharge recharge) {
		this.recharge = recharge;
	}

}
