package com.hifun.soul.gameserver.escort.info;

/**
 * 押运固定时间收益加成信息
 * 
 * @author yandajun
 * 
 */
public class EscortAmendInfo {
	private String amendDesc;
	private int remainTime;

	public String getAmendDesc() {
		return amendDesc;
	}

	public void setAmendDesc(String amendDesc) {
		this.amendDesc = amendDesc;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
}
