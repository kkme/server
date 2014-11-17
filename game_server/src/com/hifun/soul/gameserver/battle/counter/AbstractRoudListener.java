package com.hifun.soul.gameserver.battle.counter;

public class AbstractRoudListener implements IRoundListener {
	protected int times = 0;

	@Override
	public void onRoundFinished() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void recordAction() {
		times++;
	}

	@Override
	public int getActionTimes() {
		return times;
	}

}
