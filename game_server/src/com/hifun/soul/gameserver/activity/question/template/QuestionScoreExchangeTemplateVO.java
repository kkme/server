package com.hifun.soul.gameserver.activity.question.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 问答积分兑换模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class QuestionScoreExchangeTemplateVO extends TemplateObject {

	/** 积分线 */
	@ExcelCellBinding(offset = 1)
	protected int score;

	/** 金币数 */
	@ExcelCellBinding(offset = 2)
	protected int coinNum;

	/** 经验值 */
	@ExcelCellBinding(offset = 3)
	protected int exp;

	/** 科技点 */
	@ExcelCellBinding(offset = 4)
	protected int technologyPoint;


	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		if (score == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[积分线]score不可以为0");
		}
		this.score = score;
	}
	
	public int getCoinNum() {
		return this.coinNum;
	}

	public void setCoinNum(int coinNum) {
		if (coinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[金币数]coinNum的值不得小于0");
		}
		this.coinNum = coinNum;
	}
	
	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		if (exp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[经验值]exp的值不得小于0");
		}
		this.exp = exp;
	}
	
	public int getTechnologyPoint() {
		return this.technologyPoint;
	}

	public void setTechnologyPoint(int technologyPoint) {
		if (technologyPoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[科技点]technologyPoint的值不得小于0");
		}
		this.technologyPoint = technologyPoint;
	}
	

	@Override
	public String toString() {
		return "QuestionScoreExchangeTemplateVO[score=" + score + ",coinNum=" + coinNum + ",exp=" + exp + ",technologyPoint=" + technologyPoint + ",]";

	}
}