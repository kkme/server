package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class GMQueryPlayerStateStatistic extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	private long contextId;
	/** 建立网络会话中的人数 **/
	private int connectedCount;
	/** 已认证的人数 **/
	private int authronizedCount;
	/** 正在进入游戏中的人数 **/
	private int enteringCount;
	/** 游戏中的人数 **/
	private int gameingCount;
	/** 战斗中的人数 **/
	private int battlingCount;
	/** 退出中的人数 **/
	private int exitingCount;
	/** 未知状态的人数 **/
	private int otherCount;
	
	@Override
	public void execute() {
				
	}
	
	//#start properties

	@Override
	public long getContextId() {		
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		contextId=id;
	}
	

	public int getConnectedCount() {
		return connectedCount;
	}

	public void setConnectedCount(int connectedCount) {
		this.connectedCount = connectedCount;
	}

	public int getAuthronizedCount() {
		return authronizedCount;
	}

	public void setAuthronizedCount(int authronizedCount) {
		this.authronizedCount = authronizedCount;
	}

	public int getEnteringCount() {
		return enteringCount;
	}

	public void setEnteringCount(int enteringCount) {
		this.enteringCount = enteringCount;
	}

	public int getGameingCount() {
		return gameingCount;
	}

	public void setGameingCount(int gameingCount) {
		this.gameingCount = gameingCount;
	}

	public int getBattlingCount() {
		return battlingCount;
	}

	public void setBattlingCount(int battlingCount) {
		this.battlingCount = battlingCount;
	}

	public int getExitingCount() {
		return exitingCount;
	}

	public void setExitingCount(int exitingCount) {
		this.exitingCount = exitingCount;
	}
	public int getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	
	//#end

	@Override
	public short getType() {
		return MessageType.GM_QUERY_PLAYER_STATE_STATISTIC;
	}

	@Override
	protected boolean readImpl() {
		contextId=readLong();
		connectedCount=readInteger();
		authronizedCount=readInteger();
		enteringCount=readInteger();
		gameingCount=readInteger();
		battlingCount=readInteger();
		exitingCount=readInteger();
		otherCount=readInteger();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		writeInteger(connectedCount);
		writeInteger(authronizedCount);
		writeInteger(enteringCount);
		writeInteger(gameingCount);
		writeInteger(battlingCount);
		writeInteger(exitingCount);
		writeInteger(otherCount);
		return true;
	}

}
