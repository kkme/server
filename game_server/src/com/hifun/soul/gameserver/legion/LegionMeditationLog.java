package com.hifun.soul.gameserver.legion;

/**
 * 军团日志
 * 
 * @author yandajun
 */
public class LegionMeditationLog {
	private long id;
	private long legionId;
	private String content;
	private long operateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(long operateTime) {
		this.operateTime = operateTime;
	}

}
