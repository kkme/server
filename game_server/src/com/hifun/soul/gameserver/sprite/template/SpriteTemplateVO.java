package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 精灵模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteTemplateVO extends TemplateObject {

	/**  精灵图标id */
	@ExcelCellBinding(offset = 1)
	protected int iconId;

	/**  精灵名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  多语言名称id */
	@ExcelCellBinding(offset = 3)
	protected int nameLangId;

	/**  精灵描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  多语言描述id */
	@ExcelCellBinding(offset = 5)
	protected int descLangId;

	/**  精灵品质 */
	@ExcelCellBinding(offset = 6)
	protected int quality;

	/**  精灵类型 */
	@ExcelCellBinding(offset = 7)
	protected int spriteType;

	/**  所在酒馆的页面id */
	@ExcelCellBinding(offset = 8)
	protected int pubPageId;

	/**  对酒产出精魂类型 */
	@ExcelCellBinding(offset = 9)
	protected int spriteSoulType;

	/**  对酒产出精魂数量 */
	@ExcelCellBinding(offset = 10)
	protected int spriteSoulNum;

	/**  普通对酒中出现概率 */
	@ExcelCellBinding(offset = 11)
	protected int commonFingerGuessRatio;

	/**  高级对酒中出现概率 */
	@ExcelCellBinding(offset = 12)
	protected int superFingerGuessRatio;

	/**  放弃返回的精魂类型 */
	@ExcelCellBinding(offset = 13)
	protected int dropReturnSoulType;

	/**  放弃返回的精魂数量 */
	@ExcelCellBinding(offset = 14)
	protected int dropReturnSoulNum;


	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 精灵图标id]iconId的值不得小于0");
		}
		this.iconId = iconId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 精灵名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 多语言名称id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 精灵描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 多语言描述id]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public int getQuality() {
		return this.quality;
	}

	public void setQuality(int quality) {
		if (quality < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 精灵品质]quality的值不得小于0");
		}
		this.quality = quality;
	}
	
	public int getSpriteType() {
		return this.spriteType;
	}

	public void setSpriteType(int spriteType) {
		if (spriteType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 精灵类型]spriteType的值不得小于0");
		}
		this.spriteType = spriteType;
	}
	
	public int getPubPageId() {
		return this.pubPageId;
	}

	public void setPubPageId(int pubPageId) {
		if (pubPageId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 所在酒馆的页面id]pubPageId的值不得小于0");
		}
		this.pubPageId = pubPageId;
	}
	
	public int getSpriteSoulType() {
		return this.spriteSoulType;
	}

	public void setSpriteSoulType(int spriteSoulType) {
		if (spriteSoulType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 对酒产出精魂类型]spriteSoulType的值不得小于0");
		}
		this.spriteSoulType = spriteSoulType;
	}
	
	public int getSpriteSoulNum() {
		return this.spriteSoulNum;
	}

	public void setSpriteSoulNum(int spriteSoulNum) {
		if (spriteSoulNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 对酒产出精魂数量]spriteSoulNum的值不得小于0");
		}
		this.spriteSoulNum = spriteSoulNum;
	}
	
	public int getCommonFingerGuessRatio() {
		return this.commonFingerGuessRatio;
	}

	public void setCommonFingerGuessRatio(int commonFingerGuessRatio) {
		if (commonFingerGuessRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 普通对酒中出现概率]commonFingerGuessRatio的值不得小于0");
		}
		this.commonFingerGuessRatio = commonFingerGuessRatio;
	}
	
	public int getSuperFingerGuessRatio() {
		return this.superFingerGuessRatio;
	}

	public void setSuperFingerGuessRatio(int superFingerGuessRatio) {
		if (superFingerGuessRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 高级对酒中出现概率]superFingerGuessRatio的值不得小于0");
		}
		this.superFingerGuessRatio = superFingerGuessRatio;
	}
	
	public int getDropReturnSoulType() {
		return this.dropReturnSoulType;
	}

	public void setDropReturnSoulType(int dropReturnSoulType) {
		if (dropReturnSoulType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 放弃返回的精魂类型]dropReturnSoulType的值不得小于0");
		}
		this.dropReturnSoulType = dropReturnSoulType;
	}
	
	public int getDropReturnSoulNum() {
		return this.dropReturnSoulNum;
	}

	public void setDropReturnSoulNum(int dropReturnSoulNum) {
		if (dropReturnSoulNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 放弃返回的精魂数量]dropReturnSoulNum的值不得小于0");
		}
		this.dropReturnSoulNum = dropReturnSoulNum;
	}
	

	@Override
	public String toString() {
		return "SpriteTemplateVO[iconId=" + iconId + ",name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",quality=" + quality + ",spriteType=" + spriteType + ",pubPageId=" + pubPageId + ",spriteSoulType=" + spriteSoulType + ",spriteSoulNum=" + spriteSoulNum + ",commonFingerGuessRatio=" + commonFingerGuessRatio + ",superFingerGuessRatio=" + superFingerGuessRatio + ",dropReturnSoulType=" + dropReturnSoulType + ",dropReturnSoulNum=" + dropReturnSoulNum + ",]";

	}
}