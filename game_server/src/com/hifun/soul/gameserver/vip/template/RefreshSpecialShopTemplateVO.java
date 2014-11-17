package com.hifun.soul.gameserver.vip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 刷新神秘商店模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RefreshSpecialShopTemplateVO extends TemplateObject {

	/**  重置花费的魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int crystal;

	/**  每次神秘商店可以魔晶刷新的次数 */
	@ExcelCellBinding(offset = 2)
	protected int time;


	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 重置花费的魔晶数]crystal的值不得小于0");
		}
		this.crystal = crystal;
	}
	
	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		if (time < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 每次神秘商店可以魔晶刷新的次数]time的值不得小于0");
		}
		this.time = time;
	}
	

	@Override
	public String toString() {
		return "RefreshSpecialShopTemplateVO[crystal=" + crystal + ",time=" + time + ",]";

	}
}