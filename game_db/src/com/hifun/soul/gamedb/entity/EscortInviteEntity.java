package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_escort_invite")
public class EscortInviteEntity extends BaseCommonEntity {
	/**
	 * 角色ID
	 */
	@Id
	@Column
	private long id;
	@Column
	private long inviteFriendId;
	@Column
	private int inviteState;
	@Column
	private long agreeEndTime;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
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
