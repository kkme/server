package com.hifun.soul.gameserver.escort;

/**
 * 押运邀请
 * 
 * @author yandajun
 * 
 */
public class EscortInvite {
	private long humanGuid;
	private long inviteFriendId;
	private int inviteState;
	private long agreeEndTime;

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public long getInviteFriendId() {
		return inviteFriendId;
	}

	public void setInviteFriendId(long inviteFriendId) {
		this.inviteFriendId = inviteFriendId;
	}

	public int getInviteState() {
		return inviteState;
	}

	public void setInviteState(int inviteState) {
		this.inviteState = inviteState;
	}

	public long getAgreeEndTime() {
		return agreeEndTime;
	}

	public void setAgreeEndTime(long agreeEndTime) {
		this.agreeEndTime = agreeEndTime;
	}
}
