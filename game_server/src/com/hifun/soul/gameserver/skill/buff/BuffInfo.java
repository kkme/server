package com.hifun.soul.gameserver.skill.buff;

public class BuffInfo {
	/** buffId */
	private int buffId;
	/** buff类型 */
	private int buffType;
	/** buff资源ID */
	private int buffResourceId;
	/** buff名称 */
	private String buffName;
	/** buff描述 */
	private String buffDesc;
	/** buff自身类型(buff or debuff) */
	private int buffSelfType;
	/** 持续的回合数 */
	private int round;

	public int getBuffType() {
		return buffType;
	}

	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}

	public String getBuffName() {
		return buffName;
	}

	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}

	public String getBuffDesc() {
		return buffDesc;
	}

	public void setBuffDesc(String buffDesc) {
		this.buffDesc = buffDesc;
	}

	public int getBuffSelfType() {
		return buffSelfType;
	}

	public void setBuffSelfType(int buffSelfType) {
		this.buffSelfType = buffSelfType;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	public int getBuffResourceId() {
		return buffResourceId;
	}

	public void setBuffResourceId(int buffResourceId) {
		this.buffResourceId = buffResourceId;
	}
}
