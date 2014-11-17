package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团矿战矿位分布模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineTemplateVO extends TemplateObject {

	/** 是否是红矿 */
	@ExcelCellBinding(offset = 1)
	protected boolean isRedMine;

	/** 矿位类型 */
	@ExcelCellBinding(offset = 2)
	protected int mineType;

	/** 周围的矿位索引 */
	@ExcelCellBinding(offset = 3)
	protected String surroundIndexes;

	/** 可移动或战斗的矿位索引 */
	@ExcelCellBinding(offset = 4)
	protected String canMoveOrBattleIndexes;


	public boolean isIsRedMine() {
		return this.isRedMine;
	}

	public void setIsRedMine(boolean isRedMine) {
		this.isRedMine = isRedMine;
	}
	
	public int getMineType() {
		return this.mineType;
	}

	public void setMineType(int mineType) {
		if (mineType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[矿位类型]mineType不可以为0");
		}
		this.mineType = mineType;
	}
	
	public String getSurroundIndexes() {
		return this.surroundIndexes;
	}

	public void setSurroundIndexes(String surroundIndexes) {
		if (StringUtils.isEmpty(surroundIndexes)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[周围的矿位索引]surroundIndexes不可以为空");
		}
		this.surroundIndexes = surroundIndexes;
	}
	
	public String getCanMoveOrBattleIndexes() {
		return this.canMoveOrBattleIndexes;
	}

	public void setCanMoveOrBattleIndexes(String canMoveOrBattleIndexes) {
		if (StringUtils.isEmpty(canMoveOrBattleIndexes)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[可移动或战斗的矿位索引]canMoveOrBattleIndexes不可以为空");
		}
		this.canMoveOrBattleIndexes = canMoveOrBattleIndexes;
	}
	

	@Override
	public String toString() {
		return "LegionMineTemplateVO[isRedMine=" + isRedMine + ",mineType=" + mineType + ",surroundIndexes=" + surroundIndexes + ",canMoveOrBattleIndexes=" + canMoveOrBattleIndexes + ",]";

	}
}