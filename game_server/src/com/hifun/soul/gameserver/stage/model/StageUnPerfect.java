package com.hifun.soul.gameserver.stage.model;

/**
 * 没有达到满星的关卡;
 * 
 * @author crazyjohn
 * 
 */
public class StageUnPerfect {
	/** 关卡id */
	private int stageId;
	/** 关卡评分 */
	private int star;
	/** 关卡名字 */
	private String stageName;
	/** 是否通过 */
	private boolean pass;

	public boolean getPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
}
