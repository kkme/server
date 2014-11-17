package com.hifun.soul.gameserver.prison;

/**
 * 战俘营日志
 * 
 * @author yandajun
 * 
 */
public class PrisonLog {
	private long id;

	private int logType;

	private long firstId;
	private String firstName;
	private long secondId;
	private String secondName;
	private long thirdId;
	private String thirdName;
	private String content;

	private int result;

	private long operateTime;

	private int interactType;

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFirstId() {
		return firstId;
	}

	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public long getThirdId() {
		return thirdId;
	}

	public void setThirdId(long thirdId) {
		this.thirdId = thirdId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(long operateTime) {
		this.operateTime = operateTime;
	}

	public int getInteractType() {
		return interactType;
	}

	public void setInteractType(int interactType) {
		this.interactType = interactType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

}
