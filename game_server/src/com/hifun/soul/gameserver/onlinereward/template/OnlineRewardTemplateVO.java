package com.hifun.soul.gameserver.onlinereward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 在线奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class OnlineRewardTemplateVO extends TemplateObject {

	/**  次数 */
	@ExcelCellBinding(offset = 1)
	protected int times;

	/**  职业 */
	@ExcelCellBinding(offset = 2)
	protected int occupation;

	/**  CD时间(分) */
	@ExcelCellBinding(offset = 3)
	protected int cd;

	/**  奖励物品id */
	@ExcelCellBinding(offset = 4)
	protected int itemId;


	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 次数]times的值不得小于0");
		}
		this.times = times;
	}
	
	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 职业]occupation的值不得小于0");
		}
		this.occupation = occupation;
	}
	
	public int getCd() {
		return this.cd;
	}

	public void setCd(int cd) {
		if (cd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ CD时间(分)]cd的值不得小于0");
		}
		this.cd = cd;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 奖励物品id]itemId不可以为0");
		}
		if (itemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 奖励物品id]itemId的值不得小于1");
		}
		this.itemId = itemId;
	}
	

	@Override
	public String toString() {
		return "OnlineRewardTemplateVO[times=" + times + ",occupation=" + occupation + ",cd=" + cd + ",itemId=" + itemId + ",]";

	}
}