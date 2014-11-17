package com.hifun.soul.gameserver.training.model;

/**
 *  VIP训练业务模型
 *  
 * @author magicstone
 *
 */
public class VipTrainingInfo {
	/** 训练Id */
	private int trainingId;
	/** vip训练名称 */
	private String trainingName;
	/** 训练类型 */
	private int trainingType;
	/** 花费货币类型 */
	private int costCurrencyType;
	/** 花费货币数量 */
	private int costCurrencyNum;
	/** 开启需要的vip等级 */
	private int openVipLevel;	
	/** 训练所能获得的经验值 */
	private int exp;
	
	public int getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public int getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(int trainingType) {
		this.trainingType = trainingType;
	}
	public int getCostCurrencyType() {
		return costCurrencyType;
	}
	public void setCostCurrencyType(int costCurrencyType) {
		this.costCurrencyType = costCurrencyType;
	}
	public int getCostCurrencyNum() {
		return costCurrencyNum;
	}
	public void setCostCurrencyNum(int costCurrencyNum) {
		this.costCurrencyNum = costCurrencyNum;
	}	
	public int getOpenVipLevel() {
		return openVipLevel;
	}
	public void setOpenVipLevel(int openVipLevel) {
		this.openVipLevel = openVipLevel;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}

}
