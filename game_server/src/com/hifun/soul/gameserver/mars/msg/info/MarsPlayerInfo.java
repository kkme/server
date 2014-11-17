package com.hifun.soul.gameserver.mars.msg.info;

import com.hifun.soul.gameserver.mars.MarsPlayerType;

/**
 * 战神之巅玩家信息
 * 
 * @author yandajun
 * 
 */
public class MarsPlayerInfo {
	private long playerId;
	private MarsPlayerType playerType;
	private String playerName;
	private int playerLevel;
	private int occupationType;
	private int todayKillValue;
	private int isLoser;
	private long killTime;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public MarsPlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(MarsPlayerType playerType) {
		this.playerType = playerType;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(int occupationType) {
		this.occupationType = occupationType;
	}

	public int getTodayKillValue() {
		return todayKillValue;
	}

	public void setTodayKillValue(int todayKillValue) {
		this.todayKillValue = todayKillValue;
	}

	public int getIsLoser() {
		return isLoser;
	}

	public void setIsLoser(int isLoser) {
		this.isLoser = isLoser;
	}

	public long getKillTime() {
		return killTime;
	}

	public void setKillTime(long killTime) {
		this.killTime = killTime;
	}

}
