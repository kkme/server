package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 过关奖励信息
 *
 * @author SevenSoul
 */
@Component
public class GCPassStageReward extends GCMessage{
	
	/** 金币 */
	private int coin;
	/** 地图ID */
	private int mapId;
	/** 奖品1 */
	private com.hifun.soul.gameserver.item.assist.SimpleCommonItem item1;
	/** 物品1数量 */
	private int item1Num;
	/** 奖品2 */
	private com.hifun.soul.gameserver.item.assist.SimpleCommonItem item2;
	/** 物品2数量 */
	private int item2Num;

	public GCPassStageReward (){
	}
	
	public GCPassStageReward (
			int coin,
			int mapId,
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem item1,
			int item1Num,
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem item2,
			int item2Num ){
			this.coin = coin;
			this.mapId = mapId;
			this.item1 = item1;
			this.item1Num = item1Num;
			this.item2 = item2;
			this.item2Num = item2Num;
	}

	@Override
	protected boolean readImpl() {
		coin = readInteger();
		mapId = readInteger();
		item1 = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
						item1.setUUID(readString());
						item1.setItemId(readInteger());
						item1.setName(readString());
						item1.setDesc(readString());
						item1.setIcon(readInteger());
				item1Num = readInteger();
		item2 = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
						item2.setUUID(readString());
						item2.setItemId(readInteger());
						item2.setName(readString());
						item2.setDesc(readString());
						item2.setIcon(readInteger());
				item2Num = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(coin);
		writeInteger(mapId);
		writeString(item1.getUUID());
		writeInteger(item1.getItemId());
		writeString(item1.getName());
		writeString(item1.getDesc());
		writeInteger(item1.getIcon());
		writeInteger(item1Num);
		writeString(item2.getUUID());
		writeInteger(item2.getItemId());
		writeString(item2.getName());
		writeString(item2.getDesc());
		writeInteger(item2.getIcon());
		writeInteger(item2Num);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PASS_STAGE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PASS_STAGE_REWARD";
	}

	public int getCoin(){
		return coin;
	}
		
	public void setCoin(int coin){
		this.coin = coin;
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	public com.hifun.soul.gameserver.item.assist.SimpleCommonItem getItem1(){
		return item1;
	}
		
	public void setItem1(com.hifun.soul.gameserver.item.assist.SimpleCommonItem item1){
		this.item1 = item1;
	}

	public int getItem1Num(){
		return item1Num;
	}
		
	public void setItem1Num(int item1Num){
		this.item1Num = item1Num;
	}

	public com.hifun.soul.gameserver.item.assist.SimpleCommonItem getItem2(){
		return item2;
	}
		
	public void setItem2(com.hifun.soul.gameserver.item.assist.SimpleCommonItem item2){
		this.item2 = item2;
	}

	public int getItem2Num(){
		return item2Num;
	}
		
	public void setItem2Num(int item2Num){
		this.item2Num = item2Num;
	}
}