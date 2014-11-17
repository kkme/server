package com.hifun.soul.gameserver.human.quest.aim;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 任务目标描述信息;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class AimInfo {
	@BeanFieldNumber(number = 1)
	private int aimType;
	@BeanFieldNumber(number = 2)
	private int param1;
	@BeanFieldNumber(number = 3)
	private int param2;

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}

	public int getParam2() {
		return param2;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

	public int getAimType() {
		return aimType;
	}

	public void setAimType(int aimType) {
		this.aimType = aimType;
	}
}
