package com.hifun.soul.gameserver.stage.template;

import java.util.List;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo;

@ExcelRowBinding
public class StageStarRewardTemplate extends StageStarRewardTemplateVO {
	/** 下一级关卡评星奖励需要的星星数量 */
	private int nextRewardStar;
	/** 当前评星奖励信息 */
	private List<StageStarRewardItemInfo> stageStarRewardItemInfos;
	
	@Override
	public void check() throws TemplateConfigException {

	}

	public int getNextRewardStar() {
		return nextRewardStar;
	}

	public void setNextRewardStar(int nextRewardStar) {
		this.nextRewardStar = nextRewardStar;
	}

	public List<StageStarRewardItemInfo> getStageStarRewardItemInfos() {
		return stageStarRewardItemInfos;
	}

	public void setStageStarRewardItemInfos(
			List<StageStarRewardItemInfo> stageStarRewardItemInfos) {
		this.stageStarRewardItemInfos = stageStarRewardItemInfos;
	}

}
