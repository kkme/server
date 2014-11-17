package com.hifun.soul.gameserver.escort.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 押运怪物模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EscortMonsterTemplateVO extends TemplateObject {

	/** 怪物名称 */
	@ExcelCellBinding(offset = 1)
	protected String monsterName;

	/** 金钱获得系数 */
	@ExcelCellBinding(offset = 2)
	protected float coinRate;

	/** 声望获得系数 */
	@ExcelCellBinding(offset = 3)
	protected float honorRate;

	/** 押运时间系数 */
	@ExcelCellBinding(offset = 4)
	protected float escortTimeRate;

	/** 图片ID */
	@ExcelCellBinding(offset = 5)
	protected int iconId;

	/** 模型ID */
	@ExcelCellBinding(offset = 6)
	protected int modelId;


	public String getMonsterName() {
		return this.monsterName;
	}

	public void setMonsterName(String monsterName) {
		if (StringUtils.isEmpty(monsterName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[怪物名称]monsterName不可以为空");
		}
		this.monsterName = monsterName;
	}
	
	public float getCoinRate() {
		return this.coinRate;
	}

	public void setCoinRate(float coinRate) {
		if (coinRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[金钱获得系数]coinRate不可以为0");
		}
		this.coinRate = coinRate;
	}
	
	public float getHonorRate() {
		return this.honorRate;
	}

	public void setHonorRate(float honorRate) {
		if (honorRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[声望获得系数]honorRate的值不得小于0");
		}
		this.honorRate = honorRate;
	}
	
	public float getEscortTimeRate() {
		return this.escortTimeRate;
	}

	public void setEscortTimeRate(float escortTimeRate) {
		if (escortTimeRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[押运时间系数]escortTimeRate不可以为0");
		}
		this.escortTimeRate = escortTimeRate;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[图片ID]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	
	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		if (modelId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[模型ID]modelId不可以为0");
		}
		this.modelId = modelId;
	}
	

	@Override
	public String toString() {
		return "EscortMonsterTemplateVO[monsterName=" + monsterName + ",coinRate=" + coinRate + ",honorRate=" + honorRate + ",escortTimeRate=" + escortTimeRate + ",iconId=" + iconId + ",modelId=" + modelId + ",]";

	}
}