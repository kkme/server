package com.hifun.soul.gameserver.skill.msg;

/**
 * 魔法变化;
 * 
 * @author crazyjohn
 * 
 */
public class MagicChange {
	/** 红魔变化 */
	private int changeRed;
	/** 黄魔变化 */
	private int changeYellow;
	/** 蓝魔变化 */
	private int changeBlue;
	/** 绿魔变化 */
	private int changeGreen;
	/** 紫魔变化 */
	private int changePurple;

	public int getChangeRed() {
		return changeRed;
	}

	public void setChangeRed(int changeRed) {
		this.changeRed = changeRed;
	}

	public int getChangeYellow() {
		return changeYellow;
	}

	public void setChangeYellow(int changeYellow) {
		this.changeYellow = changeYellow;
	}

	public int getChangeBlue() {
		return changeBlue;
	}

	public void setChangeBlue(int changeBlue) {
		this.changeBlue = changeBlue;
	}

	public int getChangePurple() {
		return changePurple;
	}

	public void setChangePurple(int changePurple) {
		this.changePurple = changePurple;
	}

	public int getChangeGreen() {
		return changeGreen;
	}

	public void setChangeGreen(int changeGreen) {
		this.changeGreen = changeGreen;
	}

	public MagicChange reduce(MagicChange targetAfter) {
		MagicChange result = new MagicChange();
		result.setChangeBlue(this.getChangeBlue() - targetAfter.getChangeBlue());
		result.setChangeGreen(this.getChangeGreen() - targetAfter.getChangeGreen());
		result.setChangeRed(this.getChangeRed() - targetAfter.getChangeRed());
		result.setChangeYellow(this.getChangeYellow() - targetAfter.getChangeYellow());
		result.setChangePurple(this.getChangePurple() - targetAfter.getChangePurple());
		return result;
	}

}
