package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 剧情模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageDramaTemplateVO extends TemplateObject {

	/**  关卡id */
	@ExcelCellBinding(offset = 1)
	protected int stageId;

	/**  剧情触发(1:战斗前;2:战斗胜利;3:战斗失败) */
	@ExcelCellBinding(offset = 2)
	protected int triggerType;

	/**  剧情顺序 */
	@ExcelCellBinding(offset = 3)
	protected int order;

	/**  剧情类型(1:对话;2:旁白;) */
	@ExcelCellBinding(offset = 4)
	protected int dramaType;

	/**  对话内容多语言 */
	@ExcelCellBinding(offset = 5)
	protected int contentLangId;

	/**  对话内容 */
	@ExcelCellBinding(offset = 6)
	protected String content;

	/**  人物名称多语言 */
	@ExcelCellBinding(offset = 7)
	protected int nameLangId;

	/**  人物名称 */
	@ExcelCellBinding(offset = 8)
	protected String name;

	/**  头像图标 */
	@ExcelCellBinding(offset = 9)
	protected int icon;

	/**  头像位置(1:左;2:右) */
	@ExcelCellBinding(offset = 10)
	protected int positionType;


	public int getStageId() {
		return this.stageId;
	}

	public void setStageId(int stageId) {
		if (stageId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 关卡id]stageId不可以为0");
		}
		if (stageId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 关卡id]stageId的值不得小于1");
		}
		this.stageId = stageId;
	}
	
	public int getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(int triggerType) {
		if (triggerType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 剧情触发(1:战斗前;2:战斗胜利;3:战斗失败)]triggerType不可以为0");
		}
		if (triggerType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 剧情触发(1:战斗前;2:战斗胜利;3:战斗失败)]triggerType的值不得小于1");
		}
		this.triggerType = triggerType;
	}
	
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		if (order == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 剧情顺序]order不可以为0");
		}
		if (order < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 剧情顺序]order的值不得小于1");
		}
		this.order = order;
	}
	
	public int getDramaType() {
		return this.dramaType;
	}

	public void setDramaType(int dramaType) {
		if (dramaType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 剧情类型(1:对话;2:旁白;)]dramaType不可以为0");
		}
		if (dramaType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 剧情类型(1:对话;2:旁白;)]dramaType的值不得小于1");
		}
		this.dramaType = dramaType;
	}
	
	public int getContentLangId() {
		return this.contentLangId;
	}

	public void setContentLangId(int contentLangId) {
		if (contentLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 对话内容多语言]contentLangId的值不得小于0");
		}
		this.contentLangId = contentLangId;
	}
	
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		if (StringUtils.isEmpty(content)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 对话内容]content不可以为空");
		}
		this.content = content;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 人物名称多语言]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 人物名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 头像图标]icon不可以为0");
		}
		if (icon < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 头像图标]icon的值不得小于1");
		}
		this.icon = icon;
	}
	
	public int getPositionType() {
		return this.positionType;
	}

	public void setPositionType(int positionType) {
		if (positionType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 头像位置(1:左;2:右)]positionType不可以为0");
		}
		if (positionType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 头像位置(1:左;2:右)]positionType的值不得小于1");
		}
		this.positionType = positionType;
	}
	

	@Override
	public String toString() {
		return "StageDramaTemplateVO[stageId=" + stageId + ",triggerType=" + triggerType + ",order=" + order + ",dramaType=" + dramaType + ",contentLangId=" + contentLangId + ",content=" + content + ",nameLangId=" + nameLangId + ",name=" + name + ",icon=" + icon + ",positionType=" + positionType + ",]";

	}
}