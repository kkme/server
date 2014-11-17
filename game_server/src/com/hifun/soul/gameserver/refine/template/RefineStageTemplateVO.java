package com.hifun.soul.gameserver.refine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 试炼关卡模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RefineStageTemplateVO extends TemplateObject {

	/**  试炼地图id */
	@ExcelCellBinding(offset = 1)
	protected int mapId;

	/**  试炼关卡id */
	@ExcelCellBinding(offset = 2)
	protected int stageId;

	/**  试炼关卡怪物id */
	@ExcelCellBinding(offset = 3)
	protected int monsterId;

	/**  首次攻打通过奖励id */
	@ExcelCellBinding(offset = 4)
	protected int firstRewardId;

	/**  首次攻打通过奖励数量 */
	@ExcelCellBinding(offset = 5)
	protected int firstRewardNum;

	/**  攻打掉落奖励id */
	@ExcelCellBinding(offset = 6)
	protected int rewardId;

	/**  攻打掉落奖励数量 */
	@ExcelCellBinding(offset = 7)
	protected int rewardNum;

	/**  攻打掉落概率 */
	@ExcelCellBinding(offset = 8)
	protected int rate;

	/**  攻打奖励灵气 */
	@ExcelCellBinding(offset = 9)
	protected int rewardAura;


	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		if (mapId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 试炼地图id]mapId不可以为0");
		}
		if (mapId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 试炼地图id]mapId的值不得小于1");
		}
		this.mapId = mapId;
	}
	
	public int getStageId() {
		return this.stageId;
	}

	public void setStageId(int stageId) {
		if (stageId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 试炼关卡id]stageId不可以为0");
		}
		if (stageId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 试炼关卡id]stageId的值不得小于1");
		}
		this.stageId = stageId;
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		if (monsterId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 试炼关卡怪物id]monsterId不可以为0");
		}
		if (monsterId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 试炼关卡怪物id]monsterId的值不得小于1");
		}
		this.monsterId = monsterId;
	}
	
	public int getFirstRewardId() {
		return this.firstRewardId;
	}

	public void setFirstRewardId(int firstRewardId) {
		if (firstRewardId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 首次攻打通过奖励id]firstRewardId不可以为0");
		}
		if (firstRewardId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 首次攻打通过奖励id]firstRewardId的值不得小于1");
		}
		this.firstRewardId = firstRewardId;
	}
	
	public int getFirstRewardNum() {
		return this.firstRewardNum;
	}

	public void setFirstRewardNum(int firstRewardNum) {
		if (firstRewardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 首次攻打通过奖励数量]firstRewardNum的值不得小于0");
		}
		this.firstRewardNum = firstRewardNum;
	}
	
	public int getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(int rewardId) {
		if (rewardId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 攻打掉落奖励id]rewardId不可以为0");
		}
		if (rewardId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 攻打掉落奖励id]rewardId的值不得小于1");
		}
		this.rewardId = rewardId;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		if (rewardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 攻打掉落奖励数量]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
	}
	
	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		if (rate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 攻打掉落概率]rate不可以为0");
		}
		if (rate < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 攻打掉落概率]rate的值不得小于1");
		}
		this.rate = rate;
	}
	
	public int getRewardAura() {
		return this.rewardAura;
	}

	public void setRewardAura(int rewardAura) {
		if (rewardAura == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 攻打奖励灵气]rewardAura不可以为0");
		}
		this.rewardAura = rewardAura;
	}
	

	@Override
	public String toString() {
		return "RefineStageTemplateVO[mapId=" + mapId + ",stageId=" + stageId + ",monsterId=" + monsterId + ",firstRewardId=" + firstRewardId + ",firstRewardNum=" + firstRewardNum + ",rewardId=" + rewardId + ",rewardNum=" + rewardNum + ",rate=" + rate + ",rewardAura=" + rewardAura + ",]";

	}
}