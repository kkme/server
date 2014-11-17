package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hifun.soul.core.orm.BaseCommonEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.gamedb.annotation.NotBlobHumanSubEntity;

@Entity
@Table(name = "t_human_question")
@AutoCreateHumanEntityHolder(EntityHolderClass = "ExclusiveEntityHolder")
@NotBlobHumanSubEntity
public class HumanQuestionEntity extends BaseCommonEntity implements
		IHumanSubEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column
	private int id;
	/** 角色id */
	@Column
	private long humanId;
	/** 问答积分总数 */
	@Column
	private int totalScore;
	/** 问题索引 */
	@Column
	private int questionIndex;
	/** 问题Id */
	@Column
	private int questionId;
	/** 可用的祈福次数 */
	@Column
	private int usableBlessNum;
	/** 购买祈福次数 */
	@Column
	private int buyBlessTimes;
	/** 积分兑换情况 */
	@Column
	private String scoreExchangeState;
	/** 上次每日重置时间 */
	@Column
	private long lastDailyResetTime;
	/** 上次每周重置时间 */
	@Column
	private long lastWeeklyResetTime;
	/** 当天已经回答过的问题id */
	@Column
	private String answeredQuestionIds;

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getUsableBlessNum() {
		return usableBlessNum;
	}

	public void setUsableBlessNum(int usableBlessNum) {
		this.usableBlessNum = usableBlessNum;
	}

	public int getBuyBlessTimes() {
		return buyBlessTimes;
	}

	public void setBuyBlessTimes(int buyBlessTimes) {
		this.buyBlessTimes = buyBlessTimes;
	}

	public String getScoreExchangeState() {
		return scoreExchangeState;
	}

	public void setScoreExchangeState(String scoreExchangeState) {
		this.scoreExchangeState = scoreExchangeState;
	}

	public long getLastDailyResetTime() {
		return lastDailyResetTime;
	}

	public void setLastDailyResetTime(long lastDailyResetTime) {
		this.lastDailyResetTime = lastDailyResetTime;
	}

	public long getLastWeeklyResetTime() {
		return lastWeeklyResetTime;
	}

	public void setLastWeeklyResetTime(long lastWeeklyResetTime) {
		this.lastWeeklyResetTime = lastWeeklyResetTime;
	}

	public String getAnsweredQuestionIds() {
		return answeredQuestionIds;
	}

	public void setAnsweredQuestionIds(String answeredQuestionIds) {
		this.answeredQuestionIds = answeredQuestionIds;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer) id;
	}

	@Override
	public long getHumanGuid() {
		return humanId;
	}

}
