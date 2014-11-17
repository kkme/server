package com.hifun.soul.gameserver.battle;

import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;

public class PendingBattleRequest {
	private long challengerGuid;
	private long beAttackedGuid;
	private long pendingTime;
	private PVPBattleCallback callback;

	public PendingBattleRequest(long challengerGuid, long beAttackedGuid,
			long pendingTime, PVPBattleCallback callback) {
		this.challengerGuid = challengerGuid;
		this.beAttackedGuid = beAttackedGuid;
		this.pendingTime = pendingTime;
		this.callback = callback;
	}

	public long getChallengerGuid() {
		return challengerGuid;
	}

	public void setChallengerGuid(long challengerGuid) {
		this.challengerGuid = challengerGuid;
	}

	public long getBeAttackedGuid() {
		return beAttackedGuid;
	}

	public void setBeAttackedGuid(long beAttackedGuid) {
		this.beAttackedGuid = beAttackedGuid;
	}

	public long getPendingTime() {
		return pendingTime;
	}

	public void setPendingTime(long pendingTime) {
		this.pendingTime = pendingTime;
	}

	public PVPBattleCallback getCallback() {
		return callback;
	}

	public void setCallback(PVPBattleCallback callback) {
		this.callback = callback;
	}
}
