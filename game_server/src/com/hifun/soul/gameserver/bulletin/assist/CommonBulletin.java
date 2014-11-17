package com.hifun.soul.gameserver.bulletin.assist;

public class CommonBulletin {
	private int id;
	private String content;
	private int showTime;
	private int level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getShowTime() {
		return showTime;
	}
	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
