package com.hifun.soul.gameserver.escort;

/**
 * 押运协助
 * 
 * @author yandajun
 * 
 */
public class EscortHelp {
	private long humanGuid;
	private int remainHelpNum;
	private int rewardCoin;
	private int rewardState;

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public int getRemainHelpNum() {
		return remainHelpNum;
	}

	public void setRemainHelpNum(int remainHelpNum) {
		this.remainHelpNum = remainHelpNum;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}
}
