package com.hifun.soul.gameserver.legionmine;

public class SelfBuf {
	private String bufName;
	private int selfBufType;
	private int bufIcon;
	private String bufDesc;
	/** 是否正在使用中 */
	private boolean using;

	public String getBufName() {
		return bufName;
	}

	public void setBufName(String bufName) {
		this.bufName = bufName;
	}

	public int getSelfBufType() {
		return selfBufType;
	}

	public void setSelfBufType(int selfBufType) {
		this.selfBufType = selfBufType;
	}

	public int getBufIcon() {
		return bufIcon;
	}

	public void setBufIcon(int bufIcon) {
		this.bufIcon = bufIcon;
	}

	public String getBufDesc() {
		return bufDesc;
	}

	public void setBufDesc(String bufDesc) {
		this.bufDesc = bufDesc;
	}

	public boolean getUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}
}
