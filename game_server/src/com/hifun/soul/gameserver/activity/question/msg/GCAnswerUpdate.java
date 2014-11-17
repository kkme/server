package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发放问答奖励
 *
 * @author SevenSoul
 */
@Component
public class GCAnswerUpdate extends GCMessage{
	
	/** 当前问答积分 */
	private int score;
	/** 获得的金币 */
	private int coinNum;
	/** 获得的经验 */
	private int exp;
	/** 获得的科技点 */
	private int technologyPoint;
	/** 当前问答积分 */
	private int totalScore;
	/** 提交的答案是否正确 */
	private boolean result;

	public GCAnswerUpdate (){
	}
	
	public GCAnswerUpdate (
			int score,
			int coinNum,
			int exp,
			int technologyPoint,
			int totalScore,
			boolean result ){
			this.score = score;
			this.coinNum = coinNum;
			this.exp = exp;
			this.technologyPoint = technologyPoint;
			this.totalScore = totalScore;
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		score = readInteger();
		coinNum = readInteger();
		exp = readInteger();
		technologyPoint = readInteger();
		totalScore = readInteger();
		result = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(score);
		writeInteger(coinNum);
		writeInteger(exp);
		writeInteger(technologyPoint);
		writeInteger(totalScore);
		writeBoolean(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ANSWER_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ANSWER_UPDATE";
	}

	public int getScore(){
		return score;
	}
		
	public void setScore(int score){
		this.score = score;
	}

	public int getCoinNum(){
		return coinNum;
	}
		
	public void setCoinNum(int coinNum){
		this.coinNum = coinNum;
	}

	public int getExp(){
		return exp;
	}
		
	public void setExp(int exp){
		this.exp = exp;
	}

	public int getTechnologyPoint(){
		return technologyPoint;
	}
		
	public void setTechnologyPoint(int technologyPoint){
		this.technologyPoint = technologyPoint;
	}

	public int getTotalScore(){
		return totalScore;
	}
		
	public void setTotalScore(int totalScore){
		this.totalScore = totalScore;
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
	}
}