package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开启矿坑战斗请求
 *
 * @author SevenSoul
 */
@Component
public class GCOpenMineFieldBattleRequest extends GCMessage{
	
	/** 怪物名称 */
	private String monsterName;
	/** 怪物等级 */
	private int monsterLevel;
	/** 矿坑类型名称 */
	private String mineFieldName;
	/** 购买开采次数消耗货币类型 */
	private int buyMineTimeCostType;
	/** 购买开采次数消耗货币数量 */
	private int buyMineTimeCostNum;

	public GCOpenMineFieldBattleRequest (){
	}
	
	public GCOpenMineFieldBattleRequest (
			String monsterName,
			int monsterLevel,
			String mineFieldName,
			int buyMineTimeCostType,
			int buyMineTimeCostNum ){
			this.monsterName = monsterName;
			this.monsterLevel = monsterLevel;
			this.mineFieldName = mineFieldName;
			this.buyMineTimeCostType = buyMineTimeCostType;
			this.buyMineTimeCostNum = buyMineTimeCostNum;
	}

	@Override
	protected boolean readImpl() {
		monsterName = readString();
		monsterLevel = readInteger();
		mineFieldName = readString();
		buyMineTimeCostType = readInteger();
		buyMineTimeCostNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(monsterName);
		writeInteger(monsterLevel);
		writeString(mineFieldName);
		writeInteger(buyMineTimeCostType);
		writeInteger(buyMineTimeCostNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_MINE_FIELD_BATTLE_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_MINE_FIELD_BATTLE_REQUEST";
	}

	public String getMonsterName(){
		return monsterName;
	}
		
	public void setMonsterName(String monsterName){
		this.monsterName = monsterName;
	}

	public int getMonsterLevel(){
		return monsterLevel;
	}
		
	public void setMonsterLevel(int monsterLevel){
		this.monsterLevel = monsterLevel;
	}

	public String getMineFieldName(){
		return mineFieldName;
	}
		
	public void setMineFieldName(String mineFieldName){
		this.mineFieldName = mineFieldName;
	}

	public int getBuyMineTimeCostType(){
		return buyMineTimeCostType;
	}
		
	public void setBuyMineTimeCostType(int buyMineTimeCostType){
		this.buyMineTimeCostType = buyMineTimeCostType;
	}

	public int getBuyMineTimeCostNum(){
		return buyMineTimeCostNum;
	}
		
	public void setBuyMineTimeCostNum(int buyMineTimeCostNum){
		this.buyMineTimeCostNum = buyMineTimeCostNum;
	}
}