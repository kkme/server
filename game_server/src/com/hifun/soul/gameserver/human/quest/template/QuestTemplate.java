package com.hifun.soul.gameserver.human.quest.template;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.util.StringUtils;
import com.hifun.soul.gameserver.human.quest.QuestType;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;
import com.hifun.soul.gameserver.human.quest.logic.MainQuestLogic;
import com.hifun.soul.gameserver.human.quest.logic.DailyQuestLogic;
import com.hifun.soul.gameserver.human.quest.logic.IQuestLogic;

/**
 * 主线任务模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class QuestTemplate extends QuestTemplateVO {
	private IQuestLogic checkerLogic;

	
	@Override
	public void setQuestType(int questType) {
		if (questType == QuestType.QUEST_DAILY.getQuestType()) {
			checkerLogic = new DailyQuestLogic();
		} else {
			checkerLogic = new MainQuestLogic();
		}
		super.setQuestType(questType);
	}

	@Override
	public void check() throws TemplateConfigException {

	}

	public IQuestLogic getQuestCheckerLogic() {
		return checkerLogic;
	}


	/**
	 * 获取所有前置任务ID;
	 * 
	 * @return 不会返回NULL;
	 */
	public Set<Integer> getPreQuestIdSet() {
		Set<Integer> results = new HashSet<Integer>(StringUtils.getIntList(
				this.preQuestIds, ","));
		return results;
	}

	@Override
	public List<AimInfo> getAimInfoList() {
		if (this.aimInfoList == null) {
			return new ArrayList<AimInfo>();
		}
		return super.getAimInfoList();
	}

	@Override
	public void setAimInfoList(List<AimInfo> aimInfoList) {
		List<AimInfo> result = new ArrayList<AimInfo>();
		if (aimInfoList == null || aimInfoList.isEmpty()) {
			return;
		}
		for (AimInfo aim : aimInfoList) {
			if (aim.getAimType() == MainQuestAimType.INVALID_TYPE.getAimType()) {
				continue;
			}
			result.add(aim);
		}
		super.setAimInfoList(result);
	}

}
