package com.hifun.soul.gameserver.cd.msg;

/**
 * 
 * cd队列信息
 * 
 * @author magicstone
 *
 */
public class CdQueueInfo {

	/** cd队列类型 */
	private int cdType;
	/** 时间差 */
	private int timeDiff;

	public int getCdType() {
		return cdType;
	}

	public void setCdType(int cdType) {
		this.cdType = cdType;
	}

	public int getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}
}
