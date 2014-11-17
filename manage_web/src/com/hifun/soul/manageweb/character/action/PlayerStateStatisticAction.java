package com.hifun.soul.manageweb.character.action;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.CharacterStatisticRpcService;
import com.hifun.soul.proto.services.Services.QueryPlayerStateStatisticRequest;
import com.hifun.soul.proto.services.Services.QueryPlayerStateStatisticResponse;

public class PlayerStateStatisticAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4650263166351498345L;
	
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
	/** 在线人数总计 **/
	private int totalCount;
	
	
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


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public String queryPlayerStateStatistic() throws ServiceException{		
		CharacterStatisticRpcService.BlockingInterface charStatService = Managers.getServiceManager().getCharStatisticService();
		QueryPlayerStateStatisticResponse response = charStatService.queryPlayerStateStatistic(null, QueryPlayerStateStatisticRequest.newBuilder().build());
		ManageLogger.log(ManageLogType.QUERY_PLAYER_STATE_STATISTIC,PermissionType.QUERY_PLAYER_STATE_STATISTIC, "", true);
		this.authronizedCount = response.getAuthronizedCount();
		this.battlingCount = response.getBattlingCount();
		this.connectedCount=response.getConnectedCount();
		this.enteringCount=response.getEnteringCount();
		this.exitingCount=response.getExitingCount();
		this.gameingCount=response.getGameingCount();
		this.otherCount=response.getOtherCount();
		this.totalCount= authronizedCount+battlingCount+connectedCount+enteringCount+exitingCount+gameingCount+otherCount;
		return "success";
	}

}
