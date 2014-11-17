package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发送攻城怪物信息
 *
 * @author SevenSoul
 */
@Component
public class GCSendMainCityMonsterInfo extends GCMessage{
	
	/** 怪物等级 */
	private int monsterLevel;
	/** 怪物名称 */
	private String monsterName;
	/** 怪物总数 */
	private int totalNum;
	/** 剩余个数 */
	private int remainNum;

	public GCSendMainCityMonsterInfo (){
	}
	
	public GCSendMainCityMonsterInfo (
			int monsterLevel,
			String monsterName,
			int totalNum,
			int remainNum ){
			this.monsterLevel = monsterLevel;
			this.monsterName = monsterName;
			this.totalNum = totalNum;
			this.remainNum = remainNum;
	}

	@Override
	protected boolean readImpl() {
		monsterLevel = readInteger();
		monsterName = readString();
		totalNum = readInteger();
		remainNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(monsterLevel);
		writeString(monsterName);
		writeInteger(totalNum);
		writeInteger(remainNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEND_MAIN_CITY_MONSTER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEND_MAIN_CITY_MONSTER_INFO";
	}

	public int getMonsterLevel(){
		return monsterLevel;
	}
		
	public void setMonsterLevel(int monsterLevel){
		this.monsterLevel = monsterLevel;
	}

	public String getMonsterName(){
		return monsterName;
	}
		
	public void setMonsterName(String monsterName){
		this.monsterName = monsterName;
	}

	public int getTotalNum(){
		return totalNum;
	}
		
	public void setTotalNum(int totalNum){
		this.totalNum = totalNum;
	}

	public int getRemainNum(){
		return remainNum;
	}
		
	public void setRemainNum(int remainNum){
		this.remainNum = remainNum;
	}
}