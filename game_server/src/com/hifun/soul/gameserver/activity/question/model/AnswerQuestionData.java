package com.hifun.soul.gameserver.activity.question.model;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gamedb.entity.HumanQuestionEntity;

public class AnswerQuestionData {
	/** 实体id */
	private int id;
	/** 角色id */
	private long humanId;
	/** 当前积分 */
	private int totalScore;
	/** 可用免费祈福次数 */
	private int usableBlessNum;
	/** 购买祈福次数 */
	private int buyBlessTimes;
	/** 题目序号 */
	private int questionIndex;
	/** 题目id */
	private int questionId;
	/** 积分兑换状态 */
	private int[] scoreExchangeState;
	/** 上次每日重置时间 */
	private long lastDailyResetTime;
	/** 上次每周重置时间 */
	private long lastWeeklyResetTime;
	/** 当天回答过的问题id */
	private List<Integer> answeredQuestionIds = new ArrayList<Integer>(); 

	public AnswerQuestionData(long humanId){this.humanId=humanId;}
	
	public AnswerQuestionData(HumanQuestionEntity entity){
		this.id = entity.getId();
		this.humanId = entity.getHumanId();
		this.totalScore = entity.getTotalScore();
		this.usableBlessNum = entity.getUsableBlessNum();
		this.buyBlessTimes = entity.getBuyBlessTimes();
		this.questionIndex = entity.getQuestionIndex();
		this.questionId = entity.getQuestionId();
		this.lastDailyResetTime = entity.getLastDailyResetTime();
		this.lastWeeklyResetTime = entity.getLastWeeklyResetTime();
		String[] exchangeState = entity.getScoreExchangeState().split(",");
		this.scoreExchangeState = new int[exchangeState.length];
		for(int i=0;i<exchangeState.length; i++){
			scoreExchangeState[i] = Integer.parseInt(exchangeState[i]);
		}
		if (entity.getAnsweredQuestionIds() != null && entity.getAnsweredQuestionIds().length()>0) {
			String[] answeredIds = entity.getAnsweredQuestionIds().split(",");
			for (String qid : answeredIds) {
				answeredQuestionIds.add(Integer.parseInt(qid));
			}
		}
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int[] getScoreExchangeState() {
		return scoreExchangeState;
	}

	public void setScoreExchangeState(int[] scoreExchangeState) {
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

	public List<Integer> getAnsweredQuestionIds() {
		return answeredQuestionIds;
	}

	public void setAnsweredQuestionIds(List<Integer> answeredQuestionIds) {
		this.answeredQuestionIds = answeredQuestionIds;
	}

	/**
	 * 转换为数据实体
	 * 
	 * @return
	 */
	public HumanQuestionEntity toEntity() {
		HumanQuestionEntity questionEntity = new HumanQuestionEntity();
		questionEntity.setId(id);
		questionEntity.setHumanId(humanId);
		questionEntity.setQuestionIndex(questionIndex);
		questionEntity.setQuestionId(questionId);
		questionEntity.setTotalScore(totalScore);
		questionEntity.setUsableBlessNum(usableBlessNum);
		questionEntity.setBuyBlessTimes(buyBlessTimes);
		questionEntity.setLastDailyResetTime(lastDailyResetTime);
		questionEntity.setLastWeeklyResetTime(lastWeeklyResetTime);
		StringBuilder sb = new StringBuilder();
		if (this.scoreExchangeState != null) {			
			for (int i : scoreExchangeState) {
				sb.append(i);
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			questionEntity.setScoreExchangeState(sb.toString());
		}
		sb.delete(0, sb.length());
		for(Integer qid : this.answeredQuestionIds){			
				sb.append(qid);
				sb.append(",");
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		questionEntity.setAnsweredQuestionIds(sb.toString());
		return questionEntity;
	}
}
