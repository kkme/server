package com.hifun.soul.gameserver.stage;

import java.util.Map;

public class StageDramaStateInfo {
	private int stageId;
	private Map<TriggerType,Boolean> dramas;

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public Map<TriggerType, Boolean> getDramas() {
		return dramas;
	}

	public void setDramas(Map<TriggerType, Boolean> dramas) {
		this.dramas = dramas;
	}
	
}
