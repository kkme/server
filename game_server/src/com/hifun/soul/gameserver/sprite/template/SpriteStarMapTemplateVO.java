package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 精灵星图模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteStarMapTemplateVO extends TemplateObject {

	/**  星图名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/**  多语言名称id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/**  开启星图需要的玩家等级 */
	@ExcelCellBinding(offset = 3)
	protected int openLevel;

	/**  下一张星图id */
	@ExcelCellBinding(offset = 4)
	protected int nextStarMapId;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 星图名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 多语言名称id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 开启星图需要的玩家等级]openLevel的值不得小于0");
		}
		this.openLevel = openLevel;
	}
	
	public int getNextStarMapId() {
		return this.nextStarMapId;
	}

	public void setNextStarMapId(int nextStarMapId) {
		if (nextStarMapId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 下一张星图id]nextStarMapId的值不得小于0");
		}
		this.nextStarMapId = nextStarMapId;
	}
	

	@Override
	public String toString() {
		return "SpriteStarMapTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",openLevel=" + openLevel + ",nextStarMapId=" + nextStarMapId + ",]";

	}
}