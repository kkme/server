package com.hifun.soul.manageweb.character.action;

import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.CharacterStatisticRpcService;
import com.hifun.soul.proto.services.Services.LevelCounter;
import com.hifun.soul.proto.services.Services.OccupationCounter;
import com.hifun.soul.proto.services.Services.QueryCharacterDistributeRequest;
import com.hifun.soul.proto.services.Services.QueryCharacterDistributeResponse;

public class CharacterDistributeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 497572767560437642L;
	private List<OccupationCounter> occupationCounter;
	private List<LevelCounter> levelCounter;
	private long roleTotalNum=0;
	
	
	public List<OccupationCounter> getOccupationCounter() {
		return occupationCounter;
	}


	public void setOccupationCounter(List<OccupationCounter> occupationCounter) {
		this.occupationCounter = occupationCounter;
	}


	public List<LevelCounter> getLevelCounter() {
		return levelCounter;
	}


	public void setLevelCounter(List<LevelCounter> levelCounter) {
		this.levelCounter = levelCounter;
	}


	public long getRoleTotalNum() {
		return roleTotalNum;
	}


	public String queryCharacterDistribute() throws ServiceException{
		CharacterStatisticRpcService.BlockingInterface charStatService = Managers.getServiceManager().getCharStatisticService();	
		QueryCharacterDistributeResponse response = charStatService.queryCharacterDistribute(null, QueryCharacterDistributeRequest.newBuilder().build());
		occupationCounter = response.getOccupationCountList();
		levelCounter = response.getLevelCountList();
		ManageLogger.log(ManageLogType.QUERY_CHARACTER_DISTRIBUTE, PermissionType.QUERY_CHARACTER_DISTRIBUTE, "", true);
		roleTotalNum=0;
		if(occupationCounter!=null){
			for(OccupationCounter counter : occupationCounter){
				roleTotalNum+=counter.getCount();
			}
		}
		return "success";
	}

}
