package com.hifun.soul.gameserver.skill.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 技能效果模版抽象;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SkillEffectTemplate {

	@BeanFieldNumber(number = 1)
	private int effectId;

	@BeanFieldNumber(number = 2)
	private int param1;

	@BeanFieldNumber(number = 3)
	private int param2;

	@BeanFieldNumber(number = 4)
	private int param3;

	@BeanFieldNumber(number = 5)
	private int param4;

	@BeanFieldNumber(number = 6)
	private int param5;

	@BeanFieldNumber(number = 7)
	private int param6;

	public int getEffectId() {
		return effectId;
	}

	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}

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

	public int getParam3() {
		return param3;
	}

	public void setParam3(int param3) {
		this.param3 = param3;
	}

	public int getParam4() {
		return param4;
	}

	public void setParam4(int param4) {
		this.param4 = param4;
	}

	public int getParam5() {
		return param5;
	}

	public void setParam5(int param5) {
		this.param5 = param5;
	}

	public int[] formParams() {
		return new int[] { param1, param2, param3, param4, param5, param6 };
	}

	public int getParam6() {
		return param6;
	}

	public void setParam6(int param6) {
		this.param6 = param6;
	}

}
