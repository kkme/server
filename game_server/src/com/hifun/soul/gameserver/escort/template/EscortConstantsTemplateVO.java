package com.hifun.soul.gameserver.escort.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 押运常量模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EscortConstantsTemplateVO extends TemplateObject {

	/** 最大押运次数 */
	@ExcelCellBinding(offset = 1)
	protected int maxEscortNum;

	/** 最大拦截次数 */
	@ExcelCellBinding(offset = 2)
	protected int maxRobNum;

	/** 最大协助次数 */
	@ExcelCellBinding(offset = 3)
	protected int maxHelpNum;

	/** 每次押运最大被拦截次数 */
	@ExcelCellBinding(offset = 4)
	protected int maxBeRobbedNum;

	/** 拦截CD时间 */
	@ExcelCellBinding(offset = 5)
	protected int robCdTime;

	/** 召唤最高品质怪物vip开启等级 */
	@ExcelCellBinding(offset = 6)
	protected int callVipLevel;

	/** 召唤最高品质怪物消费 */
	@ExcelCellBinding(offset = 7)
	protected int callCost;

	/** 鼓舞攻击力加成 */
	@ExcelCellBinding(offset = 8)
	protected int encourageAttackRate;

	/** 鼓舞生命值加成 */
	@ExcelCellBinding(offset = 9)
	protected int encourageHpRate;

	/** 鼓舞消费 */
	@ExcelCellBinding(offset = 10)
	protected int encourageCost;

	/** 鼓舞加成方式 */
	@ExcelCellBinding(offset = 11)
	protected int encourageAmendMethod;

	/**  押运收益加成固定时间段 */
	@ExcelCellBinding(offset = 12)
	protected String revenueAmendTime;

	/** 固定时间段收益加成 */
	@ExcelCellBinding(offset = 13)
	protected int someTimeRevenueRate;

	/** 军团祈福vip开启等级 */
	@ExcelCellBinding(offset = 14)
	protected int legionPrayVipLevel;

	/** 军团祈福消费 */
	@ExcelCellBinding(offset = 15)
	protected int legionPrayCost;

	/** 最大祈福次数 */
	@ExcelCellBinding(offset = 16)
	protected int maxLegionPrayNum;

	/** 祈福加成 */
	@ExcelCellBinding(offset = 17)
	protected int legionPrayRate;

	/** 祈福时长 */
	@ExcelCellBinding(offset = 18)
	protected int legionPrayMinutes;

	/** 祈福增加军团贡献 */
	@ExcelCellBinding(offset = 19)
	protected int legionPrayContribution;

	/** 每页显示怪物数量 */
	@ExcelCellBinding(offset = 20)
	protected int pageMonsterSize;

	/** 每页显示跑道数量 */
	@ExcelCellBinding(offset = 21)
	protected int pageRoadNum;

	/** 邀请同意有效时间 */
	@ExcelCellBinding(offset = 22)
	protected int agreeValidTime;


	public int getMaxEscortNum() {
		return this.maxEscortNum;
	}

	public void setMaxEscortNum(int maxEscortNum) {
		if (maxEscortNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[最大押运次数]maxEscortNum不可以为0");
		}
		this.maxEscortNum = maxEscortNum;
	}
	
	public int getMaxRobNum() {
		return this.maxRobNum;
	}

	public void setMaxRobNum(int maxRobNum) {
		if (maxRobNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[最大拦截次数]maxRobNum不可以为0");
		}
		this.maxRobNum = maxRobNum;
	}
	
	public int getMaxHelpNum() {
		return this.maxHelpNum;
	}

	public void setMaxHelpNum(int maxHelpNum) {
		if (maxHelpNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[最大协助次数]maxHelpNum不可以为0");
		}
		this.maxHelpNum = maxHelpNum;
	}
	
	public int getMaxBeRobbedNum() {
		return this.maxBeRobbedNum;
	}

	public void setMaxBeRobbedNum(int maxBeRobbedNum) {
		if (maxBeRobbedNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[每次押运最大被拦截次数]maxBeRobbedNum不可以为0");
		}
		this.maxBeRobbedNum = maxBeRobbedNum;
	}
	
	public int getRobCdTime() {
		return this.robCdTime;
	}

	public void setRobCdTime(int robCdTime) {
		if (robCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[拦截CD时间]robCdTime不可以为0");
		}
		this.robCdTime = robCdTime;
	}
	
	public int getCallVipLevel() {
		return this.callVipLevel;
	}

	public void setCallVipLevel(int callVipLevel) {
		if (callVipLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[召唤最高品质怪物vip开启等级]callVipLevel不可以为0");
		}
		this.callVipLevel = callVipLevel;
	}
	
	public int getCallCost() {
		return this.callCost;
	}

	public void setCallCost(int callCost) {
		if (callCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[召唤最高品质怪物消费]callCost不可以为0");
		}
		this.callCost = callCost;
	}
	
	public int getEncourageAttackRate() {
		return this.encourageAttackRate;
	}

	public void setEncourageAttackRate(int encourageAttackRate) {
		if (encourageAttackRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[鼓舞攻击力加成]encourageAttackRate不可以为0");
		}
		this.encourageAttackRate = encourageAttackRate;
	}
	
	public int getEncourageHpRate() {
		return this.encourageHpRate;
	}

	public void setEncourageHpRate(int encourageHpRate) {
		if (encourageHpRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[鼓舞生命值加成]encourageHpRate不可以为0");
		}
		this.encourageHpRate = encourageHpRate;
	}
	
	public int getEncourageCost() {
		return this.encourageCost;
	}

	public void setEncourageCost(int encourageCost) {
		if (encourageCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[鼓舞消费]encourageCost不可以为0");
		}
		this.encourageCost = encourageCost;
	}
	
	public int getEncourageAmendMethod() {
		return this.encourageAmendMethod;
	}

	public void setEncourageAmendMethod(int encourageAmendMethod) {
		if (encourageAmendMethod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[鼓舞加成方式]encourageAmendMethod不可以为0");
		}
		this.encourageAmendMethod = encourageAmendMethod;
	}
	
	public String getRevenueAmendTime() {
		return this.revenueAmendTime;
	}

	public void setRevenueAmendTime(String revenueAmendTime) {
		if (StringUtils.isEmpty(revenueAmendTime)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 押运收益加成固定时间段]revenueAmendTime不可以为空");
		}
		this.revenueAmendTime = revenueAmendTime;
	}
	
	public int getSomeTimeRevenueRate() {
		return this.someTimeRevenueRate;
	}

	public void setSomeTimeRevenueRate(int someTimeRevenueRate) {
		if (someTimeRevenueRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[固定时间段收益加成]someTimeRevenueRate不可以为0");
		}
		this.someTimeRevenueRate = someTimeRevenueRate;
	}
	
	public int getLegionPrayVipLevel() {
		return this.legionPrayVipLevel;
	}

	public void setLegionPrayVipLevel(int legionPrayVipLevel) {
		if (legionPrayVipLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[军团祈福vip开启等级]legionPrayVipLevel不可以为0");
		}
		this.legionPrayVipLevel = legionPrayVipLevel;
	}
	
	public int getLegionPrayCost() {
		return this.legionPrayCost;
	}

	public void setLegionPrayCost(int legionPrayCost) {
		if (legionPrayCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[军团祈福消费]legionPrayCost不可以为0");
		}
		this.legionPrayCost = legionPrayCost;
	}
	
	public int getMaxLegionPrayNum() {
		return this.maxLegionPrayNum;
	}

	public void setMaxLegionPrayNum(int maxLegionPrayNum) {
		if (maxLegionPrayNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[最大祈福次数]maxLegionPrayNum不可以为0");
		}
		this.maxLegionPrayNum = maxLegionPrayNum;
	}
	
	public int getLegionPrayRate() {
		return this.legionPrayRate;
	}

	public void setLegionPrayRate(int legionPrayRate) {
		if (legionPrayRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[祈福加成]legionPrayRate不可以为0");
		}
		this.legionPrayRate = legionPrayRate;
	}
	
	public int getLegionPrayMinutes() {
		return this.legionPrayMinutes;
	}

	public void setLegionPrayMinutes(int legionPrayMinutes) {
		if (legionPrayMinutes == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[祈福时长]legionPrayMinutes不可以为0");
		}
		this.legionPrayMinutes = legionPrayMinutes;
	}
	
	public int getLegionPrayContribution() {
		return this.legionPrayContribution;
	}

	public void setLegionPrayContribution(int legionPrayContribution) {
		if (legionPrayContribution == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[祈福增加军团贡献]legionPrayContribution不可以为0");
		}
		this.legionPrayContribution = legionPrayContribution;
	}
	
	public int getPageMonsterSize() {
		return this.pageMonsterSize;
	}

	public void setPageMonsterSize(int pageMonsterSize) {
		if (pageMonsterSize == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[每页显示怪物数量]pageMonsterSize不可以为0");
		}
		this.pageMonsterSize = pageMonsterSize;
	}
	
	public int getPageRoadNum() {
		return this.pageRoadNum;
	}

	public void setPageRoadNum(int pageRoadNum) {
		if (pageRoadNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[每页显示跑道数量]pageRoadNum不可以为0");
		}
		this.pageRoadNum = pageRoadNum;
	}
	
	public int getAgreeValidTime() {
		return this.agreeValidTime;
	}

	public void setAgreeValidTime(int agreeValidTime) {
		if (agreeValidTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[邀请同意有效时间]agreeValidTime不可以为0");
		}
		this.agreeValidTime = agreeValidTime;
	}
	

	@Override
	public String toString() {
		return "EscortConstantsTemplateVO[maxEscortNum=" + maxEscortNum + ",maxRobNum=" + maxRobNum + ",maxHelpNum=" + maxHelpNum + ",maxBeRobbedNum=" + maxBeRobbedNum + ",robCdTime=" + robCdTime + ",callVipLevel=" + callVipLevel + ",callCost=" + callCost + ",encourageAttackRate=" + encourageAttackRate + ",encourageHpRate=" + encourageHpRate + ",encourageCost=" + encourageCost + ",encourageAmendMethod=" + encourageAmendMethod + ",revenueAmendTime=" + revenueAmendTime + ",someTimeRevenueRate=" + someTimeRevenueRate + ",legionPrayVipLevel=" + legionPrayVipLevel + ",legionPrayCost=" + legionPrayCost + ",maxLegionPrayNum=" + maxLegionPrayNum + ",legionPrayRate=" + legionPrayRate + ",legionPrayMinutes=" + legionPrayMinutes + ",legionPrayContribution=" + legionPrayContribution + ",pageMonsterSize=" + pageMonsterSize + ",pageRoadNum=" + pageRoadNum + ",agreeValidTime=" + agreeValidTime + ",]";

	}
}