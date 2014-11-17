package com.hifun.soul.payserver;

import com.hifun.soul.payserver.msg.PSScheduleMessage;

/**
 * 支付信息;
 * 
 * @author crazyjohn
 * 
 */
public class PayInfo {
	private String token;
	private String serverIp;
	private String openId;
	private int port;
	private long humanGUID;
	private String openKey;
	private PSScheduleMessage schedule;

	public PayInfo(String token, String serverIp, String openId, int port,
			long humanGUID, String openKey, PSScheduleMessage schedule) {
		this.token = token;
		this.serverIp = serverIp;
		this.openId = openId;
		this.port = port;
		this.humanGUID = humanGUID;
		this.openKey = openKey;
		this.setSchedule(schedule);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getHumanGUID() {
		return humanGUID;
	}

	public void setHumanGUID(long humanGUID) {
		this.humanGUID = humanGUID;
	}

	public String getOpenKey() {
		return openKey;
	}

	public void setOpenKey(String openKey) {
		this.openKey = openKey;
	}

	public PSScheduleMessage getSchedule() {
		return schedule;
	}

	public void setSchedule(PSScheduleMessage schedule) {
		this.schedule = schedule;
	}

}
