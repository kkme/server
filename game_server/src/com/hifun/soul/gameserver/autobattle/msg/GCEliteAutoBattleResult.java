package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 精英副本扫荡结果
 *
 * @author SevenSoul
 */
@Component
public class GCEliteAutoBattleResult extends GCMessage{
	
	/** 经验 */
	private int exp;
	/** 金币 */
	private int coin;
	/** 科技点 */
	private int techPoint;
	/** 奖励物品列表 */
	private String[] items;

	public GCEliteAutoBattleResult (){
	}
	
	public GCEliteAutoBattleResult (
			int exp,
			int coin,
			int techPoint,
			String[] items ){
			this.exp = exp;
			this.coin = coin;
			this.techPoint = techPoint;
			this.items = items;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		exp = readInteger();
		coin = readInteger();
		techPoint = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		items = new String[count];
		for(int i=0; i<count; i++){
			items[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(exp);
		writeInteger(coin);
		writeInteger(techPoint);
	writeShort(items.length);
	for(int i=0; i<items.length; i++){
	String objitems = items[i];
			writeString(objitems);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ELITE_AUTO_BATTLE_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ELITE_AUTO_BATTLE_RESULT";
	}

	public int getExp(){
		return exp;
	}
		
	public void setExp(int exp){
		this.exp = exp;
	}

	public int getCoin(){
		return coin;
	}
		
	public void setCoin(int coin){
		this.coin = coin;
	}

	public int getTechPoint(){
		return techPoint;
	}
		
	public void setTechPoint(int techPoint){
		this.techPoint = techPoint;
	}

	public String[] getItems(){
		return items;
	}

	public void setItems(String[] items){
		this.items = items;
	}	
}