package com.hifun.soul.gameserver.training.model;

/**
 * 普通训练的业务实体
 * 
 * @author magicstone
 *
 */
public class NormalTrainingInfo {
	/** 训练Id */
	private int trainingId;
	/** 训练名称 */
	private String trainingName;
	/** 训练类型 */
	private int trainingType;
	/** 训练所需的时间（分钟） */
	private int costTime;
	/** 训练所需的金币数量 */
	private int costCoinNum;
	/** 训练所能获得的经验值 */
	private int exp;
	/** 是否正在训练中 */
	private int trainingState;	
	
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
	public int getCostTime() {
		return costTime;
	}
	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}
	public int getCostCoinNum() {
		return costCoinNum;
	}
	public void setCostCoinNum(int costCoinNum) {
		this.costCoinNum = costCoinNum;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getTrainingState() {
		return trainingState;
	}
	public void setTrainingState(int trainingState) {
		this.trainingState = trainingState;
	}
	
	
}
