package com.hifun.soul.gameserver.target.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;

/**
 * 个人目标模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TargetTemplateVO extends TemplateObject {

	/** 接收任务最小等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLowestLevel;

	/** 任务图标 */
	@ExcelCellBinding(offset = 2)
	protected int targetIcon;

	/** 前置目标ID */
	@ExcelCellBinding(offset = 3)
	protected int previousTargetId;

	/** 名称多语言 */
	@ExcelCellBinding(offset = 4)
	protected int nameLanguage;

	/** 任务名称 */
	@ExcelCellBinding(offset = 5)
	protected String targetName;

	/** 描述多语言 */
	@ExcelCellBinding(offset = 6)
	protected int descLanguage;

	/** 目标描述 */
	@ExcelCellBinding(offset = 7)
	protected String targetDesc;

	/** 任务描述多语言 */
	@ExcelCellBinding(offset = 8)
	protected int taskDescLanguage;

	/** 任务描述 */
	@ExcelCellBinding(offset = 9)
	protected String targetTaskDesc;

	/** 目标类型 */
	@ExcelCellBinding(offset = 10)
	protected int targetType;

	/** 目标参数 */
	@ExcelCellBinding(offset = 11)
	protected int targetParam;

	/** 目标奖励物品 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.target.template.TargetReward[].class, collectionNumber = "12,13;14,15;16,17;18,19")
	protected com.hifun.soul.gameserver.target.template.TargetReward[] targetRewards;


	public int getNeedLowestLevel() {
		return this.needLowestLevel;
	}

	public void setNeedLowestLevel(int needLowestLevel) {
		if (needLowestLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[接收任务最小等级]needLowestLevel不可以为0");
		}
		this.needLowestLevel = needLowestLevel;
	}
	
	public int getTargetIcon() {
		return this.targetIcon;
	}

	public void setTargetIcon(int targetIcon) {
		if (targetIcon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[任务图标]targetIcon不可以为0");
		}
		this.targetIcon = targetIcon;
	}
	
	public int getPreviousTargetId() {
		return this.previousTargetId;
	}

	public void setPreviousTargetId(int previousTargetId) {
		if (previousTargetId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[前置目标ID]previousTargetId的值不得小于0");
		}
		this.previousTargetId = previousTargetId;
	}
	
	public int getNameLanguage() {
		return this.nameLanguage;
	}

	public void setNameLanguage(int nameLanguage) {
		if (nameLanguage == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[名称多语言]nameLanguage不可以为0");
		}
		this.nameLanguage = nameLanguage;
	}
	
	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String targetName) {
		if (StringUtils.isEmpty(targetName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[任务名称]targetName不可以为空");
		}
		this.targetName = targetName;
	}
	
	public int getDescLanguage() {
		return this.descLanguage;
	}

	public void setDescLanguage(int descLanguage) {
		if (descLanguage == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[描述多语言]descLanguage不可以为0");
		}
		this.descLanguage = descLanguage;
	}
	
	public String getTargetDesc() {
		return this.targetDesc;
	}

	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc;
	}
	
	public int getTaskDescLanguage() {
		return this.taskDescLanguage;
	}

	public void setTaskDescLanguage(int taskDescLanguage) {
		if (taskDescLanguage == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[任务描述多语言]taskDescLanguage不可以为0");
		}
		this.taskDescLanguage = taskDescLanguage;
	}
	
	public String getTargetTaskDesc() {
		return this.targetTaskDesc;
	}

	public void setTargetTaskDesc(String targetTaskDesc) {
		this.targetTaskDesc = targetTaskDesc;
	}
	
	public int getTargetType() {
		return this.targetType;
	}

	public void setTargetType(int targetType) {
		if (targetType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[目标类型]targetType不可以为0");
		}
		this.targetType = targetType;
	}
	
	public int getTargetParam() {
		return this.targetParam;
	}

	public void setTargetParam(int targetParam) {
		if (targetParam == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[目标参数]targetParam不可以为0");
		}
		this.targetParam = targetParam;
	}
	
	public com.hifun.soul.gameserver.target.template.TargetReward[] getTargetRewards() {
		return this.targetRewards;
	}

	public void setTargetRewards(com.hifun.soul.gameserver.target.template.TargetReward[] targetRewards) {
		if (targetRewards == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[目标奖励物品]targetRewards不可以为空");
		}	
		this.targetRewards = targetRewards;
	}
	

	@Override
	public String toString() {
		return "TargetTemplateVO[needLowestLevel=" + needLowestLevel + ",targetIcon=" + targetIcon + ",previousTargetId=" + previousTargetId + ",nameLanguage=" + nameLanguage + ",targetName=" + targetName + ",descLanguage=" + descLanguage + ",targetDesc=" + targetDesc + ",taskDescLanguage=" + taskDescLanguage + ",targetTaskDesc=" + targetTaskDesc + ",targetType=" + targetType + ",targetParam=" + targetParam + ",targetRewards=" + targetRewards + ",]";

	}
}