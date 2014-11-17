package com.hifun.soul.gameserver.activity.question.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 问答活动的问题对象
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class Answer {
	@BeanFieldNumber(number = 1)
	private String content;
	@BeanFieldNumber(number = 2)
	private int coinNum;
	@BeanFieldNumber(number = 3)
	private int exp;	
	@BeanFieldNumber(number = 4)
	private int score;
	@BeanFieldNumber(number = 5)
	private int technologyPoint;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCoinNum() {
		return coinNum;
	}
	public void setCoinNum(int coinNum) {
		this.coinNum = coinNum;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTechnologyPoint() {
		return technologyPoint;
	}
	public void setTechnologyPoint(int technologyPoint) {
		this.technologyPoint = technologyPoint;
	}
}
