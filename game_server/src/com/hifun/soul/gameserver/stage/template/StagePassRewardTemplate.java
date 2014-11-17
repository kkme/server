package com.hifun.soul.gameserver.stage.template;

import java.util.List;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo;

@ExcelRowBinding
public class StagePassRewardTemplate extends StagePassRewardTemplateVO {
	/** 完美通关奖励信息 */
	private List<StageStarRewardItemInfo> perfectRewardItemInfos;
	
	@Override
	public void check() throws TemplateConfigException {

	}

	public List<StageStarRewardItemInfo> getPerfectRewardItemInfos() {
		return perfectRewardItemInfos;
	}

	public void setPerfectRewardItemInfos(
			List<StageStarRewardItemInfo> perfectRewardItemInfos) {
		this.perfectRewardItemInfos = perfectRewardItemInfos;
	}
	
}
