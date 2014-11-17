package com.hifun.soul.gameserver.training.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 训练模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TrainingTemplateVO extends TemplateObject {

	/** 主角等级 */
	@ExcelCellBinding(offset = 1)
	protected int roleLevel;

	/** 普通训练单位时间内获得经验值（按分钟计） */
	@ExcelCellBinding(offset = 2)
	protected float normalTrainingExpUnit;

	/** 普通训练1消耗的培养币数量 */
	@ExcelCellBinding(offset = 3)
	protected int normalTrainingCost1;

	/** 普通训练2消耗的培养币数量 */
	@ExcelCellBinding(offset = 4)
	protected int normalTrainingCost2;

	/** 普通训练3消耗的培养币数量 */
	@ExcelCellBinding(offset = 5)
	protected int normalTrainingCost3;

	/** 普通训练4消耗的培养币数量 */
	@ExcelCellBinding(offset = 6)
	protected int normalTrainingCost4;

	/** vip训练1获得的经验值 */
	@ExcelCellBinding(offset = 7)
	protected int vipTrainingExp1;

	/** vip训练2获得的经验值 */
	@ExcelCellBinding(offset = 8)
	protected int vipTrainingExp2;

	/** vip训练3获得的经验值 */
	@ExcelCellBinding(offset = 9)
	protected int vipTrainingExp3;

	/** vip训练4获得的经验值 */
	@ExcelCellBinding(offset = 10)
	protected int vipTrainingExp4;


	public int getRoleLevel() {
		return this.roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		if (roleLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[主角等级]roleLevel不可以为0");
		}
		this.roleLevel = roleLevel;
	}
	
	public float getNormalTrainingExpUnit() {
		return this.normalTrainingExpUnit;
	}

	public void setNormalTrainingExpUnit(float normalTrainingExpUnit) {
		if (normalTrainingExpUnit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[普通训练单位时间内获得经验值（按分钟计）]normalTrainingExpUnit的值不得小于0");
		}
		this.normalTrainingExpUnit = normalTrainingExpUnit;
	}
	
	public int getNormalTrainingCost1() {
		return this.normalTrainingCost1;
	}

	public void setNormalTrainingCost1(int normalTrainingCost1) {
		if (normalTrainingCost1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[普通训练1消耗的培养币数量]normalTrainingCost1的值不得小于0");
		}
		this.normalTrainingCost1 = normalTrainingCost1;
	}
	
	public int getNormalTrainingCost2() {
		return this.normalTrainingCost2;
	}

	public void setNormalTrainingCost2(int normalTrainingCost2) {
		if (normalTrainingCost2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[普通训练2消耗的培养币数量]normalTrainingCost2的值不得小于0");
		}
		this.normalTrainingCost2 = normalTrainingCost2;
	}
	
	public int getNormalTrainingCost3() {
		return this.normalTrainingCost3;
	}

	public void setNormalTrainingCost3(int normalTrainingCost3) {
		if (normalTrainingCost3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[普通训练3消耗的培养币数量]normalTrainingCost3的值不得小于0");
		}
		this.normalTrainingCost3 = normalTrainingCost3;
	}
	
	public int getNormalTrainingCost4() {
		return this.normalTrainingCost4;
	}

	public void setNormalTrainingCost4(int normalTrainingCost4) {
		if (normalTrainingCost4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[普通训练4消耗的培养币数量]normalTrainingCost4的值不得小于0");
		}
		this.normalTrainingCost4 = normalTrainingCost4;
	}
	
	public int getVipTrainingExp1() {
		return this.vipTrainingExp1;
	}

	public void setVipTrainingExp1(int vipTrainingExp1) {
		if (vipTrainingExp1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[vip训练1获得的经验值]vipTrainingExp1的值不得小于0");
		}
		this.vipTrainingExp1 = vipTrainingExp1;
	}
	
	public int getVipTrainingExp2() {
		return this.vipTrainingExp2;
	}

	public void setVipTrainingExp2(int vipTrainingExp2) {
		if (vipTrainingExp2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[vip训练2获得的经验值]vipTrainingExp2的值不得小于0");
		}
		this.vipTrainingExp2 = vipTrainingExp2;
	}
	
	public int getVipTrainingExp3() {
		return this.vipTrainingExp3;
	}

	public void setVipTrainingExp3(int vipTrainingExp3) {
		if (vipTrainingExp3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[vip训练3获得的经验值]vipTrainingExp3的值不得小于0");
		}
		this.vipTrainingExp3 = vipTrainingExp3;
	}
	
	public int getVipTrainingExp4() {
		return this.vipTrainingExp4;
	}

	public void setVipTrainingExp4(int vipTrainingExp4) {
		if (vipTrainingExp4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[vip训练4获得的经验值]vipTrainingExp4的值不得小于0");
		}
		this.vipTrainingExp4 = vipTrainingExp4;
	}
	

	@Override
	public String toString() {
		return "TrainingTemplateVO[roleLevel=" + roleLevel + ",normalTrainingExpUnit=" + normalTrainingExpUnit + ",normalTrainingCost1=" + normalTrainingCost1 + ",normalTrainingCost2=" + normalTrainingCost2 + ",normalTrainingCost3=" + normalTrainingCost3 + ",normalTrainingCost4=" + normalTrainingCost4 + ",vipTrainingExp1=" + vipTrainingExp1 + ",vipTrainingExp2=" + vipTrainingExp2 + ",vipTrainingExp3=" + vipTrainingExp3 + ",vipTrainingExp4=" + vipTrainingExp4 + ",]";

	}
}